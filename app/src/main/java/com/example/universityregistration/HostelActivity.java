package com.example.universityregistration;

import android.os.Bundle;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HostelActivity extends AppCompatActivity {

    Spinner spHostelLocation, spRoomType;

    RadioGroup rgSharing;

    EditText etStudentID;

    Button btnNext, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel);

        // Connect XML components

        spHostelLocation = findViewById(R.id.spHostelLocation);
        spRoomType = findViewById(R.id.spRoomType);

        rgSharing = findViewById(R.id.rgSharing);

        etStudentID = findViewById(R.id.etHostelStudentID);

        btnNext = findViewById(R.id.btnHostelNext);
        btnClear = findViewById(R.id.btnHostelClear);


        // 1. Populate hostel locations

        String[] locations = {
                "Select Hostel Location",
                "Campus hostels",
                "Gate A",
                "Boma",
                "Nyeri view",
                "Kahawa",
                "Nyaribo",
                "Nyeri town",
                "Other",
        };

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                locations
        );

        locationAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spHostelLocation.setAdapter(locationAdapter);


        // 2. Populate room types

        String[] roomTypes = {
                "Select Room Type",
                "Bedsitter",
                "Single Room",
                "One Bedroom",
                "Two Bedroom"
        };

        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                roomTypes
        );

        roomAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spRoomType.setAdapter(roomAdapter);


        // 3. Receive student ID from previous activity

        String studentID = getIntent().getStringExtra("idNumber");

        if (studentID != null) {
            etStudentID.setText(studentID);
        }


        // 4. Clear button functionality

        btnClear.setOnClickListener(v -> {

            spHostelLocation.setSelection(0);

            spRoomType.setSelection(0);

            rgSharing.clearCheck();

            etStudentID.setText("");

            Toast.makeText(this,
                    "Hostel form cleared",
                    Toast.LENGTH_SHORT).show();
        });


        // 5. Validate information and navigate

        btnNext.setOnClickListener(v -> {

            String student = etStudentID.getText().toString().trim();


            // Check hostel location

            if (spHostelLocation.getSelectedItemPosition() == 0) {

                Toast.makeText(this,
                        "Please select hostel location",
                        Toast.LENGTH_SHORT).show();

                return;
            }


            // Check room type

            if (spRoomType.getSelectedItemPosition() == 0) {

                Toast.makeText(this,
                        "Please select room type",
                        Toast.LENGTH_SHORT).show();

                return;
            }


            // Check sharing preference

            int sharingID = rgSharing.getCheckedRadioButtonId();

            if (sharingID == -1) {

                Toast.makeText(this,
                        "Please select sharing preference",
                        Toast.LENGTH_SHORT).show();

                return;
            }


            // Check student ID

            if (student.isEmpty()) {

                etStudentID.setError("Enter student ID number");

                etStudentID.requestFocus();

                return;
            }


            String location = spHostelLocation.getSelectedItem().toString();

            String room = spRoomType.getSelectedItem().toString();

            String sharing = sharingID == R.id.rbShareable
                    ? "Shareable" : "Not Shareable";


            // Navigate to Payment Activity

            Intent intent = new Intent(
                    HostelActivity.this,
                    PaymentActivity.class
            );

            intent.putExtra("idNumber", student);
            intent.putExtra("hostelLocation", location);
            intent.putExtra("roomType", room);
            intent.putExtra("sharing", sharing);

            startActivity(intent);

        });

    }
}