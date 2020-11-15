package com.motmaen_client.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.motmaen_client.R;
import com.motmaen_client.databinding.DiseaseRowBinding;
import com.motmaen_client.models.DiseaseModel;
import com.motmaen_client.ui.activity_sign_up.SignUpActivity;

import java.util.List;

public class DiseasesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DiseaseModel> list;
    private Context context;
    private LayoutInflater inflater;
    private SignUpActivity activity;

    public DiseasesAdapter(List<DiseaseModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (SignUpActivity) context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        DiseaseRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.disease_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));



        myHolder.binding.imageClose.setOnClickListener(v -> {
            activity.deleteSelectedDisease(myHolder.getAdapterPosition());
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public DiseaseRowBinding binding;

        public MyHolder(@NonNull DiseaseRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
