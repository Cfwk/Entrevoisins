package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.FavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class ProfileNeighbourActivity extends AppCompatActivity {
    // UI Components
    @BindView(R.id.nameHeader)
    TextView mNameHeader;
    @BindView(R.id.returnButton)
    ImageButton mReturnButton;
    @BindView(R.id.favoriteButton)
    FloatingActionButton mFavoriteButton;
    @BindView(R.id.imageProfile)
    ImageView mImageProfile;
    @BindView(R.id.nameNeighbour)
    TextView mImageNameNeighbour;

    Neighbour neighbour;
    private NeighbourApiService mApiService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApiService = DI.getNeighbourApiService();
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
                favoriteIcon();
            }
        });

        mNameHeader.setText(neighbour.getName());
        mImageNameNeighbour.setText(neighbour.getName());
        favoriteIcon();
        Glide.with(mImageProfile.getContext())
                .load(neighbour.getAvatarUrl())
                .into(mImageProfile);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



    @Subscribe
    public void onFavoriteNeighbour(FavoriteNeighbourEvent event){
        mApiService.favoriteNeighbour(event.neighbour);
    }


    protected void favoriteIcon (){
        if (mApiService.isFavorite(neighbour)){
            ((FloatingActionButton)mFavoriteButton.getRootView().findViewById(R.id.favoriteButton)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_white_24dp));
        }
        else {
            ((FloatingActionButton)mFavoriteButton.getRootView().findViewById(R.id.favoriteButton)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_border_white_24dp));
        }
    }
}
