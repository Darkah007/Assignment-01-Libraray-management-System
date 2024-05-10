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
import java.util.List;

public class BranchDetailsActivity extends AppCompatActivity {

    private EditText editTextBranchId, editTextBranchName, editTextBranchAddress;
    private Button buttonAddBranch, buttonUpdateBranch, buttonDeleteBranch;
    private ListView listViewBranches;
    private ArrayList<Branch> branchesList;
    private ArrayAdapter<Branch> branchesAdapter;
    private BranchDAO branchDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_details);

        branchDAO = new BranchDAO(this);

        editTextBranchId = findViewById(R.id.editTextBranchId);
        editTextBranchName = findViewById(R.id.editTextBranchName);
        editTextBranchAddress = findViewById(R.id.editTextBranchAddress);

        buttonAddBranch = findViewById(R.id.buttonAddBranch);
        buttonUpdateBranch = findViewById(R.id.buttonUpdateBranch);
        buttonDeleteBranch = findViewById(R.id.buttonDeleteBranch);

        listViewBranches = findViewById(R.id.listViewBranches);

        branchesList = new ArrayList<>();
        branchesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, branchesList);
        listViewBranches.setAdapter(branchesAdapter);

        loadBranches();

        listViewBranches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Branch branch = branchesList.get(position);
                editTextBranchId.setText(branch.getBranchId());
                editTextBranchName.setText(branch.getBranchName());
                editTextBranchAddress.setText(branch.getBranchAddress());
            }
        });
    }

    public void addNewBranch(View view) {
        String branchId = editTextBranchId.getText().toString().trim();
        String branchName = editTextBranchName.getText().toString().trim();
        String branchAddress = editTextBranchAddress.getText().toString().trim();

        if (branchId.isEmpty() || branchName.isEmpty() || branchAddress.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Branch newBranch = new Branch(branchId, branchName, branchAddress);

        if (branchDAO.addBranch(newBranch)) {
            Toast.makeText(this, "Branch added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
            loadBranches();
        } else {
            Toast.makeText(this, "Failed to add branch", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateBranch(View view) {
        String branchId = editTextBranchId.getText().toString().trim();
        String branchName = editTextBranchName.getText().toString().trim();
        String branchAddress = editTextBranchAddress.getText().toString().trim();

        if (branchId.isEmpty()) {
            Toast.makeText(this, "Please select a branch to update", Toast.LENGTH_SHORT).show();
            return;
        }

        Branch updatedBranch = new Branch(branchId, branchName, branchAddress);

        if (branchDAO.updateBranch(updatedBranch)) {
            Toast.makeText(this, "Branch updated successfully", Toast.LENGTH_SHORT).show();
            clearFields();
            loadBranches();
        } else {
            Toast.makeText(this, "Failed to update branch", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteBranch(View view) {
        String branchId = editTextBranchId.getText().toString().trim();

        if (branchId.isEmpty()) {
            Toast.makeText(this, "Please select a branch to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        if (branchDAO.deleteBranch(branchId)) {
            Toast.makeText(this, "Branch deleted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
            loadBranches();
        } else {
            Toast.makeText(this, "Failed to delete branch", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        editTextBranchId.setText("");
        editTextBranchName.setText("");
        editTextBranchAddress.setText("");
    }

    private void loadBranches() {
        branchesList.clear();
        branchesList.addAll(branchDAO.getAllBranches());
        branchesAdapter.notifyDataSetChanged();
    }
}
