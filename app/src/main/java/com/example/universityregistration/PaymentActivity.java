package com.example.universityregistration;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    EditText etMpesaPhone;
    TextView tvPaymentStatus;
    Button btnPay, btnCheckPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        etMpesaPhone = findViewById(R.id.etMpesaPhone);
        tvPaymentStatus = findViewById(R.id.tvPaymentStatus);

        btnPay = findViewById(R.id.btnPay);
        btnCheckPayment = findViewById(R.id.btnCheckPayment);

        btnPay.setOnClickListener(v -> {

            String phone = etMpesaPhone.getText().toString().trim();

            if (phone.isEmpty()) {
                etMpesaPhone.setError("Enter your M-PESA number");
                return;
            }

            if (!phone.matches("^(07|01)[0-9]{8}$")) {
                etMpesaPhone.setError("Enter a valid Kenyan phone number");
                return;
            }

            Toast.makeText(
                    PaymentActivity.this,
                    "Payment integration will be connected next.",
                    Toast.LENGTH_LONG
            ).show();

        });

        btnCheckPayment.setOnClickListener(v -> {

            Toast.makeText(
                    PaymentActivity.this,
                    "Checking payment status...",
                    Toast.LENGTH_SHORT
            ).show();

        });
    }
}