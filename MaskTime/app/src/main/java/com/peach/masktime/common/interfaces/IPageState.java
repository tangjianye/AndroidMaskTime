package com.peach.masktime.common.interfaces;

public interface IPageState {
    void init();

    void show(boolean isAnimte);

    void hide(boolean isAnimte);

    void refresh();

    void resume();

    void pause();

    void destroy();
}