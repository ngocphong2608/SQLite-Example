package com.example.ngocphong.sqliteexample.fragment;


import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ngocphong.sqliteexample.R;
import com.example.ngocphong.sqliteexample.location.LocationReceiver;
import com.example.ngocphong.sqliteexample.location.RunManager;
import com.example.ngocphong.sqliteexample.sqlite.Run;


/**
 * A simple {@link Fragment} subclass.
 */
public class RunFragment extends Fragment {

    private RunManager mRunManager;
    private Run mRun;
    private Location mLastLocation;
    private Button mStartButton, mStopButton;
    private TextView mStartedTextView, mLatitudeTextView,
            mLongitudeTextView, mAltitudeTextView, mDurationTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mRunManager = RunManager.get(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run, container, false);
        mStartedTextView = (TextView)view.findViewById(R.id.run_startedTextView);
        mLatitudeTextView = (TextView)view.findViewById(R.id.run_latitudeTextView);
        mLongitudeTextView = (TextView)view.findViewById(R.id.run_longitudeTextView);
        mAltitudeTextView = (TextView)view.findViewById(R.id.run_altitudeTextView);
        mDurationTextView = (TextView)view.findViewById(R.id.run_durationTextView);
        mStartButton = (Button)view.findViewById(R.id.run_startButton);
        mStopButton = (Button)view.findViewById(R.id.run_stopButton);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRun = new Run();
//                mRunManager.startLocationUpdates();
                mRun = mRunManager.startNewRun();
                updateUI();
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mRunManager.stopLocationUpdates();
                mRunManager.stopRun();
                updateUI();
            }
        });

        updateUI();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(mLocationReceiver,
                new IntentFilter(RunManager.ACTION_LOCATION));
    }
    @Override
    public void onStop() {
        getActivity().unregisterReceiver(mLocationReceiver);
        super.onStop();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void updateUI() {
        boolean started = mRunManager.isTrackingRun();

        if (mRun != null)
            mStartedTextView.setText(mRun.getStartDate().toString());
        int durationSeconds = 0;
        if (mRun != null && mLastLocation != null) {
            mLastLocation.getTime();
            durationSeconds = mRun.getDurationSeconds(mLastLocation.getTime());
            mLatitudeTextView.setText(Double.toString(mLastLocation.getLatitude()));
            mLongitudeTextView.setText(Double.toString(mLastLocation.getLongitude()));
            mAltitudeTextView.setText(Double.toString(mLastLocation.getAltitude()));
        }
        mDurationTextView.setText(Run.formatDuration(durationSeconds));

        mStartButton.setEnabled(!started);
        mStopButton.setEnabled(started);
    }

    private BroadcastReceiver mLocationReceiver = new LocationReceiver() {
        @Override
        protected void onLocationReceived(Context context, Location loc) {
            mLastLocation = loc;
            if (isVisible())
                updateUI();
        }
        @Override
        protected void onProviderEnabledChanged(boolean enabled) {
            int toastText = enabled ? R.string.gps_enabled : R.string.gps_disabled;
            Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG).show();
        }
    };
}
