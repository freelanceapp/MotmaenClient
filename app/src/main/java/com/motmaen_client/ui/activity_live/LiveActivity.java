package com.motmaen_client.ui.activity_live;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.motmaen_client.R;
import com.motmaen_client.databinding.ActivityDoctorDetailsBinding;
import com.motmaen_client.databinding.ActivityLiveBinding;
import com.motmaen_client.language.Language;
import com.motmaen_client.models.DoctorModel;
import com.motmaen_client.ui.activity_reservation.ReservationActivity;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;

import java.net.MalformedURLException;
import java.net.URL;

import io.paperdb.Paper;

public class LiveActivity extends AppCompatActivity {
    private String lang;
    private ActivityLiveBinding binding;
    private int roomid;
    private String type;
    private JitsiMeetConferenceOptions options;
    private JitsiMeetActivity session;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_live);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        roomid = getIntent().getIntExtra("room", 0);
        type = getIntent().getStringExtra("type");
    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        session = new JitsiMeetActivity();;

        try {

            JitsiMeetUserInfo userInfo = new JitsiMeetUserInfo();
            if (type.equals("online")) {
                options = new JitsiMeetConferenceOptions.Builder()
                        .setServerURL(new URL("https://meet.jit.si"))
                        .setRoom("motman"+roomid + "")
                        .setUserInfo(userInfo)
                        .setAudioMuted(false)
                        .setVideoMuted(false)
                        .setAudioOnly(false)
                        .setWelcomePageEnabled(false)
                        .build();
            } else {
                options = new JitsiMeetConferenceOptions.Builder()
                        .setServerURL(new URL("https://meet.jit.si"))
                        .setRoom(roomid + "")
                        .setUserInfo(userInfo)
                        .setAudioMuted(false)
                        .setVideoMuted(true)
                        .setAudioOnly(true)
                        .setWelcomePageEnabled(false)
                        .build();

            }

            session.launch(this, options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


}