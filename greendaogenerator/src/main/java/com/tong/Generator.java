package com.tong;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.tong.tong2016");

        addNews(schema);
        new DaoGenerator().generateAll(schema,"./app/src/main/java");
    }

    private static void addNews(Schema schema){
        Entity news = schema.addEntity("News");
        news.addIdProperty();
        news.addStringProperty("question").notNull();
        news.addStringProperty("title");
        news.addStringProperty("image");
        news.addStringProperty("ids").notNull();
    }

}
