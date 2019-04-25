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
 * Implements the interface for displaying Markdown rendering effect.
 * User can share his note in this interface.
 */
public class NotePreviewActivity extends AppCompatActivity {
    private static final String TAG = "NotePreviewActivity";
    private TextView text_preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_preview);
        Intent intent = getIntent();
        this.setTitle(intent.getStringExtra("note_title"));
        String content = intent.getStringExtra("note_content");
        text_preview = findViewById(R.id.text_preview);

        // Implementation for markdown rendering here
        MarkdownRenderer markdownRenderer = new MarkdownRenderer(this);
        markdownRenderer.setMarkdown(text_preview, content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_close:
                this.finish();
                break;
            case R.id.menu_share:
                String contentQR = text_preview.getText().toString();
                Intent intentQR = new Intent(this, NoteShareActivity.class);
                intentQR.putExtra("note_QR", contentQR);
                startActivity(intentQR);
                break;
            default:
        }

        return true;
    }

}
