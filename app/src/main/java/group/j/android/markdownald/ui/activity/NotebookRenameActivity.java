package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
    private static final String EXTRA_NOTEBOOK_NAME = "notebook_name";
    private static final String DUPLICATION_REMINDER = "This notebook has existed";

    private Toolbar mToolbar;
    private EditText edit_rename_notebook;
    private DatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_rename);
        mToolbar = findViewById(R.id.toolbar_notebook_rename);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        edit_rename_notebook = findViewById(R.id.edit_rename_notebook);

        mDatabase = getDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_rename, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_rename:
                String oldName = getIntent().getStringExtra(EXTRA_NOTEBOOK_NAME);
                String newName = edit_rename_notebook.getText().toString();
                if (!mDatabase.isNotebook(newName)) {
                    mDatabase.updateNotebook(oldName, newName);
                    Intent intent = new Intent(NotebookRenameActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NotebookRenameActivity.this, DUPLICATION_REMINDER, Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return true;
    }
}
