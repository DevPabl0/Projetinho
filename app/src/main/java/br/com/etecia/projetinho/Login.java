package br.com.etecia.projetinho;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
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

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email);
        senhaEditText = findViewById(R.id.senha);
        loginButton = findViewById(R.id.BtnLog);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        final String email = emailEditText.getText().toString().trim();
        final String password = senhaEditText.getText().toString().trim();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("LoginActivity", "Starting login process");
                    URL url = new URL("http://localhost/Projetinho/api/includes/operation.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

                    Log.d("LoginActivity", "Data sent to server: " + postData.toString());

                    int responseCode = conn.getResponseCode();
                    Log.d("LoginActivity", "Response Code: " + responseCode);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }

                    reader.close();
                    conn.disconnect();

                    final String response = sb.toString();
                    Log.d("LoginActivity", "Response from server: " + response);

                    final JSONObject jsonResponse = new JSONObject(response);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (jsonResponse.getBoolean("success")) {
                                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("LoginActivity", "JSON parsing error", e);
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("LoginActivity", "Error during login process", e);
                }
            }
        }).start();
    }
}
