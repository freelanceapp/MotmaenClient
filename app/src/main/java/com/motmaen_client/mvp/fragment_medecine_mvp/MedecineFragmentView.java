package com.motmaen_client.mvp.fragment_medecine_mvp;

import com.motmaen_client.models.ApointmentModel;
import com.motmaen_client.models.DrugModel;

import java.util.List;

public interface MedecineFragmentView {
    void onSuccess(List<DrugModel> data);
    void onFailed(String msg);
    void showProgressBar();
    void hideProgressBar();


}
