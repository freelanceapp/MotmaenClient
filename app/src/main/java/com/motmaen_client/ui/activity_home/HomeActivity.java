package com.motmaen_client.ui.activity_home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;

import com.motmaen_client.R;
import com.motmaen_client.databinding.ActivityHomeBinding;
import com.motmaen_client.language.Language;
import com.motmaen_client.models.SingleDoctorModel;
import com.motmaen_client.models.UserSettingsModel;
import com.motmaen_client.mvp.activity_home_mvp.ActivityHomePresenter;
import com.motmaen_client.mvp.activity_home_mvp.HomeActivityView;
import com.motmaen_client.ui.activity_doctor.DoctorActivity;
import com.motmaen_client.ui.activity_doctor_details.DoctorDetailsActivity;
import com.motmaen_client.ui.activity_login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.motmaen_client.ui.notification_activity.NotificationActivity;

import java.util.List;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements HomeActivityView {
    private ActivityHomeBinding binding;
    private FragmentManager fragmentManager;
    private ActivityHomePresenter presenter;
    private double lat=0.0,lng=0.0;
    private String id;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent  = getIntent();

        lat = intent.getDoubleExtra("lat",0.0);
        lng = intent.getDoubleExtra("lng",0.0);
        if (intent.getData() != null) {
            List<String> pathSegments = intent.getData().getPathSegments();
            id = pathSegments.get(pathSegments.size() - 1);

            Log.e("llflfllflf", id+ pathSegments.get(pathSegments.size() - 2));
            if( pathSegments.get(pathSegments.size() - 2).equals("share-app")){
                SingleDoctorModel singleDoctorModel=new SingleDoctorModel();
                singleDoctorModel.setId(Integer.parseInt(id));
                Intent intent1 = new Intent(HomeActivity.this, DoctorDetailsActivity.class);
                intent1.putExtra("data", singleDoctorModel);
                startActivity(intent1);
            }
//            Intent intent1 = new Intent(HomeActivity.this, RestuarnantActivity.class);
//            intent1.putExtra("restaurand_id", id);
//            startActivity(intent1);
        }
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        presenter = new ActivityHomePresenter(this, this, fragmentManager,lat,lng);
        binding.navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                presenter.manageFragments(item);
                return true;
            }
        });

        binding.flSearch.setOnClickListener(view -> {
            Intent intent = new Intent(this, DoctorActivity.class);
            intent.putExtra("lat",lat);
            intent.putExtra("lng",lng);
            startActivity(intent);
        });
        binding.flnotification.setOnClickListener(view -> {
            Intent intent = new Intent(this, NotificationActivity.class);

            startActivity(intent);
        });
    }


    public void refreshActivity(String lang) {
        new Handler(Looper.getMainLooper()).postDelayed(()->{
            Paper.init(this);
            Paper.book().write("lang",lang);
            Language.updateResources(this,lang);
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        },1500);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public void onBackPressed() {
        presenter.backPress();
    }

    @Override
    public void onHomeFragmentSelected() {
        binding.navigationView.setSelectedItemId(R.id.home);
    }


    @Override
    public void onNavigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onFinished() {
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(presenter.fragment_appointment!=null){
            presenter.fragment_appointment.getdata();
        }
    }
}