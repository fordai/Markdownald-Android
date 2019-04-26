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
 * Implements the interface for renaming the note.
 */
public class NoteRenameActivity extends AppCompatActivity {
    private static final String TAG = "NoteRenameActivity";
    private static final String DUPLICATION_REMINDER = "This note has existed in this notebook";

    private EditText edit_rename_note;
    private Button btn_rename_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_rename);
        edit_rename_note = findViewById(R.id.edit_rename_note);
        btn_rename_note = findViewById(R.id.btn_rename_note);
        btn_rename_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = edit_rename_note.getText().toString();
                String oldTitle = getIntent().getStringExtra("note_title");
                String notebook = getIntent().getStringExtra("notebook_title");
                if (!FileUtils.exists(NoteRenameActivity.this, notebook, newTitle)) {
                    FileUtils.rename(NoteRenameActivity.this, notebook, oldTitle, newTitle);
                    Intent intent = new Intent(NoteRenameActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NoteRenameActivity.this, DUPLICATION_REMINDER, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
