package br.com.etecia.projetinho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    int attempts = 3;

    private EditText emailEditText;
    private EditText senhaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.email);
        password = findViewById(R.id.senha);
        loginButton = findViewById(R.id.BtnLog);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        final String username = emailEditText.getText().toString().trim();
        final String password = senhaEditText.getText().toString().trim();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://localhost/htdocs/Projetinho/api/includes/operation.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    JSONObject postData = new JSONObject();
                    postData.put("username", username);
                    postData.put("password", password);

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(postData.toString());
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }

                    reader.close();

                    final String response = sb.toString();
                    final JSONObject jsonResponse = new JSONObject(response);

                    final Context context = getApplicationContext();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (jsonResponse.getBoolean("success")) {
                                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}