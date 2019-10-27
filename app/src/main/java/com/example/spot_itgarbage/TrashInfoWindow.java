package com.example.spot_itgarbage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class TrashInfoWindow extends AppCompatActivity {

    private static ImageView trashImage;
    private MarkerData data;
    private static TextView challengeRating;
    private static TextView trashDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_info_window);
        trashImage = findViewById(R.id.trashImage);
        challengeRating = findViewById(R.id.challengeRating);
        trashDesc = findViewById(R.id.trashDesc);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // To retrieve object in from MapsActivity
        data = (MarkerData)getIntent().getSerializableExtra("Marker");
//        Loads Image from URL
        Picasso.get().load(data.getUrl()).into(trashImage);

//        Add text to textviews
        challengeRating.append(data.getRating() + "/10");
        trashDesc.append(data.getDesc());
    }




}
