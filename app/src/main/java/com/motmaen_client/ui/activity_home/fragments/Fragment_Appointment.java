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
import com.motmaen_client.adapters.AppointmentAdapter;
import com.motmaen_client.databinding.FragmentAppoinmentsBinding;
import com.motmaen_client.models.ApointmentModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.mvp.fragment_apointment_mvp.ApointmentFragmentView;
import com.motmaen_client.mvp.fragment_apointment_mvp.ApointmentPresenter;
import com.motmaen_client.preferences.Preferences;
import com.motmaen_client.ui.activity_home.HomeActivity;
import com.motmaen_client.ui.activity_reservation.ReservationActivity;
import com.motmaen_client.ui.activity_reservision_detials.ReservationDetialsActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Appointment extends Fragment implements ApointmentFragmentView {
    private FragmentAppoinmentsBinding binding;
    private AppointmentAdapter adapter;
    private HomeActivity activity;
    private Preferences preferences;
    private UserModel userModel;
    private List<ApointmentModel.Data> apointmentModelList;
    private ApointmentPresenter presenter;

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
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        apointmentModelList = new ArrayList<>();
    /*    binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recView.setAdapter(new AppointmentAdapter(new ArrayList<>(),activity));
        binding.progBar.setVisibility(View.GONE);
        presenter = new ApointmentPresenter(this,activity);
        presenter.getApointment(userModel);*/

        adapter = new AppointmentAdapter(apointmentModelList, activity,this);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recView.setAdapter(adapter);
        binding.progBar.setVisibility(View.GONE);
        presenter = new ApointmentPresenter(this,activity);
        presenter.getApointment(userModel);

    }


    @Override
    public void onSuccess(ApointmentModel apointmentModel) {
        apointmentModelList.clear();
        apointmentModelList.addAll(apointmentModel.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.progBar.setVisibility(View.GONE);

    }
    public void showdetails(ApointmentModel.Data data) {
        Intent intent=new Intent(activity, ReservationDetialsActivity.class);
        intent.putExtra("data",data);
        startActivity(intent);
    }
}