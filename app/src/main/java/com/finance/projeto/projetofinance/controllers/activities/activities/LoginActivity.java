package com.finance.projeto.projetofinance.controllers.activities.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.model.entities.User;
import com.finance.projeto.projetofinance.model.persistence.UserRepository;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextLogin;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        bindToolbar();
        bindEditTextLogin();
        bindEditTextPassword();
        bindButtonLogin();
        bindButtonNewUser();
    }

    private void bindToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.label_login);
    }

    private void bindButtonLogin() {
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLogin()) {
                    Intent redirectToTaskList = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(redirectToTaskList);
                    finish();
                } else
                    Toast.makeText(LoginActivity.this, R.string.msg_login_error, Toast.LENGTH_LONG).show();

            }
        });
    }

    public boolean checkLogin(){
        User checkUser = new User();

        checkUser.setUserName(editTextLogin.getText().toString());
        checkUser.setPassword(editTextPassword.getText().toString());

        User user = UserRepository.checkLogin(checkUser);

        return user != null;
    }

    private void bindButtonNewUser() {
        Button buttonNewUser = (Button) findViewById(R.id.buttonNewUser);

        buttonNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToNewUser = new Intent(LoginActivity.this, NewUserActivity.class);
                redirectToNewUser.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(redirectToNewUser);
            }
        });
    }

    private void bindEditTextPassword() {
        editTextPassword = (EditText) findViewById(R.id.editTextUser);
    }

    private void bindEditTextLogin() {
        editTextLogin = (EditText) findViewById(R.id.editTextPassWord);
    }

}

