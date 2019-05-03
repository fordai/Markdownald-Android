package group.j.android.markdownald.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;

import group.j.android.markdownald.R;
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
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary),0);
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
