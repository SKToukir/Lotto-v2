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
    }

}