package com.example.lms2;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "library.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL("CREATE TABLE Book(BOOK_ID VARCHAR(13), TITLE VARCHAR(30), PUBLISHER_NAME VARCHAR(20), PRIMARY KEY (BOOK_ID))");
        db.execSQL("CREATE TABLE Publisher(NAME VARCHAR(20), ADDRESS VARCHAR(30), PHONE VARCHAR(10), PRIMARY KEY (NAME))");
        db.execSQL("CREATE TABLE Branch(BRANCH_ID VARCHAR(5), BRANCH_NAME VARCHAR(20), ADDRESS VARCHAR(30), PRIMARY KEY (BRANCH_ID))");
        db.execSQL("CREATE TABLE Member(CARD_NO VARCHAR(10), NAME VARCHAR(20), ADDRESS VARCHAR(30), PHONE VARCHAR(10), UNPAID_DUES NUMBER(5,2), PRIMARY KEY (CARD_NO))");
        db.execSQL("CREATE TABLE Book_Author(BOOK_ID VARCHAR(13), AUTHOR_NAME VARCHAR(20), PRIMARY KEY(BOOK_ID, AUTHOR_NAME), FOREIGN KEY(BOOK_ID) REFERENCES Book)");
        db.execSQL("CREATE TABLE Book_Copy(BOOK_ID VARCHAR(13), BRANCH_ID VARCHAR(5), ACCESS_NO VARCHAR(5), PRIMARY KEY(ACCESS_NO, BRANCH_ID), FOREIGN KEY(BOOK_ID) REFERENCES Book, FOREIGN KEY(BRANCH_ID) REFERENCES Branch)");
        db.execSQL("CREATE TABLE Book_Loan(ACCESS_NO VARCHAR(5), BRANCH_ID VARCHAR(5), CARD_NO VARCHAR(5), DATE_OUT DATE, DATE_DUE DATE, DATE_RETURNED DATE, PRIMARY KEY(ACCESS_NO, BRANCH_ID, CARD_NO, DATE_OUT), FOREIGN KEY(ACCESS_NO, BRANCH_ID) REFERENCES Book_Copy, FOREIGN KEY(CARD_NO) REFERENCES Member, FOREIGN KEY(BRANCH_ID) REFERENCES Branch)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement upgrade policy if needed
    }
}
