package com.motmaen_client.mvp.activity_doctors_mvp;

import com.motmaen_client.models.AllCityModel;
import com.motmaen_client.models.AllSpiclixationModel;
import com.motmaen_client.models.DoctorModel;

public interface DoctorsActivityView {
    void onFinished();
    void onProgressShow(int type);
    void onProgressHide(int type);
    void onFailed(String msg);
    void onSuccess(AllSpiclixationModel allSpiclixationModel);
    void onSuccesscitie(AllCityModel allCityModel);

    void ondoctorsucess(DoctorModel body);
}
