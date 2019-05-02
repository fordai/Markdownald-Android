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
import group.j.android.markdownald.model.Note;

/**
 * Implements the interface for naming the new note.
 * After creating a new note, the user can edit it.
 * If the name is the same as the previous one, a hint should be offered.
 */
public class NoteCreateActivity extends BaseActivity {
    private static final String TAG = "NoteCreateActivity";
    private static final String DUPLICATION_REMINDER = "This note has been created";

    private DatabaseHelper mDatabase;
    private EditText edit_title;
    private Button btn_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        mDatabase = getDatabase();
        edit_title = findViewById(R.id.edit_title);
        btn_create = findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_title.getText().toString();
                if (!mDatabase.isNote(name)) {
                    long id = mDatabase.createNote(new Note(name));
                    mDatabase.createNoteToNotebook(id, mDatabase.getNotebookByName("Default").getId());
                    Intent intent = new Intent(NoteCreateActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NoteCreateActivity.this, DUPLICATION_REMINDER, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
