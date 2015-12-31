package com.peach.masktime.ui.beans;

import com.peach.masktime.db.Record;

/**
 * Entity mapped to table RECORD.
 */
public class RecordBean extends Record {
    private boolean isFirstData;

    public RecordBean() {
    }

    public RecordBean(Long id, String title, String content, String path01, String path02, String path03, Long date, boolean isFirstData) {
        super(id, title, content, path01, path02, path03, date);
        this.isFirstData = isFirstData;
    }

    public boolean isFirstData() {
        return isFirstData;
    }

    public void setIsFirstData(boolean isFirstData) {
        this.isFirstData = isFirstData;
    }

    @Override
    public String toString() {
        return "RecordBean{" +
                "isFirstData=" + isFirstData +
                '}';
    }
}
