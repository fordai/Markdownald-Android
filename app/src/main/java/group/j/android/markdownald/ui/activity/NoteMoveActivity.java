package group.j.android.markdownald.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import group.j.android.markdownald.R;
import group.j.android.markdownald.adapter.NotebookAdapter;
import group.j.android.markdownald.base.BaseActivity;
import group.j.android.markdownald.db.DatabaseHelper;

/**
 * Implements the interface for moving a note from its source to the destination.
 */
public class NoteMoveActivity extends BaseActivity {
    private static final String TAG = "NoteMoveActivity";
    private static final String NOTE_NAME = "note_name";

    public DatabaseHelper mDatabase;
    private List<MultiItemEntity> mNotes;
    private NotebookAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_move);
        mDatabase = getDatabase();
        mNotes = mDatabase.loadDB();
        mAdapter = new NotebookAdapter(mDatabase, this, mNotes);
        mAdapter.setNote(getIntent().getStringExtra(NOTE_NAME));
        mRecyclerView = findViewById(R.id.recycler_notebook_list);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Log.d(TAG, "onCreate: ");
    }
}
