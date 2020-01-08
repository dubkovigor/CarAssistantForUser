package com.example.carassistantforuserfragments.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.carassistantforuserfragments.CarStatic;
import com.example.carassistantforuserfragments.LstViewAdapter;
import com.example.carassistantforuserfragments.R;
import com.example.carassistantforuserfragments.SendUserFromAuthOrRegistrToMain;
import com.example.carassistantforuserfragments.entity.Car;
import com.example.carassistantforuserfragments.entity.User;
import com.example.carassistantforuserfragments.service.NetworkService;
import com.google.android.gms.common.logging.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControlFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    Collection<Car> cars;
    private User user;
    private ListView lstview;
    private View view;
    private Logger logger;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_control, container, false);
        cars = new ArrayList<>();

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        user = SendUserFromAuthOrRegistrToMain.getUser();

        TextView firstNameMainActivity = view.findViewById(R.id.firstNameMainActivity);
        TextView lastNameMainActivity = view.findViewById(R.id.lastNameMainActivity);
        TextView emailMainActivity = view.findViewById(R.id.emailMainActivity);
        TextView phoneNumberMainActivity = view.findViewById(R.id.phoneNumberMainActivity);

        firstNameMainActivity.append(user.getFirstName());
        lastNameMainActivity.append(user.getLastName());
        emailMainActivity.append(user.getEmail());
        phoneNumberMainActivity.append(user.getPhoneNumber());

        lstview = view.findViewById(R.id.listOfCars);
        lstview.setOnItemClickListener((parent, view1, position, id) -> Toast.makeText(view.getContext(),
                "An item of the ListView is clicked.", Toast.LENGTH_LONG).show());

        getCars();

        return view;
    }

    private void getCars() {
        NetworkService.getInstance()
                .getJSONApi().findCarsByUserId(user.getUserId()).enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(@NonNull Call<List<Car>> call, @NonNull Response<List<Car>> response) {

                cars = response.body();

                LstViewAdapter adapter = new LstViewAdapter(view.getContext(), R.layout.list_item, R.id.carName, response.body());
                lstview.setAdapter(adapter);

                lstview.setOnItemClickListener((parent, view1, position, id) -> {
                    CarStatic.setCar(adapter.getItem(position));

                    CarFragment carFragment = new CarFragment();


                    if (getFragmentManager() != null) {
                        fragmentTransaction = getFragmentManager().beginTransaction();
                    }
                    fragmentTransaction.replace(R.id.fragTag, carFragment).addToBackStack(null);
                    fragmentTransaction.commit();
                });

            }

            @Override
            public void onFailure(@NonNull Call<List<Car>> call, @NonNull Throwable t) {
                logger.wtf(t);
            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
            // Отменяем анимацию обновления
            mSwipeRefreshLayout.setRefreshing(false);
            getCars();

        }, 0);
    }

}
