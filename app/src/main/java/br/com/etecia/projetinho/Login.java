package br.com.etecia.projetinho;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {

    private EditText emailEditText;
    private EditText senhaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.edt_email);
        senhaEditText = findViewById(R.id.edt_senha);
        Button loginButton = findViewById(R.id.Btn_login);

        loginButton.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        final String email = emailEditText.getText().toString().trim();
        final String password = senhaEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Campos vazios!", Toast.LENGTH_LONG).show());
            return;
        }

        new Thread(() -> {
            HttpURLConnection conn = null;
            BufferedReader reader = null;
            try {
                Log.d("LoginActivity", "Starting login process");
                URL url = new URL("http://192.168.56.1/Projetinho/api/includes/operation.php");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                JSONObject postData = new JSONObject();
                postData.put("email", email);
                postData.put("password", password);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(postData.toString());
                wr.flush();
                wr.close();

                Log.d("LoginActivity", "Data sent to server: " + postData);

                int responseCode = conn.getResponseCode();
                Log.d("LoginActivity", "Response Code: " + responseCode);

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                final String response = sb.toString();
                Log.d("LoginActivity", "Response from server: " + response);

                final JSONObject jsonResponse = new JSONObject(response);

                runOnUiThread(() -> {
                    try {
                        if (jsonResponse.getBoolean("success")) {
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("LoginActivity", "JSON parsing error", e);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("LoginActivity", "Erro no processo de login", e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }).start();
    }
}
