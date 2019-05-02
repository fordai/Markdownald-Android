package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import group.j.android.markdownald.R;
import group.j.android.markdownald.model.Notebook;
import group.j.android.markdownald.util.FileUtils;

/**
 * Implements the interface for naming the new directory.
 * If the name is the same as the previous one, a hint should be offered.
 */
public class NotebookCreateActivity extends AppCompatActivity {
    private static final String TAG = "NotebookCreateActivity";
    private static final String DUPLICATION_REMINDER = "This notebook has been created";

    private EditText edit_notebook_title;
    private Button btn_create_notebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_create);
        edit_notebook_title = findViewById(R.id.edit_notebook_title);
        btn_create_notebook = findViewById(R.id.btn_create_notebook);
        btn_create_notebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_notebook_title.getText().toString();
                if (FileUtils.saveToDir(NotebookCreateActivity.this, name)) {
                    Intent intent = new Intent(NotebookCreateActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(NotebookCreateActivity.this, DUPLICATION_REMINDER, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();

                }
            }
        });
    }
}
