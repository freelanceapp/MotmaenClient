package com.motmaen_client.ui.activity_edit_profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.motmaen_client.R;
import com.motmaen_client.adapters.DiseasesAdapter;
import com.motmaen_client.adapters.SpinnerAdapter;
import com.motmaen_client.adapters.SpinnerDiseasesAdapter;
import com.motmaen_client.databinding.ActivityEditprofileBinding;
import com.motmaen_client.databinding.DialogSelectImageBinding;
import com.motmaen_client.language.Language;
import com.motmaen_client.models.DiseaseModel;
import com.motmaen_client.models.EditProfileModel;
import com.motmaen_client.models.PlaceGeocodeData;
import com.motmaen_client.models.SignUpModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.mvp.activity_editprofile_mvp.ActivityEditprofilePresenter;
import com.motmaen_client.mvp.activity_editprofile_mvp.EditprofileActivityView;
import com.motmaen_client.mvp.activity_sign_up_mvp.ActivitySignUpPresenter;
import com.motmaen_client.preferences.Preferences;
import com.motmaen_client.share.Common;
import com.motmaen_client.tags.Tags;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.motmaen_client.ui.activity_home.HomeActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class EditprofileActivity extends AppCompatActivity implements EditprofileActivityView {
    private ActivityEditprofileBinding binding;
    private EditProfileModel model;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private Uri uri = null;
    private List<String> genderList;
    private List<String> bloodList;
    private SpinnerAdapter genderAdapter,bloodAdapter;
    private ActivityEditprofilePresenter presenter;
    private List<DiseaseModel> diseaseModelList;
    private List<DiseaseModel>selectedDiseasesList;
    private DiseasesAdapter adapter;
    private SpinnerDiseasesAdapter spinnerDiseasesAdapter;
    private android.app.AlertDialog dialog;
    private ProgressDialog dialog2;
    private double lat=0.0,lng=0.0;
    private String lang;

    private Preferences preferences;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editprofile);
        initView();

    }

    private void initView()
    {
        preferences=Preferences.getInstance();
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        model = new EditProfileModel();
        binding.setModel(model);
        presenter = new ActivityEditprofilePresenter(this,this);
        diseaseModelList = new ArrayList<>();
        selectedDiseasesList = new ArrayList<>();
        genderList = new ArrayList<>();
        bloodList = new ArrayList<>();
        genderAdapter = new SpinnerAdapter(genderList,this);
        binding.spinnerGender.setAdapter(genderAdapter);
        bloodAdapter = new SpinnerAdapter(bloodList,this);
        binding.spinnerBlood.setAdapter(bloodAdapter);

        spinnerDiseasesAdapter = new SpinnerDiseasesAdapter(diseaseModelList,this);
        binding.spinnerDiseases.setAdapter(spinnerDiseasesAdapter);

        binding.spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    model.setGender("");
                }else {if(lang.equals("ar")){
                    if(i==1){
                        model.setGender("male");
                    }
                    else {
                        model.setGender("female");
                    }
                }else {

                    model.setGender(genderList.get(i));}
                }
                binding.setModel(model);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerBlood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    model.setBlood_type("");
                }else {
                    model.setBlood_type(bloodList.get(i));
                }
                binding.setModel(model);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerDiseases.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DiseaseModel model = diseaseModelList.get(i);
                if (model.getId()!=0){
                    if (!isItemInDiseasesList(model)){
                        selectedDiseasesList.add(model);
                        adapter.notifyItemInserted(selectedDiseasesList.size()-1);
                        EditprofileActivity.this.model.setDiseaseModelList(selectedDiseasesList);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter = new DiseasesAdapter(selectedDiseasesList,this);
        binding.recView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.recView.setAdapter(adapter);

        binding.flSelectImage.setOnClickListener(view -> {
            dialog.show();
        });
        binding.checkbox.setOnClickListener(view -> {
            if (binding.checkbox.isChecked()){
                model.setHave_diseases(true);
                binding.recView.setVisibility(View.VISIBLE);
                binding.cardViewDiseases.setVisibility(View.VISIBLE);
            }else {
                model.setHave_diseases(false);
                binding.recView.setVisibility(View.GONE);
                binding.spinnerDiseases.setSelection(0);
                binding.cardViewDiseases.setVisibility(View.GONE);
                selectedDiseasesList.clear();
                adapter.notifyDataSetChanged();

            }
            binding.setModel(model);
        });

        binding.llDate.setOnClickListener(view -> {
            presenter.showDateDialog(getFragmentManager());
        });

        binding.btnSignUp.setOnClickListener(view -> {
        //    presenter.checkData(model);
        });


        createImageDialogAlert();
        presenter.getDiseases();
        presenter.getGender();
        presenter.getBloodType();
    }

    private void createImageDialogAlert() {
        dialog = new AlertDialog.Builder(this)
                .create();

        DialogSelectImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_select_image, null, false);
        binding.btnCamera.setOnClickListener(view -> {
            dialog.dismiss();
            checkCameraPermission();
        });
        binding.btnGallery.setOnClickListener(view -> {
            dialog.dismiss();
            checkReadPermission();
        });
        binding.btnCancel.setOnClickListener(v -> dialog.dismiss()

        );
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
    }

    public void checkReadPermission() {
        if (ActivityCompat.checkSelfPermission(this, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_PERM}, READ_REQ);
        } else {
            SelectImage(READ_REQ);
        }
    }

    public void checkCameraPermission() {


        if (ContextCompat.checkSelfPermission(this, write_permission) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, camera_permission) == PackageManager.PERMISSION_GRANTED
        ) {
            SelectImage(CAMERA_REQ);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{camera_permission, write_permission}, CAMERA_REQ);
        }
    }


    private void SelectImage(int req) {

        Intent intent = new Intent();

        if (req == READ_REQ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            } else {
                intent.setAction(Intent.ACTION_GET_CONTENT);

            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");
            startActivityForResult(intent, req);

        } else if (req == CAMERA_REQ) {
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, req);
            } catch (SecurityException e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();

            }


        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_REQ) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == CAMERA_REQ) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQ && resultCode == Activity.RESULT_OK && data != null) {

            uri = data.getData();
            File file = new File(Common.getImagePath(this, uri));
            Picasso.get().load(file).fit().into(binding.image);
            binding.icon.setVisibility(View.GONE);
            model.setImageUrl(uri.toString());
            binding.setModel(model);

        } else if (requestCode == CAMERA_REQ && resultCode == Activity.RESULT_OK && data != null) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            uri = getUriFromBitmap(bitmap);
            binding.icon.setVisibility(View.GONE);

            if (uri != null) {
                model.setImageUrl(uri.toString());
                binding.setModel(model);
                String path = Common.getImagePath(this, uri);
                if (path != null) {
                    Picasso.get().load(new File(path)).fit().into(binding.image);

                } else {
                    Picasso.get().load(uri).fit().into(binding.image);

                }
            }


        }

    }

    private Uri getUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "", ""));
    }

    private boolean isItemInDiseasesList(DiseaseModel diseaseModel)
    {
        for (DiseaseModel model :selectedDiseasesList){
            if (diseaseModel.getId()==model.getId()){
                return true;
            }
        }
        return false;
    }
    private int getItemSpinnerPos(DiseaseModel diseaseModel)
    {
        int pos = -1;
        for (int index=0;index<diseaseModelList.size();index++) {
            DiseaseModel model = diseaseModelList.get(index);
            if (model.getId()==diseaseModel.getId()){
                pos = index;
                return pos;
            }
        }
        return pos;
    }
    public void deleteSelectedDisease(int adapterPosition)
    {
        selectedDiseasesList.remove(adapterPosition);
        adapter.notifyItemRemoved(adapterPosition);
        if (selectedDiseasesList.size()>0){
            int pos = getItemSpinnerPos(selectedDiseasesList.get(selectedDiseasesList.size()-1));

            if (pos!=-1){
                binding.spinnerDiseases.setSelection(pos);
            }else {
                binding.spinnerDiseases.setSelection(0);
            }
        }else {
            binding.spinnerDiseases.setSelection(0);

        }
    }
    @Override
    public void onGenderSuccess(List<String> genderList)
    {
        this.genderList.clear();
        this.genderList.addAll(genderList);
        genderAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBloodSuccess(List<String> bloodList)
    {
        this.bloodList.clear();
        this.bloodList.addAll(bloodList);
        bloodAdapter.notifyDataSetChanged();
    }
    @Override
    public void onDiseasesSuccess(List<DiseaseModel> diseaseModelList)
    {
        this.diseaseModelList.addAll(diseaseModelList);
        runOnUiThread(() -> spinnerDiseasesAdapter.notifyDataSetChanged());
    }
    @Override
    public void onDateSelected(String date)
    {
        binding.tvDate.setText(date);
        model.setBirth_date(date);
        binding.setModel(model);
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
    public void onFinished() {
        finish();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}