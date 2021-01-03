package com.motmaen_client.general_ui_method;

import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.motmaen_client.R;
import com.motmaen_client.models.NearbyModel;
import com.motmaen_client.tags.Tags;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class GeneralMethod {

    @BindingAdapter("error")
    public static void errorValidation(View view, String error) {
        if (view instanceof EditText) {
            EditText ed = (EditText) view;
            ed.setError(error);
        } else if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setError(error);


        }
    }




    @BindingAdapter("user_image")
    public static void userImage(View view, String endPoint) {
        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (endPoint != null) {

                Picasso.get().load(Uri.parse(Tags.IMAGE_URL + endPoint)).placeholder(R.drawable.ic_avatar).into(imageView);
            } else {
                Picasso.get().load(R.drawable.ic_avatar).into(imageView);

            }
        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (endPoint != null) {

                Picasso.get().load(Uri.parse(Tags.IMAGE_URL + endPoint)).placeholder(R.drawable.ic_avatar).fit().into(imageView);
            } else {
                Picasso.get().load(R.drawable.ic_avatar).into(imageView);

            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (endPoint != null) {

                Picasso.get().load(Uri.parse(Tags.IMAGE_URL + endPoint)).placeholder(R.drawable.ic_avatar).fit().into(imageView);
            } else {
                Picasso.get().load(R.drawable.ic_avatar).into(imageView);

            }
        }

    }

    @BindingAdapter({"date"})
    public static void displayDate (TextView textView,long date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm aa", Locale.ENGLISH);
        String m_date = dateFormat.format(new Date(date*1000));

        textView.setText(String.format(m_date));

    }


    @BindingAdapter("image")
    public static void image(View view, String endPoint) {
        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (endPoint != null) {

                Picasso.get().load(Uri.parse(Tags.IMAGE_URL + endPoint)).placeholder(R.drawable.ic_avatar).into(imageView);
            } else {
                Picasso.get().load(R.drawable.ic_avatar).into(imageView);

            }
        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (endPoint != null) {

                Picasso.get().load(Uri.parse(Tags.IMAGE_URL + endPoint)).placeholder(R.drawable.ic_avatar).fit().into(imageView);
            } else {
                Picasso.get().load(R.drawable.ic_avatar).into(imageView);

            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (endPoint != null) {

                Picasso.get().load(Uri.parse(Tags.IMAGE_URL + endPoint)).placeholder(R.drawable.ic_avatar).fit().into(imageView);
            } else {
                Picasso.get().load(R.drawable.ic_avatar).into(imageView);

            }
        }

    }

    @BindingAdapter("placeStoreImage")
    public static void placeStoreImage(View view, NearbyModel.Result result) {
        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;


            //https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CmRaAAAA_sTOr-cDhIZuoi4D0YpT9EmNM2UWY5Vb2fDcqO-_SQ9Qmf2dyNFg_gtSs-dXu4puk6Q957hhfPKvsfhY8w7W2aL-w9HfPrHlitd1PAA-vxb85ZDYEdxIleV8y9_uQQwzEhC-npYoXzXwrwrrxasjKXLQGhTi_joH1kyW7hzZIAIY5c04_27qUg&key=AIzaSyA6QI378BHt9eqBbiJKtqWHTSAZxcSwN3Q
            if (result.getPhotos()!=null){
                if (result.getPhotos().size()>0)
                {
                    String url = Tags.IMAGE_Places_URL+result.getPhotos().get(0).getPhoto_reference()+"&key="+view.getContext().getString(R.string.map_api_key);
                    Picasso.get().load(Uri.parse(url)).fit().into(imageView);

                }else
                {
                    Picasso.get().load(Uri.parse(result.getIcon())).fit().into(imageView);

                }
            }else {
                Picasso.get().load(Uri.parse(result.getIcon())).fit().into(imageView);

            }



        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (result.getPhotos()!=null){
                if (result.getPhotos().size()>0)
                {
                    String url = Tags.IMAGE_Places_URL+result.getPhotos().get(0).getPhoto_reference()+"&key="+view.getContext().getString(R.string.map_api_key);
                    Picasso.get().load(Uri.parse(url)).fit().into(imageView);

                }else
                {
                    Picasso.get().load(Uri.parse(result.getIcon())).fit().into(imageView);

                }
            }else {
                Picasso.get().load(Uri.parse(result.getIcon())).fit().into(imageView);

            }

        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (result.getPhotos()!=null){
                if (result.getPhotos().size()>0)
                {
                    String url = Tags.IMAGE_Places_URL+result.getPhotos().get(0).getPhoto_reference()+"&key="+view.getContext().getString(R.string.map_api_key);
                    Picasso.get().load(Uri.parse(url)).fit().into(imageView);

                }else
                {
                    Picasso.get().load(Uri.parse(result.getIcon())).fit().into(imageView);

                }
            }else {
                Picasso.get().load(Uri.parse(result.getIcon())).fit().into(imageView);

            }

        }

    }


    @BindingAdapter("placeStoreIcon")
    public static void placeStoreIcon(View view, String reference) {
        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;

            String url = Tags.IMAGE_Places_URL+reference+"&key="+view.getContext().getString(R.string.map_api_key);
            Picasso.get().load(Uri.parse(url)).fit().into(imageView);



        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            String url = Tags.IMAGE_Places_URL+reference+"&key="+view.getContext().getString(R.string.map_api_key);
            Picasso.get().load(Uri.parse(url)).fit().into(imageView);

        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            String url = Tags.IMAGE_Places_URL+reference+"&key="+view.getContext().getString(R.string.map_api_key);
            Picasso.get().load(Uri.parse(url)).fit().into(imageView);

        }

    }

    @BindingAdapter("distance")
    public static void distance(TextView view,double distance){
        view.setText(String.format(Locale.ENGLISH,"%.2f %s",distance,view.getContext().getString(R.string.km)));
    }

    @BindingAdapter("rate")
    public static void rate(SimpleRatingBar ratingBar, double rate) {
        ratingBar.setRating((float) rate);

    }

}










