package com.example.lms2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onOptionClicked(View view) {
        Intent intent;
        if (view.getId() == R.id.option1) {
            // Navigate to BookWorksActivity
            intent = new Intent(this, BookWorksActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.option2) {
            // Navigate to LendingDetailsActivity
            intent = new Intent(this, LendingDetailsActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.option3) {
            // Navigate to MembershipDetailsActivity
            intent = new Intent(this, MembershipDetailsActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.option4) {
            // Navigate to BranchDetailsActivity
            intent = new Intent(this, BranchDetailsActivity.class);
            startActivity(intent);
        } else {
            // Handle unexpected clicks
        }
    }
}
