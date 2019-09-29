package com.example.spot_itgarbage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrashInput extends AppCompatActivity {
    private static SeekBar seekbar;
    private static TextView textview;

    private static DatabaseReference Database;
    private static EditText editText;

    private static ImageView imageView;
    private static final String TAG = "TrashInput";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_input);
        seekBar();
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);
        Database = FirebaseDatabase.getInstance().getReference();


        // SUBMIT BUTTON
        Button submit = (Button) findViewById(R.id.button2);

        imageButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                               startActivityForResult(intent, 0);


                                           }

                                       }
        );

        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          String description = editText.getText().toString().trim();
                                          int rating = seekbar.getProgress();

                                          Bundle location = getIntent().getExtras();
                                          double lat = location.getDouble("lat");
                                          double lng = location.getDouble("lng");
//                                          System.out.println("lat: " + lat + " lng: " + lng);


                                          MarkerData dataEntry = new MarkerData(description, rating, lat, lng);
                                          Database.push().setValue(dataEntry);


                                      }
                                  }
        );


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }


    public void seekBar() {
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        textview = (TextView) findViewById(R.id.textView2);
        textview.setText(seekbar.getProgress() + "/10");


        seekbar.setOnSeekBarChangeListener(

                new SeekBar.OnSeekBarChangeListener() {
                    int progress_value;

                    @Override
                    public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
                        progress_value = progress;
                        textview.setText(progress + "/10");

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textview.setText(progress_value + "/10");

                    }
                }
        );
    }
}
