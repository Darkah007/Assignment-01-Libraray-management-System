package com.example.lms2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LendingDBOperations {

    private DBHelper dbHelper;

    public LendingDBOperations(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Insert method for lending details
    public long addLoan(String accessNo, String branchId, String cardNo, String dateOut, String dateDue, String dateReturned) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Add values
        values.put("ACCESS_NO", accessNo);
        values.put("BRANCH_ID", branchId);
        values.put("CARD_NO", cardNo);
        values.put("DATE_OUT", dateOut);
        values.put("DATE_DUE", dateDue);
        values.put("DATE_RETURNED", dateReturned);
        // Insert row
        long result = db.insert("Book_Loan", null, values);
        db.close();
        return result;
    }

    // Retrieve method for lending details
    public List<String> getAllLoans() {
        List<String> loansList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Book_Loan", null);
        if (cursor.moveToFirst()) {
            do {
                String loanInfo = "Access No: " + cursor.getString(0) +
                        ", Branch ID: " + cursor.getString(1) +
                        ", Card No: " + cursor.getString(2) +
                        ", Date Out: " + cursor.getString(3) +
                        ", Date Due: " + cursor.getString(4) +
                        ", Date Returned: " + cursor.getString(5);
                loansList.add(loanInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return loansList;
    }

    // Update method for lending details
    public int updateLoanReturnDate(String accessNo, String branchId, String cardNo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DATE_RETURNED", getCurrentDate());
        // Update row
        int rowsAffected = db.update("Book_Loan", values,
                "ACCESS_NO = ? AND BRANCH_ID = ? AND CARD_NO = ?",
                new String[]{accessNo, branchId, cardNo});
        db.close();
        return rowsAffected;
    }

    // Delete method for lending details
    public int deleteLoan(String accessNo, String branchId, String cardNo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Delete row
        int rowsDeleted = db.delete("Book_Loan",
                "ACCESS_NO = ? AND BRANCH_ID = ? AND CARD_NO = ?",
                new String[]{accessNo, branchId, cardNo});
        db.close();
        return rowsDeleted;
    }

    // Helper method to get current date in yyyy-MM-dd format
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}
