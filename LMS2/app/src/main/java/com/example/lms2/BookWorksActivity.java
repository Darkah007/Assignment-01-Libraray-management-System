package com.example.lms2;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class BookWorksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_works);

        ImageView bookList = findViewById(R.id.bookList);
        ImageView bookDetails = findViewById(R.id.bookDetails);
        ImageView authorDetails = findViewById(R.id.authorDetails);
        ImageView publisherDetails = findViewById(R.id.publisherDetails);

        bookList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Option 1: View book list
                Intent intent = new Intent(BookWorksActivity.this, BookListActivity.class);
                startActivity(intent);
            }
        });

        bookDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Option 2: View book copy details
                Intent intent = new Intent(BookWorksActivity.this, BookCopyDetailsActivity.class);
                startActivity(intent);
            }
        });

        authorDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Option 3: View author details
                Intent intent = new Intent(BookWorksActivity.this, AuthorDetailsActivity.class);
                startActivity(intent);
            }
        });

        publisherDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Option 4: View publisher details
                Intent intent = new Intent(BookWorksActivity.this, PublisherDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void viewBookList(View view) {
        Intent intent = new Intent(this, BookListActivity.class);
        startActivity(intent);
    }

    public void viewBookCopyDetails(View view) {
        Intent intent = new Intent(this, BookCopyDetailsActivity.class);
        startActivity(intent);
    }

    public void viewAuthorDetails(View view) {
        Intent intent = new Intent(this, AuthorDetailsActivity.class);
        startActivity(intent);
    }

    public void viewPublisherDetails(View view) {
        Intent intent = new Intent(this, PublisherDetailsActivity.class);
        startActivity(intent);
    }
}
