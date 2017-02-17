package com.aisautocare.aismechanics.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aisautocare.aismechanics.R;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText email, password;
    private Button loginButton;
    private TextView createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email = (EditText) findViewById(R.id.login_email_edit_text);
        password = (EditText) findViewById(R.id.login_password_edit_text);
        loginButton = (Button) findViewById(R.id.login_button);
        createAccount = (TextView) findViewById(R.id.login_create_account_button);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
