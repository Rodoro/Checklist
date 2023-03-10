package com.example.checklist;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class PreviewActivity extends AppCompatActivity {
    private SharedPreferences pref;
    ImageView imageView;
    Matrix matrix = new Matrix();

    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        pref = getSharedPreferences("MyPref", MODE_PRIVATE);

        imageView = findViewById(R.id.ivPhoto);
        Bundle arguments = getIntent().getExtras();
        matrix.postRotate(90);
        Bitmap img = BitmapFactory.decodeFile(arguments.getString("img"));
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(img, img.getWidth(), img.getHeight(), true);
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        imageView.setImageBitmap(rotatedBitmap);
    }

    public void bCancel(View view){
        goHome();
    }

    public void bScan(View view){
        var amount = pref.getString("summaryAmount", "0");
        if (amount != null) {
            var editor = pref.edit();
            editor.putString("summaryAmount", Integer.toString(ThreadLocalRandom.current().nextInt(50, 200 + 1) + Integer.parseInt(amount))).apply();
        }
        goHome();
    }

    private void goHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
