package com.motmaen_client.ui.activity_location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import com.motmaen_client.R;
import com.motmaen_client.databinding.ActivityLocationBinding;
import com.motmaen_client.databinding.ActivityLoginBinding;
import com.motmaen_client.language.Language;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.mvp.activity_location_presenter.ActivityLocationPresenter;
import com.motmaen_client.mvp.activity_location_presenter.ActivityLocationView;
import com.motmaen_client.preferences.Preferences;
import com.motmaen_client.ui.activity_home.HomeActivity;
import com.motmaen_client.ui.activity_login.LoginActivity;
import com.google.android.gms.maps.model.LatLng;

import io.paperdb.Paper;

public class LocationActivity extends AppCompatActivity implements ActivityLocationView {
    private ActivityLocationBinding binding;
    private ActivityLocationPresenter presenter;
    private UserModel userModel;
    private Preferences preference;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_location);
        initView();
    }

    private void initView() {
        binding.tv.setText(Html.fromHtml(getString(R.string.get_location)));
        preference = Preferences.getInstance();
        userModel = preference.getUserData(this);
        presenter = new ActivityLocationPresenter(this,this);

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == ActivityLocationPresenter.loc_req) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.initGoogleApiClient();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1255 && resultCode == RESULT_OK) {
            presenter.startLocationUpdate();
        }
    }

    @Override
    public void onLocationChanged(LatLng latLng) {
        Intent intent;
        if (userModel!=null){
            intent = new Intent(this, HomeActivity.class);
        }else {
            intent = new Intent(this, LoginActivity.class);
        }
        intent.putExtra("lat",latLng.latitude);
        intent.putExtra("lng",latLng.longitude);
        startActivity(intent);
        finish();
    }
}