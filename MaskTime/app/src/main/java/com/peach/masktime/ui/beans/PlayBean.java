package com.peach.masktime.ui.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/17 0017.
 */
public class PlayBean implements Serializable {
    public int msg;
    public String path;

    public PlayBean(int msg, String path) {
        this.msg = msg;
        this.path = path;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "PlayBean{" +
                "msg=" + msg +
                ", path='" + path + '\'' +
                '}';
    }
}
