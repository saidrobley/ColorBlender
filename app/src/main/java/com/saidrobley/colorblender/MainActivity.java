package com.saidrobley.colorblender;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button color1;
    private TextView hexText1;
    private TextView hexText2;
    private TextView color1TextView;
    private TextView color2TextView;
    private Button color2;
    private String color1Hex;
    private String color2Hex;
    private int c1;
    private int c2;
    private SeekBar seekBar;
    private TextView mixColorTextView;
    private TextView seekNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        color1 = (Button) findViewById(R.id.color1Button);
        color2 = (Button) findViewById(R.id.color2Button);
        color1TextView = (TextView) findViewById(R.id.color1TextView);
        color2TextView = (TextView) findViewById(R.id.color2TextView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        mixColorTextView = (TextView) findViewById(R.id.mixColorTextView);
        seekNumber = (TextView) findViewById(R.id.seekNumber);

        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().
                        getLaunchIntentForPackage("com.saidrobley.colorfinder");

                    startActivity(launchIntent);

            }

        });

        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getPackageManager().
                        getLaunchIntentForPackage("com.saidrobley.colorfinder");
                startActivity(intent);

            }
        });


        Intent i = getIntent();
        color1Hex = i.getStringExtra("color1");
        color2Hex = i.getStringExtra("color2");
        if(color1Hex != null && color2Hex == null){
            c1 = Color.parseColor(color1Hex);
            color1TextView.setBackgroundColor(c1);
        }
        else if(color1Hex != null && color2Hex !=null){
            c1 = Color.parseColor(color1Hex);
            c2 = Color.parseColor(color2Hex);
            color1TextView.setBackgroundColor(c1);
            color2TextView.setBackgroundColor(c2);
        }
        seekNumber.setText("0/100");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                update(seekBar);
                seekNumber.setText(progress + "/" + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        update(seekBar);
    }


    public void update(SeekBar skBar){
        int colorStart = 0;
        int colorEnd = 0;
        if(color1Hex != null)
            colorStart = Color.parseColor(color1Hex);
        if(color2Hex != null)
          colorEnd = Color.parseColor(color2Hex);
        if(colorStart !=0 && colorEnd !=0)

        mixColorTextView.setBackgroundColor(blendColors(colorEnd, colorStart,
                skBar.getProgress()/100f));
    }

    private static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);

    }


}
