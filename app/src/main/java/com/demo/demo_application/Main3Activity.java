package com.demo.demo_application;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Main3Activity extends AppCompatActivity {


    private View color100, color90, color80, color70, color60, color50, color40, color30, color20, color10, color0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        color100 = (View) findViewById(R.id.color100);
        color90 = (View) findViewById(R.id.color90);
        color80 = (View) findViewById(R.id.color80);
        color70 = (View) findViewById(R.id.color70);
        color60 = (View) findViewById(R.id.color60);
        color50 = (View) findViewById(R.id.color50);
        color40 = (View) findViewById(R.id.color40);
        color30 = (View) findViewById(R.id.color30);
        color20 = (View) findViewById(R.id.color20);
        color10 = (View) findViewById(R.id.color10);
        color0 = (View) findViewById(R.id.color0);

        color100.setBackgroundColor(Color.GREEN);
  /*      color90.setBackgroundColor(getColorWithAlpha(Color.GREEN, 1f));
        color90.setBackgroundColor(getColorWithAlpha(Color.GREEN, 0.9f));
        color80.setBackgroundColor(getColorWithAlpha(Color.GREEN, 0.8f));
        color70.setBackgroundColor(getColorWithAlpha(Color.GREEN, 0.7f));
        color60.setBackgroundColor(getColorWithAlpha(Color.GREEN, 0.6f));
        color50.setBackgroundColor(getColorWithAlpha(Color.GREEN, 0.5f));
        color40.setBackgroundColor(getColorWithAlpha(Color.GREEN, 0.4f));
        color30.setBackgroundColor(getColorWithAlpha(Color.GREEN, 0.3f));
        color20.setBackgroundColor(getColorWithAlpha(Color.GREEN, 0.2f));*/
        color10.setBackgroundColor(getColorWithAlpha(Color.GREEN, 0.89f));
       /* color0.setBackgroundColor(getColorWithAlpha(Color.GREEN, 0.0f));*/

        Log.d("VISHAL************",ColorToHex(getColorWithAlpha(Color.GREEN, 0.70f)));
        Log.d("VISHAL************",ColorToHex(getColorWithAlpha(Color.GREEN, 0.69f)));
        Log.d("VISHAL************",ColorToHex(getColorWithAlpha(Color.GREEN, 0.68f)));
        Log.d("VISHAL************",ColorToHex(getColorWithAlpha(Color.GREEN, 0.67f)));
        Log.d("VISHAL************",ColorToHex(getColorWithAlpha(Color.GREEN, 0.66f)));
        Log.d("VISHAL************",ColorToHex(getColorWithAlpha(Color.GREEN, 0.65f)));
        Log.d("VISHAL************",ColorToHex(getColorWithAlpha(Color.GREEN, 0.64f)));
        Log.d("VISHAL************",ColorToHex(getColorWithAlpha(Color.GREEN, 0.63f)));
        Log.d("VISHAL************",ColorToHex(getColorWithAlpha(Color.GREEN, 0.62f)));
        Log.d("VISHAL************",ColorToHex(getColorWithAlpha(Color.GREEN, 0.61f)));
        Log.d("VISHAL************",ColorToHex(getColorWithAlpha(Color.GREEN, 0.0f)));



    }
    public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

    public static String ColorToHex(int color) {
        int alpha = android.graphics.Color.alpha(color);
        int blue = android.graphics.Color.blue(color);
        int green = android.graphics.Color.green(color);
        int red = android.graphics.Color.red(color);

        String alphaHex = To00Hex(alpha);
        String blueHex = To00Hex(blue);
        String greenHex = To00Hex(green);
        String redHex = To00Hex(red);

        // hexBinary value: aabbggrr
        StringBuilder str = new StringBuilder("#");
        str.append(alphaHex);
        str.append(blueHex);
        str.append(greenHex);
        str.append(redHex );

        return str.toString();
    }

    private static String To00Hex(int value) {
        String hex = "00".concat(Integer.toHexString(value));
        return hex.substring(hex.length()-2, hex.length());
    }
}
