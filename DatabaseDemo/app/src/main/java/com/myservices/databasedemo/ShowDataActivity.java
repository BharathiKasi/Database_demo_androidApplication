package com.myservices.databasedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ShowDataActivity extends AppCompatActivity {

    List list;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        recyclerView=findViewById(R.id.recyclerView);

        if(getIntent().hasExtra("list")){
            Bundle bundle=getIntent().getExtras();
            if(list==null){
                list=new ArrayList();
            }
            list=(ArrayList)bundle.getSerializable("list");
            layoutManager=new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            if(recyclerAdapter==null){
                recyclerAdapter=new RecyclerAdapter(list,this);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(recyclerAdapter);
            }






        }
    }
}
