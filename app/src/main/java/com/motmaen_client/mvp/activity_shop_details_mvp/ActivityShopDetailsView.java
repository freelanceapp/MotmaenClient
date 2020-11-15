package com.motmaen_client.mvp.activity_shop_details_mvp;

import com.motmaen_client.models.PlaceDetailsModel;

public interface ActivityShopDetailsView {
    void onProgressShow();
    void onProgressHide();
    void onFailed(String msg);
    void onSuccess(PlaceDetailsModel model);
}
