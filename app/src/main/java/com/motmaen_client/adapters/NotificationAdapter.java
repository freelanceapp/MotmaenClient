package com.motmaen_client.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.motmaen_client.R;
import com.motmaen_client.databinding.NotificationRowBinding;
import com.motmaen_client.models.NotificationModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.preferences.Preferences;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationAdapterVH> {

    private List<NotificationModel.Data> notificationList;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    Preferences preferences;
    UserModel userModel;


    public NotificationAdapter(Context context) {
        this.context = context;
    }

    public NotificationAdapter(List<NotificationModel.Data> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

    }

    @NonNull
    @Override
    public NotificationAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.notification_row, parent, false);
        return new NotificationAdapterVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapterVH holder, int position) {
        holder.binding.setModel(notificationList.get(position));

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class NotificationAdapterVH extends RecyclerView.ViewHolder {
        public NotificationRowBinding binding;

        public NotificationAdapterVH(@NonNull NotificationRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
