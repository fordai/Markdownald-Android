package group.j.android.markdownald.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import group.j.android.markdownald.db.DatabaseHelper;

/**
 * Implements base activity.
 */
public class BaseActivity extends AppCompatActivity {
    private DatabaseHelper mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = DatabaseHelper.getInstance(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public DatabaseHelper getDatabase() {
        return mDatabase;
    }
}
