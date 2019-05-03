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
 * Implements the interface for renaming the note.
 */
public class NoteRenameActivity extends BaseActivity {
    private static final String TAG = "NoteRenameActivity";
    private static final String NOTE_NAME = "note_name";
    private static final String NOTEBOOK_NAME = "notebook_name";
    private static final String DUPLICATION_REMINDER = "This note has existed in this notebook.";

    private Toolbar mToolbar;
    private DatabaseHelper mDatabase;
    private EditText edit_rename_note;
    private Button btn_rename_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_rename);
        mToolbar = findViewById(R.id.toolbar_note_rename);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mDatabase = getDatabase();
        edit_rename_note = findViewById(R.id.edit_rename_note);
        btn_rename_note = findViewById(R.id.btn_rename_note);
        btn_rename_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = edit_rename_note.getText().toString();
                String oldName = getIntent().getStringExtra(NOTE_NAME);
                String notebookName = getIntent().getStringExtra(NOTEBOOK_NAME);
                if (!mDatabase.isNoteByNotebook(newName, notebookName)) {
                    mDatabase.updateNoteName(oldName, newName);
                    Intent intent = new Intent(NoteRenameActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NoteRenameActivity.this, DUPLICATION_REMINDER, Toast.LENGTH_SHORT).show();
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
