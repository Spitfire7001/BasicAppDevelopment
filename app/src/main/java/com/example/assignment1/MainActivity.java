package com.example.assignment1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText principleInput, interestInput, timeInput;
    TextView calculation;
    Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Default Empty Activity Setup
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get text box inputs
        principleInput = findViewById(R.id.principleInput);
        interestInput = findViewById(R.id.interestInput);
        timeInput = findViewById(R.id.timeInput);

        calculation = findViewById(R.id.calculation);

        // Find button ID and set on click listener to calculate when pressed
        calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(v -> calculate());
    }

    // Calculates and Displays final calculation on home screen
    public void calculate() {
        double principleAmount, interestAmount;
        int numOfYears;
        DecimalFormat df = new DecimalFormat("#.##");

        // Tries to parse text box input as numbers
        // If unable to parse text to numbers throw the user a TOAST msg
        try {
            principleAmount = Double.parseDouble(principleInput.getText().toString());
            interestAmount = Double.parseDouble(interestInput.getText().toString());
            numOfYears = Integer.parseInt(timeInput.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please fill all fields properly", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert years to months and yearly interest to monthly interest
        double numOfMonths = numOfYears * 12;
        double monthlyInterest = (interestAmount / 100) / 12;

        // Mortgage calculation and final string creation
        double monthlyMortgage = principleAmount * (monthlyInterest * Math.pow(1 + monthlyInterest, numOfMonths)) /
                (Math.pow(1 + monthlyInterest, numOfMonths) - 1);
        String calculationString = "$" + df.format(monthlyMortgage) + " / Month";

        // Display the final mortgage calculation to user
        calculation.setText(calculationString);

        // Send user a TOAST message
        Toast.makeText(this, "Calculation Complete", Toast.LENGTH_SHORT).show();
    }
}