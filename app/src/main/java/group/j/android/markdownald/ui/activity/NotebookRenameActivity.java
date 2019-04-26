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
 * Implements the interface for renaming a notebook.
 */
public class NotebookRenameActivity extends AppCompatActivity {
    private static final String TAG = "NotebookRenameActivity";
    private static final String DUPLICATION_REMINDER = "This notebook has existed";

    private EditText edit_rename_notebook;
    private Button btn_rename_notebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_rename);
        edit_rename_notebook = findViewById(R.id.edit_rename_notebook);
        btn_rename_notebook = findViewById(R.id.btn_rename_notebook);
        btn_rename_notebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldTitle = getIntent().getStringExtra("notebook_title");
                String newTitle = edit_rename_notebook.getText().toString();
                if (!FileUtils.notebookExists(NotebookRenameActivity.this, newTitle)) {
                    FileUtils.rename(NotebookRenameActivity.this, oldTitle, newTitle);
                    Intent intent = new Intent(NotebookRenameActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NotebookRenameActivity.this, DUPLICATION_REMINDER, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
