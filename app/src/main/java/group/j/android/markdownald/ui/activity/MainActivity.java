package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

import group.j.android.markdownald.R;
import group.j.android.markdownald.adapter.ExpandableItemAdapter;
import group.j.android.markdownald.util.FileUtils;

/**
 * Implements the home page.
 * By clicking the button, user can create the new note or directory.
 */
public class MainActivity extends AppCompatActivity {
    private ArrayList<MultiItemEntity> notes;
    private ExpandableItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_note_list);
        notes = FileUtils.load(this);
        adapter = new ExpandableItemAdapter(notes, MainActivity.this, R.layout.activity_main_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        notes.clear();
        notes.addAll(FileUtils.load(this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_create_note:
                Intent noteIntent = new Intent(this, NoteCreateActivity.class);
                startActivity(noteIntent);
                break;
            case R.id.menu_create_directory:
                Intent directoryIntent = new Intent(this, DirectoryCreateActivity.class);
                startActivity(directoryIntent);
                break;
            default:
                break;
        }

        return true;
    }
}
