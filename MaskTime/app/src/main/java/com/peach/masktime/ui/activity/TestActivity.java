package com.peach.masktime.ui.activity;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.peach.masktime.R;

public class TestActivity extends Activity {
    private Button lButton;
    private Button rButton;
    private TextView myTextView;
    private MediaPlayer mMediaPlayer01 = null;
    private AudioTrack aAudioTrack01 = null;
    private String strFilePath = "/sdcard/test.caf";
    private float midVol = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);

        lButton = (Button) findViewById(R.id.ButtonL);
        rButton = (Button) findViewById(R.id.ButtonR);
        myTextView = (TextView) findViewById(R.id.myTextView);

        // left button response
        lButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                playSound(strFilePath, 0);

            }
        });

        // right button response
        rButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                playSound(strFilePath, 1);
            }
        });
    }

    // iChannel = 0 means left channel test, iChannel = 1 means right channel test.
    private void playSound(String strPath, int iChannel) {
        // If now is playing...
        if (aAudioTrack01 != null) {
            aAudioTrack01.release();
            aAudioTrack01 = null;
        }

        // Get the AudioTrack minimum buffer size
        int iMinBufSize = AudioTrack.getMinBufferSize(44100,
                AudioFormat.CHANNEL_CONFIGURATION_STEREO,
                AudioFormat.ENCODING_PCM_16BIT);
        if (iMinBufSize == AudioTrack.ERROR_BAD_VALUE || iMinBufSize == AudioTrack.ERROR) {
            return;
        }

        // Constructor a AudioTrack object
        try {
            aAudioTrack01 = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
                    AudioFormat.CHANNEL_CONFIGURATION_STEREO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    iMinBufSize,
                    AudioTrack.MODE_STREAM);
        } catch (IllegalArgumentException iae) {
            myTextView.setText("new AudioTrack Exceeption:" + iae.toString());
            iae.printStackTrace();
        }

        // Write data to buffer
        byte data[] = new byte[iMinBufSize];
        aAudioTrack01.write(data, 0, data.length);
        aAudioTrack01.write(data, 0, data.length);
        float lValue = 0;
        float rValue = 0;

        if (iChannel == 0) {
            lValue = 1.0f;
            rValue = 0.0f;
        } else if (iChannel == 1) {
            lValue = 0.0f;
            rValue = 1.0f;
        }

        aAudioTrack01.play();
        if (aAudioTrack01.setStereoVolume(lValue, rValue) == AudioTrack.SUCCESS) {
            myTextView.setText("setStereoVolume successfully!");
        }
        aAudioTrack01.stop();
        if (aAudioTrack01.setStereoVolume(midVol, midVol) == AudioTrack.SUCCESS) {
            myTextView.setText("Restore setStereoVolume successfully!");
        }
        aAudioTrack01.release();
        aAudioTrack01 = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO Auto-generated method stub
        if (aAudioTrack01 != null) {
            aAudioTrack01.setStereoVolume(midVol, midVol);
            aAudioTrack01.release();
            aAudioTrack01 = null;
        }
    }
}
