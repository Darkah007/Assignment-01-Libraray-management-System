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

public class AuthorDetailsActivity extends AppCompatActivity {

    private EditText editTextBookId, editTextAuthorName;
    private Button buttonAdd, buttonUpdate, buttonDelete;
    private ListView listViewRecords;
    private DBHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> authorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_details);

        // Initialize views
        editTextBookId = findViewById(R.id.editTextBookId);
        editTextAuthorName = findViewById(R.id.editTextAuthorName);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        listViewRecords = findViewById(R.id.listViewRecords);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Initialize author list and adapter
        authorList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, authorList);
        listViewRecords.setAdapter(adapter);

        // Set click listeners
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAuthor();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAuthor();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAuthor();
            }
        });

        // Display initial records
        displayAuthors();
    }

    // Method to add an author
    private void addAuthor() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BOOK_ID", editTextBookId.getText().toString());
        values.put("AUTHOR_NAME", editTextAuthorName.getText().toString());
        long newRowId = db.insert("Book_Author", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Author added successfully", Toast.LENGTH_SHORT).show();
            // Refresh list view
            displayAuthors();
        } else {
            Toast.makeText(this, "Error adding author", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    // Method to update an author
    private void updateAuthor() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("AUTHOR_NAME", editTextAuthorName.getText().toString());
        int rowsAffected = db.update("Book_Author", values, "BOOK_ID = ?",
                new String[]{editTextBookId.getText().toString()});
        if (rowsAffected > 0) {
            Toast.makeText(this, "Author updated successfully", Toast.LENGTH_SHORT).show();
            // Refresh list view
            displayAuthors();
        } else {
            Toast.makeText(this, "Error updating author", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    // Method to delete an author
    private void deleteAuthor() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete("Book_Author", "BOOK_ID = ? AND AUTHOR_NAME = ?",
                new String[]{editTextBookId.getText().toString(), editTextAuthorName.getText().toString()});
        if (rowsDeleted > 0) {
            Toast.makeText(this, "Author deleted successfully", Toast.LENGTH_SHORT).show();
            // Refresh list view
            displayAuthors();
        } else {
            Toast.makeText(this, "Error deleting author", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    // Method to display authors in the list view
    private void displayAuthors() {
        authorList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Book_Author", null);
        while (cursor.moveToNext()) {
            int bookIdIndex = cursor.getColumnIndex("BOOK_ID");
            int authorNameIndex = cursor.getColumnIndex("AUTHOR_NAME");

            String bookId = cursor.getString(bookIdIndex);
            String authorName = cursor.getString(authorNameIndex);

            authorList.add("Book ID: " + bookId + ", Author: " + authorName);
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}
