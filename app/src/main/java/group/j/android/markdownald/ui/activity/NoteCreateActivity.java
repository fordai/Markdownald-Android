package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import group.j.android.markdownald.R;

/**
 * Implements the interface for naming the new note.
 * After creating a new note, the user can edit it.
 */
public class NoteCreateActivity extends AppCompatActivity {
    private EditText edit_title;
    private Button btn_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        edit_title = findViewById(R.id.edit_title);
        btn_create = findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_title.getText().toString();
                Intent intent = new Intent(NoteCreateActivity.this, NoteEditActivity.class);
                intent.putExtra("note_title", name);
                startActivity(intent);
            }
        });
    }
}
