package com.motmaen_client.mvp.activity_patient_details_mvp;

import android.content.Context;
import android.util.Log;



import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPatientDetailsPresenter {
    private ActivityPatientDetailsView view;
    private Context context;


    public ActivityPatientDetailsPresenter(ActivityPatientDetailsView view, Context context) {
        this.view = view;
        this.context = context;

    }

}