package group.j.android.markdownald.ui.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import group.j.android.markdownald.R;
import group.j.android.markdownald.base.BaseActivity;
import group.j.android.markdownald.db.JsonCreator;
import group.j.android.markdownald.db.NoteSyncTask;

public class RegisterActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TextView toolbar_title;
    private TextView text_tutorial;
    private Button button_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mToolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar_title = mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.app_name));
        button_register = findViewById(R.id.button_register);
        button_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String uid = ((EditText)findViewById(R.id.userId)).getText().toString();
                String name = ((EditText)findViewById(R.id.username)).getText().toString();
                String password = ((EditText)findViewById(R.id.password)).getText().toString();
                NoteSyncTask syncTask = new NoteSyncTask(new NoteSyncTask.SyncListener() {
                    @Override
                    public void onStart() { }
                    @Override
                    public void onSuccess() {
                        Intent noteIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(noteIntent);
                    }
                    @Override
                    public void onFailed() { }

                    @Override
                    public void onRegistered() {
                        Toast.makeText(RegisterActivity.this, "User Register Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                JsonCreator js = new JsonCreator();
                syncTask.execute(js.registerJson(uid,name,password).toString());
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }

        return true;
    }
}

