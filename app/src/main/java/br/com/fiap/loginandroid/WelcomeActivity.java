package br.com.fiap.loginandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.fiap.loginandroid.entity.Account;

public class WelcomeActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private Button changeProfileButton;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setTitle(R.string.welcome);

        welcomeTextView = findViewById(R.id.welcomeTextView);

        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("account");
        welcomeTextView.setText(getString(R.string.welcome) + " " + account.getUsername());

        changeProfileButton = findViewById(R.id.changeProfileButton);
        changeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(WelcomeActivity.this, ProfileActivity.class);
                intent1.putExtra("account", account);
                startActivity(intent1);
            }
        });

    }
}
