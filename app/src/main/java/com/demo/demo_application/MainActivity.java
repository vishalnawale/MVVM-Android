package com.demo.demo_application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Environment;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView imageView,secondimage;
    TextView textView;
    Bitmap bitmap;
    Button buttonColor;
    Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageview1);
        //secondimage=(ImageView) findViewById(R.id.imageview2);
        textView = (TextView) findViewById(R.id.txt_val);

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache(true);

        buttonColor=findViewById(R.id.buttonColor);

    /*    secondimage.setDrawingCacheEnabled(true);
        secondimage.buildDrawingCache(true);
*/

        int early =  imageView.getImageAlpha();
        System.out.println("getalpha early : " + early);


        int iconColor = 0x99000000;
        int iconPartialColor = 0x3d000000;

        //final int color1=mixSrcOver(Color.RED,iconColor);
        String strColor = String.format("#%06X", 0xFFFFFF & iconColor);
        String strColor1 = String.format("#%06X", 0xFFFFFF & iconPartialColor);
        //Log.d("VISHAL======", String.valueOf(color1)+"iconColor======"+strColor+"iconPartialColor"+strColor1);


        //convertYuvToBitmap();

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Matrix inverse = new Matrix();
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE)
                    bitmap = imageView.getDrawingCache();
                int pixel = bitmap.getPixel((int ) motionEvent.getX(), (int)motionEvent.getY());


                // imageView.setImageAlpha(80);

                Log.d("VISHAL","pixel......"+String.valueOf(pixel));
                // Color c = new Color(imageView.getRGB(x, y), true);
                Bitmap shadows = bitmap.extractAlpha();
                Bitmap.Config bt = shadows.getConfig() ;

                // System.out.println(" extract "  + shadows.setPixel(););

                imageView.getImageMatrix().invert(inverse);
                float[] pts = {
                        motionEvent.getX(), motionEvent.getY()
                };
                inverse.mapPoints(pts);
                Log.d("VISHAL", "onTouch x: " + Math.floor(pts[0]) + ", y: " + Math.floor(pts[1]));


                int colour = bitmap.getPixel((int)motionEvent.getX(), (int)motionEvent.getY());

                int colorvalue=mixSrcOver(Color.WHITE,colour);
                Log.d("VISHAL+++++++++++", String.valueOf(Color.alpha(colorvalue)));
                String hexaVal=ColorToHex(colour);
                Log.d("VISHAL======hexaVal",hexaVal);
                getHexaColor(1,hexaVal);

               // buttonColor.setBackgroundColor(colour);
               // buttonColor.setBackgroundColor(getColorWithAlpha(Color.GREEN,0.33f));
                buttonColor.setBackgroundColor(colorvalue);
                int red = Color.red(colour);
                int blue = Color.blue(colour);
                int green = Color.green(colour);
                int alpha_val = Color.alpha(colour);

                Log.d("VISHAL","red..."+red+"blue...."+blue+"green...."+green+"alpha...."+alpha_val+"Color....."+colour);






                int y =  imageView.getImageAlpha();
                System.out.println("getalpha : " + y);

                Log.d("VISHAL",String.valueOf(y));
                int r = Color.red(pixel);
                int g = Color.green(pixel);
                int b = Color.blue(pixel);
                int alp = Color.alpha(pixel);
                int alpha = (pixel >> 24) & 0xff;
                System.out.println("aplaaaaaa  "  +alpha );

                String hexa = String.format("#%02x%02x%02x", r, g, b);

                //int hexa=  Color.rgb(91,251,91);
                Color f =  Color.valueOf(pixel);



                final float[] hsl = new float[] { 0f, 0f, 0f };
                ColorUtils.colorToHSL(g, hsl);

                final int a = Color.alpha(g);
                final int result = ColorUtils.HSLToColor(hsl);
                System.out.println("result  " + result );

                double lu = ColorUtils.calculateLuminance(pixel);
                System.out.println("liminance  " + lu );

                double lu_green = ColorUtils.calculateLuminance(g);
                System.out.println("liminance_green " + lu_green );

                int util =  ColorUtils.setAlphaComponent(result, a);
                System.out.println("uitils " + util );

                imageView.isOpaque();

                ColorUtils.setAlphaComponent(g, 50);
               // textView.setBackgroundColor(Color.argb(150,91,251,91));
                textView.setBackgroundColor(Color.rgb(r,g,b));
                textView.setText("R(" + r + ")\n" + "G(" +g + ")\n" + "B("+b + ")\n" + "alpha(" + alp + ")\n"  +")\n" +"f("+ f + "o( " +"====hexa...."+hexa) ;
                return true ;
            }
        });



        /*secondimage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Matrix inverse = new Matrix();
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE)
                    bitmap = secondimage.getDrawingCache();
                int pixel = bitmap.getPixel((int ) motionEvent.getX(), (int)motionEvent.getY());

                // imageView.setImageAlpha(80);

                Log.d("VISHAL","pixel......"+String.valueOf(pixel));
                // Color c = new Color(imageView.getRGB(x, y), true);
                Bitmap shadows = bitmap.extractAlpha();
                Bitmap.Config bt = shadows.getConfig() ;

                // System.out.println(" extract "  + shadows.setPixel(););

                secondimage.getImageMatrix().invert(inverse);
                float[] pts = {
                        motionEvent.getX(), motionEvent.getY()
                };
                inverse.mapPoints(pts);
                Log.d("VISHAL", "onTouch x: " + Math.floor(pts[0]) + ", y: " + Math.floor(pts[1]));


                int colour = bitmap.getPixel((int)motionEvent.getX(), (int)motionEvent.getY());

                int red = Color.red(colour);
                int blue = Color.blue(colour);
                int green = Color.green(colour);
                int alpha_val = Color.alpha(colour);

                Log.d("VISHAL","red..."+red+"blue...."+blue+"green...."+green+"alpha...."+alpha_val+"Color....."+colour);






                int y =  secondimage .getImageAlpha();
                System.out.println("getalpha : " + y);

                Log.d("VISHAL",String.valueOf(y));
                int r = Color.red(pixel);
                int g = Color.green(pixel);
                int b = Color.blue(pixel);
                int alp = Color.alpha(pixel);
                int alpha = (pixel >> 24) & 0xff;
                System.out.println("aplaaaaaa  "  +alpha );
                String hex = String.format("#%02x%02x%02x", 0,r, g, b);

                Color f =  Color.valueOf(pixel);

                final float[] hsl = new float[] { 0f, 0f, 0f };
                ColorUtils.colorToHSL(g, hsl);

                final int a = Color.alpha(g);
                final int result = ColorUtils.HSLToColor(hsl);
                System.out.println("result  " + result );

                double lu = ColorUtils.calculateLuminance(pixel);
                System.out.println("liminance  " + lu );

                double lu_green = ColorUtils.calculateLuminance(g);
                System.out.println("liminance_green " + lu_green );

                int util =  ColorUtils.setAlphaComponent(result, a);
                System.out.println("uitils " + util );

                secondimage.isOpaque();

                ColorUtils.setAlphaComponent(g, 50);


                textView.setBackgroundColor(Color.argb(0,91,251,91));
                textView.setText("R(" + r + ")\n" + "G(" +g + ")\n" + "B("+b + ")\n" + "alpha(" + alp + ")\n"  +")\n" +"f("+ f + "o( " +"hex"+hex) ;
                return true ;
            }
        });*/

    }



    private void convertYuvToBitmap(){

        InputStream iS;
        // getResources().openRawResource()
      //  File file = new File(); // The input NV21 file
        int rID = getResources().getIdentifier("com.demo.demo_application:raw/"+"yuvscreen1", null, null);
      /*  if (!file.exists())
            return;*/
        iS = getResources().openRawResource(rID);
        // BMP file info
        int width = 2048, height = 1536;
        short pixelBits = 32;

        try {
            // Read all bytes
            byte[] bytes = new byte[iS.available()];

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            YuvImage yuvImage = new YuvImage(bytes, ImageFormat.NV21, width, height, null);
            yuvImage.compressToJpeg(new Rect(0, 0, width, height), 50, out);
            byte[] imageBytes = out.toByteArray();
            Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            imageView.setImageBitmap(toGrayscale(image));




            //Bitmap sb = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        /*    int[] data = NV21.yuv2rgb(bytes, width, height);
            BMP bmp = new BMP(width, height, pixelBits, data);
            bmp.saveBMP("android.resource://com.demo.demo_application/res/nv21.bmp"); */// The output BMP file

           // imageView.setImageBitmap(sb);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Conversion is done.");
    }

    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
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
    public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }
    private static String To00Hex(int value) {
        String hex = "00".concat(Integer.toHexString(value));
        return hex.substring(hex.length()-2, hex.length());
    }

    private void getHexaColor(int val,String hexaColor){


        ArrayList<String> hexval=new ArrayList<>();
//#5bfb5b =aarrggbb
        String hex ;
        String hexa = String.format("#%02x%02x%02x", 255,91, 251, 91);

        for(int i=val;i<=255;i++){

            hex = String.format("#%02x%02x%02x",i, 0, 255, 0);
            hexval.add(hex);
            if(hex.equals(hexaColor)){
                Log.d("VishaL.....found","Found");
            }
            Log.d("VishaL.....",hex+"i value..."+i+"hexa"+hexaColor);
        }

       /* do {



            hex = String.format("#%02x%02x%02x",val, 91, 251, 91);

            val++;
        }while (val==255);*/



    }
    private int mixSrcOver(int background, int foreground) {
                int bgAlpha = Color.alpha(background);
                int bgRed = Color.red(background);
                int bgGreen = Color.green(background);
                int bgBlue = Color.blue(background);

                int fgAlpha = Color.alpha(foreground);
                int fgRed = Color.red(foreground);
                int fgGreen = Color.green(foreground);
                int fgBlue = Color.blue(foreground);

                Log.d("VISHAL***********", "fgAlpha"+fgAlpha+"bgAlpha"+bgAlpha);
                return Color.argb(fgAlpha + (255 - fgAlpha) * bgAlpha / 255,
                                    fgRed + (255 - fgAlpha) * bgRed / 255,
                                    fgGreen + (255 - fgAlpha) * bgGreen / 255,
                                    fgBlue + (255 - fgAlpha) * bgBlue / 255);
            }

}
