package com.myservices.databasedemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolderr> {
    private static String TAG="RecyclerAdapter";
    List list;
    Context context;
    TextView userId,username;

    public RecyclerAdapter(List list, Context context){
        if(this.list==null){
            this.list=new ArrayList();
        }
        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public RecyclerViewHolderr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG,"this is the oncreateViewHolder method");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,parent,false);
        RecyclerViewHolderr recyclerViewHolderr=new RecyclerViewHolderr(view,list,context);
        userId=view.findViewById(R.id.recyclerView_userId);
        username=view.findViewById(R.id.recyclerView_username);
        return recyclerViewHolderr;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolderr holder, int position) {
        Log.i(TAG,"this is the onbindViewHOlder");
        DataHelperClass dataHelperClass=(DataHelperClass) list.get(position);
        holder.userID.setText(dataHelperClass.getId());
        holder.username.setText(dataHelperClass.getName());

    }

    @Override
    public int getItemCount() {
        return list.size() ;
    }
    class RecyclerViewHolderr extends  ViewHolder {
        TextView userID,username;
        public RecyclerViewHolderr(@NonNull View itemView, List list, Context context) {
            super(itemView);
            userID=itemView.findViewById(R.id.recyclerView_userId);
            username=itemView.findViewById(R.id.recyclerView_username);

        }
    }
}
