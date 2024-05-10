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

public class BookListActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextPublisher, editTextBookId;
    private Button buttonAdd, buttonUpdate, buttonDelete;
    private ListView listViewBooks;
    private DBHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        // Initialize views
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextPublisher = findViewById(R.id.editTextPublisher);
        editTextBookId = findViewById(R.id.editTextBookId);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        listViewBooks = findViewById(R.id.listViewBooks);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Initialize book list and adapter
        bookList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookList);
        listViewBooks.setAdapter(adapter);

        // Set click listeners
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });

        // Display initial records
        displayBooks();
    }

    // Method to add a book
    private void addBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TITLE", editTextTitle.getText().toString());
        values.put("PUBLISHER_NAME", editTextPublisher.getText().toString());
        values.put("BOOK_ID", editTextBookId.getText().toString());
        long newRowId = db.insert("Book", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show();
            // Refresh list view
            displayBooks();
        } else {
            Toast.makeText(this, "Error adding book", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    // Method to update a book
    private void updateBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TITLE", editTextTitle.getText().toString());
        values.put("PUBLISHER_NAME", editTextPublisher.getText().toString());
        int rowsAffected = db.update("Book", values, "BOOK_ID = ?",
                new String[]{editTextBookId.getText().toString()});
        if (rowsAffected > 0) {
            Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show();
            // Refresh list view
            displayBooks();
        } else {
            Toast.makeText(this, "Error updating book", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    // Method to delete a book
    private void deleteBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete("Book", "BOOK_ID = ?",
                new String[]{editTextBookId.getText().toString()});
        if (rowsDeleted > 0) {
            Toast.makeText(this, "Book deleted successfully", Toast.LENGTH_SHORT).show();
            // Refresh list view
            displayBooks();
        } else {
            Toast.makeText(this, "Error deleting book", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    // Method to display books in the list view
    private void displayBooks() {
        bookList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Book", null);
        while (cursor.moveToNext()) {
            int bookIdIndex = cursor.getColumnIndex("BOOK_ID");
            int titleIndex = cursor.getColumnIndex("TITLE");
            int publisherIndex = cursor.getColumnIndex("PUBLISHER_NAME");

            String bookId = cursor.getString(bookIdIndex);
            String title = cursor.getString(titleIndex);
            String publisher = cursor.getString(publisherIndex);

            bookList.add("Book ID: " + bookId + ", Title: " + title + ", Publisher: " + publisher);
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}
