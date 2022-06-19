package com.example.golla;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class HistoryFragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<RecyclerViewItem> list;
    private FirebaseFirestore firestore;
    private CollectionReference reference;

//    private RecyclerView recyclerView;
//    private ArrayList<RecyclerViewItem> list;
//    private RecyclerViewAdapter recyclerViewAdapter;
//    FirebaseFirestore database = FirebaseFirestore.getInstance();
//
//
//    public HistoryFragment() {
//
//    }
//
//    public static HistoryFragment newInstance() {
//        HistoryFragment fragment = new HistoryFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_history, container, false);
//        firstInit(view);
//        database.collection("기록").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                int historyNum = task.getResult().size();
//                for (int i = 0; i < historyNum; i++) {
//                    addItem((String) task.getResult().getDocuments().get(i).get("식당이름"), (String) task.getResult().getDocuments().get(i).get("방문날짜"));
//                }
//                recyclerViewAdapter.notifyDataSetChanged();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "DB 연결 실패");
//            }
//        });
//
//
//
//        recyclerViewAdapter = new RecyclerViewAdapter(list);
//        recyclerView.setAdapter(recyclerViewAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        return view;
//    }
//
//    private void firstInit(View view) {
//        recyclerView = view.findViewById(R.id.recyclerView);
//        list = new ArrayList<>();
//    }
//
//    public void addItem(String name, String date) {
//        RecyclerViewItem item = new RecyclerViewItem();
//        item.setRestaurantName(name);
//        item.setRestaurantDate(date);
//
//        list.add(item);
//    }
//}

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("기록");
        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                list.clear();
                int historyNum = queryDocumentSnapshots.size();
                for(int i = 0; i < historyNum; i++) {
                    RecyclerViewItem item = new RecyclerViewItem();
                    item.setRestaurantName((String)queryDocumentSnapshots.getDocuments().get(i).get("식당이름"));
                    item.setRestaurantDate((String)queryDocumentSnapshots.getDocuments().get(i).get("방문날짜"));
                    list.add(item);
                }
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new RecyclerViewAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }
}