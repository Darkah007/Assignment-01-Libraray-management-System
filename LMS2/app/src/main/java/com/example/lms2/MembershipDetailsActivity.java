package com.example.lms2;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MembershipDetailsActivity extends AppCompatActivity {

    private EditText cardNoEditText, nameEditText, addressEditText, phoneEditText, duesEditText;
    private Button addButton, updateButton, deleteButton;
    private MembershipDatabaseHelper dbHelper;
    private ListView membersListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_details);

        // Initialize views
        cardNoEditText = findViewById(R.id.cardNoEditText);
        nameEditText = findViewById(R.id.nameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        duesEditText = findViewById(R.id.duesEditText);
        addButton = findViewById(R.id.addButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        membersListView = findViewById(R.id.membersListView);

        // Initialize MembershipDatabaseHelper
        dbHelper = new MembershipDatabaseHelper(this);

        // Initialize member list
        memberList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, memberList);
        membersListView.setAdapter(adapter);

        // Set onClickListeners
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMember();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMember();
            }
        });

        // Set item click listener for the list view to display a toast with member details
        membersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String memberDetails = memberList.get(position);
                Toast.makeText(MembershipDetailsActivity.this, memberDetails, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMember() {
        boolean inserted = dbHelper.addMember(
                cardNoEditText.getText().toString(),
                nameEditText.getText().toString(),
                addressEditText.getText().toString(),
                phoneEditText.getText().toString(),
                Double.parseDouble(duesEditText.getText().toString())
        );

        if (inserted) {
            Toast.makeText(this, "Member added successfully", Toast.LENGTH_SHORT).show();
            updateListView();
        } else {
            Toast.makeText(this, "Error adding member", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMember() {
        int updatedRows = dbHelper.updateMember(
                cardNoEditText.getText().toString(),
                nameEditText.getText().toString(),
                addressEditText.getText().toString(),
                phoneEditText.getText().toString(),
                Double.parseDouble(duesEditText.getText().toString())
        );

        if (updatedRows > 0) {
            Toast.makeText(this, "Member updated successfully", Toast.LENGTH_SHORT).show();
            updateListView();
        } else {
            Toast.makeText(this, "No member found with this card number", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteMember() {
        int deletedRows = dbHelper.deleteMember(cardNoEditText.getText().toString());

        if (deletedRows > 0) {
            Toast.makeText(this, "Member deleted successfully", Toast.LENGTH_SHORT).show();
            updateListView();
        } else {
            Toast.makeText(this, "No member found with this card number", Toast.LENGTH_SHORT).show();
        }
    }

    // Update the list view with the latest data from the database
    private void updateListView() {
        memberList.clear();
        memberList.addAll(dbHelper.getAllMembers());
        adapter.notifyDataSetChanged();
    }
}