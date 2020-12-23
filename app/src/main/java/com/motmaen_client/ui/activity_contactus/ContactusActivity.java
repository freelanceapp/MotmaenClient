package com.motmaen_client.ui.activity_contactus;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.motmaen_client.R;
import com.motmaen_client.adapters.DiseasesAdapter;
import com.motmaen_client.adapters.SpinnerAdapter;
import com.motmaen_client.adapters.SpinnerDiseasesAdapter;
import com.motmaen_client.databinding.ActivityContactUsBinding;
import com.motmaen_client.databinding.ActivitySignUpBinding;
import com.motmaen_client.databinding.DialogSelectImageBinding;
import com.motmaen_client.language.Language;
import com.motmaen_client.models.ContactUsModel;
import com.motmaen_client.models.DiseaseModel;
import com.motmaen_client.models.SignUpModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.mvp.activity_contactus_mvp.ActivityContactusPresenter;
import com.motmaen_client.mvp.activity_contactus_mvp.ActivityContactusView;
import com.motmaen_client.mvp.activity_sign_up_mvp.ActivitySignUpPresenter;
import com.motmaen_client.mvp.activity_sign_up_mvp.ActivitySignUpView;
import com.motmaen_client.preferences.Preferences;
import com.motmaen_client.share.Common;
import com.motmaen_client.ui.activity_home.HomeActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ContactusActivity extends AppCompatActivity implements ActivityContactusView {
    private ActivityContactUsBinding binding;

    private ContactUsModel contactUsModel;
    private ActivityContactusPresenter presenter;
    private Preferences preferences;
    private String lang;
    private ProgressDialog dialog2;
    private UserModel userModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us);
        initView();

    }


    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        binding.setModel(userModel);
        Paper.init(this);

        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        contactUsModel = new ContactUsModel();
        binding.setContactModel(contactUsModel);

        presenter = new ActivityContactusPresenter(this, this);
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.checkData(contactUsModel);
            }
        });
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void onLoad() {
        dialog2 = Common.createProgressDialog(this, getString(R.string.wait));
        dialog2.setCancelable(false);
        dialog2.show();
    }

    @Override
    public void onFinishload() {
        dialog2.dismiss();
    }

    @Override
    public void onContactVaild() {
        finish();

    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onnotconnect(String msg) {
        Toast.makeText(ContactusActivity.this, msg, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onFailed() {
        Toast.makeText(ContactusActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServer() {
        Toast.makeText(ContactusActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();

    }
}