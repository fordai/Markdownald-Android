package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import group.j.android.markdownald.R;
import group.j.android.markdownald.base.BaseActivity;
import group.j.android.markdownald.db.DatabaseHelper;

/**
 * Implements the interface for naming the new directory.
 * If the name is the same as the previous one, a hint should be offered.
 */
public class NotebookCreateActivity extends BaseActivity {
    private static final String TAG = "NotebookCreateActivity";
    private static final String DUPLICATION_REMINDER = "This notebook has been created";

    private Toolbar mToolbar;
    private DatabaseHelper mDatabase;
    private EditText edit_notebook_title;
    private Button btn_create_notebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_create);
        mToolbar = findViewById(R.id.toolbar_notebook_create);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mDatabase = getDatabase();
        edit_notebook_title = findViewById(R.id.edit_notebook_title);
        btn_create_notebook = findViewById(R.id.btn_create_notebook);
        btn_create_notebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_notebook_title.getText().toString();
                if (!mDatabase.isNotebook(name)) {
                    mDatabase.createNotebook(name);
                    Intent intent = new Intent(NotebookCreateActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NotebookCreateActivity.this, DUPLICATION_REMINDER, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
