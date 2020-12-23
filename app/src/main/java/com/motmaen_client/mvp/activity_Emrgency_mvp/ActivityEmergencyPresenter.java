package com.motmaen_client.mvp.activity_Emrgency_mvp;

import android.content.Context;
import android.util.Log;

import com.motmaen_client.R;
import com.motmaen_client.models.DoctorModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.preferences.Preferences;
import com.motmaen_client.remote.Api;
import com.motmaen_client.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEmergencyPresenter {
    private UserModel userModel;
    private Preferences preferences;
    private EmergencyActivityView view;
    private Context context;

    public ActivityEmergencyPresenter(EmergencyActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void backPress() {

        view.onFinished();


    }


    public void getdoctors()
    {
        view.onProgressShow();

        Api.getService(Tags.base_url)
                .getdoctors()
                .enqueue(new Callback<DoctorModel>() {
                    @Override
                    public void onResponse(Call<DoctorModel> call, Response<DoctorModel> response) {
                        view.onProgressHide();
                        if (response.isSuccessful() && response.body() != null) {
                            view.ondoctorsucess(response.body());
                        } else {
                            view.onProgressHide();
                            view.onFailed(context.getString(R.string.something));
                            try {
                                Log.e("error_code",response.code()+  response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<DoctorModel> call, Throwable t) {
                        try {
                            view.onProgressHide();
                            view.onFailed(context.getString(R.string.something));
                            Log.e("Error", t.getMessage());
                        } catch (Exception e) {

                        }
                    }
                });
    }
}
