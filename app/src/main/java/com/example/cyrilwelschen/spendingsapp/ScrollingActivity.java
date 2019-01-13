package com.example.cyrilwelschen.spendingsapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity {

    ArrayList<String> itemCategories = new ArrayList<>();
    ArrayList<String> itemCreationDates = new ArrayList<>();
    ArrayList<String> itemSpendingAmount = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initMyList();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void initMyList() {
        itemCategories.add("one");
        itemCreationDates.add("11.Feb 19");
        itemSpendingAmount.add("120");
        itemCategories.add("two");
        itemCreationDates.add("12.Feb 19");
        itemSpendingAmount.add("120.40");
        itemCategories.add("one");
        itemCreationDates.add("11.Feb 19");
        itemSpendingAmount.add("7.50");
        itemCategories.add("one");
        itemCreationDates.add("11.Feb 19");
        itemSpendingAmount.add("120");
        itemCategories.add("one");
        itemCreationDates.add("11.Feb 19");
        itemSpendingAmount.add("120");
        itemCategories.add("one");
        itemCreationDates.add("11.Feb 19");
        itemSpendingAmount.add("11");

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        MyAdapter adapter = new MyAdapter(this, itemCategories,
                itemCreationDates, itemSpendingAmount);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
