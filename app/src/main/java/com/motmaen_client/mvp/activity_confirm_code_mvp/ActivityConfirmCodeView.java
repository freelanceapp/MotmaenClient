package com.motmaen_client.mvp.activity_confirm_code_mvp;

import com.motmaen_client.models.UserModel;

public interface ActivityConfirmCodeView {
    void onCounterStarted(String time);
    void onCounterFinished();
    void onCodeFailed(String msg);
    void onUserFound(UserModel userModel);
    void onUserNoFound();
}
