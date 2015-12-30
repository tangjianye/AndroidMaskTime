package com.peach.masktime.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.peach.masktime.BaseApplication;
import com.peach.masktime.config.GlobalSetting;

/**
 * DbManager 数据库管理类<br>
 * Created by tangjy on 2015/3/2.
 */
public class DbManager {
    private static final String TAG = DbManager.class.getSimpleName();

    private static Context sCtx;
    private static DbManager sINSTANTCE;
    private SQLiteDatabase mDb;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private NoteDao mNoteDao;

    private DbManager() {
    }

    public static synchronized DbManager getInstance() {
        if (sINSTANTCE == null) {
            sINSTANTCE = new DbManager();
        }
        return sINSTANTCE;
    }

    public void init(Context context) {
        if (!(context instanceof BaseApplication)) {
            throw new AssertionError();
        }
        sCtx = context;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(sCtx, GlobalSetting.DATABASE_NAME, null);
        mDb = helper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDb);
        mDaoSession = mDaoMaster.newSession();

        mNoteDao = mDaoSession.getNoteDao();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }

    public NoteDao getNoteDao() {
        return mNoteDao;
    }
}
