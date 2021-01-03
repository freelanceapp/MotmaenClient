package com.motmaen_client.ui.activity_room;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.motmaen_client.R;
import com.motmaen_client.adapters.Room_Adapter;
import com.motmaen_client.databinding.ActivityChatRoomBinding;
import com.motmaen_client.interfaces.Listeners;
import com.motmaen_client.language.Language;
import com.motmaen_client.models.ChatUserModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.models.UserRoomModelData;
import com.motmaen_client.mvp.actvity_chat_room_mvp.ActivityChatRoomPresenter;
import com.motmaen_client.mvp.actvity_chat_room_mvp.ChatRoomActivityView;
import com.motmaen_client.preferences.Preferences;
import com.motmaen_client.remote.Api;
import com.motmaen_client.tags.Tags;
import com.motmaen_client.ui.chat_activity.ChatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRoomActivity extends AppCompatActivity implements ChatRoomActivityView {
    private ActivityChatRoomBinding binding;
    private String lang;
    private Preferences preferences;
    private UserModel userModel;
    private List<UserRoomModelData.UserRoomModel> userRoomModelList;
    private Room_Adapter room_adapter;
    private LinearLayoutManager manager;
    private boolean isLoading = false;
    private ActivityChatRoomPresenter presenter;

    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_room);
        initView();
        if (userModel != null) {
            presenter.getRooms(userModel);
        }

    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        presenter = new ActivityChatRoomPresenter(this, this);
        binding.setLang(lang);
        userRoomModelList = new ArrayList<>();
        room_adapter = new Room_Adapter(this, userRoomModelList);
        manager = new LinearLayoutManager(this);
        binding.recView.setLayoutManager(manager);
        binding.recView.setItemViewCacheSize(25);
        binding.recView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recView.setDrawingCacheEnabled(true);
        binding.recView.setAdapter(room_adapter);
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.backPress();
            }
        });
//        binding.recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    int total_items = room_adapter.getItemCount();
//                    int lastItemPos = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
//
//                    if (total_items >= 6 && (lastItemPos == total_items - 2) && !isLoading) {
//                        isLoading = true;
//                        userRoomModelList.add(null);
//                        room_adapter.notifyItemInserted(userRoomModelList.size() - 1);
//                        //  loadMore(page);
//                    }
//
//                }
//            }
//        });
        presenter.getRooms(userModel);


    }

    public void setItemData(UserRoomModelData.UserRoomModel userRoomModel, int adapterPosition) {

        // userRoomModel.setMy_message_unread_count(0);
        userRoomModelList.set(adapterPosition, userRoomModel);
        room_adapter.notifyItemChanged(adapterPosition);

        ChatUserModel chatUserModel = new ChatUserModel(userRoomModel.getDoctor_fk().getName(), userRoomModel.getDoctor_fk().getLogo(), userRoomModel.getDoctor_id()+"", userRoomModel.getId());
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("chat_user_data", chatUserModel);
        startActivityForResult(intent, 1000);
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
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void ondata(UserRoomModelData body) {
        userRoomModelList.clear();
        userRoomModelList.addAll(body.getData());
        if (userRoomModelList.size() > 0) {
            room_adapter.notifyDataSetChanged();
            binding.tvNoConversation.setVisibility(View.GONE);
        } else {
            binding.tvNoConversation.setVisibility(View.VISIBLE);

        }
    }
}
