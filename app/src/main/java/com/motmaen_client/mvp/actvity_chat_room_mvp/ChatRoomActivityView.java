package com.motmaen_client.mvp.actvity_chat_room_mvp;

import com.motmaen_client.models.MessageDataModel;
import com.motmaen_client.models.MessageModel;
import com.motmaen_client.models.UserRoomModelData;

public interface ChatRoomActivityView {
    void onFinished();
    void onProgressShow();
    void onProgressHide();
    void onFailed(String msg);

    void ondata(UserRoomModelData body);
}
