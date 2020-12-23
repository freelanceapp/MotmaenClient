package com.motmaen_client.mvp.activity_Emrgency_mvp;

import com.motmaen_client.models.DoctorModel;

public interface EmergencyActivityView {
    void onFinished();
    void onProgressShow();
    void onProgressHide();
    void onFailed(String msg);


    void ondoctorsucess(DoctorModel body);
}
