package com.peach.masktime.test;

import android.content.Context;
import android.util.Log;

import com.peach.masktime.BaseApplication;
import com.peach.masktime.db.DBManager;
import com.peach.masktime.db.Note;
import com.peach.masktime.db.NoteDao;

import java.util.List;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
public class DBService {
    private static final String TAG = DBService.class.getSimpleName();
    private static DBService sINSTANTCE;
    private static Context sCtx;
    private NoteDao noteDao;

    private DBService() {
    }

    public static synchronized DBService getInstance() {
        if (sINSTANTCE == null) {
            sINSTANTCE = new DBService();
        }
        return sINSTANTCE;
    }

    public void init(Context context) {
        if (!(context instanceof BaseApplication)) {
            throw new AssertionError();
        }
        sCtx = context;
        noteDao = DBManager.getInstance().getNoteDao();
    }


    public Note loadNote(long id) {
        return noteDao.load(id);
    }

    public List<Note> loadAllNote() {
        return noteDao.loadAll();
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
        return noteDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     *
     * @param note
     * @return insert or update note id
     */
    public long saveNote(Note note) {
        return noteDao.insertOrReplace(note);
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
        noteDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    Note note = list.get(i);
                    noteDao.insertOrReplace(note);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllNote() {
        noteDao.deleteAll();
    }

    /**
     * delete note by id
     *
     * @param id
     */
    public void deleteNote(long id) {
        noteDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(Note note) {
        noteDao.delete(note);
    }
}
