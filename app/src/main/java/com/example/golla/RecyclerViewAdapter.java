package com.example.golla;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.golla.RecyclerViewItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
//
//public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
//    String storageAddress = "gs://golla-d8534.appspot.com";
//    String[] imageFile;
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView restaurantImgView;
//        TextView history_restaurantNameTv;
//        TextView history_restaurantDateTv;
//
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            this.restaurantImgView = (ImageView) itemView.findViewById(R.id.restaurantImgView);
//            this.history_restaurantNameTv = (TextView) itemView.findViewById(R.id.history_restaurantNameTv);
//            this.history_restaurantDateTv = (TextView) itemView.findViewById(R.id.history_restaurantDateTv);
//        }
//    }
//
//    private ArrayList<RecyclerViewItem> mList = null;
//
//    public RecyclerViewAdapter(ArrayList<RecyclerViewItem> mList) {
//        this.mList = mList;
//    }
//
//    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.history_item, parent, false);
//        ViewHolder holder = new ViewHolder(view);
//
//        return holder;
//    }
//
//    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
//        RecyclerViewItem item = mList.get(holder.getAdapterPosition());
//
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        FirebaseStorage storage = FirebaseStorage.getInstance(storageAddress);
//        StorageReference storageRef = storage.getReference();
//        database.collection("기록").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isComplete()) {
//                    int historyNum = task.getResult().size();
//                    String[] stringDate = new String[historyNum];
//                    imageFile = new String[historyNum];
//                    for (int i = 0; i < historyNum; i++) {
//                        final int index = i;
//                        imageFile[i] = (String) task.getResult().getDocuments().get(i).get("식당이름");
//                        stringDate[i] = (String) task.getResult().getDocuments().get(i).get("방문날짜");
//
//                        storageRef.child(imageFile[i] + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                Glide.with(holder.itemView).load(uri).into((ImageView) holder.restaurantImgView);
//                                holder.history_restaurantNameTv.setText(imageFile[index]);
//                                holder.history_restaurantDateTv.setText(stringDate[index]);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d(TAG, "이미지 가져오기 실패");
//                            }
//                        });
//                    }
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "기록 가져오기 실패");
//            }
//        });
//    }
//    @Override
//    public int getItemCount() {
//        return mList.size();
//    }
//}

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<RecyclerViewItem> list = new ArrayList<>();
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView history_iv;
        TextView history_name;
        TextView history_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.history_iv = itemView.findViewById(R.id.restaurantImgView);
            this.history_name = itemView.findViewById(R.id.history_restaurantNameTv);
            this.history_date = itemView.findViewById(R.id.history_restaurantDateTv);
        }

        public void setData(RecyclerViewItem item) {
            FirebaseStorage.getInstance()
                    .getReference()
                    .child(item.getRestaurantName() + ".jpg")
                    .getDownloadUrl()
                    .addOnSuccessListener(uri -> {
                        Glide.with(itemView)
                                .load(uri)
                                .into(history_iv);
                    });


            history_name.setText(item.getRestaurantName());
            history_date.setText(item.getRestaurantDate());
        }

    }

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<RecyclerViewItem> list) {
        this.list = list;
        Log.d("RecyclerViewAdapter", "Data Updated : " + list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}