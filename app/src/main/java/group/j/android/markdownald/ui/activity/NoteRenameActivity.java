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
 * Implements the interface for renaming the note.
 */
public class NoteRenameActivity extends BaseActivity {
    private static final String TAG = "NoteRenameActivity";
    private static final String EXTRA_NOTE_NAME = "note_name";
    private static final String EXTRA_NOTEBOOK_NAME = "notebook_name";
    private static final String DUPLICATION_REMINDER = "Repeated title.";

    private Toolbar mToolbar;
    private TextView toolbar_title;
    private EditText edit_rename_note;
    private DatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_rename);
        mToolbar = findViewById(R.id.toolbar_note_rename);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar_title = mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.all_rename_note));
        edit_rename_note = findViewById(R.id.edit_rename_note);

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
                String oldName = getIntent().getStringExtra(EXTRA_NOTE_NAME);
                String notebookName = getIntent().getStringExtra(EXTRA_NOTEBOOK_NAME);
                String newName = edit_rename_note.getText().toString();
                if (!mDatabase.isNoteByNotebook(newName, notebookName)) {
                    mDatabase.updateNoteName(oldName, newName);
                    Intent intent = new Intent(NoteRenameActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NoteRenameActivity.this, DUPLICATION_REMINDER, Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return true;
    }
}
