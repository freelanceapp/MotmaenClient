package com.motmaen_client.mvp.activity_reservation_mvp;

import com.motmaen_client.models.MessageDataModel;
import com.motmaen_client.models.ReservisionTimeModel;
import com.motmaen_client.models.RoomIdModel;

public interface ActivityReservationView {
    void onDateSelected(String date, String dayname);
    void onLoad();
    void onFinishload();
    void onFailed(String msg);

    void onreservtimesucess(ReservisionTimeModel body);

    void onsucess(RoomIdModel body);
}
