package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
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
    private static final String DUPLICATION_REMINDER = "Notebook already exists.";

    private Toolbar mToolbar;
    private TextView toolbar_title;
    private EditText edit_notebook_title;
    private DatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_create);
        mToolbar = findViewById(R.id.toolbar_notebook_create);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar_title = mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.all_create_notebook));
        edit_notebook_title = findViewById(R.id.edit_notebook_title);

        mDatabase = getDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_create:
                String name = edit_notebook_title.getText().toString();
                if (!mDatabase.isNotebook(name)) {
                    mDatabase.createNotebook(name);
                    Intent intent = new Intent(NotebookCreateActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NotebookCreateActivity.this, DUPLICATION_REMINDER, Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return true;
    }
}
