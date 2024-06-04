package br.com.etecia.projetinho;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    int attempts = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.user);
        password = findViewById(R.id.senha);
        loginButton = findViewById(R.id.BtnLog);

        loginButton.setOnClickListener(v -> {
            String enteredUsername = username.getText().toString();
            String enteredPassword = password.getText().toString();

            if (enteredUsername.equals("admin") && enteredPassword.equals("1234")) {
                Toast.makeText(this, "Login bem sucedido!", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            } else {
                attempts--;
                if (attempts > 0) {
                    Toast.makeText(this, "Credenciais incorretas. Tentativas restantes: " + attempts, Toast.LENGTH_SHORT).show();
                } else {
                    loginButton.setEnabled(false);
                    Toast.makeText(this, "Número máximo de tentativas atingido. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
