package com.example.lms2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO {

    private final DBHelper dbHelper;

    // Define column names as constants in BranchDAO
    public static final String COLUMN_BRANCH_ID = "BRANCH_ID";
    public static final String COLUMN_BRANCH_NAME = "BRANCH_NAME";
    public static final String COLUMN_BRANCH_ADDRESS = "ADDRESS";

    public BranchDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public boolean addBranch(Branch branch) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BRANCH_ID, branch.getBranchId());
        values.put(COLUMN_BRANCH_NAME, branch.getBranchName());
        values.put(COLUMN_BRANCH_ADDRESS, branch.getBranchAddress());
        long result = db.insert("Branch", null, values);
        db.close();
        return result != -1;
    }

    public boolean updateBranch(Branch branch) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BRANCH_NAME, branch.getBranchName());
        values.put(COLUMN_BRANCH_ADDRESS, branch.getBranchAddress());
        int result = db.update("Branch", values, COLUMN_BRANCH_ID + "=?", new String[]{branch.getBranchId()});
        db.close();
        return result > 0;
    }

    public boolean deleteBranch(String branchId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("Branch", COLUMN_BRANCH_ID + "=?", new String[]{branchId});
        db.close();
        return result > 0;
    }

    public List<Branch> getAllBranches() {
        List<Branch> branches = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Branch", null);

        int columnIndexBranchId = cursor.getColumnIndex(COLUMN_BRANCH_ID);
        int columnIndexBranchName = cursor.getColumnIndex(COLUMN_BRANCH_NAME);
        int columnIndexBranchAddress = cursor.getColumnIndex(COLUMN_BRANCH_ADDRESS);

        while (cursor.moveToNext()) {
            String branchId = null;
            String branchName = null;
            String branchAddress = null;

            // Check if column index is valid before accessing cursor's data
            if (columnIndexBranchId != -1) {
                branchId = cursor.getString(columnIndexBranchId);
            }
            if (columnIndexBranchName != -1) {
                branchName = cursor.getString(columnIndexBranchName);
            }
            if (columnIndexBranchAddress != -1) {
                branchAddress = cursor.getString(columnIndexBranchAddress);
            }

            if (branchId != null && branchName != null && branchAddress != null) {
                Branch branch = new Branch(branchId, branchName, branchAddress);
                branches.add(branch);
            }
        }

        cursor.close();
        db.close();
        return branches;
    }
}
