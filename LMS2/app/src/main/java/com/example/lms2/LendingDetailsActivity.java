package com.example.lms2;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class LendingDetailsActivity extends AppCompatActivity {

    private LendingDBOperations lendingDBOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lending_details);

        lendingDBOperations = new LendingDBOperations(this);

        Button btnAddLoan = findViewById(R.id.btnAddLoan);
        btnAddLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add Loan functionality
                EditText etAccessNo = findViewById(R.id.etAccessNo);
                EditText etBranchId = findViewById(R.id.etBranchId);
                EditText etCardNo = findViewById(R.id.etCardNo);
                EditText etDateOut = findViewById(R.id.etDateOut);
                EditText etDateDue = findViewById(R.id.etDateDue);
                EditText etDateReturned = findViewById(R.id.etDateReturned);

                String accessNo = etAccessNo.getText().toString();
                String branchId = etBranchId.getText().toString();
                String cardNo = etCardNo.getText().toString();
                String dateOut = etDateOut.getText().toString();
                String dateDue = etDateDue.getText().toString();
                String dateReturned = etDateReturned.getText().toString();

                long result = lendingDBOperations.addLoan(accessNo, branchId, cardNo, dateOut, dateDue, dateReturned);
                if (result > 0) {
                    Toast.makeText(LendingDetailsActivity.this, "Loan added successfully", Toast.LENGTH_SHORT).show();
                    refreshLoanList();
                } else {
                    Toast.makeText(LendingDetailsActivity.this, "Error adding loan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnUpdateLoan = findViewById(R.id.btnUpdateLoan);
        btnUpdateLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update Loan functionality
                EditText etAccessNo = findViewById(R.id.etAccessNo);
                EditText etBranchId = findViewById(R.id.etBranchId);
                EditText etCardNo = findViewById(R.id.etCardNo);

                String accessNo = etAccessNo.getText().toString();
                String branchId = etBranchId.getText().toString();
                String cardNo = etCardNo.getText().toString();

                int rowsAffected = lendingDBOperations.updateLoanReturnDate(accessNo, branchId, cardNo);
                if (rowsAffected > 0) {
                    Toast.makeText(LendingDetailsActivity.this, "Loan return date updated successfully", Toast.LENGTH_SHORT).show();
                    refreshLoanList();
                } else {
                    Toast.makeText(LendingDetailsActivity.this, "Error updating loan return date", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnDeleteLoan = findViewById(R.id.btnDeleteLoan);
        btnDeleteLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete Loan functionality
                EditText etAccessNo = findViewById(R.id.etAccessNo);
                EditText etBranchId = findViewById(R.id.etBranchId);
                EditText etCardNo = findViewById(R.id.etCardNo);

                String accessNo = etAccessNo.getText().toString();
                String branchId = etBranchId.getText().toString();
                String cardNo = etCardNo.getText().toString();

                int rowsDeleted = lendingDBOperations.deleteLoan(accessNo, branchId, cardNo);
                if (rowsDeleted > 0) {
                    Toast.makeText(LendingDetailsActivity.this, "Loan deleted successfully", Toast.LENGTH_SHORT).show();
                    refreshLoanList();
                } else {
                    Toast.makeText(LendingDetailsActivity.this, "Error deleting loan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Display loans in ListView
        refreshLoanList();
    }

    // Method to retrieve and display all loans in the ListView
    private void refreshLoanList() {
        ListView listView = findViewById(R.id.listView);
        List<String> loansList = lendingDBOperations.getAllLoans();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, loansList);
        listView.setAdapter(adapter);
    }
}
