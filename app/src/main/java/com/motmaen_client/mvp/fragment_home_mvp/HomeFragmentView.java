package com.motmaen_client.mvp.fragment_home_mvp;

import com.motmaen_client.models.ApointmentModel;
import com.motmaen_client.models.Slider_Model;

import java.util.List;

public interface HomeFragmentView {
    void onFailed(String msg);

    void onProgressSliderShow();
    void onProgressSliderHide();
    void onSliderSuccess(List<Slider_Model.Data> sliderModelList);
}
