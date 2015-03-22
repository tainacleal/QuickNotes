package tainaleal.quicknotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class UpdateStatusActivity extends Activity {

    protected EditText updatedStatus;
    protected Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);

        updatedStatus = (EditText)findViewById(R.id.updateStatusBox);
        buttonUpdate = (Button)findViewById(R.id.updateButton);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                String userName = currentUser.getUsername();
                String status = updatedStatus.getText().toString();

                if (status.isEmpty()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(UpdateStatusActivity.this);
                    alert.setMessage("Status can't be empty.");
                    alert.setTitle("Oops!");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialogAlert = alert.create();
                    dialogAlert.show();
                }
                else {
                    ParseObject statusObject = new ParseObject("Status");
                    statusObject.put("newStatus", status);
                    statusObject.put("user", userName);
                    statusObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(UpdateStatusActivity.this, "Status Updated!", Toast.LENGTH_LONG).show();
                                Intent directUserHome = new Intent(UpdateStatusActivity.this, MainActivity.class);
                                startActivity(directUserHome);

                            } else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(UpdateStatusActivity.this);
                                alert.setMessage(e.getMessage());
                                alert.setTitle("Sorry");
                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialogAlert = alert.create();
                                dialogAlert.show();
                            }
                        }
                    });
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
