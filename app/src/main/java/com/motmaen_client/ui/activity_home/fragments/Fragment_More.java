package com.motmaen_client.ui.activity_home.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.motmaen_client.R;
import com.motmaen_client.databinding.FragmentMedicineBinding;
import com.motmaen_client.databinding.FragmentMoreBinding;
import com.motmaen_client.models.SettingModel;
import com.motmaen_client.models.UserModel;
import com.motmaen_client.mvp.fragment_more_mvp.FragmentMorePresenter;
import com.motmaen_client.mvp.fragment_more_mvp.MoreFragmentView;
import com.motmaen_client.preferences.Preferences;
import com.motmaen_client.share.Common;
import com.motmaen_client.tags.Tags;
import com.motmaen_client.ui.activity_contactus.ContactusActivity;
import com.motmaen_client.ui.activity_edit_profile.EditprofileActivity;
import com.motmaen_client.ui.activity_favourite.FavouriteActivity;
import com.motmaen_client.ui.activity_home.HomeActivity;
import com.motmaen_client.ui.activity_language.LanguageActivity;
import com.motmaen_client.ui.activity_login.LoginActivity;
import com.motmaen_client.ui.activity_room.ChatRoomActivity;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class Fragment_More extends Fragment implements MoreFragmentView {
    private FragmentMoreBinding binding;
    private String lang;
    private HomeActivity activity;
    private FragmentMorePresenter presenter;
    private Preferences preferences;
    private UserModel userModel;
    private ProgressDialog dialog;
    private SettingModel setting;

    public static Fragment_More newInstance() {
        return new Fragment_More();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        presenter = new FragmentMorePresenter(this, activity);
        binding.llChangeLanguage.setOnClickListener(view -> {
            Intent intent = new Intent(activity, LanguageActivity.class);
            startActivityForResult(intent, 100);
        });
        binding.llcontactus.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ContactusActivity.class);
            startActivity(intent);
        });

        binding.lleditprofile.setOnClickListener(view -> {
            if (userModel != null) {
                Intent intent = new Intent(activity, EditprofileActivity.class);
                startActivity(intent);
            } else {
                Common.CreateDialogAlert(activity, activity.getResources().getString(R.string.please_sign_in_or_sign_up));
            }
        });
        binding.llchat.setOnClickListener(view -> {
            if (userModel != null) {
                Intent intent = new Intent(activity, ChatRoomActivity.class);
                startActivity(intent);
            } else {
                Common.CreateDialogAlert(activity, activity.getResources().getString(R.string.please_sign_in_or_sign_up));
            }
        });
        binding.llfav.setOnClickListener(view -> {
            if (userModel != null) {
                Intent intent = new Intent(activity, FavouriteActivity.class);
                startActivity(intent);
            } else {
                Common.CreateDialogAlert(activity, activity.getResources().getString(R.string.please_sign_in_or_sign_up));
            }
        });
        binding.logout.setOnClickListener(view -> {
            if (userModel != null) {
                presenter.logout(userModel);
            } else {
                Common.CreateDialogAlert(activity, activity.getResources().getString(R.string.please_sign_in_or_sign_up));
            }
        });
        binding.facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setting != null && setting.getSettings() != null && setting.getSettings().getFacebook() != null) {
                    presenter.open(setting.getSettings().getFacebook());
                }
            }
        });
        binding.instgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setting != null && setting.getSettings() != null && setting.getSettings().getInstagram() != null) {
                    presenter.open(setting.getSettings().getInstagram());
                }
            }
        });
        binding.google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setting != null && setting.getSettings() != null && setting.getSettings().getGoogle_plus() != null) {
                    presenter.open(setting.getSettings().getGoogle_plus());
                }
            }
        });
        binding.twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setting != null && setting.getSettings() != null && setting.getSettings().getTwitter() != null) {
                    presenter.open(setting.getSettings().getTwitter());
                }
            }
        });
        presenter.getSetting();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            String lang = data.getStringExtra("lang");
            activity.refreshActivity(lang);

        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoad() {
        if (dialog == null) {
            dialog = Common.createProgressDialog(activity, getString(R.string.wait));
            dialog.setCancelable(false);
        } else {
            dialog.dismiss();
        }
        dialog.show();
    }

    @Override
    public void onFinishload() {
        dialog.dismiss();
    }


    @Override
    public void logout() {
        preferences.clear(activity);
        navigateToSignInActivity();
    }

    @Override
    public void onsetting(SettingModel body) {
        this.setting = body;
        if (setting.getSettings().getGoogle_plus() == null) {
            binding.google.setVisibility(View.GONE);
        }
        if (setting.getSettings().getInstagram() == null) {
            binding.instgram.setVisibility(View.GONE);
        }
        if (setting.getSettings().getFacebook() == null) {
            binding.facebook.setVisibility(View.GONE);
        }
        if (setting.getSettings().getTwitter() == null) {
            binding.twitter.setVisibility(View.GONE);
        }
        if (setting.getSettings().getGoogle_plus() == null && setting.getSettings().getInstagram() == null && setting.getSettings().getFacebook() == null && setting.getSettings().getTwitter() == null) {
            binding.tv.setVisibility(View.GONE);
        } else {
            binding.tv.setVisibility(View.VISIBLE);

        }

    }

    private void navigateToSignInActivity() {

        Intent intent = new Intent(activity, LoginActivity.class);
        activity.finish();
        startActivity(intent);
    }

    @Override
    public void ViewSocial(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
        startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (preferences != null) {
            userModel = preferences.getUserData(activity);
            udpate();
        }
    }

    private void udpate() {
        if (userModel != null && userModel.getData().getLogo() != null) {
            Picasso.get().load(Tags.IMAGE_URL + userModel.getData().getLogo()).resize(720, 480).onlyScaleDown().into(binding.image);
        }
    }
}
