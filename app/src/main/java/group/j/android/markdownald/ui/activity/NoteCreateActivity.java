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
import group.j.android.markdownald.model.Note;

/**
 * Implements the interface for naming the new note.
 * After creating a new note, the user can edit it.
 * If the name is the same as the previous one, a hint should be offered.
 */
public class NoteCreateActivity extends BaseActivity {
    private static final String TAG = "NoteCreateActivity";
    private static final String DUPLICATION_REMINDER = "This note has been created";

    private Toolbar mToolbar;
    private EditText edit_note_title;
    private DatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);
        mToolbar = findViewById(R.id.toolbar_note_create);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        edit_note_title = findViewById(R.id.edit_note_title);

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
                String name = edit_note_title.getText().toString();
                if (!mDatabase.isNoteByNotebook(name, "Default")) {
                    long id = mDatabase.createNote(new Note(name));
                    mDatabase.createNoteToNotebook(id, mDatabase.getNotebookByName("Default").getId());
                    Intent intent = new Intent(NoteCreateActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NoteCreateActivity.this, DUPLICATION_REMINDER, Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return true;
    }
}
