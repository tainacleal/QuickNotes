package tainaleal.quicknotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {

    protected EditText username;
    protected EditText password;
    protected Button login;
    protected Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize
        username = (EditText)findViewById(R.id.usernameLogin);
        password = (EditText)findViewById(R.id.passwordLogin);
        login = (Button)findViewById(R.id.loginButton);
        signup = (Button)findViewById(R.id.signupLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                ParseUser.logInInBackground(name, pass, new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Toast.makeText(LoginActivity.this, "Welcome back!", Toast.LENGTH_LONG).show();
                            Intent directUserHome = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(directUserHome);
                        } else {
                            MainActivity.WarningAlert.CreateAlert(LoginActivity.this, "Sorry", e.getMessage(), "Ok");
                        }
                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent directUserSignUp = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(directUserSignUp);
            }
        });
    }
}
