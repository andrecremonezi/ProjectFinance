package com.finance.projeto.projetofinance.controllers.activities.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.model.entities.User;
import com.finance.projeto.projetofinance.model.services.UserBussinessService;
import com.finance.projeto.projetofinance.util.FormHelper;

public class NewUserActivity extends AppCompatActivity{
    private EditText editTextNewUser;
    private EditText editTextNewPassword;
    private EditText editTextNewFirstName;
    private EditText editTextNewLastName;
    private Button buttonCriar;
    private User user;
    private Toolbar toolbar;
    public static final String PARAM_TASK = "PARAM_TASK";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_actitivy);

        initUser();
        bindToolbar();
        bindElements();
        bindButtonCreate();
    }

    private void bindToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.label_newLogin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initUser() {
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            this.user = (User) extras.getParcelable(PARAM_TASK);
        }
        this.user = this.user == null ? new User() : this.user;
    }

    private void bindElements() {
        editTextNewUser = (EditText) findViewById(R.id.editTextNewUser);
        editTextNewUser.setText(user.getUserName() == null ? "" : user.getUserName());

        editTextNewPassword = (EditText) findViewById(R.id.editTextNewPassWord);
        editTextNewPassword.setText(user.getPassword() == null ? "" : user.getPassword());

        editTextNewFirstName = (EditText) findViewById(R.id.editTextNewFirstName);
        editTextNewFirstName.setText(user.getFirstName() == null ? "" : user.getFirstName());

        editTextNewLastName = (EditText) findViewById(R.id.editTextNewFirstName);
        editTextNewLastName.setText(user.getLastName() == null ? "" : user.getLastName());
    }

    public void bindButtonCreate(){
        buttonCriar = (Button) findViewById(R.id.buttonCreateUser);
        buttonCriar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String requiredMessage = getResources().getString(R.string.msg_field_required);

                if (!FormHelper.validateRequired(requiredMessage, editTextNewUser, editTextNewPassword, editTextNewFirstName, editTextNewLastName)) {
                    bindUser();
                    UserBussinessService.save(user);
                    Toast.makeText(NewUserActivity.this, R.string.msg_user_create_sucess, Toast.LENGTH_LONG).show();
                    NewUserActivity.this.finish();
                }
            }
        });
    }

    public void bindUser(){
        user.setUserName(editTextNewUser.getText().toString());
        user.setPassword(editTextNewPassword.getText().toString());
        user.setFirstName(editTextNewFirstName.getText().toString());
        user.setLastName(editTextNewLastName.getText().toString());
    }

}
