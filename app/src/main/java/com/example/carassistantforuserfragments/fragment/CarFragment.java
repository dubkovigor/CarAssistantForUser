package com.example.carassistantforuserfragments.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.carassistantforuserfragments.CarStatic;
import com.example.carassistantforuserfragments.R;
import com.example.carassistantforuserfragments.entity.Car;
import com.example.carassistantforuserfragments.service.NetworkService;
import com.google.android.gms.common.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.carassistantforuserfragments.constants.ConstantsList.STATES;


public class CarFragment extends Fragment {

    private Logger logger;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car, container, false);

        TextView statusDoor = view.findViewById(R.id.state);
        TextView name = view.findViewById(R.id.name);
        TextView number = view.findViewById(R.id.number);
        Button button = view.findViewById(R.id.unlock);
        Button button1 = view.findViewById(R.id.lock);

        name.setText("Имя машины : " + CarStatic.getCar().getCarName());
        number.setText("Гос. номер: " + CarStatic.getCar().getPlateNumber());

        Car car = new Car();

        button1.setOnClickListener(v -> {
            car.setDoorsState("locked");
            NetworkService.getInstance().getJSONApi().doorState(CarStatic.getCar().getCarId(), car).enqueue(new Callback<Car>() {
                @Override
                public void onResponse(@NonNull Call<Car> call, @NonNull Response<Car> response) {

                    if (response.body() != null) {
                        statusDoor.setText(STATES.get(response.body().getDoorsState()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Car> call, @NonNull Throwable t) {
                    logger.wtf(t);
                }
            });
        });

        button.setOnClickListener(v -> {
            car.setDoorsState("unlocked");
            NetworkService.getInstance().getJSONApi().doorState(CarStatic.getCar().getCarId(), car).enqueue(new Callback<Car>() {
                @Override
                public void onResponse(@NonNull Call<Car> call, @NonNull Response<Car> response) {

                    if (response.body() != null) {
                        statusDoor.setText(STATES.get(response.body().getDoorsState()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Car> call, @NonNull Throwable t) {
                    logger.wtf(t);
                }
            });
        });

        return view;
    }
}
