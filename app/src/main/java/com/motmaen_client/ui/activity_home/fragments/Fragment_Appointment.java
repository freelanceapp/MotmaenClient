package com.motmaen_client.ui.activity_home.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.motmaen_client.R;
import com.motmaen_client.adapters.AppointmentAdapter;
import com.motmaen_client.databinding.FragmentAppoinmentsBinding;
import com.motmaen_client.ui.activity_home.HomeActivity;

import java.util.ArrayList;

public class Fragment_Appointment extends Fragment {
    private FragmentAppoinmentsBinding binding;
    private AppointmentAdapter adapter;
    private HomeActivity activity;
    public static Fragment_Appointment newInstance(){
        return new Fragment_Appointment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appoinments,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recView.setAdapter(new AppointmentAdapter(new ArrayList<>(),activity));
        binding.progBar.setVisibility(View.GONE);
    }

}
