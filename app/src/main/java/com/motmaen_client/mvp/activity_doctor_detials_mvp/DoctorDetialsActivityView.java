package com.motmaen_client.mvp.activity_doctor_detials_mvp;

import com.motmaen_client.models.SingleDataDoctorModel;

public interface DoctorDetialsActivityView {
    void onFinished();
    void onLoad();
    void onFinishload();
    void onFailed(String msg);


    void ondoctorsucess(SingleDataDoctorModel body);

    void onlikesucess();
}
