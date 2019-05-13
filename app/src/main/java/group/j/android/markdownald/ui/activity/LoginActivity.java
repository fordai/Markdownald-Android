package group.j.android.markdownald.ui.activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends BaseActivity {
    private Toolbar mToolbar;
    private TextView toolbar_title;
    private Button button_login;
    private String con;
    private TextView text_tutorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar_title = mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.app_name));

        button_login = findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String uid = ((EditText)findViewById(R.id.userId2)).getText().toString();
                final String password = ((EditText)findViewById(R.id.password2)).getText().toString();
                NoteSyncTask syncTask = new NoteSyncTask(new NoteSyncTask.SyncListener() {
                    @Override
                    public void onStart() { }
                    @Override
                    public void onSuccess() {
                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(CONFIG, LoginActivity.this.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userid", uid);
                        editor.putString("password", password);
                        editor.commit();
                        Intent noteIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(noteIntent);
                    }
                    @Override
                    public void onFailed() { }

                    @Override
                    public void onRegistered() {
                        Toast.makeText(LoginActivity.this, "User or Password Error", Toast.LENGTH_SHORT).show();
                    }
                });
                JsonCreator js = new JsonCreator();
                syncTask.execute(js.loginJson(uid,password).toString());
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
