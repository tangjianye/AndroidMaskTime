package com.peach.masktime.common.interfaces;

public interface ICycle {
    void init();

    void show(Object obj);

    void hide(Object obj);

    void refresh();

    void resume();

    void pause();

    void destroy();
}