package com.tatlicilar.downtoup;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//public class EgitimIcerik1 extends AppCompatActivity {
public class EgitimIcerik1 extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egitim_icerik1);

        // her image butona click metodu koydum

      View button1 = findViewById(R.id.imageButton);
       View button2 = findViewById(R.id.imageButton2);
       View button3 = findViewById(R.id.imageButton3);
       button1.setOnClickListener(this);
       button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    // on click icin gerekli metod

   @Override
   public void onClick(View v) {

       // aynı anda tek ses için
       if(mp != null) mp.release();
//       if(mp != null) {
//           mp.stop();
//           mp.reset();
//           mp.release();
//       }

        // hangi imagebutton basıldıgı icin

       switch (v.getId()) {

           // inek butonu
           case R.id.imageButton:
              //Log.i("Test", "Cow Button");
               mp = MediaPlayer.create(this, R.raw.cow);
               break;

           // ordek
           case R.id.imageButton2:
               //Log.i("Test", "Duck Button");
               mp = MediaPlayer.create(this, R.raw.duck);
               break;

           // koyun
           case R.id.imageButton3:
               //Log.i("Test", "Sheep Button");
                mp = MediaPlayer.create(this, R.raw.sheep);
               break;

       }
       mp.seekTo(0);
       mp.start();
   }

   @Override
   public void onPause() {
       super.onPause();
       // media player bırakmak icin
       if(mp != null) mp.release();
    }
}
