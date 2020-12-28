package com.motmaen_client.ui.notification_activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.motmaen_client.R;
import com.motmaen_client.adapters.NotificationAdapter;
import com.motmaen_client.databinding.ActivityNotificationBinding;
import com.motmaen_client.language.Language;
import com.motmaen_client.models.NotificationModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.mvp.activity_notification_mvp.ActivityNotificationPresenter;
import com.motmaen_client.mvp.activity_notification_mvp.NotificationActivityView;
import com.motmaen_client.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class NotificationActivity extends AppCompatActivity implements NotificationActivityView {
    private ActivityNotificationBinding binding;
    private ActivityNotificationPresenter presenter;
    private String lang;
    private List<NotificationModel.Data> notficationmodellist;
    private NotificationAdapter notificationAdapter;
    private Preferences preferences;
    private UserModel userModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        initView();

    }


    private void initView() {
        presenter = new ActivityNotificationPresenter(this, this);
        notficationmodellist = new ArrayList<>();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        notificationAdapter = new NotificationAdapter(notficationmodellist, this);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(notificationAdapter);
        presenter.getnotifications(userModel);
        binding.llBack.setOnClickListener(view -> {
            presenter.backPress();
        });


    }


    @Override
    public void onFinished() {
        finish();
    }

    @Override
    public void onProgressShow() {

        binding.progBar.setVisibility(View.VISIBLE);


    }

    @Override
    public void onProgressHide() {

        binding.progBar.setVisibility(View.GONE);


    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(NotificationActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(NotificationModel notificationModel) {
        notficationmodellist.clear();
        notficationmodellist.addAll(notificationModel.getData());
        notificationAdapter.notifyDataSetChanged();
        if(notficationmodellist.size()==0){
            binding.tvNoData.setVisibility(View.VISIBLE);
        }
        else {
            binding.tvNoData.setVisibility(View.GONE);

        }
    }


}