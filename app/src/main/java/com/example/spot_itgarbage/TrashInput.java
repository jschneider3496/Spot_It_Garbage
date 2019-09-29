package com.example.spot_itgarbage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class TrashInput extends AppCompatActivity {
    private static SeekBar seekbar;
    private static TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_input);
        seekBar();
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
