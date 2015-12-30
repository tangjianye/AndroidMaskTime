package com.peach.masktime.test;

import android.content.Context;

import com.peach.masktime.db.DBManager;
import com.peach.masktime.db.RecordDao;

/**
 * DBManager 数据库管理类<br>
 * Created by tangjy on 2015/3/2.
 */
public class DBHelper {
    private static final String TAG = DBHelper.class.getSimpleName();

    private static Context sCtx;
    private static DBHelper sINSTANTCE;

    // private SQLiteDatabase mDb;
    // private DaoMaster mDaoMaster;
    // private DaoSession mDaoSession;
    private RecordDao mRecordDao;

    private DBHelper() {
        mRecordDao = DBManager.getInstance().getRecordDao();
    }

    public static synchronized DBHelper getInstance() {
        if (sINSTANTCE == null) {
            sINSTANTCE = new DBHelper();
        }
        return sINSTANTCE;
    }

//    public void init(Context context) {
//        if (!(context instanceof BaseApplication)) {
//            throw new AssertionError();
//        }
//        sCtx = context;
//
//        mRecordDao = DBManager.getInstance().getRecordDao();
//    }

    public RecordDao getRecordDao() {
        return mRecordDao;
    }
}
