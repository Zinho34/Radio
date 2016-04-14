package com.app.zinho.radio;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Zinho on 24/03/2016.
 */
public class SocialNetworks extends AppCompatActivity
{
    /*@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_radio);
    }*/

    // Launch Facebook Application after clicking the button
    public void launchFacebookApp(View view)
    {
        Intent launchFacebookApp;

        try {
            launchFacebookApp = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
            //launchFacebookApp = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
            if ( launchFacebookApp == null )
                throw new PackageManager.NameNotFoundException();
            launchFacebookApp.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(launchFacebookApp);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Launch Twitter Application after clicking the button
    public void launchTwitterApp(View view)
    {
        Intent launchTwitterApp = getPackageManager().getLaunchIntentForPackage("com.twitter.android");
        startActivity(launchTwitterApp);
    }

    // Launch SoundClound Application after clicking the button
    public void launchSoundCloundApp(View view)
    {
        Intent launchSoundCloundApp = getPackageManager().getLaunchIntentForPackage("com.soundcloud.android.crop");
        startActivity(launchSoundCloundApp);
    }

    // Launch SnapChat Application after clicking the button
    public void launchSnapChatApp(View view)
    {
        Intent launchSnapChatApp = getPackageManager().getLaunchIntentForPackage("com.snapchat.android");
        startActivity(launchSnapChatApp);
    }

    // Launch Periscope Application after clicking the button
    public void launchPeriscopeApp(View view)
    {
        Intent launchPeriscopeApp = getPackageManager().getLaunchIntentForPackage("tv.periscope.android");
        startActivity(launchPeriscopeApp);
    }
}
