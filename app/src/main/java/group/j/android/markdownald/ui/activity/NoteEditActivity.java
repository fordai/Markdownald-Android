package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


import group.j.android.markdownald.R;
import group.j.android.markdownald.util.AutoCompleter;
import group.j.android.markdownald.util.MarkdownSyntaxHighlighter;


/**
 * The note edit interface for editing the content of a note.
 * Syntax highlight and auto-completion should be offered here.
 */
public class NoteEditActivity extends AppCompatActivity {
    EditText noteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        noteEditText = findViewById(R.id.note_edit_text);

        //Implementation for auto-completion here
        AutoCompleter z = new AutoCompleter(noteEditText);
        z.run();

        //Implementation for syntax highlight
        MarkdownSyntaxHighlighter highlighter = new MarkdownSyntaxHighlighter();
        highlighter.highlight(noteEditText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preview_note:
                String content = noteEditText.getText().toString();
                Intent intent = new Intent(this, NotePreviewActivity.class);
                intent.putExtra("note_content", content);
                startActivity(intent);
            default:
        }
        return true;
    }
}
