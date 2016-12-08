package com.example.ngocphong.sqliteexample.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ngocphong.sqliteexample.R;
import com.example.ngocphong.sqliteexample.fragment.RunFragment;

public class RunActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new RunFragment();
    }

}
