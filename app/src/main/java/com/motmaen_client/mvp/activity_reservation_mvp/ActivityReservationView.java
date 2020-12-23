package com.motmaen_client.mvp.activity_reservation_mvp;

import com.motmaen_client.models.ReservisionTimeModel;

public interface ActivityReservationView {
    void onDateSelected(String date, String dayname);
    void onLoad();
    void onFinishload();
    void onFailed(String msg);

    void onreservtimesucess(ReservisionTimeModel body);
}
