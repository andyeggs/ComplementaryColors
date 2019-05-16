package com.ctech.complementarycolors;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    //Reference the seek bars
    SeekBar SeekR;
    SeekBar SeekG;
    SeekBar SeekB;
    SeekBar seekResultR;
    SeekBar seekResultG;
    SeekBar seekResultB;
    //Reference the TextView
    TextView ShowColor;
    Button ShowComp;
    TextView ShowHex;
    EditText ChangeHex;
    Button SubmitButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_picker_main);
        //Get a reference to the seekbars
        SeekR=(SeekBar)findViewById(R.id.seekR);
        SeekG=(SeekBar)findViewById(R.id.seekG);
        SeekB=(SeekBar)findViewById(R.id.seekB);
        seekResultR=(SeekBar)findViewById(R.id.seekResultR);
        seekResultG=(SeekBar)findViewById(R.id.seekResultG);
        seekResultB=(SeekBar)findViewById(R.id.seekResultB);
        //Reference the TextView
        ShowColor=(Button)findViewById(R.id.buttonA);
        ShowComp=(Button)findViewById(R.id.buttonD);
        ShowHex=(TextView)findViewById(R.id.resultHex);
        ChangeHex=(EditText)findViewById(R.id.hexEdit);
        //This activity implements SeekBar OnSeekBarChangeListener
        SeekR.setOnSeekBarChangeListener(this);
        SeekG.setOnSeekBarChangeListener(this);
        SeekB.setOnSeekBarChangeListener(this);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = ChangeHex.getText().toString();
                int enteredText = Integer.parseInt(value);
                ShowColor.setBackgroundColor(Color.parseColor("#" + enteredText));
            }
        });
    }

    //Satisfy the implements
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        //get current RGB values
        int R=SeekR.getProgress();
        int G=SeekG.getProgress();
        int B=SeekB.getProgress();
        int T=255-R;
        int C=255-G;
        int E=255-B;
        //Reference the value changing
        int id=seekBar.getId();
        //Get the chnaged value
        if(id == com.ctech.complementarycolors.R.id.seekR)
            R=progress;
        else if(id == com.ctech.complementarycolors.R.id.seekG)
            G=progress;
        else if(id == com.ctech.complementarycolors.R.id.seekB)
            B=progress;
        //Build and show the new color
        ShowColor.setBackgroundColor(Color.rgb(R,G,B));
        ShowComp.setBackgroundColor(Color.rgb(255-R,255-G,255-B));
        //show the color value
        ShowHex.setText(String.format("%02x", T)
                +String.format("%02x", C)+String.format("%02x", E));
        //some math so text shows (needs improvement for greys)
        ChangeHex.setHint(String.format("%02x", R)
                +String.format("%02x", G)+String.format("%02x", B));
        seekResultR.setProgress(T);
        seekResultG.setProgress(C);
        seekResultB.setProgress(E);

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
            SeekR.setProgress(Color.red(color));
            SeekG.setProgress(Color.green(color));
            SeekB.setProgress(Color.blue(color));
        }
    }
}