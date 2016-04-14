package com.app.zinho.radio;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class InterfaceRadio extends AppCompatActivity implements View.OnClickListener
{
    MediaPlayer m_myMediaPlayer;
    ImageButton m_myButton, m_facebook, m_twitter, m_soundclound, m_snapchat, m_periscope;
    TextView m_myTitle, m_myArtist;
    String m_url, m_urlData;
    int m_status;
    TrackInfoListener m_listener;
    SocialNetworks m_socialNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_radio);

        initView();
        initMediaplayer();
        initSoundInfo();
    }

    private void initView()
    {
        /* */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        m_myButton = (ImageButton)findViewById(R.id.imageButton);

        m_myArtist = (TextView) findViewById(R.id.artisteson);
        m_myTitle = (TextView) findViewById(R.id.titreson);

        m_socialNetwork = new SocialNetworks();

        m_facebook = (ImageButton) findViewById(R.id.facebookButton);
        m_twitter = (ImageButton) findViewById(R.id.twitterButton);
        m_soundclound = (ImageButton) findViewById(R.id.soundcloundButton);
        m_snapchat = (ImageButton) findViewById(R.id.snapchatButton);
        m_periscope = (ImageButton) findViewById(R.id.periscopeButton);

        /* */
        setSupportActionBar(toolbar);

        m_myButton.setOnClickListener(this);
        m_status = 0;

        m_facebook.setOnClickListener(this);
        m_twitter.setOnClickListener(this);
        m_soundclound.setOnClickListener(this);
        m_snapchat.setOnClickListener(this);
        m_periscope.setOnClickListener(this);

        /* */
        final Handler handler = new Handler();
        Timer timer = new Timer();
        m_listener = new TrackInfoListener();

        TimerTask doAsynchronousTask = new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            m_myArtist.setText(m_listener.m_artist);
                            m_myTitle.setText(m_listener.m_title);
                        }
                        catch (Exception e)
                        {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 1000); //execute in every 1000 ms

    }

    private void initMediaplayer()
    {
        try
        {
            m_myMediaPlayer = new MediaPlayer();
            m_url = new String("");
            m_myMediaPlayer.setDataSource(m_url);
            m_myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            m_myMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
            {
                @Override
                public void onPrepared(MediaPlayer mp)
                {
                    mp.start();
                }
            });
            m_myMediaPlayer.prepareAsync();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void initSoundInfo()
    {
        m_urlData = new String("");

        try
        {
            initTrackInfoListener(m_urlData);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void initTrackInfoListener(final String url)
    {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        m_listener = new TrackInfoListener();
        TimerTask doAsynchronousTask = new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            m_listener.execute(url);
                        }
                        catch (Exception e)
                        {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 1000); //execute in every 1000 ms
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_interface_radio, menu);

        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        ShareActionProvider m_Share = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Si comme moi, tu veux écouter de la bonne musique, connecte-toi sur www.chillin-radio.com");

        m_Share.setShareIntent(intent);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.imageButton:
                if (m_status == 0) {
                    m_myButton.setImageResource(R.drawable.play);
                    m_myMediaPlayer.stop();
                    m_status = 1;
                }
                else
                {
                    m_myButton.setImageResource(R.drawable.stop);
                    m_myMediaPlayer.reset();
                    try
                    {
                        m_myMediaPlayer.setDataSource(m_url);
                        m_myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        m_myMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
                        {
                            @Override
                            public void onPrepared(MediaPlayer mp)
                            {
                                mp.start();
                            }
                        });
                        m_myMediaPlayer.prepareAsync();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    m_status = 0;
                }
                break;
            case R.id.facebookButton:
                m_socialNetwork.launchFacebookApp(v);
                break;
            case R.id.twitterButton:
                m_socialNetwork.launchTwitterApp(v);
                break;
            case R.id.soundcloundButton:
                m_socialNetwork.launchSoundCloundApp(v);
                break;
            case R.id.snapchatButton:
                m_socialNetwork.launchSnapChatApp(v);
                break;
            case R.id.periscopeButton:
                m_socialNetwork.launchPeriscopeApp(v);
                break;
        }
    }
}
