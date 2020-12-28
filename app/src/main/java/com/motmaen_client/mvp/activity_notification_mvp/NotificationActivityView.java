package com.motmaen_client.mvp.activity_notification_mvp;

import com.motmaen_client.models.NotificationModel;

public interface NotificationActivityView {
    void onFinished();

    void onProgressShow();

    void onProgressHide();

    void onFailed(String msg);

    void onSuccess(NotificationModel notificationModel);
}
