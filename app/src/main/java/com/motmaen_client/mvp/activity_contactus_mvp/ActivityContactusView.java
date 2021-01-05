package com.motmaen_client.mvp.activity_contactus_mvp;

import com.motmaen_client.models.DiseaseModel;
import com.motmaen_client.models.SettingModel;
import com.motmaen_client.models.UserModel;

import java.util.List;

public interface ActivityContactusView {

    void onFailed(String msg);
    void onLoad();
    void onFinishload();
    void onContactVaild();
    void onFailed();
    void onServer();

    void onnotconnect(String msg);

    void ViewSocial(String link);

    void onsetting(SettingModel body);
}
