package com.motmaen_client.mvp.fragment_more_mvp;

import android.content.Context;
import android.util.Log;

import com.motmaen_client.R;
import com.motmaen_client.models.Slider_Model;
import com.motmaen_client.remote.Api;
import com.motmaen_client.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMorePresenter {
    private MoreFragmentView view;
    private Context context;

    public FragmentMorePresenter(MoreFragmentView view, Context context) {
        this.view = view;
        this.context = context;
    }

}