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
 * Implements the homepage.
 * The notebooks are displayed and there is a default notebook on the top that cannot be moved/swiped and deleted.
 * <p>
 * Its functionality includes:
 * <ul>
 * <li>By clicking the button on the top right corner, create a new note or notebook;</li>
 * <li>By clicking one notebook, display/hide its notes;</li>
 * <li>By clicking one note, display and edit its content;</li>
 * <li>By swiping to left, delete a note or notebook;</li>
 * <li>By swiping to left, do more operations including moving and renaming;</li>
 * </ul>
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ArrayList<MultiItemEntity> mNotes;
    private ExpandableItemAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_note_list);
        mNotes = FileUtils.load(this);
        mAdapter = new ExpandableItemAdapter(mNotes, MainActivity.this, R.layout.activity_main_adapter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mNotes.clear();
        mNotes.addAll(FileUtils.load(this));
        mAdapter.notifyDataSetChanged();
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
