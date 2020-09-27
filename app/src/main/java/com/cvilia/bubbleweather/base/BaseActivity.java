package com.cvilia.bubbleweather.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cvilia.bubbleweather.ActivityManager;
import com.cvilia.bubbleweather.IView;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.ButterKnife;
import me.jessyan.autosize.internal.CustomAdapt;

import static com.cvilia.bubbleweather.base.BaseApplication.app;


public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IView, CustomAdapt {

    protected Activity mContext;

    protected T mPresenter;

    private boolean isFullScreen = true;

    public LocalChangedBroadcastReceiver mLocalChangedReceiver;//用于监听系统语言切换

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            ButterKnife.bind(this);

        }

        mLocalChangedReceiver = new LocalChangedBroadcastReceiver();

        mContext = this;

        ActivityManager.getInstance().addActivity(this);
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        if (registerEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getIntentData();
        initWidget();
        onViewCreated();
        initWidgetEvent();
        initData();

    }


    protected void onViewCreated() {
        StatusBarUtil.setTranslucent(mContext, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setDarkMode(this);

        }
    }

    protected abstract void initWidget();

    protected abstract void initWidgetEvent();

    protected abstract void initData();

    protected abstract void getIntentData();

    private boolean registerEventBus() {
        return false;
    }


    protected abstract int getLayoutId();

    protected abstract T getPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 360;
    }

    /**
     * 监听系统语言切换
     */
    public static class LocalChangedBroadcastReceiver extends BroadcastReceiver {

        public LocalChangedBroadcastReceiver() {
            register();
        }

        public void unRegister() {
            app.unregisterReceiver(this);
        }

        private void register() {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_LOCALE_CHANGED);
            app.registerReceiver(this, filter);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), Intent.ACTION_LOCALE_CHANGED)) {
                Toast.makeText(app, "监听到系统语言切换", Toast.LENGTH_LONG).show();
                if (ActivityManager.getInstance().getActivitySize() > 0) {
                    ActivityManager.getInstance().removeAllActivity();
                    System.exit(0);
                }
            }
        }
    }

}
