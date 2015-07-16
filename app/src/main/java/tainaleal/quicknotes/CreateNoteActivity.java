package tainaleal.quicknotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class CreateNoteActivity extends Activity {

    protected EditText newNote;
    protected Button buttonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        newNote = (EditText)findViewById(R.id.createNoteEdittext);
        buttonCreate = (Button)findViewById(R.id.createButton);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                String userName = currentUser.getUsername();
                String note = newNote.getText().toString();

                if (note.isEmpty()) {
                    MainActivity.WarningAlert.CreateAlert(CreateNoteActivity.this, "Oops!", "Note can't be empty", "Ok");
                } else {
                    ParseObject noteObject = new ParseObject("Note");
                    noteObject.put("newNote", note);
                    noteObject.put("user", userName);
                    noteObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(CreateNoteActivity.this, "Note created!", Toast.LENGTH_LONG).show();
                                Intent directUserHome = new Intent(CreateNoteActivity.this, MainActivity.class);
                                startActivity(directUserHome);

                            } else {
                                MainActivity.WarningAlert.CreateAlert(CreateNoteActivity.this, "Sorry", e.getMessage(), "Ok");
                            }
                        }
                    });
                }
            }
        });
    }
}
