package com.example.thatstime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.example.thatstime.models.LoginResponse;
import com.example.thatstime.models.RecordFromFrontEnd;
import com.example.thatstime.ApiService;
import com.example.thatstime.models.RecordResponse;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Calendar calendar;
    private TextView textViewMonth;
    private TableLayout tableLayoutWeeks;
    private Button buttonPrevious, buttonNext, buttonSubmit, buttonLogOut, buttonViewRecords;

    private TextView selectedDay;
    private EditText recordName, recordContent;

    private RecordFromFrontEnd record = new RecordFromFrontEnd();
    // Private variable to hold the selected day
    private int selectedDayOfMonth;

    private String username;

    private ApiService apiService;

    private int month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedpreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String token = sharedpreferences.getString("jwt_token", null);
        DecodedJWT jwt = JWT.decode(token);
        username = jwt.getClaim("unique_name").asString();

        apiService = RetrofitClient.getClient(token).create(ApiService.class);

        calendar = Calendar.getInstance(); // Get current date

        month = calendar.get(Calendar.MONTH) + 1;
        selectedDayOfMonth = 1;

        record.setSelectedYear(calendar.get(Calendar.YEAR));
        record.setSelectedMonth(month);
        record.setCreator(username);
        record.setImportance(0);
        record.setHour(0);
        record.setMinute(0);
        record.setSelectedObject(username);
        record.setShowGroupList(false);
        record.setYourSelf(true);

        textViewMonth = findViewById(R.id.textViewMonth);
        tableLayoutWeeks = findViewById(R.id.tableLayoutWeeks);
        buttonPrevious = findViewById(R.id.buttonPrevious);
        buttonNext = findViewById(R.id.buttonNext);
        buttonSubmit = findViewById(R.id.submitButton);
        buttonLogOut = findViewById(R.id.logoutButton);
        buttonViewRecords = findViewById(R.id.checkRecords);
        recordName = findViewById(R.id.recordName);
        recordContent = findViewById(R.id.recordContent);
        selectedDay = findViewById(R.id.selectedDay);
        recordName = findViewById(R.id.recordName);
        recordContent = findViewById(R.id.recordContent);

        selectedDay.setText("Day selected: " + selectedDayOfMonth);

        updateMonthDisplay();

        buttonPrevious.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, -1); // Go to the previous month
            month--;
            record.setSelectedMonth(month);
            updateMonthDisplay();
        });

        buttonNext.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, 1); // Go to the next month
            month++;
            record.setSelectedMonth(month);
            updateMonthDisplay();
        });

        buttonLogOut.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("jwt_token"); // Remove the specific key-value pair
            editor.apply(); // Apply changes
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRecord();
            }
        });

        buttonViewRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewRecordActivity.class);
                startActivity(intent);
            }
        });

        recordName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                record.setRecordName(s.toString());
            }
        });

        recordContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                record.setRecordContent(s.toString());
            }
        });
    }

    private void updateMonthDisplay() {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        textViewMonth.setText(monthFormat.format(calendar.getTime()));

        populateWeeks();
    }

    private void populateWeeks() {
        // Clear any existing rows
        tableLayoutWeeks.removeAllViews();
        boolean isFinished = false;

        // Get the number of days in the current month
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar weekCalendar = (Calendar) calendar.clone();
        weekCalendar.set(Calendar.DAY_OF_MONTH, 1);

        // Loop through the weeks in the month
        for (int week = 0; week < weekCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH); week++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));

            int dayOfMonth = weekCalendar.get(Calendar.DAY_OF_MONTH);
            if(isFinished)
                break;

            // Add up to 7 buttons for each week
            for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++) {
                int day = weekCalendar.get(Calendar.DAY_OF_MONTH);

                // Create a button for each day in the week
                Button dayButton = new Button(this);
                dayButton.setText(String.valueOf(day));
                dayButton.setOnClickListener(v -> {
                    selectedDayOfMonth = day+1;
                    selectedDay.setText("Day selected: " + day);
                    record.setSelectedDay(day);
                });

                row.addView(dayButton);

                // Move to the next day
                weekCalendar.add(Calendar.DAY_OF_MONTH, 1);

                // Break if we've gone past the last day of the month
                if (day >= maxDay) {
                    isFinished = true;
                    break;
                }
            }

            // Add the row to the TableLayout
            tableLayoutWeeks.addView(row);
        }
    }

    private void submitRecord(){

        Call<RecordResponse> call = apiService.submitNewRecord(record);

        call.enqueue(new Callback<RecordResponse>() {
            @Override
            public void onResponse(Call<RecordResponse> call, Response<RecordResponse> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {
                    // Redirect or show success
                    Toast.makeText(MainActivity.this, "Record creation Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Record creation failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecordResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

