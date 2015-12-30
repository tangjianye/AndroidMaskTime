package com.peach.masktime.test;

import android.content.Context;
import android.util.Log;

import com.peach.masktime.db.DBManager;
import com.peach.masktime.db.Note;
import com.peach.masktime.db.NoteDao;
import com.peach.masktime.db.RecordDao;

import java.util.List;

/**
 * DBHelper 数据库管理类<br>
 * Created by tangjy on 2015/3/2.
 */
public class DBHelper {
    private static final String TAG = DBHelper.class.getSimpleName();
    private static Context sCtx;
    private static DBHelper sINSTANTCE;
    private NoteDao mNoteDao;
    private RecordDao mRecordDao;

    private DBHelper() {
        mNoteDao = DBManager.getInstance().getNoteDao();
        mRecordDao = DBManager.getInstance().getRecordDao();
    }

    public static synchronized DBHelper getInstance() {
        if (sINSTANTCE == null) {
            sINSTANTCE = new DBHelper();
        }
        return sINSTANTCE;
    }

    public RecordDao getRecordDao() {
        return mRecordDao;
    }

    public NoteDao getNoteDao() {
        return mNoteDao;
    }

    /**********************************************************************************************/
    public Note loadNote(long id) {
        return mNoteDao.load(id);
    }

    public List<Note> loadAllNote() {
        return mNoteDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     *
     * @param where  where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<Note> queryNote(String where, String... params) {
        return mNoteDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     *
     * @param note
     * @return insert or update note id
     */
    public long saveNote(Note note) {
        return mNoteDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     *
     * @param list
     */
    public void saveNoteLists(final List<Note> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        mNoteDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    Note note = list.get(i);
                    mNoteDao.insertOrReplace(note);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllNote() {
        mNoteDao.deleteAll();
    }

    /**
     * delete note by id
     *
     * @param id
     */
    public void deleteNote(long id) {
        mNoteDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(Note note) {
        mNoteDao.delete(note);
    }
}
