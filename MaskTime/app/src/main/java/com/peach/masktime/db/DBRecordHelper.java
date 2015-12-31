package com.peach.masktime.db;

import android.content.Context;

import java.util.List;

import de.greenrobot.dao.query.Query;

/**
 * DBHelper 数据库管理类<br> 负责具体的表的添删改查操作。
 * Created by tangjy on 2015/3/2.
 */
public class DBRecordHelper {
    private static final String TAG = DBRecordHelper.class.getSimpleName();
    private static Context sCtx;
    private static DBRecordHelper sINSTANTCE;
    private RecordDao mRecordDao;

    private DBRecordHelper() {
        mRecordDao = DBManager.getInstance().getRecordDao();
    }

    public static synchronized DBRecordHelper getInstance() {
        if (sINSTANTCE == null) {
            sINSTANTCE = new DBRecordHelper();
        }
        return sINSTANTCE;
    }

    public RecordDao getRecordDao() {
        return mRecordDao;
    }

    /**********************************************************************************************/
    public Record load(long id) {
        return mRecordDao.load(id);
    }

    public List<Record> loadAll() {
        return mRecordDao.loadAll();
    }

    public List<Record> loadAllByDate() {
        Query<Record> query = mRecordDao
                .queryBuilder()
                .orderAsc(RecordDao.Properties.Date)
                .build();
        return query.list();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     *
     * @param where  where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<Record> queryNote(String where, String... params) {
        return mRecordDao.queryRaw(where, params);
    }

    /**
     * insert or update note
     *
     * @param record
     * @return insert or update record id
     */
    public long save(Record record) {
        return mRecordDao.insertOrReplace(record);
    }


    /**
     * insert or update noteList use transaction
     *
     * @param list
     */
    public void save(final List<Record> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        mRecordDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    Record record = list.get(i);
                    mRecordDao.insertOrReplace(record);
                }
            }
        });
    }

    /**
     * delete all
     */
    public void deleteAll() {
        mRecordDao.deleteAll();
    }
}
