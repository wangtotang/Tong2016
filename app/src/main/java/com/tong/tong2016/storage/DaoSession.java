package com.tong.tong2016.storage;

import android.database.sqlite.SQLiteDatabase;

import com.tong.tong2016.bean.db.News;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig newsDaoConfig;

    private final NewsDao newsDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        newsDaoConfig = daoConfigMap.get(NewsDao.class).clone();
        newsDaoConfig.initIdentityScope(type);

        newsDao = new NewsDao(newsDaoConfig, this);

        registerDao(News.class, newsDao);
    }
    
    public void clear() {
        newsDaoConfig.getIdentityScope().clear();
    }

    public NewsDao getNewsDao() {
        return newsDao;
    }

}
