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
import com.example.carassistantforuserfragments.entity.User;
import com.example.carassistantforuserfragments.service.NetworkService;
import com.google.android.gms.common.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrFragment extends Fragment {

    private User user = new User();
    private FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registr, container, false);
        EditText email = view.findViewById(R.id.insertEmail);
        EditText phoneNumber = view.findViewById(R.id.insetPhoneNumber);
        EditText firstName = view.findViewById(R.id.firstName);
        EditText lastName = view.findViewById(R.id.lastName);

        Button button = view.findViewById(R.id.confirm);
        Button button1 = view.findViewById(R.id.backToAuthorization);

        AuthFragment authFragment = new AuthFragment();
        ChoiceFragment choiceFragment = new ChoiceFragment();

        button1.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragTag, authFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        button.setOnClickListener(v ->

                NetworkService.getInstance()
                        .getJSONApi().
                        registerUser(registerUser(email.getText().toString(),
                                phoneNumber.getText().toString(),
                                firstName.getText().toString(),
                                lastName.getText().toString()))
                        .enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                                user = response.body();

                                SendUserFromAuthOrRegistrToMain.setUser(user);

                                if (getFragmentManager() != null) {
                                    fragmentTransaction = getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragTag, choiceFragment).addToBackStack(null);
                                    fragmentTransaction.commit();
                                }

                            }

                            @Override
                            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                                Log.getStackTraceString(t);
                            }
                        }));
        return view;
    }

    private User registerUser(String email, String phoneNumber, String firstName, String lastName) {
        User registerUser = new User();
        registerUser.setEmail(email);
        registerUser.setFirstName(firstName);
        registerUser.setLastName(lastName);
        registerUser.setPhoneNumber(phoneNumber);
        return registerUser;
    }


}
