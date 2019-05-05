package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import group.j.android.markdownald.R;
import group.j.android.markdownald.base.BaseActivity;
import group.j.android.markdownald.util.MarkdownRenderer;
import group.j.android.markdownald.util.PDFCreater;

/**
 * Implements the interface for displaying Markdown rendering effect.
 * User can share his note in this interface.
 */
public class NotePreviewActivity extends BaseActivity {
    private static final String TAG = "NotePreviewActivity";
    private static final String EXTRA_NOTE_NAME = "note_name";
    private static final String EXTRA_NOTE_CONTENT = "note_content";

    private Toolbar mToolbar;
    private TextView toolbar_title;
    private TextView text_preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_preview);
        mToolbar = findViewById(R.id.toolbar_note_preview);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        Intent intent = getIntent();
        toolbar_title = mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(intent.getStringExtra(EXTRA_NOTE_NAME));
        String content = intent.getStringExtra(EXTRA_NOTE_CONTENT);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_pdf:
                PDFCreater pdfCreater = new PDFCreater(this);
                pdfCreater.createPDF(text_preview);
                this.finish();
                break;
            case R.id.menu_QR:
                String contentQR = text_preview.getText().toString();
                Intent intentQR = new Intent(this, NoteQRActivity.class);
                intentQR.putExtra("note_QR", contentQR);
                startActivity(intentQR);
                break;
            case android.R.id.home:
                finish();
                break;
            default:
        }

        return true;
    }

}
