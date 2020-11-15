package com.motmaen_client.mvp.activity_sign_up_mvp;

import android.app.FragmentManager;
import android.content.Context;

import androidx.core.content.ContextCompat;

import com.motmaen_client.R;
import com.motmaen_client.models.DiseaseModel;
import com.motmaen_client.models.SignUpModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActivitySignUpPresenter implements DatePickerDialog.OnDateSetListener{
    private Context context;
    private ActivitySignUpView view;
    private List<String> genderList;
    private List<String> bloodList;
    private List<DiseaseModel> diseaseModelList;
    private DatePickerDialog datePickerDialog;



    public ActivitySignUpPresenter(Context context, ActivitySignUpView view)
    {
        this.context = context;
        this.view = view;
        genderList = new ArrayList<>();
        bloodList = new ArrayList<>();
        diseaseModelList = new ArrayList<>();
        createDateDialog();

    }

    private void createDateDialog()
    {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR));
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.MONTH,Calendar.JANUARY);
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
    public void getGender()
    {
        genderList.clear();
        genderList.add(context.getString(R.string.ch_gender));
        genderList.add(context.getString(R.string.male));
        genderList.add(context.getString(R.string.female));
        view.onGenderSuccess(genderList);

    }
    public void getBloodType()
    {
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
    public void getDiseases(){
        diseaseModelList.clear();
        DiseaseModel model = new DiseaseModel();
        model.setId(0);
        model.setTitle(context.getString(R.string.ch_diseases));
        diseaseModelList.add(model);

        DiseaseModel model1 = new DiseaseModel();
        model1.setId(1);
        model1.setTitle("الضغط");
        diseaseModelList.add(model1);

        DiseaseModel model2 = new DiseaseModel();
        model2.setId(2);
        model2.setTitle("السكر");
        diseaseModelList.add(model2);

        DiseaseModel model3 = new DiseaseModel();
        model3.setId(3);
        model3.setTitle("القلب");
        diseaseModelList.add(model3);



        view.onDiseasesSuccess(diseaseModelList);

    }
    public void checkData(SignUpModel signUpModel)
    {
        if (signUpModel.isDataValid(context)){
            if (signUpModel.getImageUrl().isEmpty()){
                sign_up_without_image(signUpModel);
            }else {
                sign_up_with_image(signUpModel);

            }
        }
    }
    public void showDateDialog(FragmentManager fragmentManager){
        try {
            datePickerDialog.show(fragmentManager,"");

        }catch (Exception e){}
    }

    private void sign_up_with_image(SignUpModel signUpModel) {

    }

    private void sign_up_without_image(SignUpModel signUpModel) {


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, monthOfYear);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String date = dateFormat.format(new Date(calendar.getTimeInMillis()));
        ActivitySignUpPresenter.this.view.onDateSelected(date);
    }
}
