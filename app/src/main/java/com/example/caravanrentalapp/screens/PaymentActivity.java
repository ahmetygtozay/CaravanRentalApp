package com.example.caravanrentalapp.screens;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caravanrentalapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PaymentActivity extends AppCompatActivity {

    private TextView paymentPrice; // Caravan fiyatını gösterecek
    private EditText nameInput;
    private EditText dateInput, dateInput2;
    private EditText cardNumberInput;
    private EditText cvvInput;
    private EditText cardEndDateInput;
    private Button applyButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // UI bileşenlerini tanımla
        paymentPrice = findViewById(R.id.payment_price);
        nameInput = findViewById(R.id.name_input);
        dateInput = findViewById(R.id.date_input);
        dateInput2 = findViewById(R.id.date_input2);
        cardNumberInput = findViewById(R.id.card_number_input);
        cvvInput = findViewById(R.id.cvv_input);
        applyButton = findViewById(R.id.apply_button);

        dateInput.setOnClickListener(v -> showDatePickerDialog());
        dateInput2.setOnClickListener(v -> showDatePickerDialog2());
        // Caravan fiyatını Intent ile al
        String price = getIntent().getStringExtra("price");
        paymentPrice.setText("Price: " + price);

        // Apply butonu
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String date = dateInput.getText().toString();
                String date2 = dateInput2.getText().toString();
                String cardNumber = cardNumberInput.getText().toString();
                String cvv = cvvInput.getText().toString();
                String cardEndDate = cardEndDateInput.getText().toString();

                if (name.isEmpty() || date.isEmpty() || cardNumber.isEmpty() || cvv.isEmpty() || cardEndDate.isEmpty() || date2.isEmpty()) {
                    Toast.makeText(PaymentActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PaymentActivity.this, "Payment Successful!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    dateInput.setText(selectedDate);

                    // Tarih farkını hesapla
                    calculateDateDifference();
                }, year, month, day);

        datePickerDialog.show();
    }

    private void showDatePickerDialog2() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    dateInput2.setText(selectedDate);

                    // Tarih farkını hesapla
                    calculateDateDifference();
                }, year, month, day);

        datePickerDialog.show();
    }
    private void calculateDateDifference() {
        try {
            String dateStr1 = dateInput.getText().toString();
            String dateStr2 = dateInput2.getText().toString();

            // Tarih formatını belirle
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = format.parse(dateStr1);
            Date date2 = format.parse(dateStr2);

            // Tarih farkını hesapla (milisaniye cinsinden)
            long differenceInMillis = date2.getTime() - date1.getTime();

            // Milisaniyeyi gün cinsine çevir
            long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis);

            // Fiyatı hesapla (Örnek olarak price'ı gün sayısı ile çarpıyoruz)
            String price = getIntent().getStringExtra("price");
            double totalPrice = Double.parseDouble(price) * differenceInDays;

            // Küsüratsız bir sayı için yuvarlama
            long roundedPrice = Math.round(totalPrice);

            // Sonucu ekrana yazdır
            paymentPrice.setText("Price: " + roundedPrice + " for " + differenceInDays + " days");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
