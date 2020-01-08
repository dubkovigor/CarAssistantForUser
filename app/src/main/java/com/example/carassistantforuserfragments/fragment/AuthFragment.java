package com.example.carassistantforuserfragments.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.carassistantforuserfragments.R;
import com.example.carassistantforuserfragments.SendUserFromAuthOrRegistrToMain;
import com.example.carassistantforuserfragments.entity.UsCar;
import com.example.carassistantforuserfragments.entity.User;
import com.example.carassistantforuserfragments.service.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthFragment extends Fragment {

    private FragmentTransaction fragmentTransaction;

    private User user = new User();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RegistrFragment registrFragment = new RegistrFragment();
        ChoiceFragment choiceFragment = new ChoiceFragment();

        View view = inflater.inflate(R.layout.fragment_auth, container, false);

        EditText enterMobileNumber = view.findViewById(R.id.enterMobileNumber);

        Button signIn = view.findViewById(R.id.signIn);

        Button signUp = view.findViewById(R.id.changeActivityToRegistration);

        User user12 = new User();

        signUp.setOnClickListener(v -> {

            if (getFragmentManager() != null) {
                fragmentTransaction = getFragmentManager().beginTransaction();
            }
            fragmentTransaction.replace(R.id.fragTag, registrFragment).addToBackStack(null);
            fragmentTransaction.commit();
        });

        signIn.setOnClickListener(v -> {

            if (getFragmentManager() != null) {
                fragmentTransaction = getFragmentManager().beginTransaction();
            }
            user12.setPhoneNumber(enterMobileNumber.getText().toString());

            NetworkService.getInstance().getJSONApi().getUserByNumber(user12).enqueue(new Callback<UsCar>() {
                @Override
                public void onResponse(@NonNull Call<UsCar> call, @NonNull Response<UsCar> response) {
                    if (response.code() == 200) {

                        UsCar usCar;
                        usCar = response.body();

                        if (usCar != null) {
                            user = usCar.getUser();
                        }

                        SendUserFromAuthOrRegistrToMain.setUser(user);

                        fragmentTransaction.replace(R.id.fragTag, choiceFragment);
                        fragmentTransaction.commit();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UsCar> call, @NonNull Throwable t) {
                    Log.i("", "", t);
                }
            });
        });
        return view;
    }
}
