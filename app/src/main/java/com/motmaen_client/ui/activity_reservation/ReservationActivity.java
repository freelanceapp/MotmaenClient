package com.motmaen_client.ui.activity_reservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.motmaen_client.R;
import com.motmaen_client.databinding.ActivityReservationBinding;
import com.motmaen_client.language.Language;
import com.motmaen_client.models.DoctorModel;
import com.motmaen_client.mvp.activity_reservation_mvp.ActivityReservationPresenter;
import com.motmaen_client.mvp.activity_reservation_mvp.ActivityReservationView;
import com.motmaen_client.ui.activity_live.LiveActivity;

import io.paperdb.Paper;

public class ReservationActivity extends AppCompatActivity implements ActivityReservationView {
    private String lang;
    private ActivityReservationBinding binding;
    private DoctorModel doctorModel;
    private String type="";
    private String date ="";
    private String time = "";
    private ActivityReservationPresenter presenter;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_reservation);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        doctorModel = (DoctorModel) intent.getSerializableExtra("data");

    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        presenter = new ActivityReservationPresenter(this,this);


        binding.cardCall.setOnClickListener(view -> {
            type = "call";
            binding.flCall.setBackgroundResource(R.drawable.small_rounded_red_strock);
            binding.flLive.setBackgroundResource(0);
            binding.flChat.setBackgroundResource(0);

        });

        binding.cardLive.setOnClickListener(view -> {
            type = "live";
            binding.flCall.setBackgroundResource(0);
            binding.flChat.setBackgroundResource(0);
            binding.flLive.setBackgroundResource(R.drawable.small_rounded_red_strock);

            Intent intent=new Intent(ReservationActivity.this, LiveActivity.class);
            startActivity(intent);
        });

        binding.cardChat.setOnClickListener(view -> {
            type = "chat";
            binding.flCall.setBackgroundResource(0);
            binding.flLive.setBackgroundResource(0);
            binding.flChat.setBackgroundResource(R.drawable.small_rounded_red_strock);

        });

        binding.imageBack.setOnClickListener(view -> {
            finish();
        });

        binding.llDate.setOnClickListener(view -> presenter.showDateDialog(getFragmentManager()));
    }

    @Override
    public void onDateSelected(String date) {
        this.date = date;
        binding.tvDate.setText(date);
    }
}