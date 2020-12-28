package com.motmaen_client.mvp.activity_editprofile_mvp;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.motmaen_client.R;
import com.motmaen_client.models.AllDiseasesModel;
import com.motmaen_client.models.DiseaseModel;
import com.motmaen_client.models.EditProfileModel;
import com.motmaen_client.models.PlaceGeocodeData;
import com.motmaen_client.models.PlaceMapDetailsData;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.mvp.activity_sign_up_mvp.ActivitySignUpPresenter;
import com.motmaen_client.preferences.Preferences;
import com.motmaen_client.remote.Api;
import com.motmaen_client.share.Common;
import com.motmaen_client.tags.Tags;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditprofilePresenter implements DatePickerDialog.OnDateSetListener{
    private UserModel userModel;
    private Preferences preferences;
    private EditprofileActivityView view;
    private Context context;
    private List<String> genderList;
    private List<String> bloodList;
    private List<DiseaseModel> diseaseModelList;
    private DatePickerDialog datePickerDialog;
    public ActivityEditprofilePresenter(EditprofileActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void backPress() {

        view.onFinished();


    }

//    public void checkData(EditProfileModel editProfileModel, UserModel userModel) {
//        if (editProfileModel.isDataValid(context)) {
//            if (!editProfileModel.getImage_url().isEmpty()) {
//                editprofileimage(editProfileModel, userModel, context);
//            } else {
//                editprofile(editProfileModel, userModel);
//            }
//        }
//    }
//
//    private void editprofileimage(
//            EditProfileModel editProfileModel, UserModel userModel, Context context) {
//       // Log.e("dlldldl", "dlkkdkdk");
//        view.onLoad();
//        RequestBody name_part = Common.getRequestBodyText(editProfileModel.getName());
//        RequestBody phonecode_part = Common.getRequestBodyText(editProfileModel.getPhone_code());
//        RequestBody phone_part = Common.getRequestBodyText(editProfileModel.getPhone());
//        RequestBody address_part = Common.getRequestBodyText(editProfileModel.getAddress());
//
//        RequestBody long_part = Common.getRequestBodyText(editProfileModel.getLongutide() + "");
//        RequestBody lat_part = Common.getRequestBodyText(editProfileModel.getLatuide() + "");
//        RequestBody currency_part = Common.getRequestBodyText(editProfileModel.getCurrency());
//        RequestBody taxamount_part = Common.getRequestBodyText(editProfileModel.getTaxamount());
//
//
//        MultipartBody.Part image_form_part = Common.getMultiPart(context, Uri.parse(editProfileModel.getImage_url()), "logo");
//
//
//        Api.getService(Tags.base_url).
//                Editprofilewithimage("Bearer " + userModel.getToken(), name_part, phonecode_part, phone_part, address_part, long_part, lat_part, currency_part, taxamount_part, image_form_part)
//                .enqueue(new Callback<UserModel>() {
//                    @Override
//                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
//                        view.onFinishload();
//                        if (response.isSuccessful() && response.body() != null) {
//
//                            view.onSuccess(response.body());
//                        } else {
//                            try {
//                                view.onFailed(response.message());
//                                Log.e("jdjjdjjd", response.errorBody().string());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            if (response.code() == 422) {
//                                //Toast.makeText(SignUpDriverActivity.this, R.string.user_found, Toast.LENGTH_SHORT).show();
//                                view.onFailed(response.message());
//
//                            } else {
//                                try {
//                                    view.onFailed(response.message());
//                                    Log.e("jdjjdjjd", response.errorBody().string());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<UserModel> call, Throwable t) {
//                        try {
//                            view.onFinishload();
//                            if (t.getMessage() != null) {
//                                Log.e("msg_category_error", t.getMessage() + "__");
//
//                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
//                                    view.onFailed(t.getMessage());
//
//                                    //  Toast.makeText(SignUpDriverActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
//                                } else {
//                                    view.onFailed(t.getMessage());
//
//                                    // Toast.makeText(SignUpDriverActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        } catch (Exception e) {
//                            Log.e("Error", e.getMessage() + "__");
//                        }
//                    }
//                });
//    }
//
//    private void editprofile(EditProfileModel editProfileModel, UserModel userModel) {
//        view.onLoad();
//        Api.getService(Tags.base_url)
//                .Editprofile("Bearer " + userModel.getToken(), editProfileModel.getName(), editProfileModel.getPhone_code(), editProfileModel.getPhone(), editProfileModel.getAddress(), editProfileModel.getLongutide() + "", editProfileModel.getLatuide() + "", editProfileModel.getCurrency(), editProfileModel.getTaxamount())
//                .enqueue(new Callback<UserModel>() {
//                    @Override
//                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
//                        view.onFinishload();
//                        if (response.isSuccessful() && response.body() != null) {
//                            //  Log.e("eeeeee", response.body().getUser().getName());
//                            // view.onUserFound(response.body());
//                            view.onSuccess(response.body());
//                        } else {
//                            try {
//                                Log.e("mmmmmmmmmm", response.errorBody().string());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//
//                            if (response.code() == 500) {
//                                // view.onServer();
//                            } else {
//                                view.onFailed(response.message());
//                                //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<UserModel> call, Throwable t) {
//                        try {
//                            view.onFinishload();
//                            if (t.getMessage() != null) {
//                                Log.e("msg_category_error", t.getMessage() + "__");
//
//                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
//                                    //   view.onnotconnect(t.getMessage().toLowerCase());
//                                    //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
//                                } else {
//                                    view.onFailed(t.getMessage());
//                                    // Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        } catch (Exception e) {
//                            Log.e("Error", e.getMessage() + "__");
//                        }
//                    }
//                });
//
//    }

    private void createDateDialog() {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setOkText(context.getString(R.string.select));
        datePickerDialog.setCancelText(context.getString(R.string.cancel));
        datePickerDialog.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
        datePickerDialog.setOkColor(ContextCompat.getColor(context, R.color.colorPrimary));
        datePickerDialog.setCancelColor(ContextCompat.getColor(context, R.color.gray4));
        datePickerDialog.setLocale(Locale.ENGLISH);
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_1);
        datePickerDialog.setMaxDate(calendar);

    }

    public void getGender() {
        genderList.clear();
        genderList.add(context.getString(R.string.ch_gender));
        genderList.add(context.getString(R.string.male));
        genderList.add(context.getString(R.string.female));
        view.onGenderSuccess(genderList);

    }

    public void getBloodType() {
        bloodList.clear();
        bloodList.add(context.getString(R.string.ch_blood));
        bloodList.add("A+");
        bloodList.add("A-");
        bloodList.add("B+");
        bloodList.add("B-");
        bloodList.add("O+");
        bloodList.add("O-");
        bloodList.add("AB+");
        bloodList.add("AB-");
        view.onBloodSuccess(bloodList);
    }

    public void getDiseases() {
        // Log.e("tjtjtj",userModel.getIs_confirmed());
        view.onLoad();

        Api.getService(Tags.base_url)
                .getdiseas()
                .enqueue(new Callback<AllDiseasesModel>() {
                    @Override
                    public void onResponse(Call<AllDiseasesModel> call, Response<AllDiseasesModel> response) {
                        view.onFinishload();
                        if (response.isSuccessful() && response.body() != null) {
                            view.onDiseasesSuccess(response.body().getData());
                        } else {
                            view.onFinishload();
                            view.onFailed(context.getString(R.string.something));
                            try {
                                Log.e("error_codess", response.code() + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<AllDiseasesModel> call, Throwable t) {
                        try {
                            view.onFinishload();
                            view.onFailed(context.getString(R.string.something));
                            Log.e("Error", t.getMessage());
                        } catch (Exception e) {

                        }
                    }
                });


    }
    public void showDateDialog(FragmentManager fragmentManager) {
        try {
            datePickerDialog.show(fragmentManager, "");

        } catch (Exception e) {
        }
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, monthOfYear);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String date = dateFormat.format(new Date(calendar.getTimeInMillis()));
        ActivityEditprofilePresenter.this.view.onDateSelected(date);
    }
}
