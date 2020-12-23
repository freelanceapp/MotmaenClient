package com.motmaen_client.mvp.activity_complete_clinic_reservision;

import android.content.Context;
import android.util.Log;

import com.motmaen_client.models.SingleDoctorModel;
import com.motmaen_client.models.SingleReservisionTimeModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.remote.Api;
import com.motmaen_client.tags.Tags;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCompleteClinicReservationPresenter {
    private Context context;
    private ActivityCompleteClinicReservationView view;

    public ActivityCompleteClinicReservationPresenter(Context context, ActivityCompleteClinicReservationView view) {
        this.context = context;
        this.view = view;
    }
    public void addresrevision(UserModel userModel, SingleDoctorModel singleDoctorModel, SingleReservisionTimeModel.Detials detials,String date,String dayname) {

        //Log.e("llll",detials.getFrom_hour_type());
        if(userModel!=null) {
    view.onLoad();
    Api.getService(Tags.base_url)
            .addreservision(userModel.getData().getId() + "", singleDoctorModel.getId() + "", date, detials.getFrom(), singleDoctorModel.getDetection_price() + "", "normal", dayname.toUpperCase(),detials.getFrom_hour_type())
            .enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    view.onFinishload();
                    if (response.isSuccessful() && response.body() != null) {
                        //  Log.e("eeeeee", response.body().getUser().getName());
                        //view.onSignupValid(response.body());
                        view.onsucsess();
                    } else {
                        try {
                            Log.e("mmmmmmmmmmssss", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        if (response.code() == 500) {
                            view.onServer();
                        } else {
                            try {
                                view.onFailed(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    try {
                        view.onFinishload();
                        if (t.getMessage() != null) {
                            Log.e("msg_category_error", t.getMessage() + "__");

                            if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                view.onnotconnect(t.getMessage().toLowerCase());
                                //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            } else {
                                view.onFailed();
                                // Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage() + "__");
                    }
                }
            });

}
else {
    view.onnotlogin();
}
    }
//    public void changeresrevision(UserModel userModel,int type, SingleReservisionTimeModel.Detials detials,String date,String dayname) {
//
//        //Log.e("llll",detials.getFrom_hour_type());
//        if(userModel!=null) {
//            view.onLoad();
//            Api.getService(Tags.base_url)
//                    .addreservision(userModel.getData().getId() + "", singleDoctorModel.getId() + "", date, detials.getFrom(), singleDoctorModel.getDetection_price() + "", "normal", dayname.toUpperCase(),detials.getFrom_hour_type())
//                    .enqueue(new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            view.onFinishload();
//                            if (response.isSuccessful() && response.body() != null) {
//                                //  Log.e("eeeeee", response.body().getUser().getName());
//                                //view.onSignupValid(response.body());
//                                view.onsucsess();
//                            } else {
//                                try {
//                                    Log.e("mmmmmmmmmmssss", response.errorBody().string());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                                if (response.code() == 500) {
//                                    view.onServer();
//                                } else {
//                                    try {
//                                        view.onFailed(response.errorBody().string());
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                    //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            try {
//                                view.onFinishload();
//                                if (t.getMessage() != null) {
//                                    Log.e("msg_category_error", t.getMessage() + "__");
//
//                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
//                                        view.onnotconnect(t.getMessage().toLowerCase());
//                                        //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        view.onFailed();
//                                        // Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            } catch (Exception e) {
//                                Log.e("Error", e.getMessage() + "__");
//                            }
//                        }
//                    });
//
//        }
//        else {
//            view.onnotlogin();
//        }
//    }

}
