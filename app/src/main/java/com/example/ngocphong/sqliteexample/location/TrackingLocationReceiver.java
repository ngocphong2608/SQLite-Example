package com.example.ngocphong.sqliteexample.location;

import android.content.Context;
import android.location.Location;

/**
 * Created by ngocphong on 12/12/2016.
 */
public class TrackingLocationReceiver extends LocationReceiver {
    @Override
    protected void onLocationReceived(Context c, Location loc) {
        RunManager.get(c).insertLocation(loc);
    }
}
