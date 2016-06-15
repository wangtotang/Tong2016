package com.tong.tong2016.util;

import android.text.TextUtils;
import android.util.Pair;

import com.tong.tong2016.bean.db.News;
import com.tong.tong2016.bean.json.Article;
import com.tong.tong2016.bean.json.Question;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 2016/6/8.
 */
public class ParseArticleUtil {

    public static Document getArticleDocument(String body) {
        return body != null ? Jsoup.parse(body) : null;
    }

    public static  Pair<Article, Document> createPair(Article article, Document document) {
        return Pair.create(article, document);
    }

    public static News convertToNews(Pair<Article, Document> pair) {
        News result = new News();
        Article article = pair.first;
        Document document = pair.second;
        String title = article.getTitle();
        result.setIds(article.getId());
        result.setImage(article.getImage());
        result.setTitle(title);
        if(document!=null){
            List<Question> questions = getQuestions(document, title);
            if(questions.size()>1){
                result.setTitle("这里包含多个问题讨论，请点击后选择");
                result.setQuestion(title);
            }else{
                result.setQuestion(questions.get(0).getTitle());
            }
        }else{
            result.setQuestion(title);
        }
        return result;
    }

    private static List<Question> getQuestions(Document document, String dailyTitle) {
        List<Question> result = new ArrayList<>();
        Elements questionElements = getQuestionElements(document);

        for (Element questionElement : questionElements) {
            Question question = new Question();

            String questionTitle = getQuestionTitleFromQuestionElement(questionElement);
            String questionUrl = getQuestionUrlFromQuestionElement(questionElement);
            // 确保问题不为空.
            questionTitle = TextUtils.isEmpty(questionTitle) ? dailyTitle : questionTitle;

            question.setTitle(questionTitle);
            question.setUrl(questionUrl);

            result.add(question);
        }

        return result;
    }

    private static Elements getQuestionElements(Document document) {
        return document.select("div.question");
    }

    private static String getQuestionTitleFromQuestionElement(Element questionElement) {
        Element questionTitleElement = questionElement.select("h2.question-title").first();

        if (questionTitleElement == null) {
            return null;
        } else {
            return questionTitleElement.text();
        }
    }

    private static String getQuestionUrlFromQuestionElement(Element questionElement) {
        Element viewMoreElement = questionElement.select("div.view-more a").first();

        if (viewMoreElement == null) {
            return null;
        } else {
            return viewMoreElement.attr("href");
        }
    }

}
