package com.motmaen_client.ui.activity_favourite;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.motmaen_client.R;
import com.motmaen_client.adapters.EmergencyAdapter;
import com.motmaen_client.adapters.FavouriteAdapter;
import com.motmaen_client.databinding.ActivityEmergencyBinding;
import com.motmaen_client.databinding.ActivityFavouriteBinding;
import com.motmaen_client.databinding.EmergencyDoctorRowBinding;
import com.motmaen_client.databinding.FavDoctorRowBinding;
import com.motmaen_client.language.Language;
import com.motmaen_client.models.DoctorModel;
import com.motmaen_client.models.FavouriteDoctorModel;
import com.motmaen_client.models.SingleDoctorModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.mvp.activity_Emrgency_mvp.ActivityEmergencyPresenter;
import com.motmaen_client.mvp.activity_Emrgency_mvp.EmergencyActivityView;
import com.motmaen_client.mvp.activity_favourite_mvp.ActivityFavouritePresenter;
import com.motmaen_client.mvp.activity_favourite_mvp.FavouriteActivityView;
import com.motmaen_client.preferences.Preferences;
import com.motmaen_client.ui.activity_doctor_details.DoctorDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class FavouriteActivity extends AppCompatActivity implements FavouriteActivityView {
    private ActivityFavouriteBinding binding;
    private ActivityFavouritePresenter presenter;
    private String lang;
    private FavouriteAdapter adapter;
    private List<FavouriteDoctorModel.Data> singleDoctorModelList;
    private UserModel userModel;
    private Preferences preferences;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourite);
        initView();

    }

    private void initView() {
        preferences=Preferences.getInstance();
        userModel=preferences.getUserData(this);
        singleDoctorModelList = new ArrayList<>();
        presenter = new ActivityFavouritePresenter(this, this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        adapter = new FavouriteAdapter(singleDoctorModelList, this);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.progBar.setVisibility(View.GONE);
        binding.recView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recView.setAdapter(adapter);
        binding.llBack.setOnClickListener(view -> presenter.backPress());
        presenter.getdoctors(userModel);

    }

    @Override
    public void onFinished() {
        finish();
    }

    @Override
    public void onProgressShow() {
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressHide() {
        binding.progBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void ondoctorsucess(FavouriteDoctorModel body) {
        singleDoctorModelList.clear();
        singleDoctorModelList.addAll(body.getData());
        adapter.notifyDataSetChanged();
        if (singleDoctorModelList.size() == 0) {
            binding.tvNoDoctor.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoDoctor.setVisibility(View.GONE);
        }
    }

    public void setItemData(FavouriteDoctorModel.Data doctorModel, FavDoctorRowBinding binding, int adapterPosition) {
        Intent intent = new Intent(this, DoctorDetailsActivity.class);
        intent.putExtra("data", doctorModel.getFav_fk());
        List<Pair<View, String>> pairs = new ArrayList<>();
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                Pair.create(binding.image, binding.image.getTransitionName()),
                Pair.create(binding.rateBar, binding.rateBar.getTransitionName()),
                Pair.create(binding.llAddress, binding.llAddress.getTransitionName()),
                Pair.create(binding.tvDistance, binding.tvDistance.getTransitionName())
        );
        startActivityForResult(intent, 1, options.toBundle());
    }

}