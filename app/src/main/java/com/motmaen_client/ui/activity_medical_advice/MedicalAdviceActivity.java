package com.motmaen_client.ui.activity_medical_advice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import com.motmaen_client.R;
import com.motmaen_client.databinding.ActivityMedicalAdviceBinding;
import com.motmaen_client.language.Language;

import io.paperdb.Paper;

public class MedicalAdviceActivity extends AppCompatActivity {
    private ActivityMedicalAdviceBinding binding;
    private String lang;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_medical_advice);
        initView();

    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.llBack.setOnClickListener(view -> finish());
    }
}