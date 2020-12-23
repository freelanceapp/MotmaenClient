package com.motmaen_client.mvp.activity_reservation_mvp;

import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.motmaen_client.R;
import com.motmaen_client.models.ReservisionTimeModel;
import com.motmaen_client.models.SingleDoctorModel;
import com.motmaen_client.remote.Api;
import com.motmaen_client.tags.Tags;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityReservationPresenter implements DatePickerDialog.OnDateSetListener{
    private Context context;
    private ActivityReservationView view;
    private DatePickerDialog datePickerDialog;

    public ActivityReservationPresenter(Context context, ActivityReservationView view) {
        this.context = context;
        this.view = view;
        createDateDialog();
    }
    private void createDateDialog()
    {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR));
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setOkText(context.getString(R.string.select));
        datePickerDialog.setCancelText(context.getString(R.string.cancel));
        datePickerDialog.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
        datePickerDialog.setOkColor(ContextCompat.getColor(context, R.color.colorPrimary));
        datePickerDialog.setCancelColor(ContextCompat.getColor(context, R.color.gray4));
        datePickerDialog.setLocale(Locale.ENGLISH);
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_1);
        datePickerDialog.setMinDate(calendar);

    }
    public void showDateDialog(FragmentManager fragmentManager){
        try {
            datePickerDialog.show(fragmentManager,"");

        }catch (Exception e){}
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, monthOfYear);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String date = dateFormat.format(new Date(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("EEE",Locale.ENGLISH);
        String stringDate = sdf.format(new Date(calendar.getTimeInMillis()));
        ActivityReservationPresenter.this.view.onDateSelected(date,stringDate.toUpperCase());
    }
    public void getreservisiontime(SingleDoctorModel singleDoctorModel, String type, String date, String dayname)
    {
        // Log.e("tjtjtj",userModel.getIs_confirmed());
        view.onLoad();

        Api.getService(Tags.base_url)
                .getreservision(singleDoctorModel.getId()+"",date,dayname,type)
                .enqueue(new Callback<ReservisionTimeModel>() {
                    @Override
                    public void onResponse(Call<ReservisionTimeModel> call, Response<ReservisionTimeModel> response) {
                        view.onFinishload();

                        if (response.isSuccessful() && response.body() != null) {
                            view.onreservtimesucess(response.body());
                        } else {
                            view.onFinishload();
                            view.onFailed(context.getString(R.string.something));

                            try {
                                Log.e("error_codess",response.code()+ response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<ReservisionTimeModel> call, Throwable t) {
                        try {
                            view.onFinishload();
                            view.onFailed(context.getString(R.string.something));
                            Log.e("Error", t.getMessage());
                        } catch (Exception e) {

                        }
                    }
                });
    }
}
