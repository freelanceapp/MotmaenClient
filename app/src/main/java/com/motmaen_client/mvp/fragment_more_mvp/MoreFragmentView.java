package com.motmaen_client.mvp.fragment_more_mvp;

import com.motmaen_client.models.SettingModel;
import com.motmaen_client.models.Slider_Model;

import java.util.List;

public interface MoreFragmentView {
    void onFailed(String msg);

    void onLoad();

    void onFinishload();


    void logout();

    void onsetting(SettingModel body);
}
