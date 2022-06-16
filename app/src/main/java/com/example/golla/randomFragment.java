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
    static List<WheelItem> wheelItems = new ArrayList<>();
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
        luckyWheel = view.findViewById(R.id.randomWheel);

        db.collection("식당").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    int restaurantNum = document.size();
                    for (int i = 0; i < restaurantNum; i++) {
                        restaurantName[i] = (String) task.getResult().getDocuments().get(i).get("식당이름");
                        restaurantType[i] = (String) task.getResult().getDocuments().get(i).get("분류");
                        restaurantMenu[i] = (String) task.getResult().getDocuments().get(i).get("대표메뉴");
                    }
                }
                else {
                    Log.d(TAG, "Error getting Documents." + task.getException());
                }
            }
        });
        if(restaurantName[0] == null || restaurantName[0].equals("")) {
            Log.d(TAG, "빈 값임");
        }
        wheelItems.add(new WheelItem(Color.parseColor("#80bdff"), BitmapFactory.decodeResource(getResources(), R.drawable.tempimg), "고씨네"));
        wheelItems.add(new WheelItem(Color.parseColor("#91ff99"), BitmapFactory.decodeResource(getResources(), R.drawable.tempimg), "궁가"));
        wheelItems.add(new WheelItem(Color.parseColor("#80bdff"), BitmapFactory.decodeResource(getResources(), R.drawable.tempimg), "긴자료코"));
        wheelItems.add(new WheelItem(Color.parseColor("#91ff99"), BitmapFactory.decodeResource(getResources(), R.drawable.tempimg), "담윤순대국"));
        wheelItems.add(new WheelItem(Color.parseColor("#fa7f9e"), BitmapFactory.decodeResource(getResources(), R.drawable.tempimg), "동궁찜닭"));
        luckyWheel.addWheelItems(wheelItems);

        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                String result = wheelItems.get(randomvalue).text;
                Toast.makeText(context, "결과 : " + result, Toast.LENGTH_SHORT).show();
                Map<String, Object> history = new HashMap<>();
                history.put("식당이름", result);
                history.put("방문날짜", new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date()));
                db.collection("기록").add(history).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, "과거 기록에 기록되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "과거 기록 실패");
                    }
                });
            }
        });


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