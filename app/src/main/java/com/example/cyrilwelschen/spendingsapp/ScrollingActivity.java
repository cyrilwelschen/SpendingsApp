package com.example.cyrilwelschen.spendingsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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

    ArrayList<String> itemDescription = new ArrayList<>();
    ArrayList<Drawable> itemCategoryDrawable = new ArrayList<>();
    ArrayList<String> itemCreationDates = new ArrayList<>();
    ArrayList<String> itemSpendingAmount = new ArrayList<>();

    DatabaseHelper amountDB;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        amountDB = new DatabaseHelper(this);
        loadSpendings();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askUserInput();
            }
        });
    }

    private String[] CategoryChoices() {
        final String[] choices = {"Komisionen", "Auswärts", "NiceToHave", "Rechnung"};
        return choices;
    }

    public int CategoryNameToDrawable(String categoryName) {
        int drawable = R.mipmap.standard_category;
        switch (categoryName) {
            case "Komisionen":
                drawable = R.drawable.shopping;
                break;
            case "Auswärts":
                drawable = R.drawable.food;
                break;
            case "NiceToHave":
                drawable = R.drawable.nice_to_have;
                break;
            case "Rechnung":
                drawable = R.drawable.bank;
                break;
        }
        return drawable;
    }

    private int[] CategoryArrayToDrawableIdArray(String[] data) {
        int[] returnArray = new int[data.length];
        for (int l = 0; l < data.length; l++) {
            returnArray[l] = CategoryNameToDrawable(data[l]);
        }
        return returnArray;
    }

    public void askUserInput() {
        LayoutInflater li = LayoutInflater.from(this);
        @SuppressLint("InflateParams") View dialogView = li.inflate(R.layout.input_dialog, null);

        final EditText userInputCategory = dialogView.findViewById(R.id.category_user_input);
        final EditText userInputDescription = dialogView.findViewById(R.id.description_user_input);
        final EditText userInputAmount = dialogView.findViewById(R.id.amount_user_input);

        // data to populate the RecyclerView with
        final String[] data = CategoryChoices();
        int[] drawable = CategoryArrayToDrawableIdArray(data);
        // set up the RecyclerView
        RecyclerView recyclerView = dialogView.findViewById(R.id.category_input_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        CategoryInputViewAdapter adapter = new CategoryInputViewAdapter(this, data, drawable);
        adapter.setClickListener(new CategoryInputViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                userInputCategory.setText(data[position]);
            }
        });
        recyclerView.setAdapter(adapter);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(dialogView);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String dateSQLiteStamp = currentDateSQLiteStamp();
                                String category = userInputCategory.getText().toString();
                                String description = userInputDescription.getText().toString();
                                String amountString = userInputAmount.getText().toString();
                                float amount = Float.parseFloat(amountString);

                                addToDatabase(category, description, dateSQLiteStamp, amount);
                                loadSpendings();
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

    public void addToDatabase(String cat, String desc, String date, float amount) {
        boolean insertionSuccessful = amountDB.addData(cat, date, amount, desc);
        if (insertionSuccessful) {
            // don't show toast if successful
            @SuppressLint("ShowToast") Toast toast = Toast.makeText(this,
                    "Data insertion successful", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(this, "Data insertion failed!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public String convertSQLiteDateToDisplay(String sqLiteDateFormat){
        // todo: simplify this method
        SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd. MMM, HH:mm");
        SimpleDateFormat sqLiteDate = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss.SSS");
        Date date = new Date(12234);
        try {
            date = sqLiteDate.parse(sqLiteDateFormat);
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

    public void loadSpendings() {
        reloadAllData();
        reloadRecyclerView();
    }

    public void reloadAllData() {
        Cursor data = amountDB.queryWholeDatabase();

        itemDescription.clear();
        itemCategoryDrawable.clear();
        itemCreationDates.clear();
        itemSpendingAmount.clear();

        if (data.getCount() == 0){
            itemSpendingAmount.add(" ");
            itemCategoryDrawable.add(getResources().getDrawable(R.drawable.ic_launcher_foreground));
            itemCreationDates.add(" ");
            itemDescription.add("Add spendings!");
        }
        while (data.moveToNext()) {
            itemCategoryDrawable.add(getResources().getDrawable(CategoryNameToDrawable(data.getString(1))));
            String rawDateFormat = data.getString(2);
            itemCreationDates.add(convertSQLiteDateToDisplay(rawDateFormat));
            String amountFloatAsString = data.getString(3);
            itemSpendingAmount.add(convertAmountToDisplayFormat(amountFloatAsString));
            itemDescription.add(data.getString(4));
        }
    }

    private String convertAmountToDisplayFormat(String floatAsString) {
        String[] parts = floatAsString.split("\\.");
        try {
            String wholeUnit = parts[0];
            String fractionUnit = parts[1];
            if (fractionUnit.length() == 1) {
                fractionUnit = parts[1] + "0";
            }
            wholeUnit = hyphenThousand(wholeUnit);
            return wholeUnit + "." + fractionUnit;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            return  hyphenThousand(floatAsString);
        }
    }

    private String hyphenThousand(String amountString) {
        String result = amountString;
        if (amountString.length() > 3) {
            switch (amountString.length()) {
                case 4:
                    result = amountString.substring(0, 1) + "'" + amountString.substring(1, amountString.length());
                    break;
                case 5:
                    result = amountString.substring(0, 2) + "'" + amountString.substring(2, amountString.length());
                    break;
                default:
                    result = amountString;
                    break;
            }
        }
        return result;
    }

    private void reloadRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        AmountMainViewAdapter adapter = new AmountMainViewAdapter(itemDescription,
                itemCreationDates, itemSpendingAmount, itemCategoryDrawable);
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
