package com.claudiodegio.dbsync.sample;

import android.app.Application;

import com.claudiodegio.dbsync.sample.db1.Db1OpenHelper;
import com.claudiodegio.dbsync.sample.db2.Db2OpenHelper;
import com.claudiodegio.dbsync.sample.db3.Db3OpenHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;


public class DbSyncApplication extends Application {
    static private Logger log;

    public Db1OpenHelper db1OpenHelper;
    public Db2OpenHelper db2OpenHelper;
    public Db3OpenHelper db3OpenHelper;



    @Override
    public void onCreate() {
        super.onCreate();

        StaticLoggerBinder.init(this);
        log = LoggerFactory.getLogger(DbSyncApplication.class);

        log.info("onCreate");

        db1OpenHelper = new Db1OpenHelper(this);
        db1OpenHelper.getReadableDatabase();

        db2OpenHelper = new Db2OpenHelper(this);
        db2OpenHelper.getReadableDatabase();

        db3OpenHelper = new Db3OpenHelper(this);
        db3OpenHelper.getReadableDatabase();
    }
}
