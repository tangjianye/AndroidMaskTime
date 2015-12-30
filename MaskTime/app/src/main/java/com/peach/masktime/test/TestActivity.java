package com.peach.masktime.test;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.peach.masktime.R;
import com.peach.masktime.db.DbManager;
import com.peach.masktime.db.Note;
import com.peach.masktime.db.NoteDao;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/16 0016.
 */
public class TestActivity extends ListActivity {
    private static final String TAG = TestActivity.class.getSimpleName();
    private final boolean DEBUG = true;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        /********************************************************************/
//        setContentView(R.layout.activity_test);
//
////        CircleSeekBar circleSeekBar = (CircleSeekBar) findViewById(R.id.circle_seekbar);
////        // circleSeekBar.setProgressMax(100);
////        circleSeekBar.setProgress(10);
////        circleSeekBar.setOnSeekBarChangeListener(new CircleSeekBarOnChangeListener());
//
////        CircleSeekBar circleSeekBar2 = new CircleSeekBar(this);
////        circleSeekBar2.setProgress(10);
////        circleSeekBar2.setProgressFrontColor(Color.RED);
////        circleSeekBar2.setProgressThumb(R.drawable.thumb_circle_skbar);
////        circleSeekBar2.setOnSeekBarChangeListener(new CircleSeekBarOnChangeListener());
////        RelativeLayout.LayoutParams circleSeekBarParams = new RelativeLayout.LayoutParams(200, 200);
////        circleSeekBarParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
////        mainLayout.addView(circleSeekBar2, circleSeekBarParams);
//
//
//        /********************************************************************/
////        CircularSeekBar circularSeekbar = (CircularSeekBar) findViewById(R.id.circular_seekbar);
////        circularSeekbar.setMaxProgress(100);
////        circularSeekbar.setProgress(50);
////        circularSeekbar.hideSeekBar();
////        // setContentView(circularSeekbar);
////        circularSeekbar.invalidate();
////
////        circularSeekbar.setSeekBarChangeListener(new CircularSeekBar.OnSeekChangeListener() {
////            @Override
////            public void onProgressChange(CircularSeekBar view, int newProgress) {
////                LogUtils.i(TAG, "Progress:" + view.getProgress() + "/" + view.getMaxProgress());
////            }
////        });
//
//        /********************************************************************/
//    }

//    private class CircleSeekBarOnChangeListener implements OnSeekBarChangeListener {
//
//        @Override
//        public void onProgressChanged(int progress) {
//            if (DEBUG) Log.d(TAG, "onProgressChanged progress = " + progress);
//        }
//
//        @Override
//        public void onStartTrackingTouch() {
//            if (DEBUG) Log.d(TAG, "onStartTrackingTouch");
//        }
//
//        @Override
//        public void onStopTrackingTouch() {
//            if (DEBUG) Log.d(TAG, "onStopTrackingTouch");
//        }
//    }

    private SQLiteDatabase db;

    private EditText editText;

//    private DaoMaster daoMaster;
//    private DaoSession daoSession;
    private NoteDao noteDao;

    private Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

//        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
//        db = helper.getWritableDatabase();
//        daoMaster = new DaoMaster(db);
//        daoSession = daoMaster.newSession();
//        noteDao = daoSession.getNoteDao();
        db = DbManager.getInstance().getDb();
        noteDao = DbManager.getInstance().getNoteDao();

        String textColumn = NoteDao.Properties.Text.columnName;
        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
        cursor = db.query(noteDao.getTablename(), noteDao.getAllColumns(), null, null, null, null, orderBy);
        String[] from = {textColumn, NoteDao.Properties.Comment.columnName};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from,
                to);
        setListAdapter(adapter);

        editText = (EditText) findViewById(R.id.editTextNote);
        addUiListeners();
    }

    protected void addUiListeners() {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addNote();
                    return true;
                }
                return false;
            }
        });

        final View button = findViewById(R.id.buttonAdd);
        button.setEnabled(false);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = s.length() != 0;
                button.setEnabled(enable);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void onMyButtonClick(View view) {
        addNote();
    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());
        Note note = new Note(null, noteText, comment, new Date());
        noteDao.insert(note);
        Log.d("DaoExample", "Inserted new note, ID: " + note.getId());

        cursor.requery();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        noteDao.deleteByKey(id);
        Log.d("DaoExample", "Deleted note, ID: " + id);
        cursor.requery();
    }
}
