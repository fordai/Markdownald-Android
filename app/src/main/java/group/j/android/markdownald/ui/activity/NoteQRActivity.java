package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import group.j.android.markdownald.R;
import group.j.android.markdownald.util.ShareNodeHandler;

public class NoteQRActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_qr);
        ShareNodeHandler shareNodeHandler = new ShareNodeHandler();
        Intent intent = getIntent();
        String content = intent.getStringExtra("note_QR");
        imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(shareNodeHandler.generateBitmap(content, 600, 600));
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
            default:
        }

        return true;
    }
}
