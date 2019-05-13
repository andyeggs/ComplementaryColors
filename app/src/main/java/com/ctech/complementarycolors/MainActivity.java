package com.ctech.complementarycolors;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    //Reference the seek bars
    SeekBar SeekA;
    SeekBar SeekR;
    SeekBar SeekG;
    SeekBar SeekB;
    //Reference the TextView
    TextView ShowColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get a reference to the seekbars
        SeekA=(SeekBar)findViewById(R.id.seekA);
        SeekR=(SeekBar)findViewById(R.id.seekR);
        SeekG=(SeekBar)findViewById(R.id.seekG);
        SeekB=(SeekBar)findViewById(R.id.seekB);
        //Reference the TextView
        ShowColor=(TextView)findViewById(R.id.textView);
        //This activity implements SeekBar OnSeekBarChangeListener
        SeekA.setOnSeekBarChangeListener(this);
        SeekR.setOnSeekBarChangeListener(this);
        SeekG.setOnSeekBarChangeListener(this);
        SeekB.setOnSeekBarChangeListener(this);
    }
    //Satisfy the implements
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        //get current ARGB values
        int A=SeekA.getProgress();
        int R=SeekR.getProgress();
        int G=SeekG.getProgress();
        int B=SeekB.getProgress();
        //Reference the value changing
        int id=seekBar.getId();
        //Get the chnaged value
        if(id == com.ctech.complementarycolors.R.id.seekA)
            A=progress;
        else if(id == com.ctech.complementarycolors.R.id.seekR)
            R=progress;
        else if(id == com.ctech.complementarycolors.R.id.seekA)
            G=progress;
        else if(id == com.ctech.complementarycolors.R.id.seekA)
            B=progress;
        //Build and show the new color
        ShowColor.setBackgroundColor(Color.argb(A,R,G,B));
        //show the color value
        ShowColor.setText("0x"+String.format("%02x", A)+String.format("%02x", R)
                +String.format("%02x", G)+String.format("%02x", B));
        //some math so text shows (needs improvement for greys)
        ShowColor.setTextColor(Color.argb(0xff,255-R,255-G,255-B));
    }
    public void onStartTrackingTouch(SeekBar seekBar) {
        //Only required due to implements
    }
    public void onStopTrackingTouch(SeekBar seekBar) {
        //Only required due to implements
    }
    //Handle button
    public void Picker1Click(View arg0) {
        //no direct way to get background color as it could be a drawable
        if (ShowColor.getBackground() instanceof ColorDrawable) {
            ColorDrawable cd = (ColorDrawable) ShowColor.getBackground();
            int colorCode = cd.getColor();
            //pick a color (changed in the UpdateColor listener)
            new ColorPickerDialog(MainActivity.this, new UpdateColor(), colorCode).show();
        }
    }
    public class UpdateColor implements ColorPickerDialog.OnColorChangedListener {
        public void colorChanged(int color) {
            //ShowColor.setBackgroundColor(color);
            //show the color value
            ShowColor.setText("0x"+String.format("%08x", color));
            SeekA.setProgress(Color.alpha(color));
            SeekR.setProgress(Color.red(color));
            SeekG.setProgress(Color.green(color));
            SeekB.setProgress(Color.blue(color));
        }
    }
}