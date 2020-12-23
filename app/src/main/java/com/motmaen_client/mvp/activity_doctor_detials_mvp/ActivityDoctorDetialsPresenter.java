package com.motmaen_client.mvp.activity_doctor_detials_mvp;

import android.content.Context;
import android.util.Log;

import com.motmaen_client.R;
import com.motmaen_client.models.SingleDataDoctorModel;
import com.motmaen_client.models.SingleDoctorModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.preferences.Preferences;
import com.motmaen_client.remote.Api;
import com.motmaen_client.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDoctorDetialsPresenter {
    private UserModel userModel;
    private Preferences preferences;
    private DoctorDetialsActivityView view;
    private Context context;

    public ActivityDoctorDetialsPresenter(DoctorDetialsActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void backPress() {

        view.onFinished();


    }

    public void getDoctorDtials(SingleDoctorModel singleDoctorModel, String user_id)
    {
         Log.e("tjtjtj",singleDoctorModel.getId()+"  "+user_id);
        view.onLoad();

        Api.getService(Tags.base_url)
                .getsingledoctor(singleDoctorModel.getId()+"",user_id)
                .enqueue(new Callback<SingleDataDoctorModel>() {
                    @Override
                    public void onResponse(Call<SingleDataDoctorModel> call, Response<SingleDataDoctorModel> response) {
                        view.onFinishload();

                        if (response.isSuccessful() && response.body() != null) {
                            view.ondoctorsucess(response.body());
                        } else {
                            view.onFinishload();
                            view.onFailed(context.getString(R.string.something));
                            try {
                                Log.e("error_codess",response.code()+ response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<SingleDataDoctorModel> call, Throwable t) {
                        try {
                            view.onFinishload();
                            view.onFailed(context.getString(R.string.something));
                            Log.e("Error", t.getMessage());
                        } catch (Exception e) {

                        }
                    }
                });
    }

}
