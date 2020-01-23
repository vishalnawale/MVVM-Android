package com.demo.demo_application;

import android.Manifest;
import android.content.pm.PackageManager;
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
import android.net.Uri;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Main2Activity extends AppCompatActivity {
    private static final int COLOR_COMPONENT_ERROR_MARGIN = 10;
    ImageView imageView;
    Bitmap bmpGrayscale;
    TextView textView;
    private RenderScript mRs ;


    String TAG="Main2Activity";
    Button buttonColor;
    byte[] bytes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        imageView = (ImageView) findViewById(R.id.imageview1);
        textView = (TextView) findViewById(R.id.txt_val);

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache(true);

        buttonColor=findViewById(R.id.buttonColor);
        mRs = RenderScript.create(this);

        int early =  imageView.getImageAlpha();
        System.out.println("getalpha early : " + early);

        InputStream iS;
        // getResources().openRawResource()
        //  File file = new File(); // The input NV21 file
        int rID = getResources().getIdentifier("com.demo.demo_application:raw/"+"yuvscreen2", null, null);
      /*  if (!file.exists())
            return;*/
        iS = getResources().openRawResource(rID);
        // BMP file info
        int width = 1360, height = 900;
        try {
            bytes = new byte[iS.available()];

        }catch (Exception e){
            e.printStackTrace();
        }

        Bitmap bitmap1= convertYuvToRgbIntrinsic(mRs,bytes);

        imageView.setImageBitmap(toGrayscale(bitmap1));

       // convertYuvToBitmap();

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Matrix inverse = new Matrix();
                if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
                    bmpGrayscale = imageView.getDrawingCache();



                int pixel = bmpGrayscale.getPixel((int)event.getX(), (int)event.getY());

                printPixelARGB(pixel);

                buttonColor.setBackgroundColor(pixel);
                int red = Color.red(pixel);
                int blue = Color.blue(pixel);
                int green = Color.green(pixel);
                int alpha_val = Color.alpha(pixel);


                int color = (alpha_val & 0xff) << 24 | (blue & 0xff) << 16 | (green & 0xff) << 8 | (red & 0xff);

                int grey=(red+blue+green)/3;

                int greycolor=Color.argb(alpha_val,grey,grey,grey);

                int val=Color.alpha(greycolor);


                Log.d("VISHAL","red..."+red+"blue...."+blue+"green...."+green+"alpha...."+val+"Color....."+pixel);

                String s="red-"+red+",blue"+blue+",green"+green+",Alpha val"+val;
                textView.setBackgroundColor(color);
                textView.setText(s);
                return true;
            }
        });

    }


    public void printPixelARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);

        Log.d(TAG,"argb: " + alpha + ", " + red + ", " + green + ", " + blue);
    }


    private void convertYuvToBitmap(){

        InputStream iS;
        // getResources().openRawResource()
        //  File file = new File(); // The input NV21 file
        int rID = getResources().getIdentifier("com.demo.demo_application:raw/"+"yuvscreen2", null, null);
      /*  if (!file.exists())
            return;*/
        iS = getResources().openRawResource(rID);
        // BMP file info
        int width = 1360, height = 900;
        short pixelBits = 32;

        try {
            // Read all bytes
            byte[] bytes = new byte[iS.available()];

            //byte[] imagebyte=yuv420pToRGBA8888(bytes,width,height);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            YuvImage yuvImage = new YuvImage(bytes, ImageFormat.NV21, width, height, null);

            yuvImage.compressToJpeg(new Rect(0, 0, width, height), 100, out);

            
            byte[] imageBytes = out.toByteArray();
            Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);


            /*FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory() + "/imagename.png");
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.close();*/
            int numPixels = width*height;
            IntBuffer intBuffer = IntBuffer.allocate(width*height);

            Rect rect=new Rect(0,0,width,height);


           // imageView.setImageBitmap(toGrayscale(image));

            //imageView.setImageBitmap(image);


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


    public static Bitmap convertYuvToRgbIntrinsic(RenderScript rs, byte[] data) {

        int imageWidth =1360 ;
        int imageHeight = 900 ;

        ScriptIntrinsicYuvToRGB yuvToRgbIntrinsic = ScriptIntrinsicYuvToRGB.create(rs, Element.RGBA_8888(rs));

        // Create the input allocation  memory for Renderscript to work with
        Type.Builder yuvType = new Type.Builder(rs, Element.U8(rs))
                .setX(imageWidth)
                .setY(imageHeight)
                .setYuvFormat(android.graphics.ImageFormat.NV21);

        Allocation aIn = Allocation.createTyped(rs, yuvType.create(), Allocation.USAGE_SCRIPT);
        // Set the YUV frame data into the input allocation
        aIn.copyFrom(data);


        // Create the output allocation
        Type.Builder rgbType = new Type.Builder(rs, Element.RGBA_8888(rs))
                .setX(imageWidth)
                .setY(imageHeight);

        Allocation aOut = Allocation.createTyped(rs, rgbType.create(), Allocation.USAGE_SCRIPT);



        yuvToRgbIntrinsic.setInput(aIn);
        // Run the script for every pixel on the input allocation and put the result in aOut
        yuvToRgbIntrinsic.forEach(aOut);

        // Create an output bitmap and copy the result into that bitmap
        Bitmap outBitmap = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_4444);
        aOut.copyTo(outBitmap);

        return outBitmap ;

    }


    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);

        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    private Bitmap loadScaledBitmap(Uri src, int req_w, int req_h) throws FileNotFoundException {

        Bitmap bm = null;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getBaseContext().getContentResolver().openInputStream(src),
                null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, req_w, req_h);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeStream(
                getBaseContext().getContentResolver().openInputStream(src), null, options);

        return bm;
    }

    public int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    public byte[] yuv420pToRGBA8888(byte[] yuv, int width, int height) {
        int[] out=null;
        if (out.length < width * height) {
            throw new IllegalArgumentException("Size of out must be " + width * height);
        }
        if (yuv.length < width * height * 3.0 / 2) {
            throw new IllegalArgumentException("Size of yuv must be " + width * height * 3.0 / 2);
        }
        int size = width * height;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                /*accessing YUV420P elements*/
                int indexY = j * width + i;
                int indexU = (size + (j / 2) * (width / 2) + i / 2);
                int indexV = (int) (size * 1.25 + (j / 2) * (width / 2) + i / 2);

                // todo; this conversion to int and then later back to int really isn't required.
                // There's room for better work here.
                int Y = 0xFF & yuv[indexY];
                int U = 0xFF & yuv[indexU];
                int V = 0xFF & yuv[indexV];

                /*constants picked up from http://www.fourcc.org/fccyvrgb.php*/
                int R = (int) (Y + 1.402f * (V - 128));
                int G = (int) (Y - 0.344f * (U - 128) - 0.714f * (V - 128));
                int B = (int) (Y + 1.772f * (U - 128));

                /*clamping values*/
                R = R < 0 ? 0 : R;
                G = G < 0 ? 0 : G;
                B = B < 0 ? 0 : B;
                R = R > 255 ? 255 : R;
                G = G > 255 ? 255 : G;
                B = B > 255 ? 255 : B;

                out[width * j + i] = 0xff000000 + (R << 16) + (G << 8) + B;

            }
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(out.length);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(out);

        byte[] array = byteBuffer.array();

        return array;
    }


    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted1");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted2");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted2");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d(TAG, "External storage2");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                    //convertYuvToBitmap();
                }
                break;

          /*  case 3:
                Log.d(TAG, "External storage1");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                    SharePdfFile();
                }else{
                    progress.dismiss();
                }
                break;*/
        }
    }
}
