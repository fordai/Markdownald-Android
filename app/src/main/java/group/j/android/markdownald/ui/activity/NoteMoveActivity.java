package group.j.android.markdownald.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

import group.j.android.markdownald.R;
import group.j.android.markdownald.adapter.NotebookAdapter;
import group.j.android.markdownald.util.FileUtils;

/**
 * Implements the interface for moving a note from its source to the destination.
 */
public class NoteMoveActivity extends AppCompatActivity {
    private static final String TAG = "NoteMoveActivity";

    private ArrayList<MultiItemEntity> mNotes;
    private NotebookAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_move);
        mRecyclerView = findViewById(R.id.recycler_notebook_list);
        mNotes = FileUtils.load(this);
        mAdapter = new NotebookAdapter(this, mNotes);
        mAdapter.setNotebook(getIntent().getStringExtra("notebook_title"));
        mAdapter.setNote(getIntent().getStringExtra("note_title"));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Log.d(TAG, "onCreate: ");
    }
}
