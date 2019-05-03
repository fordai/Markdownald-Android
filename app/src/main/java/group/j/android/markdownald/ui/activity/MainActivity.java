package group.j.android.markdownald.ui.activity;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import group.j.android.markdownald.R;
import group.j.android.markdownald.adapter.ExpandableItemAdapter;
import group.j.android.markdownald.base.BaseActivity;
import group.j.android.markdownald.db.DatabaseHelper;

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
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private Toolbar mToolbar;
    private DatabaseHelper mDatabase;
    private List<MultiItemEntity> mNotes;
    private ExpandableItemAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        mRecyclerView = findViewById(R.id.recycler_note_list);
        mDatabase = getDatabase();
        mNotes = mDatabase.loadDB();
        mAdapter = new ExpandableItemAdapter(mDatabase, mNotes, MainActivity.this, R.layout.activity_main_adapter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mNotes.clear();
        mNotes.addAll(mDatabase.loadDB());
        mAdapter.notifyDataSetChanged();
        Log.d(TAG, "onRestart: ");
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
                Intent directoryIntent = new Intent(this, NotebookCreateActivity.class);
                startActivity(directoryIntent);
                break;
            default:
                break;
        }

        return true;
    }


}
