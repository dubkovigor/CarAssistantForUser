package com.example.carassistantforuserfragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.carassistantforuserfragments.fragment.AuthFragment;


public class MainActivity extends AppCompatActivity {


    AuthFragment authFragment;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authFragment = new AuthFragment();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragTag, authFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        if (FragmentManager.POP_BACK_STACK_INCLUSIVE == 1) {
            super.onBackPressed();
        } else
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
