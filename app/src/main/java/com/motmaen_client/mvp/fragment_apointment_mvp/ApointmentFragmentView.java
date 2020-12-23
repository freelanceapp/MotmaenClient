package com.motmaen_client.mvp.fragment_apointment_mvp;

import com.motmaen_client.models.ApointmentModel;

public interface ApointmentFragmentView {
    void onSuccess(ApointmentModel apointmentModel);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();

}
