package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.FavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;



public class ProfileNeighbourActivity extends AppCompatActivity {
    // UI Components
    @BindView(R.id.nameHeader)
    TextView mNameHeader;
    @BindView(R.id.returnButton)
    ImageButton mReturnButton;
    @BindView(R.id.favoriteButton)
    ImageButton mFavoriteButton;
    @BindView(R.id.imageProfile)
    ImageView mImageProfile;
    @BindView(R.id.nameNeighbour)
    TextView mImageNameNeighbour;

    Neighbour neighbour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent Intent = getIntent();
        if (Intent != null){
            neighbour = Intent.getParcelableExtra("neighbour");
            if (neighbour != null){
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_neighbour);
        ButterKnife.bind(this);
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new FavoriteNeighbourEvent(neighbour));
                System.out.println(neighbour.getFavorite().toString());
                favoriteIcon();
            }
        });

        Glide.with(mReturnButton.getContext())
                .load("https://img.icons8.com/ios/100/000000/left")
                .into(mReturnButton);
        mNameHeader.setText(neighbour.getName());
        mImageNameNeighbour.setText(neighbour.getName());
        favoriteIcon();
        Glide.with(mImageProfile.getContext())
                .load(neighbour.getAvatarUrl())
                .into(mImageProfile);

    }

    protected void favoriteIcon (){
        if (neighbour.getFavorite()){
            Glide.with(mFavoriteButton.getContext())
                    .load("https://img.icons8.com/ios-filled/200/000000/star.png")
                    .apply(RequestOptions.circleCropTransform())
                    .into(mFavoriteButton);
        }
        else {
            Glide.with(mFavoriteButton.getContext())
                    .load("https://img.icons8.com/ios/200/000000/star.png")
                    .apply(RequestOptions.circleCropTransform())
                    .into(mFavoriteButton);
        }
    }
}
