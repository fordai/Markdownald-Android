package group.j.android.markdownald.ui.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import group.j.android.markdownald.R;
import group.j.android.markdownald.base.BaseActivity;
public class LoginActivity extends BaseActivity {
    private Button mBack2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBack2 = findViewById(R.id.button_back2);
        mBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View iv) {
                Intent backIntent = new Intent();
                backIntent.setClass(LoginActivity.this,MainActivity.class);
                startActivity(backIntent);
            }
        });
    }
}
