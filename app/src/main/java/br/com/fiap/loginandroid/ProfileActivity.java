package br.com.fiap.loginandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.fiap.loginandroid.database.AccountDB;
import br.com.fiap.loginandroid.entity.Account;

public class ProfileActivity extends AppCompatActivity {


    private EditText usernameEditText, passwordEditText, fullNameEditText, emailEditText;
    private Button saveButton, cancelButton;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(R.string.change_profile);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        fullNameEditText = findViewById(R.id.fullNameEdtiText);
        emailEditText = findViewById(R.id.emailEditView);
        saveButton = findViewById(R.id.saveButton);

        loadData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
            }
        });
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, WelcomeActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });



    }

    private void save(View v) {
        try {
            AccountDB accountDB = new AccountDB(getApplicationContext());
            Account currentAccount = accountDB.find(account.getId());

            String newUsername = usernameEditText.getText().toString();
            Account temp = accountDB.checkUsername(newUsername);
            if(!newUsername.equalsIgnoreCase(currentAccount.getUsername()) && temp != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.error);
                builder.setMessage(R.string.username_exists);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return;
            }

            currentAccount.setUsername(usernameEditText.getText().toString());
            currentAccount.setFullName(fullNameEditText.getText().toString());
            currentAccount.setEmail(emailEditText.getText().toString());
            String password = passwordEditText.getText().toString();
            if(!password.isEmpty()) {
                currentAccount.setPassword(passwordEditText.getText().toString());
            }
            if(accountDB.update(currentAccount)) {
                Intent intent = new Intent(ProfileActivity.this, WelcomeActivity.class);
                intent.putExtra("account", currentAccount);
                startActivity(intent);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.error);
                builder.setMessage(R.string.failed);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }


        }catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle(R.string.error);
            builder.setMessage(e.getMessage());
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }

    private void loadData() {
        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("account");
        emailEditText.setText(account.getEmail());
        fullNameEditText.setText(account.getFullName());
        usernameEditText.setText(account.getUsername());
    }

}
