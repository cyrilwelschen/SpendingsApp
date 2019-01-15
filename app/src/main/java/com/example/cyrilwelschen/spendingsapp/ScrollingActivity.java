package com.example.cyrilwelschen.spendingsapp;

import android.content.DialogInterface;
import android.database.Cursor;
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
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class ScrollingActivity extends AppCompatActivity {

    ArrayList<String> itemCategories = new ArrayList<>();
    ArrayList<String> itemCreationDates = new ArrayList<>();
    ArrayList<String> itemSpendingAmount = new ArrayList<>();

    DatabaseHelper amountDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initMyList();
        amountDB = new DatabaseHelper(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askUserInput();
            }
        });
    }

    public void addToDatabase(String cat, String date, float amount, String comment){
        boolean insertionSuccessful = amountDB.addData(cat, date, amount, comment);
        if (insertionSuccessful){
            Toast.makeText(this, "Data insertion successful",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data insertion failed!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void reloadAllData() {
        Cursor data = amountDB.queryWholeDatabase();
        if (data.getCount() == 0){
            String databaseLoadingResponse = "DB empty";
        }
        itemCategories.clear();
        itemCreationDates.clear();
        itemSpendingAmount.clear();
        while (data.moveToNext()) {
            itemCategories.add(data.getString(1));
            String rawDateFormat = data.getString(2);
            itemCreationDates.add(convertSQLiteDateToDisplay(rawDateFormat));
            itemSpendingAmount.add(data.getString(3));
        }
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
                                String dateSQLiteStamp = currentDateSQLiteStamp();
                                String category = userInputCategory.getText().toString();
                                String amountString = userInputAmount.getText().toString();
                                float amount = Float.parseFloat(amountString);

                                addToDatabase(category, dateSQLiteStamp, amount, null);
                                reloadAllData();
                                reloadRecyclerView();
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

    public String convertSQLiteDateToDisplay(String sqliteDateFormat){
        // todo: simplify this method
        SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd. MMM, HH:mm");
        SimpleDateFormat sqliteDate = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss.SSS");
        Date date = new Date(12234);
        try {
            date = sqliteDate.parse(sqliteDateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return displayDateFormat.format(calendar);
    }

    public String currentDateSQLiteStamp(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss.SSS");
        return dateFormat.format(calendar.getTime());
    }

    public void initMyList() {
        itemCategories.add("one");
        itemCreationDates.add("11.Feb 19");
        itemSpendingAmount.add("120");
        reloadRecyclerView();
    }

    private void reloadRecyclerView(){
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
