package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import group.j.android.markdownald.R;
import group.j.android.markdownald.base.BaseActivity;
import group.j.android.markdownald.db.DatabaseHelper;

/**
 * Implements the interface for renaming a notebook.
 */
public class NotebookRenameActivity extends BaseActivity {
    private static final String TAG = "NotebookRenameActivity";
    private static final String NOTEBOOK_NAME = "notebook_name";
    private static final String DUPLICATION_REMINDER = "This notebook has existed";

    private DatabaseHelper mDatabase;
    private EditText edit_rename_notebook;
    private Button btn_rename_notebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_rename);
        mDatabase = getDatabase();
        edit_rename_notebook = findViewById(R.id.edit_rename_notebook);
        btn_rename_notebook = findViewById(R.id.btn_rename_notebook);
        btn_rename_notebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldName = getIntent().getStringExtra(NOTEBOOK_NAME);
                String newName = edit_rename_notebook.getText().toString();
                if (!mDatabase.isNotebook(newName)) {
                    mDatabase.updateNotebook(oldName, newName);
                    Intent intent = new Intent(NotebookRenameActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NotebookRenameActivity.this, DUPLICATION_REMINDER, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
