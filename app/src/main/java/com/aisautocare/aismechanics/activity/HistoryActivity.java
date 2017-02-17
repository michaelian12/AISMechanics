package com.aisautocare.aismechanics.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.aisautocare.aismechanics.R;
import com.aisautocare.aismechanics.adapter.HistoryRecyclerViewAdapter;
import com.aisautocare.aismechanics.model.History;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private HistoryRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        ArrayList<History> histories = new ArrayList<History>();
        histories.add(new History("15 Februari 2017", "Rp 50.000", "Agus Mujiya", "Jl. Karang Asem no. 33", "Toyota Yaris", "2015", "Ganti Aki", "Tunai", 5));
        histories.add(new History("15 Februari 2017", "Rp 50.000", "Agus Mujiya", "Jl. Karang Asem no. 33", "Toyota Yaris", "2015", "Ganti Aki", "Tunai", 4));
        histories.add(new History("15 Februari 2017", "Rp 50.000", "Agus Mujiya", "Jl. Karang Asem no. 33", "Toyota Yaris", "2015", "Ganti Aki", "Tunai", 3));

        adapter = new HistoryRecyclerViewAdapter(this, histories);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
    }
}
