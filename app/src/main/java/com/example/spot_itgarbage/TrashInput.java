package com.example.spot_itgarbage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class TrashInput extends AppCompatActivity {
    private static SeekBar seekbar;
    private static TextView textview;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_input);
        seekBar();
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }

                                       }
        );

    }


    protected void onActivity(int requestCode, int resultCode, Intent data) {
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
                    public void onStartTrackingTouch(SeekBar seekBar){

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textview.setText(progress_value + "/10");

                    }
                }
        );
    }
}
