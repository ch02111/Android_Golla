package com.example.golla;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<RecyclerViewItem> list;
    private RecyclerViewAdapter recyclerViewAdapter;

    public HistoryFragment() {

    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        firstInit(view);

        for(int i = 0; i < 5; i++) {
            addItem("고씨네 카레", "2022년 06월 10일");
        }

        recyclerViewAdapter = new RecyclerViewAdapter(list);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private void firstInit(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        list = new ArrayList<>();
    }

    public void addItem(String name, String date) {
        RecyclerViewItem item = new RecyclerViewItem();
        item.setRestaurantName(name);
        item.setRestaurantDate(date);

        list.add(item);
    }
}