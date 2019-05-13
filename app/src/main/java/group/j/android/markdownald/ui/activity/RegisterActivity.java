package group.j.android.markdownald.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import group.j.android.markdownald.R;
import group.j.android.markdownald.base.BaseActivity;

public class RegisterActivity extends BaseActivity {
    private Button mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mBack = findViewById(R.id.button_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View iv) {
                Intent backIntent = new Intent();
                backIntent.setClass(RegisterActivity.this,MainActivity.class);
                startActivity(backIntent);
            }
        });
    }
}
