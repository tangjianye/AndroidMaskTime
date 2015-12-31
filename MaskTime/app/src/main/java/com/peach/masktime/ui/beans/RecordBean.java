package com.peach.masktime.ui.beans;

import com.peach.masktime.db.Record;

/**
 * Entity mapped to table RECORD.
 */
public class RecordBean extends Record {
    private boolean isFirstDay;
    private int count;

    public RecordBean() {
    }

    public RecordBean(Long id, String title, String content, String path01, String path02, String path03, Long date, int count, boolean isFirstDay) {
        super(id, title, content, path01, path02, path03, date);
        this.count = count;
        this.isFirstDay = isFirstDay;
    }

    public RecordBean(Long id, String title, String content, String path01, String path02, String path03, Long date, boolean isFirstDay) {
        this(id, title, content, path01, path02, path03, date, 0, isFirstDay);
    }

    public boolean isFirstDay() {
        return isFirstDay;
    }

    public void setIsFirstDay(boolean isFirstData) {
        this.isFirstDay = isFirstData;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "RecordBean{" +
                "count=" + count +
                ", isFirstDay=" + isFirstDay +
                '}';
    }
}
