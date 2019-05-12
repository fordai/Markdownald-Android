package group.j.android.markdownald.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import group.j.android.markdownald.R;
import group.j.android.markdownald.base.BaseActivity;


public class RegisterActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TextView toolbar_title;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mToolbar = findViewById(R.id.toolbar_register);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar_title = mToolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(getString(R.string.all_register));
        mToolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_baseline_create_white));


        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast tot = Toast.makeText(
                        RegisterActivity.this,
                        "r",
                        Toast.LENGTH_LONG);
                tot.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}
