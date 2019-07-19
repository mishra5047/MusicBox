package com.example.musicbox;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   private MediaPlayer mediaPlayer;
   private ImageView artistImage;
   private TextView leftTime;
   private TextView rightTime;
   private SeekBar seekBar;
   private Button prevButton;
   private Button playButton;
    private Button nextButton;
   private Thread thread;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpUi();

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
             if (fromUser){
                 mediaPlayer.seekTo(progress);
             }
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
              int currentPos = mediaPlayer.getCurrentPosition();
              int duration = mediaPlayer.getDuration();
             leftTime.setText(dateFormat.format(new Date(currentPos)));
             rightTime.setText(dateFormat.format(new Date(duration - currentPos)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }

public void setUpUi(){


        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.savage);
        artistImage = (ImageView) findViewById(R.id.imageView);
        leftTime = (TextView) findViewById(R.id.lefttime);
        rightTime = (TextView) findViewById(R.id.righttime);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        prevButton = (Button) findViewById(R.id.prevbutton);
        playButton = (Button) findViewById(R.id.playbutton);
        nextButton = (Button) findViewById(R.id.nextbutton);

        prevButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

}
    public void onClick(View v){
        switch (v.getId()){
            case R.id.prevbutton:

                break;

            case R.id.playbutton:
              if (mediaPlayer.isPlaying()){
                  pauseMusic();
              }else {
                  startMusic();
              }
                break;

            case R.id.nextbutton:

                break;

        }
    }
public void backmusic()
{
    if (mediaPlayer.isPlaying()){
        mediaPlayer.seekTo(0);
    }
}
public void nextmusic()
{
    if(mediaPlayer.isPlaying()){
        mediaPlayer.seekTo(mediaPlayer.getDuration() - 1000);
    }
}
public void pauseMusic(){
        if (mediaPlayer !=null){
            mediaPlayer.pause();
            playButton.setBackgroundResource(android.R.drawable.ic_media_play);
        }
}

public void startMusic(){
        if (mediaPlayer !=null){
            mediaPlayer.start();
             playButton.setBackgroundResource(android.R.drawable.ic_media_pause);
            updateThread();
        }
}

public void updateThread(){
        thread = new Thread(){
            @Override
            public void run() {

                try {

                    while (mediaPlayer !=null && mediaPlayer.isPlaying()) {

                    }
                    Thread.sleep(50);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int newPosition = mediaPlayer.getCurrentPosition();
                            int newMax = mediaPlayer.getDuration();
                            seekBar.setMax(newMax);
                            seekBar.setProgress(newPosition);

                            leftTime.setText(String.valueOf(new java.text.SimpleDateFormat("mm:ss").format(new Date(mediaPlayer.getCurrentPosition()))));

                            rightTime.setText(String.valueOf(new java.text.SimpleDateFormat("mm:ss").format(new Date(mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition()))));


                        }
                    });

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
thread.start();
    }
    @Override
    protected void onDestroy() {
        if(mediaPlayer !=null && mediaPlayer.isPlaying()){
       mediaPlayer.stop();
       mediaPlayer.release();
        mediaPlayer = null;

        }
        thread.interrupt();
        thread = null;
        super.onDestroy();
    }
}
