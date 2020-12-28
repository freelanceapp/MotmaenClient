package com.motmaen_client.ui.activity_reservision_detials;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.motmaen_client.R;
import com.motmaen_client.adapters.ReasonAdapter;
import com.motmaen_client.databinding.ActivityReservisionDetialsBinding;
import com.motmaen_client.language.Language;
import com.motmaen_client.models.ApointmentModel;
import com.motmaen_client.models.ReasonModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.mvp.actvity_reservision_detials_mvp.ActivityReservationDetialsPresenter;
import com.motmaen_client.mvp.actvity_reservision_detials_mvp.ActivityReservationDetialsView;
import com.motmaen_client.preferences.Preferences;
import com.motmaen_client.share.Common;
import com.motmaen_client.ui.activity_reservation.ReservationActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ReservationDetialsActivity extends AppCompatActivity implements ActivityReservationDetialsView {
    private String lang;
    private ActivityReservisionDetialsBinding binding;
    private ApointmentModel.Data apointmentModel;
    private List<ReasonModel.Data> dataList;
    private ReasonAdapter reasonAdapter;
    private ActivityReservationDetialsPresenter presenter;

    private ProgressDialog dialog2;
    private UserModel usermodel;
    private Preferences preferences;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservision_detials);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        apointmentModel = (ApointmentModel.Data) intent.getSerializableExtra("data");

    }

    private void initView() {
        dataList = new ArrayList<>();
        preferences = Preferences.getInstance();
        usermodel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        binding.setModel(apointmentModel);

        reasonAdapter = new ReasonAdapter(dataList, this);
        binding.recViewreason.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewreason.setAdapter(reasonAdapter);
        presenter = new ActivityReservationDetialsPresenter(this, this);

        binding.llBack.setOnClickListener(view -> {
            finish();
        });
        binding.cancelreserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSheet();
            }
        });
        binding.imageCloseSpecialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSheet();

            }
        });
        binding.btncahngeresev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReservationDetialsActivity.this, ReservationActivity.class);
                intent.putExtra("DATA", apointmentModel);
                startActivity(intent);
                //  presenter.addresrevision(usermodel, doctorModel, singletimemodel, date, dayname);

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
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onsucsess() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public void onServer() {
        Toast.makeText(com.motmaen_client.ui.activity_reservision_detials.ReservationDetialsActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onnotlogin() {


        Common.CreateDialogAlert(this, getResources().getString(R.string.please_sign_in_or_sign_up));
    }


    @Override
    public void onFailed() {
        Toast.makeText(ReservationDetialsActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressShow() {
        binding.progBarreason.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressHide() {
        binding.progBarreason.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(ReasonModel body) {
        if (body.getData() != null && body.getData().size() > 0) {
            dataList.addAll(body.getData());
            reasonAdapter.notifyDataSetChanged();
            binding.tvNoDatareason.setVisibility(View.GONE);

        } else {
            binding.tvNoDatareason.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onnotconnect(String msg) {
        Toast.makeText(ReservationDetialsActivity.this, msg, Toast.LENGTH_SHORT).show();

    }

    private void openSheet() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        binding.flSpecializationSheet.clearAnimation();
        binding.flSpecializationSheet.startAnimation(animation);
        presenter.getreasons();

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.flSpecializationSheet.setVisibility(View.VISIBLE);


            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void closeSheet() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_down);


        binding.flSpecializationSheet.clearAnimation();
        binding.flSpecializationSheet.startAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.flSpecializationSheet.setVisibility(View.GONE);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void setreason(String reason) {
        presenter.cancelreserv(reason, apointmentModel);
    }
}