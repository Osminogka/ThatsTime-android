package com.example.thatstime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.example.thatstime.models.LoginResponse;
import com.example.thatstime.models.RecordFromFrontEnd;
import com.example.thatstime.ApiService;
import com.example.thatstime.models.RecordResponse;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRecordActivity extends AppCompatActivity {

    private EditText dayEdit, monthEdit, yearEdit;

    private Button bthSearch, btnReturn;

    private int day, month, year;

    private ApiService apiService;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);

        SharedPreferences sharedpreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String token = sharedpreferences.getString("jwt_token", null);
        DecodedJWT jwt = JWT.decode(token);
        username = jwt.getClaim("unique_name").asString();

        apiService = RetrofitClient.getClient(token).create(ApiService.class);

        btnReturn = findViewById(R.id.returnButton);
        bthSearch = findViewById(R.id.seachButton);
        dayEdit = findViewById(R.id.dayEdit);
        monthEdit = findViewById(R.id.monthEdit);
        yearEdit = findViewById(R.id.yearEdit);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bthSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRecord();
            }
        });

        dayEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                day = Integer.parseInt(s.toString());
            }
        });

        monthEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                month = Integer.parseInt(s.toString());
            }
        });

        yearEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                year = Integer.parseInt(s.toString());
            }
        });

    }

    private void searchRecord(){
        Call<RecordResponse> call = apiService.getRecords(username, true, false, year, month, day);

        call.enqueue(new Callback<RecordResponse>() {
            @Override
            public void onResponse(Call<RecordResponse> call, Response<RecordResponse> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {
                    List<RecordFromFrontEnd> records = response.body().getRecords();
                    setRecords(records);
                    // Redirect or show success
                    Toast.makeText(ViewRecordActivity.this, "Record get Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ViewRecordActivity.this, "Record get failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecordResponse> call, Throwable t) {
                Toast.makeText(ViewRecordActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecords(List<RecordFromFrontEnd> records) {
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL); // Stack records vertically

        for (RecordFromFrontEnd record : records) {
            // Create a CardView to wrap each record
            CardView cardView = new CardView(this);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(16, 16, 16, 16); // Add margins around the card
            cardView.setLayoutParams(cardParams);
            cardView.setRadius(16); // Rounded corners for the card
            cardView.setCardElevation(8); // Add shadow to the card
            cardView.setPadding(16, 16, 16, 16); // Inner padding for the card

            // Create a LinearLayout inside the CardView to arrange TextViews vertically
            LinearLayout cardLinearLayout = new LinearLayout(this);
            cardLinearLayout.setOrientation(LinearLayout.VERTICAL);

            // Create a new TextView for RecordName
            TextView recordNameView = new TextView(this);
            recordNameView.setText("Record Name: " + record.getRecordName());
            recordNameView.setTextSize(18);
            recordNameView.setTypeface(null, Typeface.BOLD);
            cardLinearLayout.addView(recordNameView); // Add to inner layout

            // Create a new TextView for RecordContent
            TextView recordContentView = new TextView(this);
            recordContentView.setText("Content: " + record.getRecordContent());
            cardLinearLayout.addView(recordContentView);

            // Create a new TextView for the Date
            TextView dateView = new TextView(this);
            dateView.setText("Date: " + record.getSelectedDay() + "/" + record.getSelectedMonth() + "/" + record.getSelectedYear());
            cardLinearLayout.addView(dateView);

            // Add the inner LinearLayout to the CardView
            cardView.addView(cardLinearLayout);

            // Add the CardView to the main LinearLayout
            linearLayout.addView(cardView);
        }
    }

}