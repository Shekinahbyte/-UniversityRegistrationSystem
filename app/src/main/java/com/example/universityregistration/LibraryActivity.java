package com.example.universityregistration;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class LibraryActivity extends AppCompatActivity {

    Spinner spISBN;

    TextView tvAuthor, tvTitle;

    EditText etDateBorrowed, etReturnDate, etStudentID;

    Button btnNext, btnClear;

    String[] isbn = {
            "Select ISBN",
            "9780135166307",
            "9780131103627",
            "9780132350884",
            "9780134685991"
    };

    String[] authors = {
            "",
            "Herbert Schildt",
            "Brian W. Kernighan",
            "Robert C. Martin",
            "Joshua Bloch"
    };

    String[] titles = {
            "",
            "Java: The Complete Reference",
            "The C Programming Language",
            "Clean Code",
            "Effective Java"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        // Connect Java variables to XML components

        spISBN = findViewById(R.id.spISBN);

        tvAuthor = findViewById(R.id.tvAuthor);
        tvTitle = findViewById(R.id.tvTitle);

        etDateBorrowed = findViewById(R.id.etDateBorrowed);
        etReturnDate = findViewById(R.id.etReturnDate);
        etStudentID = findViewById(R.id.etStudentID);

        btnNext = findViewById(R.id.btnLibraryNext);
        btnClear = findViewById(R.id.btnLibraryClear);


        // Populate ISBN dropdown

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                isbn
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spISBN.setAdapter(adapter);


        // Receive student ID from Activity 1

        String studentID = getIntent().getStringExtra("idNumber");

        if (studentID != null) {
            etStudentID.setText(studentID);
        }


        // Automatically display author and title

        spISBN.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            android.view.View view,
                            int position,
                            long id) {

                        if (position > 0) {
                            tvAuthor.setText(authors[position]);
                            tvTitle.setText(titles[position]);
                        } else {
                            tvAuthor.setText("Select a book ISBN");
                            tvTitle.setText("Select a book ISBN");
                        }
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );


        // Date Borrowed picker

        etDateBorrowed.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            new DatePickerDialog(this,
                    (view, year, month, day) -> {

                        String date = day + "/" + (month + 1) + "/" + year;

                        etDateBorrowed.setText(date);

                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });


        // Return Date picker

        etReturnDate.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            new DatePickerDialog(this,
                    (view, year, month, day) -> {

                        String date = day + "/" + (month + 1) + "/" + year;

                        etReturnDate.setText(date);

                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });


        // Clear button

        btnClear.setOnClickListener(v -> {

            spISBN.setSelection(0);

            tvAuthor.setText("Select a book ISBN");
            tvTitle.setText("Select a book ISBN");

            etDateBorrowed.setText("");
            etReturnDate.setText("");

            etStudentID.setText("");

            Toast.makeText(this,
                    "Library form cleared",
                    Toast.LENGTH_SHORT).show();
        });


        // Next button validation

        btnNext.setOnClickListener(v -> {

            String student = etStudentID.getText().toString().trim();

            String borrowed = etDateBorrowed.getText().toString().trim();

            String returned = etReturnDate.getText().toString().trim();


            if (spISBN.getSelectedItemPosition() == 0) {

                Toast.makeText(this,
                        "Please select a book",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            if (borrowed.isEmpty()) {

                etDateBorrowed.setError("Select borrowing date");
                return;
            }

            if (returned.isEmpty()) {

                etReturnDate.setError("Select return date");
                return;
            }

            if (student.isEmpty()) {

                etStudentID.setError("Enter student ID");
                return;
            }


            Intent intent = new Intent(
                    LibraryActivity.this,
                    HostelActivity.class
            );

            intent.putExtra("idNumber", student);

            startActivity(intent);

        });

    }
}

