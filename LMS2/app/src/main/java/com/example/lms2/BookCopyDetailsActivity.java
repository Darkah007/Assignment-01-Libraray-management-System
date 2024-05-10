package com.example.lms2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class BookCopyDetailsActivity extends AppCompatActivity {

    private EditText editTextBookId, editTextBranchId, editTextAccessNo;
    private Button buttonAdd, buttonUpdate, buttonDelete;
    private ListView listViewRecords;
    private DBHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> bookCopyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_copy_details);

        // Initialize views
        editTextBookId = findViewById(R.id.editTextBookId);
        editTextBranchId = findViewById(R.id.editTextBranchId);
        editTextAccessNo = findViewById(R.id.editTextAccessNo);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        listViewRecords = findViewById(R.id.listViewRecords);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Initialize book copy list and adapter
        bookCopyList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookCopyList);
        listViewRecords.setAdapter(adapter);

        // Set click listeners
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookCopy();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBookCopy();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBookCopy();
            }
        });

        // Display initial records
        displayBookCopies();
    }

    // Method to add a book copy
    private void addBookCopy() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BOOK_ID", editTextBookId.getText().toString());
        values.put("BRANCH_ID", editTextBranchId.getText().toString());
        values.put("ACCESS_NO", editTextAccessNo.getText().toString());
        long newRowId = db.insert("Book_Copy", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Book copy added successfully", Toast.LENGTH_SHORT).show();
            // Refresh list view
            displayBookCopies();
        } else {
            Toast.makeText(this, "Error adding book copy", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    // Method to update a book copy
    private void updateBookCopy() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BRANCH_ID", editTextBranchId.getText().toString());
        int rowsAffected = db.update("Book_Copy", values, "BOOK_ID = ? AND ACCESS_NO = ?",
                new String[]{editTextBookId.getText().toString(), editTextAccessNo.getText().toString()});
        if (rowsAffected > 0) {
            Toast.makeText(this, "Book copy updated successfully", Toast.LENGTH_SHORT).show();
            // Refresh list view
            displayBookCopies();
        } else {
            Toast.makeText(this, "Error updating book copy", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    // Method to delete a book copy
    private void deleteBookCopy() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete("Book_Copy", "BOOK_ID = ? AND BRANCH_ID = ? AND ACCESS_NO = ?",
                new String[]{editTextBookId.getText().toString(), editTextBranchId.getText().toString(), editTextAccessNo.getText().toString()});
        if (rowsDeleted > 0) {
            Toast.makeText(this, "Book copy deleted successfully", Toast.LENGTH_SHORT).show();
            // Refresh list view
            displayBookCopies();
        } else {
            Toast.makeText(this, "Error deleting book copy", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    // Method to display book copies in the list view
    private void displayBookCopies() {
        bookCopyList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Book_Copy", null);
        while (cursor.moveToNext()) {
            int bookIdIndex = cursor.getColumnIndex("BOOK_ID");
            int branchIdIndex = cursor.getColumnIndex("BRANCH_ID");
            int accessNoIndex = cursor.getColumnIndex("ACCESS_NO");

            String bookId = cursor.getString(bookIdIndex);
            String branchId = cursor.getString(branchIdIndex);
            String accessNo = cursor.getString(accessNoIndex);

            bookCopyList.add("Book ID: " + bookId + ", Branch ID: " + branchId + ", Access No: " + accessNo);
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}
