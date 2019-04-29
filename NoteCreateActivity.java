package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import group.j.android.markdownald.R;
import group.j.android.markdownald.util.FileUtils;

/**
 * Implements the interface for naming the new note.
 * After creating a new note, the user can edit it.
 * If the name is the same as the previous one, a hint should be offered.
 */
public class NoteCreateActivity extends AppCompatActivity {
    private static final String TAG = "NoteCreateActivity";
    private static final String DUPLICATION_REMINDER = "This note has been created";

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
                String title = edit_title.getText().toString();
                if (!FileUtils.exists(NoteCreateActivity.this, title)) {
                    FileUtils.saveToDefault(NoteCreateActivity.this, title);
                    Intent intent = new Intent(NoteCreateActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                   Toast toast = Toast.makeText(NoteCreateActivity.this, DUPLICATION_REMINDER, Toast.LENGTH_SHORT);
                   toast.setGravity(Gravity.CENTER,0,0);
                   toast.show();


                }
            }
        });
    }
}
