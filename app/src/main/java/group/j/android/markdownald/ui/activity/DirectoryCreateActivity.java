package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import group.j.android.markdownald.R;
import group.j.android.markdownald.util.FileUtils;

/**
 * Implements the interface for naming the new directory.
 */
public class DirectoryCreateActivity extends AppCompatActivity {
    private EditText edit_directory_title;
    private Button btn_create_directory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_create);
        edit_directory_title = findViewById(R.id.edit_directory_title);
        btn_create_directory = findViewById(R.id.btn_create_directory);
        btn_create_directory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_directory_title.getText().toString();
                if (FileUtils.saveToDir(DirectoryCreateActivity.this, name)) {
                    Intent intent = new Intent(DirectoryCreateActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DirectoryCreateActivity.this, "This directory has been created", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}