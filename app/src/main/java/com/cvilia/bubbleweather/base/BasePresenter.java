package com.cvilia.bubbleweather.base;

import com.cvilia.bubbleweather.IPresenter;
import com.cvilia.bubbleweather.IView;


public class BasePresenter<T extends IView> implements IPresenter<T> {

    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
