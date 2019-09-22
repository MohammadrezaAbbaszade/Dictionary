package com.example.dictionary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.dictionary.R;
import com.example.dictionary.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragments_container);
        if (fragment == null) {
            fm.beginTransaction().replace(R.id.fragments_container, new MainFragment())
                    .commit();
        }


    }
}
