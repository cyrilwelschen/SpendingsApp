package com.example.cyrilwelschen.spendingsapp;

import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

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
                askUserInput();
            }
        });
    }

    public void askUserInput() {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.input_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(dialogView);

        final EditText userInputCategory = dialogView.findViewById(R.id.category_user_input);
        final EditText userInputAmount = dialogView.findViewById(R.id.amount_user_input);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                itemCategories.add(userInputCategory.getText().toString());
                                itemSpendingAmount.add(userInputAmount.getText().toString());
                                String dateStamp = currentDateDisplay();
                                itemCreationDates.add(dateStamp);
                                initRecyclerView();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public String currentDateDisplay(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MMM, HH:mm");
        return dateFormat.format(calendar.getTime());
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
        myRecyclerViewAdapter adapter = new myRecyclerViewAdapter(this, itemCategories,
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
