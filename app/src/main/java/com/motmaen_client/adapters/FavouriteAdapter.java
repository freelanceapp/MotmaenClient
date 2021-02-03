package com.motmaen_client.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.motmaen_client.R;
import com.motmaen_client.databinding.EmergencyDoctorRowBinding;
import com.motmaen_client.databinding.FavDoctorRowBinding;
import com.motmaen_client.models.FavouriteDoctorModel;
import com.motmaen_client.models.SingleDoctorModel;
import com.motmaen_client.ui.activity_emergency.EmergencyActivity;
import com.motmaen_client.ui.activity_favourite.FavouriteActivity;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FavouriteDoctorModel.Data> list;
    private Context context;
    private LayoutInflater inflater;
    private AppCompatActivity activity;

    public FavouriteAdapter(List<FavouriteDoctorModel.Data> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (AppCompatActivity) context;


    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        FavDoctorRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.fav_doctor_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(view -> {
            if(context instanceof FavouriteActivity){
                FavouriteActivity activity=(FavouriteActivity)context;
            FavouriteDoctorModel.Data doctorModel = null;
            doctorModel = list.get(myHolder.getAdapterPosition());
            activity.setItemData(doctorModel,myHolder.binding,myHolder.getAdapterPosition());
        }});

        if (list.get(position).getFav_fk().getIs_emergency().equals("yes")){
            myHolder.binding.imgAvilable.setColorFilter(ContextCompat.getColor(context,R.color.green));
            // myHolder.binding.txtAvilable.setHintTextColor(R.color.green);
        }else {
            myHolder.binding.imgAvilable.setColorFilter(ContextCompat.getColor(context,R.color.gray5));

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private FavDoctorRowBinding binding;

        public MyHolder(FavDoctorRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }




}
