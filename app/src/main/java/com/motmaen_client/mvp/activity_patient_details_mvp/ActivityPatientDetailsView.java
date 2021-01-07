package com.motmaen_client.mvp.activity_patient_details_mvp;


import java.util.List;

public interface ActivityPatientDetailsView {
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();

}
