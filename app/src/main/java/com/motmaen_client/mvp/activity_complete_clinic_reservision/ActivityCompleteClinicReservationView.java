package com.motmaen_client.mvp.activity_complete_clinic_reservision;

public interface ActivityCompleteClinicReservationView {
    void onLoad();
    void onFinishload();
    void onFailed(String msg);

    void onsucsess();

    void onServer();

    void onnotlogin();

    void onnotconnect(String toLowerCase);

    void onFailed();
}
