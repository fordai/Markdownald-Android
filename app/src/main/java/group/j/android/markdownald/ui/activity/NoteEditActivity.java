package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import group.j.android.markdownald.R;
import group.j.android.markdownald.util.AutoCompleter;
import group.j.android.markdownald.util.FileUtils;
import group.j.android.markdownald.util.MarkdownSyntaxHighlighter;

/**
 * Implements the interface for editing the note. Syntax highlight and auto-completion should be offered here.
 * By clicking the button, user can preview the effect of Markdown. Auto-completion is offered.
 */
public class NoteEditActivity extends AppCompatActivity {
    EditText edit_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        Intent intent = getIntent();
        this.setTitle(intent.getStringExtra("note_title"));
        edit_note = findViewById(R.id.edit_note);

        //Implementation for auto-completion here
        AutoCompleter z = new AutoCompleter(edit_note);
        z.run();

        //Implementation for syntax highlight
        MarkdownSyntaxHighlighter highlighter = new MarkdownSyntaxHighlighter();
        highlighter.highlight(edit_note);

        edit_note.setText(highlighter.highlight(intent.getStringExtra("note_content")));
        edit_note.setSelection(edit_note.getText().length());

        // Implementation for auto-save
        FileUtils.save(this, "Default", this.getTitle().toString(), edit_note);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_note_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_preview:
                String content = edit_note.getText().toString();
                Intent intent = new Intent(this, NotePreviewActivity.class);
                intent.putExtra("note_content", content);
                intent.putExtra("note_title", getTitle());
                startActivity(intent);
            default:
        }

        return true;
    }
}
