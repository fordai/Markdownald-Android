package group.j.android.markdownald.ui.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.JsonObject;

import java.util.List;

import group.j.android.markdownald.R;
import group.j.android.markdownald.adapter.ExpandableItemAdapter;
import group.j.android.markdownald.base.BaseActivity;
import group.j.android.markdownald.db.DatabaseHelper;
import group.j.android.markdownald.db.JsonCreator;
import group.j.android.markdownald.db.NoteSyncTask;
import group.j.android.markdownald.model.Note;
import group.j.android.markdownald.model.Notebook;

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
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private Toolbar mToolbar;
    private TextView toolbar_title;
    private RecyclerView mRecyclerView;
    private DatabaseHelper mDatabase;
    private List<MultiItemEntity> mNotes;
    private ExpandableItemAdapter mAdapter;
    private DrawerLayout layout_navigation;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure the Toolbar
        mToolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        NavigationView navigationView = findViewById(R.id.NavigationId);
        navigationView.setNavigationItemSelectedListener(this);

        // Configure the navigation icon
        mToolbar.setNavigationIcon(R.drawable.ic_baseline_menu_white);
        layout_navigation = findViewById(R.id.layout_navigation);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_navigation.openDrawer(Gravity.START);
            }
        });

        // Configure the title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar_title = mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.app_name));

        // Configure the overflow icon
        mToolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_baseline_add_white));

        // Configure the RecyclerView
        mRecyclerView = findViewById(R.id.recycler_note_list);
        mDatabase = getDatabase();
        mNotes = mDatabase.loadDB();
        mAdapter = new ExpandableItemAdapter(mDatabase, mNotes, MainActivity.this, R.layout.activity_notebook_adapter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mProgressBar = findViewById(R.id.progress_circular);

        //change the header of navigationView in activity_main
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.UserName);
        navUsername.setText("fuck~");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mNotes.clear();
        mNotes.addAll(mDatabase.loadDB());
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
            case R.id.menu_create_notebook:
                Intent notebookIntent = new Intent(this, NotebookCreateActivity.class);
                startActivity(notebookIntent);
                break;
            case R.id.menu_scan:
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.menu_sync:
                SharedPreferences sharedPreferences = getSharedPreferences(CONFIG, MODE_PRIVATE);
                String uid = sharedPreferences.getString("userid", "");
                String password = sharedPreferences.getString("password", "");
                JsonCreator js = new JsonCreator();
                List<Notebook> lbk = mDatabase.getAllNotebooks();
                for (Notebook nb : lbk) {
                    List<Note> alln = mDatabase.getAllNotesByNotebook(nb.getName());
                    for (Note n : alln) {
                        NoteSyncTask syncTask = new NoteSyncTask(new NoteSyncTask.SyncListener() {
                            @Override
                            public void onStart() {
                                mRecyclerView.setVisibility(View.GONE);
                                mProgressBar.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onSuccess() {
                                mProgressBar.setVisibility(View.GONE);
                                mNotes.clear();
                                mNotes.addAll(mDatabase.loadDB());
                                mAdapter.notifyDataSetChanged();
                                mRecyclerView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFailed() {
                                mRecyclerView.setVisibility(View.VISIBLE);
                                mProgressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onRegistered() {
                                mRecyclerView.setVisibility(View.VISIBLE);
                                mProgressBar.setVisibility(View.GONE);
                            }
                        }, mDatabase);
                        syncTask.execute(js.addNote(n.getId(), n.getName(), nb.getName(), n.getContent(), uid).toString());
                    }
                }
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.loginId:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.registerId:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.tutorialId:
                Intent settingIntent = new Intent(this, TutorialActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.aboutId:
                Intent aboutIntent = new Intent(this, TutorialActivity.class);
                startActivity(aboutIntent);
                break;
            default:
                break;
        }

        return true;
    }
}
