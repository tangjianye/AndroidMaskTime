package com.peach.masktime.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table RECORD.
 */
public class Record {

    private Long id;
    /** Not-null value. */
    private String title;
    private String content;
    private String path01;
    private String path02;
    private String path03;
    private Long date;

    public Record() {
    }

    public Record(Long id) {
        this.id = id;
    }

    public Record(Long id, String title, String content, String path01, String path02, String path03, Long date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.path01 = path01;
        this.path02 = path02;
        this.path03 = path03;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getTitle() {
        return title;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath01() {
        return path01;
    }

    public void setPath01(String path01) {
        this.path01 = path01;
    }

    public String getPath02() {
        return path02;
    }

    public void setPath02(String path02) {
        this.path02 = path02;
    }

    public String getPath03() {
        return path03;
    }

    public void setPath03(String path03) {
        this.path03 = path03;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

}