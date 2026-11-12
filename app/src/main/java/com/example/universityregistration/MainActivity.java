package com.example.universityregistration;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etFullName, etID, etRegNo;

    RadioGroup rgGender;

    Spinner spCourse, spDepartment;

    Button btnNext, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect Java variables to XML components

        etFullName = findViewById(R.id.etFullName);
        etID = findViewById(R.id.etID);
        etRegNo = findViewById(R.id.etRegNo);

        rgGender = findViewById(R.id.rgGender);

        spCourse = findViewById(R.id.spCourse);
        spDepartment = findViewById(R.id.spDepartment);

        btnNext = findViewById(R.id.btnNext);
        btnClear = findViewById(R.id.btnClear);


        // 1. Populate Course dropdown

        String[] courses = {
                "Select Course",
                "BSc Computer Science",
                "BSc Information Technology",
                "BSc Electrical Engineering",
                "BSc Mechanical Engineering",
                "BSc Civil Engineering",
                "BSc Business Information Technology"
        };

        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                courses
        );

        courseAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spCourse.setAdapter(courseAdapter);


        // 2. Populate Department dropdown

        String[] departments = {
                "Select Department",
                "Computer Science and Information Technology",
                "Electrical and Electronic Engineering",
                "Mechanical Engineering",
                "Civil Engineering",
                "Business and Management Studies"
        };

        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                departments
        );

        departmentAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spDepartment.setAdapter(departmentAdapter);


        // 3. Clear button functionality

        btnClear.setOnClickListener(v -> {

            etFullName.setText("");
            etID.setText("");
            etRegNo.setText("");

            rgGender.clearCheck();

            spCourse.setSelection(0);
            spDepartment.setSelection(0);

            Toast.makeText(this,
                    "Form cleared",
                    Toast.LENGTH_SHORT).show();
        });


        // 4. Validate information and navigate

        btnNext.setOnClickListener(v -> {

            String fullName = etFullName.getText().toString().trim();

            String idNumber = etID.getText().toString().trim();

            String regNumber = etRegNo.getText().toString().trim();


            // Check full names

            if (fullName.isEmpty()) {
                etFullName.setError("Enter your full names");
                etFullName.requestFocus();
                return;
            }


            // Check ID number

            if (idNumber.isEmpty()) {
                etID.setError("Enter your ID number");
                etID.requestFocus();
                return;
            }


            // Check registration number

            if (regNumber.isEmpty()) {
                etRegNo.setError("Enter registration number");
                etRegNo.requestFocus();
                return;
            }


            // Check gender

            int selectedGender = rgGender.getCheckedRadioButtonId();

            if (selectedGender == -1) {
                Toast.makeText(this,
                        "Please select gender",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            String gender;

            if (selectedGender == R.id.rbMale) {
                gender = "Male";
            } else {
                gender = "Female";
            }


            // Check course

            if (spCourse.getSelectedItemPosition() == 0) {
                Toast.makeText(this,
                        "Please select a course",
                        Toast.LENGTH_SHORT).show();
                return;
            }


            // Check department

            if (spDepartment.getSelectedItemPosition() == 0) {
                Toast.makeText(this,
                        "Please select a department",
                        Toast.LENGTH_SHORT).show();
                return;
            }


            String course = spCourse.getSelectedItem().toString();

            String department = spDepartment.getSelectedItem().toString();


            // 5. Create Student object

            Student student = new Student();

            student.setFullName(fullName);
            student.setIdNumber(idNumber);
            student.setRegNumber(regNumber);
            student.setGender(gender);
            student.setCourse(course);
            student.setDepartment(department);

// 6. Connect to Firebase

            DatabaseReference studentsRef = FirebaseDatabase
                    .getInstance("https://university-registration-1627e-default-rtdb.firebaseio.com")
                    .getReference("students");

// 7. Save student information

            studentsRef.push().setValue(student)
                    .addOnSuccessListener(unused -> {

                        Toast.makeText(MainActivity.this,
                                "Student registered successfully!",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(
                                MainActivity.this,
                                LibraryActivity.class
                        );

                        intent.putExtra("fullName", fullName);
                        intent.putExtra("idNumber", idNumber);
                        intent.putExtra("regNumber", regNumber);
                        intent.putExtra("gender", gender);
                        intent.putExtra("course", course);
                        intent.putExtra("department", department);

                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(MainActivity.this,
                                "Registration failed: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    });

        });

    }
}