package com.motmaen_client.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.motmaen_client.R;
import com.motmaen_client.databinding.DoctorRowBinding;
import com.motmaen_client.databinding.EmergencyDoctorRowBinding;
import com.motmaen_client.models.DoctorModel;
import com.motmaen_client.models.SingleDoctorModel;
import com.motmaen_client.ui.activity_doctor.DoctorActivity;

import java.util.List;

public class DoctorsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SingleDoctorModel> list;
    private Context context;
    private LayoutInflater inflater;
    private DoctorActivity activity;

    public DoctorsAdapter(List<SingleDoctorModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (DoctorActivity) context;


    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        DoctorRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.doctor_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(view -> {
            SingleDoctorModel doctorModel = null;
             doctorModel = list.get(myHolder.getAdapterPosition());
            activity.setItemData(doctorModel,myHolder.binding,myHolder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private DoctorRowBinding binding;

        public MyHolder(DoctorRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }




}
