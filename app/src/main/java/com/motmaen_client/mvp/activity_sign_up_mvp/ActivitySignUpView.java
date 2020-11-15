package com.motmaen_client.mvp.activity_sign_up_mvp;

import com.motmaen_client.models.DiseaseModel;

import java.util.List;

public interface ActivitySignUpView {
    void onGenderSuccess(List<String>genderList);
    void onBloodSuccess(List<String>bloodList);
    void onDiseasesSuccess(List<DiseaseModel> diseaseModelList);
    void onDateSelected(String date);
    void onFailed(String msg);
}
