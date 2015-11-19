package com.peach.masktime.module.net.response;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/19 0019.
 */
public class BannerSet {
    private int errno;
    private String err;
    private ArrayList<BannerItem> rsm;

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

    public ArrayList<BannerItem> getRsm() {
        return rsm;
    }

    public void setRsm(ArrayList<BannerItem> rsm) {
        this.rsm = rsm;
    }

    @Override
    public String toString() {
        return "BannerSet{" +
                "err='" + err + '\'' +
                ", errno=" + errno +
                ", rsm=" + rsm +
                '}';
    }
}
