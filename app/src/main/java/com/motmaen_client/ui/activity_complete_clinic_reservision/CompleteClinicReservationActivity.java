package com.motmaen_client.ui.activity_complete_clinic_reservision;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import com.motmaen_client.R;
import com.motmaen_client.adapters.ImagesAdapter;
import com.motmaen_client.databinding.ActivityCompleteClinicReservisionBinding;
import com.motmaen_client.databinding.DialogSelectImageBinding;
import com.motmaen_client.language.Language;
import com.motmaen_client.models.SingleDoctorModel;
import com.motmaen_client.models.SingleReservisionTimeModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.mvp.activity_complete_clinic_reservision.ActivityCompleteClinicReservationPresenter;
import com.motmaen_client.mvp.activity_complete_clinic_reservision.ActivityCompleteClinicReservationView;
import com.motmaen_client.preferences.Preferences;
import com.motmaen_client.share.Common;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.MultipartBody;

public class CompleteClinicReservationActivity extends AppCompatActivity implements ActivityCompleteClinicReservationView {
    private String lang;
    private ActivityCompleteClinicReservisionBinding binding;
    private SingleDoctorModel doctorModel;
    private String date = "";
    private String dayname = "";
    private int reservid;
    private ActivityCompleteClinicReservationPresenter presenter;
    private SingleReservisionTimeModel.Detials singletimemodel;
    private ProgressDialog dialog2;
    private UserModel usermodel;
    private Preferences preferences;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private List<Uri> imagesList;
    private ImagesAdapter imagesAdapter;
    private AlertDialog dialog;
    private String type;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_complete_clinic_reservision);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        doctorModel = (SingleDoctorModel) intent.getSerializableExtra("data");
        singletimemodel = (SingleReservisionTimeModel.Detials) intent.getSerializableExtra("time");
        date = intent.getStringExtra("date");
        dayname = intent.getStringExtra("dayname");
        reservid =intent.getIntExtra("resrvid",0);
        type =intent.getStringExtra("type");

    }

    private void initView() {

        preferences = Preferences.getInstance();
        usermodel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setDate(date);
        binding.setModel(doctorModel);
        binding.setTimemodel(singletimemodel);
        presenter = new ActivityCompleteClinicReservationPresenter(this, this);

        imagesList = new ArrayList<>();

        binding.recViewImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imagesAdapter = new ImagesAdapter(imagesList, this);
        binding.recViewImages.setAdapter(imagesAdapter);
        binding.llBack.setOnClickListener(view -> {
            finish();
        });
        if(reservid!=0){
            binding.btnConsultationReserve.setText(getResources().getString(R.string.Update_resev));
            binding.tv.setText(getResources().getString(R.string.Update_resev));
            binding.card.setVisibility(View.GONE);

        }
        binding.btnConsultationReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String detials=binding.edtdetials.getText().toString();

                if(reservid ==0) {
                    if(imagesList.size()==0){
                    presenter.addresrevision(usermodel, doctorModel, singletimemodel, date, dayname,detials,type);}
                    else {
                        presenter.addresrevision(usermodel,doctorModel,singletimemodel,date,dayname,imagesList,detials,type);
                    }
                }
                else {
                    Log.e("dkdkdk",dayname+" "+reservid+" "+usermodel.getData().getId());
                    presenter.updateresrevision(usermodel,  singletimemodel, date,  reservid,dayname);

                }
            }
        });
        binding.imageCamera.setOnClickListener(v -> createDialogAlert());


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
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onsucsess() {
        Intent intent=getIntent();
        setResult(RESULT_OK,intent);

        finish();
    }

    @Override
    public void onServer() {
        Toast.makeText(CompleteClinicReservationActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onnotlogin() {


        Common.CreateDialogAlert(this, getResources().getString(R.string.please_sign_in_or_sign_up));
    }


    @Override
    public void onFailed() {
        Toast.makeText(CompleteClinicReservationActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onnotconnect(String msg) {
        Toast.makeText(CompleteClinicReservationActivity.this, msg, Toast.LENGTH_SHORT).show();

    }

    public void createDialogAlert() {
        dialog = new AlertDialog.Builder(this)
                .create();

        DialogSelectImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_select_image, null, false);
        binding.btnCamera.setOnClickListener(v -> checkCameraPermission());
        binding.btnGallery.setOnClickListener(v -> checkReadPermission());

        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
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
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

            Uri uri = data.getData();
            cropImage(uri);


        } else if (requestCode == CAMERA_REQ && resultCode == Activity.RESULT_OK && data != null) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Uri uri = getUriFromBitmap(bitmap);
            if (uri != null) {
                cropImage(uri);

            }


        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri uri = result.getUri();

                if (imagesList.size() > 0) {
                    imagesList.add(imagesList.size() - 1, uri);
                    imagesAdapter.notifyItemInserted(imagesList.size() - 1);

                } else {
                    imagesList.add(uri);
                    imagesList.add(null);
                    imagesAdapter.notifyItemRangeInserted(0, imagesList.size());
                }


                dialog.dismiss();

                binding.recViewImages.postDelayed(() -> {
                    binding.recViewImages.smoothScrollToPosition(imagesList.size() - 1);
                }, 100);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


    }
    private void cropImage(Uri uri) {

        CropImage.activity(uri).setAspectRatio(1, 1).setGuidelines(CropImageView.Guidelines.ON).start(this);

    }

    private Uri getUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "", ""));
    }


    public void delete(int adapterPosition) {
        imagesList.remove(adapterPosition);
        if (imagesList.size() == 1) {
            imagesList.clear();
            imagesAdapter.notifyDataSetChanged();
        } else {
            imagesAdapter.notifyItemRemoved(adapterPosition);
        }
    }
}