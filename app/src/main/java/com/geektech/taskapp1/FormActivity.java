package com.geektech.taskapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FormActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        editTitle = findViewById(R.id.editTitle);
        editDescription= findViewById(R.id.editDescription);
    }

    public void onClick(View view) {

        String title = editTitle.getText().toString().trim();
        String description= editDescription.getText().toString().trim();
        Task task = new Task(title, description);
        App.getDatabase().taskDao().insert(task);

        //Intent intent  = new Intent();
        //intent.putExtra("title",title);
        //intent.putExtra("description",description);
        //setResult(RESULT_OK,intent);
        finish();


    }
}
