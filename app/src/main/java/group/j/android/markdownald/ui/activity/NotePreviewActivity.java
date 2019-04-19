package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import group.j.android.markdownald.R;
import group.j.android.markdownald.util.MarkdownRenderer;

/**
 * The note preview interface for displaying markdown rendering effect.
 */
public class NotePreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_preview);
        Intent intent = getIntent();
        String title = intent.getStringExtra("note_name");
        this.setTitle(title);
        String content = intent.getStringExtra("note_content");
        TextView textView = findViewById(R.id.preview_view);

        // Implementation for markdown rendering here
        MarkdownRenderer markdownRenderer = new MarkdownRenderer(this);
        markdownRenderer.setMarkdown(textView, content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.close_item:
                this.finish();
            default:
        }

        return true;
    }

}
