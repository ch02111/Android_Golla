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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.golla.RecyclerViewItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView restaurantImgView;
        TextView history_restaurantNameTv;
        TextView history_restaurantDateTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantImgView = (ImageView) itemView.findViewById(R.id.restaurantImgView);
            history_restaurantNameTv = (TextView) itemView.findViewById(R.id.history_restaurantNameTv);
            history_restaurantDateTv = (TextView) itemView.findViewById(R.id.history_restaurantDateTv);
        }
    }

    private ArrayList<RecyclerViewItem> mList = null;

    public RecyclerViewAdapter(ArrayList<RecyclerViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://golla-d8534.appspot.com");
        StorageReference storageRef = storage.getReference();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        storageRef.child("고씨네.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into((ImageView) parent.findViewById(R.id.restaurantImgView));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "이미지 가져오기 실패");
            }
        });

        View view = inflater.inflate(R.layout.history_item, parent, false);
        RecyclerViewAdapter.ViewHolder vh = new RecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        RecyclerViewItem item = mList.get(position);

        holder.restaurantImgView.setImageResource(R.drawable.ic_launcher_background);   // 사진 없어서 기본 파일로 이미지 띄움
        holder.history_restaurantNameTv.setText(item.getRestaurantName());
        holder.history_restaurantDateTv.setText(item.getRestaurantDate());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}