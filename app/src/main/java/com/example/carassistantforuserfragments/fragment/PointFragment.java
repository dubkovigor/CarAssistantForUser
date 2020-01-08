package com.example.carassistantforuserfragments.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.carassistantforuserfragments.R;
import com.example.carassistantforuserfragments.SendPoints;
import com.example.carassistantforuserfragments.entity.map.ResponseFromGoogle;
import com.example.carassistantforuserfragments.entity.map.Value;
import com.example.carassistantforuserfragments.service.NetworkService1;
import com.google.android.gms.common.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.carassistantforuserfragments.constants.ConstantsList.*;


public class PointFragment extends Fragment {

    private FragmentTransaction fragmentTransaction;
    private MapsFragment mapsFragment;

    private Logger logger;

    private ArrayList<Long> allTimes = new ArrayList<>();
    private ArrayList<String> points = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point, container, false);

        Button button = view.findViewById(R.id.buildRouteWithOptimalDistance);
        Button button1 = view.findViewById(R.id.buildRouteWithOptimalTime);


        button.setOnClickListener(v -> buttonListener("distance", view));

        button1.setOnClickListener(v -> buttonListener("duration", view));

        return view;
    }

    private void buttonListener(String optimizationProperty, View view) {

        EditText fistPoint = view.findViewById(R.id.fistPoint);
        EditText secondPoint = view.findViewById(R.id.secondPoint);
        EditText thirdPoint = view.findViewById(R.id.thirdPoint);
        EditText fourPoint = view.findViewById(R.id.fourPoint);

        String firstPlace = fistPoint.getText().toString();
        String secondPlace = secondPoint.getText().toString();
        String thirdPlace = thirdPoint.getText().toString();
        String fourPlace = fourPoint.getText().toString();

        if (checkIsAllFieldsIsNotNullAndEmpty(firstPlace, secondPlace, thirdPlace, fourPlace)) {

            String origins = concatString(firstPlace, secondPlace, thirdPlace, fourPlace);

            points.add(firstPlace);
            points.add(secondPlace);
            points.add(thirdPlace);
            points.add(fourPlace);

            NetworkService1.getInstance().getJSONApi().getAllRows(origins, origins, MODE, LANGUAGE, GOOGLE_KEY, SENSOR).enqueue(new Callback<ResponseFromGoogle>() {
                @Override
                public void onResponse(@NonNull Call<ResponseFromGoogle> call, @NonNull Response<ResponseFromGoogle> response) {
                    configureOptimalRoute(response.body(), optimizationProperty, view);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseFromGoogle> call, @NonNull Throwable t) {
                    logger.wtf(t);
                }
            });
        } else {
            DialogFragment dialogFragment = new DialogFragment();

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Ошибка!")
                    .setMessage("Заполните все поля!")
                    .setPositiveButton("Понял, ща заполню!", (dialog, id) -> dialog.cancel());

            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    private void configureOptimalRoute(ResponseFromGoogle response, String optimizationProperty, View view) {
        int size = 0;
        if (response != null) {
            size = response.getRows().size();
        }
        long[][] matrix1 = new long[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Value value = response.getRows().get(i).getElements().get(j).getValueByName(optimizationProperty);
                matrix1[i][j] = value.getValue();
            }
        }

        long dist = 0;

        ArrayList<String> possibleValues = sort(size);

        for (int i = 0; i < possibleValues.size() - 1; i++) {

            for (int j = 0; j < possibleValues.get(i).length() - 1; j++) {

                char ch = possibleValues.get(i).charAt(j);
                char ch1 = possibleValues.get(i).charAt(j + 1);

                dist += matrix1[(int) ch - 48][(int) ch1 - 48];

            }

            allTimes.add(dist);
            dist = 0;
        }

        long minValue1 = Collections.min(allTimes);

        int number = 0;

        for (int i = 0; i < allTimes.size(); i++) {

            if (allTimes.get(i) == minValue1) {
                number = i;
            }
        }

        String best = possibleValues.get(number);
        StringBuilder request = new StringBuilder();

        for (int i = 1; i < best.toCharArray().length - 1; i++) {

            request.append("via:").append(points.get((int) best.charAt(i) - 48)).append("|");
        }

        String origins = points.get((int) best.charAt(0) - 48);
        String destination = points.get((int) best.charAt(points.size() - 1) - 48);
        String waypoints = request.toString();
        String waypointOne = points.get((int) best.charAt(1) - 48);
        String waypointTwo = points.get((int) best.charAt(2) - 48);

        SendPoints.setOrigin(origins);
        SendPoints.setDestination(destination);
        SendPoints.setVia(waypoints);


        String alertMessage = "1. " + origins + "\n2. " + waypointOne + "\n3. " + waypointTwo + " \n4. " + destination + "\n";

        if ("distance".equalsIgnoreCase(optimizationProperty)) {
            alertMessage = alertMessage + "Расстояние: " + minValue1 / 1000 + " км";
        } else {
            alertMessage = alertMessage + "Время: " + minValue1 / 60 + " мин";
        }

        buildAlert(alertMessage, view);
    }

    private ArrayList<String> sort(int num) {

        int[] abc = new int[num];
        ArrayList<String> combinedBArrayList = new ArrayList<>();

        int r = 0;

        for (int f = 0; f < num; f++) {
            abc[f] = r;
            r++;
        }

        int[] pow = new int[num + 1];//массив для степеней числа N: N^0, N^1, .., N^K
        pow[0] = 1;
        for (int i = 1; i <= num; i++) {//вычисляем степени числа N
            pow[i] = pow[i - 1] * num;
        }

        for (int i = 0; i < pow[num]; i++) {

            int[] arr = new int[num];

            for (int j = 0; j < num; j++) {
                arr[j] = abc[(i / pow[j]) % num];
            }

            StringBuilder s = new StringBuilder();

            for (int ch : arr) {
                s.append(ch);
            }

            if (lol(s.toString()) && s.toString().startsWith("0")) {
                combinedBArrayList.add(s.toString());
            }

        }

        return combinedBArrayList;
    }

    private boolean lol(String string) {

        for (char c : string.toCharArray()) {
            if (string.indexOf(c) != string.lastIndexOf(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIsAllFieldsIsNotNullAndEmpty(String fistPoint, String secondPoint,
                                                      String thirdPoint, String fourPoint) {

        return StringUtils.isNotEmpty(fistPoint) && StringUtils.isNotEmpty(secondPoint)
                && StringUtils.isNotEmpty(thirdPoint) && StringUtils.isNotEmpty(fourPoint);

    }

    private String concatString(String firstPlace, String secondPlace, String thirdPlace, String fourPlace) {

        return firstPlace.concat("|").concat(secondPlace).concat("|").concat(thirdPlace).concat("|").concat(fourPlace);
    }

    private void buildAlert(String message, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Маршрут")
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("Закрыть", (dialog, which) -> {
                    dialog.cancel();
                    allTimes.clear();
                    points.clear();
                })
                .setPositiveButton("Показать на карте",
                        (dialog, id) -> {
                            dialog.cancel();
                            allTimes.clear();
                            points.clear();

                            mapsFragment = new MapsFragment();

                            if (getFragmentManager() != null) {
                                fragmentTransaction = getFragmentManager().beginTransaction();
                            }
                            fragmentTransaction.replace(R.id.fragTag, mapsFragment).addToBackStack(null);
                            fragmentTransaction.commit();
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
