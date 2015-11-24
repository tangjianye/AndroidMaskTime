package com.peach.masktime.module.net.response;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/19 0019.
 */
public class AlbumSet {
    private int errno;
    private String err;
    private ArrayList<AlbumItem> rsm;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public ArrayList<AlbumItem> getRsm() {
        return rsm;
    }

    public void setRsm(ArrayList<AlbumItem> rsm) {
        this.rsm = rsm;
    }

    @Override
    public String toString() {
        return "AlbumSet{" +
                "err='" + err + '\'' +
                ", errno=" + errno +
                ", rsm=" + rsm +
                '}';
    }
}
