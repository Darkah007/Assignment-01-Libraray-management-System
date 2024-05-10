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

public class PublisherDetailsActivity extends AppCompatActivity {

    private EditText editTextPublisherName, editTextAddress, editTextPhone;
    private Button buttonAdd, buttonUpdate, buttonDelete;
    private ListView listViewRecords;
    private DBHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> publisherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_details);

        // Initialize views
        editTextPublisherName = findViewById(R.id.editTextPublisherName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        listViewRecords = findViewById(R.id.listViewRecords);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Initialize publisher list and adapter
        publisherList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, publisherList);
        listViewRecords.setAdapter(adapter);

        // Set click listeners
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPublisher();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePublisher();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePublisher();
            }
        });

        // Display initial records
        displayPublishers();
    }

    // Method to add a publisher
    private void addPublisher() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", editTextPublisherName.getText().toString());
        values.put("ADDRESS", editTextAddress.getText().toString());
        values.put("PHONE", editTextPhone.getText().toString());
        long newRowId = db.insert("Publisher", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Publisher added successfully", Toast.LENGTH_SHORT).show();
            // Refresh list view
            displayPublishers();
        } else {
            Toast.makeText(this, "Error adding publisher", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    // Method to update a publisher
    private void updatePublisher() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ADDRESS", editTextAddress.getText().toString());
        values.put("PHONE", editTextPhone.getText().toString());
        int rowsAffected = db.update("Publisher", values, "NAME = ?",
                new String[]{editTextPublisherName.getText().toString()});
        if (rowsAffected > 0) {
            Toast.makeText(this, "Publisher updated successfully", Toast.LENGTH_SHORT).show();
            // Refresh list view
            displayPublishers();
        } else {
            Toast.makeText(this, "Error updating publisher", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    // Method to delete a publisher
    private void deletePublisher() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete("Publisher", "NAME = ?",
                new String[]{editTextPublisherName.getText().toString()});
        if (rowsDeleted > 0) {
            Toast.makeText(this, "Publisher deleted successfully", Toast.LENGTH_SHORT).show();
            // Refresh list view
            displayPublishers();
        } else {
            Toast.makeText(this, "Error deleting publisher", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    // Method to display publishers in the list view
    private void displayPublishers() {
        publisherList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Publisher", null);
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex("NAME");
            int addressIndex = cursor.getColumnIndex("ADDRESS");
            int phoneIndex = cursor.getColumnIndex("PHONE");

            String name = cursor.getString(nameIndex);
            String address = cursor.getString(addressIndex);
            String phone = cursor.getString(phoneIndex);

            publisherList.add("Name: " + name + ", Address: " + address + ", Phone: " + phone);
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}
