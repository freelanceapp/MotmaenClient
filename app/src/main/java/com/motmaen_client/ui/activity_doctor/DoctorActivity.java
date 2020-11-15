package com.motmaen_client.ui.activity_doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.motmaen_client.R;
import com.motmaen_client.adapters.DoctorsAdapter;
import com.motmaen_client.adapters.FilterAdapter;
import com.motmaen_client.databinding.ActivityDoctorBinding;
import com.motmaen_client.databinding.DoctorRowBinding;
import com.motmaen_client.language.Language;
import com.motmaen_client.models.DoctorModel;
import com.motmaen_client.models.FilterModel;
import com.motmaen_client.ui.activity_doctor_details.DoctorDetailsActivity;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class DoctorActivity extends AppCompatActivity {
    private ActivityDoctorBinding binding;
    private String lang;
    private double lat=0.0,lng=0.0;
    private float distance =0f;
    private String city_id="";
    private int specialization_id=0;
    private String filter = "";
    private DoctorsAdapter adapter;
    private FilterAdapter filterAdapter;
    private List<FilterModel> filterModelList;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_doctor);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat",0.0);
        lng = intent.getDoubleExtra("lng",0.0);

    }

    private void initView() {
        filterModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(new DoctorsAdapter(new ArrayList<>(),this));
        binding.progBar.setVisibility(View.GONE);
        binding.llBack.setOnClickListener(view -> finish());
        binding.llSpecialization.setOnClickListener(view -> openSheet(1));
        binding.llNearby.setOnClickListener(view -> openSheet(2));
        binding.llCity.setOnClickListener(view -> openSheet(3));

        binding.imageCloseSpecialization.setOnClickListener(view -> closeSheet(1));
        binding.imageCloseNearby.setOnClickListener(view -> closeSheet(2));
        binding.imageCloseCity.setOnClickListener(view -> closeSheet(3));


        filterAdapter = new FilterAdapter(filterModelList,this);
        binding.recViewFilter.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.recViewFilter.setAdapter(filterAdapter);

        binding.btnFilter.setOnClickListener(view -> {
            String title = "";
            if (binding.rbHighPrice.isChecked()){
                title = getString(R.string.high_price);
            }else if (binding.rbLowPrice.isChecked()){
                title = getString(R.string.low_price);
            }else if (binding.rbRate.isChecked()){
                title = getString(R.string.rate);
            }else if (binding.rbTime.isChecked()){
                title = getString(R.string.less_time);
            }
            int pos = getItemPos("filter");
            if (pos==-1){
                FilterModel filterModel = new FilterModel(title,"filter");
                filterModelList.add(filterModel);
                filterAdapter.notifyDataSetChanged();
            }else {
                FilterModel filterModel = filterModelList.get(pos);
                filterModel.setTitle(title);
                filterModelList.set(pos,filterModel);
                filterAdapter.notifyItemChanged(pos);
            }
            closeSheet(2);
            binding.llFilter.setVisibility(View.VISIBLE);
        });



    }


    private void openSheet(int type)
    {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.slide_up);

        if (type ==1){
            binding.flSpecializationSheet.clearAnimation();
            binding.flSpecializationSheet.startAnimation(animation);


        }
        else if (type ==2){
            binding.flFilterSheet.clearAnimation();
            binding.flFilterSheet.startAnimation(animation);


        }
        else if (type ==3){
            binding.flCitySheet.clearAnimation();
            binding.flCitySheet.startAnimation(animation);


        }
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (type ==1){
                    binding.flSpecializationSheet.setVisibility(View.VISIBLE);


                }else if (type ==2){
                    binding.flFilterSheet.setVisibility(View.VISIBLE);


                }else if (type ==3){
                    binding.flCitySheet.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void closeSheet(int type)
    {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.slide_down);

        if (type ==1){
            binding.flSpecializationSheet.clearAnimation();
            binding.flSpecializationSheet.startAnimation(animation);


        }
        else if (type ==2){
            binding.flFilterSheet.clearAnimation();
            binding.flFilterSheet.startAnimation(animation);


        }
        else if (type ==3){
            binding.flCitySheet.clearAnimation();
            binding.flCitySheet.startAnimation(animation);


        }
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (type ==1){
                    binding.flSpecializationSheet.setVisibility(View.GONE);


                }else if (type ==2){
                    binding.flFilterSheet.setVisibility(View.GONE);


                }else if (type ==3){
                    binding.flCitySheet.setVisibility(View.GONE);


                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }





    private int getItemPos(String type){
        int pos =-1;
        for (int index=0;index<filterModelList.size();index++){
            FilterModel filterModel = filterModelList.get(index);
            if (filterModel.getType().equals(type)){
                pos = index;
                return pos;
            }
        }

        return pos;
    }

    public void deleteFilter(FilterModel model, int adapterPosition) {
        filterModelList.remove(adapterPosition);
        filterAdapter.notifyItemRemoved(adapterPosition);
        if (model.getType().equals("filter")){
            binding.rbHighPrice.setChecked(false);
            binding.rbLowPrice.setChecked(false);
            binding.rbRate.setChecked(false);
            binding.rbTime.setChecked(false);
            filter = "";
        }else if (model.getType().equals("specialization")){
            specialization_id = 0;
        }else if (model.getType().equals("city")){
            city_id = "";
        }

        if (filterModelList.size()>0){
            binding.llFilter.setVisibility(View.VISIBLE);
        }else {
            binding.llFilter.setVisibility(View.GONE);

        }
    }

    public void setItemData(DoctorModel doctorModel, DoctorRowBinding binding, int adapterPosition) {
        Intent intent = new Intent(this, DoctorDetailsActivity.class);
        List<Pair<View,String>> pairs = new ArrayList<>();
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                Pair.create(binding.image,binding.image.getTransitionName()),
                Pair.create(binding.rateBar,binding.rateBar.getTransitionName()),
                Pair.create(binding.llAddress,binding.llAddress.getTransitionName()),
                Pair.create(binding.tvDistance,binding.tvDistance.getTransitionName())
                );
        startActivity(intent,options.toBundle());
    }
}