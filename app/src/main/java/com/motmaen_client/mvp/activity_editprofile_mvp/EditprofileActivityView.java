package com.motmaen_client.mvp.activity_editprofile_mvp;


import com.motmaen_client.models.DiseaseModel;

import java.util.List;

public interface EditprofileActivityView {
    void onGenderSuccess(List<String> genderList);
    void onBloodSuccess(List<String>bloodList);
    void onDiseasesSuccess(List<DiseaseModel> diseaseModelList);
    void onDateSelected(String date);
    void onFailed(String msg);
    void onLoad();
    void onFinishload();

    void onFinished();
}
