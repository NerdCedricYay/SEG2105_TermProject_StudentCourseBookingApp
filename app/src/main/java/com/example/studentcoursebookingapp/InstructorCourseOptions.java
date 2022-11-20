package com.example.studentcoursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Arrays;

public class InstructorCourseOptions extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    ToggleButton tb_monday, tb_tuesday, tb_wednesday, tb_thursday, tb_friday;
    EditText et_mondayStart, et_mondayEnd, et_tuesdayStart, et_tuesdayEnd, et_wednesdayStart,
            et_wednesdayEnd, et_thursdayStart, et_thursdayEnd, et_fridayStart, et_fridayEnd, et_courseDescription, et_courseCapacity;
    Button btn_confirm;
    Boolean mondayIsChecked = false, tuesdayIsChecked = false, wednesdayIsChecked = false, thursdayIsChecked = false, fridayIsChecked = false;

    String daysAndHours = "";
    int courseCapacity = 0;
    String courseDescription = "";
    public int selectedCourseId = -1;
    public int userId = -1;
    String instructorName = "";

    CourseDatabaseHelper courseDatabaseHelper;
    InstructorDatabaseHelper instructorDatabaseHelper;
    InstructorHome instructorHome;
    MainActivity mainActivity;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_course_options);

        tb_monday = (ToggleButton) findViewById(R.id.mondayToggleButton);
        tb_tuesday = (ToggleButton) findViewById(R.id.tuesdayToggleButton);
        tb_wednesday = (ToggleButton) findViewById(R.id.wednesdayToggleButton);
        tb_thursday = (ToggleButton) findViewById(R.id.thursdayToggleButton);
        tb_friday = (ToggleButton) findViewById(R.id.fridayToggleButton);

        et_mondayStart = (EditText) findViewById(R.id.mondayStartEditText);
        et_mondayEnd = (EditText) findViewById(R.id.mondayEndEditText);
        et_tuesdayStart = (EditText) findViewById(R.id.tuesdayStartEditText);
        et_tuesdayEnd = (EditText) findViewById(R.id.tuesdayEndEditText);
        et_wednesdayStart = (EditText) findViewById(R.id.wednesdayStartEditText);
        et_wednesdayEnd = (EditText) findViewById(R.id.wednesdayEndEditText);
        et_thursdayStart = (EditText) findViewById(R.id.thursdayStartEditText);
        et_thursdayEnd = (EditText) findViewById(R.id.thursdayEndEditText);
        et_fridayStart = (EditText) findViewById(R.id.fridayStartEditText);
        et_fridayEnd = (EditText) findViewById(R.id.fridayEndEditText);

        et_courseDescription = (EditText) findViewById(R.id.courseDescriptionEditText);

        et_courseCapacity = (EditText) findViewById(R.id.courseCapacityEditText);

        btn_confirm = (Button) findViewById(R.id.assignMyselfButton);
        setOnCheckedChangeListeners();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daysAndHours = daysAndHoursToString(mondayIsChecked, tuesdayIsChecked, wednesdayIsChecked, thursdayIsChecked, fridayIsChecked);
                courseCapacity = Integer.parseInt(et_courseCapacity.getText().toString());
                courseDescription = et_courseDescription.getText().toString();

                Bundle extras = getIntent().getExtras(); // getting user id from MainActivity
                if (extras != null){ userId = extras.getInt("userId"); selectedCourseId = extras.getInt("selectedCourseId"); }
                // instructorName = instructorDatabaseHelper.getInstructorName(userId);

                if (daysAndHours == "error") {
                    Toast.makeText(InstructorCourseOptions.this, "Please enter a start time and end time for the selected days.", Toast.LENGTH_SHORT).show();
                }else if (daysAndHours == ""){
                    Toast.makeText(InstructorCourseOptions.this, "Please select at least one day.", Toast.LENGTH_SHORT).show();
                }else if (courseCapacity == 0){
                    Toast.makeText(InstructorCourseOptions.this, "Please enter a course capacity.", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(InstructorCourseOptions.this, "courseId " + selectedCourseId + " name " + instructorName + " userId " + userId + " daysHours "
                            + daysAndHours + " description " + courseDescription + " capacity " + courseCapacity, Toast.LENGTH_SHORT).show();

                    //courseDatabaseHelper.updateCourseInfo(selectedCourseId, instructorName, userId, daysAndHours, courseDescription, courseCapacity);
                }
            }
        });
    }

    private void setOnCheckedChangeListeners() {
        for (ToggleButton toggleButton : Arrays.asList(tb_monday, tb_tuesday, tb_wednesday, tb_wednesday, tb_friday)){
            toggleButton.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.mondayToggleButton:
                if (mondayIsChecked) {
                    mondayIsChecked = false;
                } else {
                    mondayIsChecked = true;
                }
                break;
            case R.id.tuesdayToggleButton:
                if (tuesdayIsChecked) {
                    tuesdayIsChecked = false;
                } else {
                    tuesdayIsChecked = true;
                }
                break;
            case R.id.wednesdayToggleButton:
                if (wednesdayIsChecked) {
                    wednesdayIsChecked = false;
                } else {
                    wednesdayIsChecked = true;
                }
                break;
            case R.id.thursdayToggleButton:
                if (thursdayIsChecked) {
                    thursdayIsChecked = false;
                } else {
                    thursdayIsChecked = true;
                }
                break;
            case R.id.fridayToggleButton:
                if (fridayIsChecked) {
                    fridayIsChecked = false;
                } else {
                    fridayIsChecked = true;
                }
                break;
        }
    }

    private String daysAndHoursToString(Boolean mondayIsChecked, Boolean tuesdayIsChecked, Boolean wednesdayIsChecked, Boolean thursdayIsChecked, Boolean fridayIsChecked){
        if(mondayIsChecked){
            String start = et_mondayStart.getText().toString();
            String end = et_mondayEnd.getText().toString();
            if (start != null && end != null) {
                daysAndHours += "Monday from " + start + " to " + end + ". ";
            }else{
                return "error";
            }
        }

        if(tuesdayIsChecked){
            String start = et_tuesdayStart.getText().toString();
            String end = et_tuesdayEnd.getText().toString();
            if (start != null && end != null) {
                daysAndHours += "Tuesday from " + start + " to " + end + ". ";
            }else{
                return "error";
            }
        }

        if(wednesdayIsChecked){
            String start = et_wednesdayStart.getText().toString();
            String end = et_wednesdayEnd.getText().toString();
            if (start != null && end != null) {
                daysAndHours += "Wednesday from " + start + " to " + end + ". ";
            }else{
                return "error";
            }
        }

        if(thursdayIsChecked){
            String start = et_thursdayStart.getText().toString();
            String end = et_thursdayEnd.getText().toString();
            if (start != null && end != null) {
                daysAndHours += "Thursday from " + start + " to " + end + ". ";
            }else{
                return "error";
            }
        }

        if(fridayIsChecked) {
            String start = et_fridayStart.getText().toString();
            String end = et_fridayEnd.getText().toString();
            if (start != null && end != null) {
                daysAndHours += "Friday from " + start + " to " + end + ". ";
            } else {
                return "error";
            }
        }

        return daysAndHours;
    }
}