package tainaleal.quicknotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class RegisterActivity extends Activity {

    protected EditText userName;
    protected EditText userEmail;
    protected EditText userPassword;
    protected Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //Initialize
        userName = (EditText)findViewById(R.id.usernameRegister);
        userEmail = (EditText)findViewById(R.id.emailRegister);
        userPassword = (EditText)findViewById(R.id.passwordRegister);
        signUp = (Button)findViewById(R.id.signupRegister);

        //Sign Up listener
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = userName.getText().toString().trim();
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                //Store user
                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // Hooray! Let them use the app now.
                            Toast.makeText(RegisterActivity.this, "Success!", Toast.LENGTH_LONG).show();
                            Intent directUserHomePage = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(directUserHomePage);
                        } else {

                                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                                alert.setMessage(e.getMessage());
                                alert.setTitle("Sorry");
                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = alert.create();
                                alertDialog.show();
                        }
                    }
                });
            }
        });
    }
}
