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

public class NoteMoveActivity extends AppCompatActivity {
    private EditText edit_destination_title;
    private Button btn_move_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_move);
        edit_destination_title = findViewById(R.id.edit_destination_title);
        btn_move_note = findViewById(R.id.btn_move_note);
        btn_move_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String source = getIntent().getStringExtra("notebook_title");
                String title = getIntent().getStringExtra("note_title");
                String destination = edit_destination_title.getText().toString();
                if (!FileUtils.exists(NoteMoveActivity.this, destination, title)) {
                    FileUtils.move(NoteMoveActivity.this, source, destination, title);
                    Intent intent = new Intent(NoteMoveActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NoteMoveActivity.this, "The destination has the same note", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
