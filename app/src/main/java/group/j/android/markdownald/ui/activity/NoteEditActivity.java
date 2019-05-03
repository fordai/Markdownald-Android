package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import group.j.android.markdownald.R;
import group.j.android.markdownald.base.BaseActivity;
import group.j.android.markdownald.db.DatabaseHelper;
import group.j.android.markdownald.util.AutoCompleter;
import group.j.android.markdownald.util.MarkdownSyntaxHighlighter;
import group.j.android.markdownald.util.PDFCreater;

/**
 * Implements the interface for editing the note. Syntax highlight and auto-completion should be offered here.
 * By clicking the button, user can preview the effect of Markdown. Auto-completion is offered.
 */
public class NoteEditActivity extends BaseActivity {
    private static final String TAG = "NoteEditActivity";
    private static final String NOTE_NAME = "note_name";
    private static final String NOTE_CONTENT = "note_content";

    private Toolbar mToolbar;
    private DatabaseHelper mDatabase;
    EditText edit_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        mToolbar = findViewById(R.id.toolbar_note_edit);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mDatabase = getDatabase();
        Intent intent = getIntent();
        String name = intent.getStringExtra(NOTE_NAME);
        getSupportActionBar().setTitle(name);
        edit_note = findViewById(R.id.edit_note);

        //Implementation for auto-completion here
        AutoCompleter z = new AutoCompleter(edit_note);
        z.run();

        //Implementation for syntax highlight
        MarkdownSyntaxHighlighter highlighter = new MarkdownSyntaxHighlighter();
        highlighter.highlight(edit_note);

        edit_note.setText(highlighter.highlight(intent.getStringExtra(NOTE_CONTENT)));
        edit_note.setSelection(edit_note.getText().length());

        save(name, edit_note);
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
                intent.putExtra(NOTE_NAME, mToolbar.getTitle());
                intent.putExtra(NOTE_CONTENT, content);
                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
                break;
            default:
        }

        return true;
    }

    private void save(final String name, final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            Handler handler = new Handler(Looper.myLooper());
            Runnable runnable;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeCallbacks(runnable);
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        mDatabase.updateNoteContent(name, editText.getText().toString());
                    }
                };
                handler.postDelayed(runnable, 500);
            }
        });
    }
}
