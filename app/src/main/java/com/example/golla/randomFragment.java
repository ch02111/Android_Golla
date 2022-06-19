package com.example.golla;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;
import com.jhdroid.view.RotateListener;
import com.jhdroid.view.Roulette;

import java.lang.ref.Reference;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class randomFragment extends Fragment {

    LuckyWheel luckyWheel;
    ArrayList<String> restList = new ArrayList<>();
    List<WheelItem> itemList = new ArrayList<>();
    static String[] randomList = new String[5];
    private Context context;
    FragmentContainerView rouletteFragment, ladderFragment;
    ArrayList<String> restaurantName = new ArrayList<>();    //DB에서 가져올 식당 이름
    ArrayList<String> restaurantType = new ArrayList<>();    //DB에서 가져올 식당 분류
    ArrayList<String> restaurantMenu = new ArrayList<>();    //DB에서 가져올 식당 메뉴
    float[] restaurantLat;   //DB에서 가져올 식당 위도
    float[] restaurantLng;  //DB에서 가져올 식당 경도
    Random random = new Random();
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public randomFragment() {

    }

    public static randomFragment newInstance() {
        randomFragment fragment = new randomFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_random, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        luckyWheel = view.findViewById(R.id.randomWheel);

        db.collection("식당").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                generateWheelItems(task.getResult().getDocuments());
            } else {
                Log.d(TAG, "Error getting Documents." + task.getException());
            }
        });

        ArrayList<WheelItem> dummyItems = new ArrayList<>();
        dummyItems.add(new WheelItem(Color.parseColor("#FFFFFF"), BitmapFactory.decodeResource(getResources(), R.drawable.tempimg), ""));
        luckyWheel.addWheelItems(dummyItems);

        Button btnStartWheel = view.findViewById(R.id.btnStartWheel);
        btnStartWheel.setOnClickListener(v -> {
            int randomIndex = new Random().nextInt(itemList.size() - 1);
            luckyWheel.rotateWheelTo(randomIndex);
        });
    }

    private void generateWheelItems(List<DocumentSnapshot> snapshots) {

        int restaurantNum = snapshots.size();

        for (int i = 0; i < restaurantNum; i++) {
            DocumentSnapshot document = snapshots.get(i);
            restaurantName.add((String) document.get("식당이름"));
            restaurantType.add((String) document.get("분류"));
            restaurantMenu.add((String) document.get("대표메뉴"));
        }

        for (int i = 0; i < 5; i++) {
            int rv = new Random().nextInt(restaurantName.size() - 1);
            String item = restaurantName.get(rv);
            itemList.add(new WheelItem(Color.BLACK, BitmapFactory.decodeResource(getResources(), R.drawable.tempimg), item));
        }

        luckyWheel.addWheelItems(itemList);
    }

}