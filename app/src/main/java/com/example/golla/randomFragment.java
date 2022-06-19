package com.example.golla;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.jhdroid.view.RotateListener;
import com.jhdroid.view.Roulette;

import java.lang.ref.Reference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    static String[] restaurantName = new String[14];    //DB에서 가져올 식당 이름
    String[] restaurantType = new String[14];    //DB에서 가져올 식당 분류
    String[] restaurantMenu = new String[14];    //DB에서 가져올 식당 메뉴
    float[] restaurantLat;   //DB에서 가져올 식당 위도
    float[] restaurantLng;  //DB에서 가져올 식당 경도
    int randomvalue = (int)((Math.random()) * 10 / 2);
    Random random = new Random();
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    Roulette roulette;

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

        View view = inflater.inflate(R.layout.fragment_random, container, false);
        context = container.getContext();
        roulette = view.findViewById(R.id.roulette);
        luckyWheel = view.findViewById(R.id.randomWheel);


        db.collection("식당").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    int restaurantNum = task.getResult().size();
                    for (int i = 0; i < restaurantNum; i++) {
                        restaurantName[i] = (String) task.getResult().getDocuments().get(i).get("식당이름");
                        restaurantType[i] = (String) task.getResult().getDocuments().get(i).get("분류");
                        restaurantMenu[i] = (String) task.getResult().getDocuments().get(i).get("대표메뉴");
                    }
//                    for(int i = 0; i < 5; i++) {
//                        int rv = random.nextInt(14);
//                        itemList.add(new WheelItem(Color.parseColor("#FFFFFF"), BitmapFactory.decodeResource(getResources(), R.drawable.tempimg), restaurantName[rv]));
//                    }
                    Log.d(TAG, "실행됨");
                }
                else {
                    Log.d(TAG, "Error getting Documents." + task.getException());
                }
            }
        });

        itemList.add(new WheelItem(Color.parseColor("#FFFFFF"), BitmapFactory.decodeResource(getResources(), R.drawable.tempimg), "궁가"));
        luckyWheel.addWheelItems(itemList);


        Button btnStartWheel = view.findViewById(R.id.btnStartWheel);
        btnStartWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                luckyWheel.rotateWheelTo(randomvalue + 1);
            }
        });

        return view;
    }
}