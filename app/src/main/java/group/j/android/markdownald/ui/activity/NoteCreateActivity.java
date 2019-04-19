package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import group.j.android.markdownald.R;

/**
 * The interface for naming the new note.
 */
public class NoteCreateActivity extends AppCompatActivity {
    private EditText nameEdit;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        nameEdit = findViewById(R.id.name);
        create = findViewById(R.id.create_button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                Intent intent = new Intent(NoteCreateActivity.this, NoteEditActivity.class);
                intent.putExtra("note_name", name);
                startActivity(intent);
            }
        });
    }
}
