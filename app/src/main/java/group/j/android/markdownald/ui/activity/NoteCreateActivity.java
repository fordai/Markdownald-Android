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
import group.j.android.markdownald.model.Note;

/**
 * Implements the interface for naming the new note.
 * After creating a new note, the user can edit it.
 * If the name is the same as the previous one, a hint should be offered.
 */
public class NoteCreateActivity extends BaseActivity {
    private static final String TAG = "NoteCreateActivity";
    private static final String DUPLICATION_REMINDER = "Note already exists.";

    private Toolbar mToolbar;
    private TextView toolbar_title;
    private EditText edit_note_title;
    private DatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);

        // Configure the Toolbar
        mToolbar = findViewById(R.id.toolbar_note_create);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar_title = mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.all_create_note));
        mToolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_baseline_create_white));

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
