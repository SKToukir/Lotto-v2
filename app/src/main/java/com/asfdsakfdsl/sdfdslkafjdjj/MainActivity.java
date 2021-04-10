package com.asfdsakfdsl.sdfdslkafjdjj;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.asfdsakfdsl.sdfdslkafjdjj.adapter.ItemAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerItem;
    private ItemAdapter itemAdapter;

    private RecyclerView recyclerItem2;
    private ItemAdapter itemAdapter2;

    private RecyclerView recyclerItem3;
    private ItemAdapter itemAdapter3;

    private RecyclerView recyclerItem4;
    private ItemAdapter itemAdapter4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

    }

    private void initUI() {
        recyclerItem = findViewById(R.id.recyclerItem);
        recyclerItem.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(this);
        recyclerItem.setNestedScrollingEnabled(false);
        recyclerItem.setAdapter(itemAdapter);

        recyclerItem2 = findViewById(R.id.recyclerItem2);
        recyclerItem2.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter2 = new ItemAdapter(this);
        recyclerItem2.setNestedScrollingEnabled(false);
        recyclerItem2.setAdapter(itemAdapter2);

        recyclerItem3 = findViewById(R.id.recyclerItem3);
        recyclerItem3.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter3 = new ItemAdapter(this);
        recyclerItem3.setNestedScrollingEnabled(false);
        recyclerItem3.setAdapter(itemAdapter3);

        recyclerItem4 = findViewById(R.id.recyclerItem4);
        recyclerItem4.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter4 = new ItemAdapter(this);
        recyclerItem4.setNestedScrollingEnabled(false);
        recyclerItem4.setAdapter(itemAdapter4);
    }

}