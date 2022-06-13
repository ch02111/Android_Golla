package com.example.golla;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class randomFragment extends Fragment {

    private LuckyWheel luckyWheel;
    List<WheelItem> wheelItems;
    private Context context;
    Button btnSelectLadder, btnSelectWheel;
    FragmentContainerView fragmentLayout;

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
        btnSelectLadder = view.findViewById(R.id.btnSelectLadder);
        btnSelectWheel = view.findViewById(R.id.btnSelectWheel);
        btnSelectLadder.setOnClickListener(null);
        btnSelectWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentLayout.startViewTransition(fragmentLayout);
            }
        });
        generateWheelItems();

        luckyWheel = view.findViewById(R.id.randomWheel);
        luckyWheel.addWheelItems(wheelItems);
        luckyWheel.setTarget(1);
        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                String result = wheelItems.get(0).text;
                Toast.makeText(context, "결과 : " + result, Toast.LENGTH_SHORT).show();
            }
        });

        Button btnStartWheel = view.findViewById(R.id.btnStartWheel);
        btnStartWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                luckyWheel.rotateWheelTo(1);
            }
        });
        return view;
    }


    private void generateWheelItems() {
        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#d1c880"), BitmapFactory.decodeResource(context.getResources(), R.drawable.tempimg), "윤씨네 카레"));
        wheelItems.add(new WheelItem(Color.parseColor("#92d4d0"), BitmapFactory.decodeResource(context.getResources(), R.drawable.tempimg), "동궁찜닭"));
        wheelItems.add(new WheelItem(Color.parseColor("#d1c880"), BitmapFactory.decodeResource(context.getResources(), R.drawable.tempimg), "궁가"));
        wheelItems.add(new WheelItem(Color.parseColor("#92d4d0"), BitmapFactory.decodeResource(context.getResources(), R.drawable.tempimg), "아쯔다무라"));
        wheelItems.add(new WheelItem(Color.parseColor("#92d4d0"), BitmapFactory.decodeResource(context.getResources(), R.drawable.tempimg), "써니 김치찌개"));
    }
}