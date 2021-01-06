package com.motmaen_client.mvp.activity_favourite_mvp;

import android.content.Context;
import android.util.Log;

import com.motmaen_client.R;
import com.motmaen_client.models.DoctorModel;
import com.motmaen_client.models.FavouriteDoctorModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.preferences.Preferences;
import com.motmaen_client.remote.Api;
import com.motmaen_client.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFavouritePresenter {
    private UserModel userModel;
    private Preferences preferences;
    private FavouriteActivityView view;
    private Context context;

    public ActivityFavouritePresenter(FavouriteActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void backPress() {

        view.onFinished();


    }


    public void getdoctors(UserModel userModel)
    {
        view.onProgressShow();

        Api.getService(Tags.base_url)
                .getdoctorsfav("Bearer " + userModel.getData().getToken(),userModel.getData().getId()+"","off")
                .enqueue(new Callback<FavouriteDoctorModel>() {
                    @Override
                    public void onResponse(Call<FavouriteDoctorModel> call, Response<FavouriteDoctorModel> response) {
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
                    public void onFailure(Call<FavouriteDoctorModel> call, Throwable t) {
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
