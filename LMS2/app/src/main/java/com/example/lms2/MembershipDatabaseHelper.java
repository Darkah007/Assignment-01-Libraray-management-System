package com.example.lms2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class MembershipDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "library.db";
    private static final int DATABASE_VERSION = 1;

    public MembershipDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Member(CARD_NO VARCHAR(10) PRIMARY KEY, NAME VARCHAR(20), ADDRESS VARCHAR(30), PHONE VARCHAR(10), UNPAID_DUES NUMBER(5,2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement upgrade policy if needed
    }

    public boolean addMember(String cardNo, String name, String address, String phone, double unpaidDues) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CARD_NO", cardNo);
        values.put("NAME", name);
        values.put("ADDRESS", address);
        values.put("PHONE", phone);
        values.put("UNPAID_DUES", unpaidDues);
        long result = db.insert("Member", null, values);
        return result != -1;
    }

    public int updateMember(String cardNo, String name, String address, String phone, double unpaidDues) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("ADDRESS", address);
        values.put("PHONE", phone);
        values.put("UNPAID_DUES", unpaidDues);
        return db.update("Member", values, "CARD_NO = ?", new String[]{cardNo});
    }

    public int deleteMember(String cardNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Member", "CARD_NO = ?", new String[]{cardNo});
    }

    // Retrieve all members from the database
    public List<String> getAllMembers() {
        List<String> memberList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Member", null);
        if (cursor.moveToFirst()) {
            do {
                String cardNo = cursor.getString(cursor.getColumnIndex("CARD_NO"));
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
                String phone = cursor.getString(cursor.getColumnIndex("PHONE"));
                double unpaidDues = cursor.getDouble(cursor.getColumnIndex("UNPAID_DUES"));
                memberList.add("Card No: " + cardNo + ", Name: " + name + ", Address: " + address + ", Phone: " + phone + ", Unpaid Dues: " + unpaidDues);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return memberList;
    }
}
