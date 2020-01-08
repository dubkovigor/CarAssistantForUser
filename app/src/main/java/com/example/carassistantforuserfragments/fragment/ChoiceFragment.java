package com.example.carassistantforuserfragments.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.carassistantforuserfragments.R;

public class ChoiceFragment extends Fragment {

    FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_choise, container, false);

        Button build = view.findViewById(R.id.Navigation);
        Button control = view.findViewById(R.id.control);

        PointFragment pointFragment = new PointFragment();
        ControlFragment controlFragment = new ControlFragment();

        build.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                fragmentTransaction = getFragmentManager().beginTransaction();
            }

            fragmentTransaction.replace(R.id.fragTag, pointFragment).addToBackStack(null);
            fragmentTransaction.commit();
        });

        control.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                fragmentTransaction = getFragmentManager().beginTransaction();
            }

            fragmentTransaction.replace(R.id.fragTag, controlFragment).addToBackStack(null);
            fragmentTransaction.commit();
        });

        return view;
    }
}
