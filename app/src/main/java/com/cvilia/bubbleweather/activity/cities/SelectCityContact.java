package com.cvilia.bubbleweather.activity.cities;

import com.amap.api.location.AMapLocation;
import com.cvilia.bubbleweather.base.IPresenter;
import com.cvilia.bubbleweather.base.IView;
import com.cvilia.bubbleweather.bean.City;

import java.util.List;

/**
 * author: lzy
 * date: 2020/8/31
 * describe：描述
 */
public abstract class SelectCityContact {

    interface Presenter extends IPresenter<View> {
        void readDb(String cityName);
        void startLocate();
    }

    interface View extends IView {
        void readDbSuccess(List<City> cityList);
        void searchSuccess(List<String> cities);

        void locateSuccess(AMapLocation location);

        void locateFailed();
    }

}
