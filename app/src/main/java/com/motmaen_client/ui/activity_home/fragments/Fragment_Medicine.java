package com.motmaen_client.ui.activity_home.fragments;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.motmaen_client.R;
import com.motmaen_client.adapters.DrugsAdapter;
import com.motmaen_client.databinding.FragmentHomeBinding;
import com.motmaen_client.databinding.FragmentMedicineBinding;
import com.motmaen_client.models.DrugModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.mvp.fragment_medecine_mvp.MedecineFragmentView;
import com.motmaen_client.mvp.fragment_medecine_mvp.MedecinePresenter;
import com.motmaen_client.preferences.Preferences;
import com.motmaen_client.ui.activity_home.HomeActivity;
import com.motmaen_client.ui.activity_patient_details.PatientDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Medicine extends Fragment implements MedecineFragmentView {
    private FragmentMedicineBinding binding;
    private MedecinePresenter presenter;
    private List<DrugModel> drugModels;
    private DrugsAdapter drugsAdapter;
    private HomeActivity activity;
    private UserModel userModel;
    private Preferences preferences;

    public static Fragment_Medicine newInstance() {
        return new Fragment_Medicine();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_medicine, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        drugModels = new ArrayList<>();
        drugsAdapter = new DrugsAdapter(drugModels, activity,this);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recView.setAdapter(drugsAdapter);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        presenter = new MedecinePresenter(this, activity);
        presenter.getDrugs(userModel);

    }

    @Override
    public void onSuccess(List<DrugModel> data) {
        drugModels.clear();
        drugModels.addAll(data);
        if (data.size() == 0) {
            binding.tvNoData.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoData.setVisibility(View.GONE);
        }
        drugsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.progBar.setVisibility(View.GONE);
    }
    public void setItemData(DrugModel drugModel) {
        Intent intent = new Intent(activity, PatientDetailsActivity.class);
        intent.putExtra("data",drugModel);
        startActivity(intent);
    }
}
