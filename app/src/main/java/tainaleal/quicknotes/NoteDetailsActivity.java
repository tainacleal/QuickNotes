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
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class NoteDetailsActivity extends Activity {

    String objectId;
    protected EditText updatedNote;
    protected Button updatedNoteButton;
    protected Button cancelUpdate;
    protected Button deleteNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");

        updatedNote = (EditText)findViewById(R.id.updateNote);
        updatedNoteButton = (Button)findViewById(R.id.updateNoteButton);
        cancelUpdate = (Button)findViewById(R.id.cancelUpdateButton);
        deleteNote = (Button)findViewById(R.id.deleteNoteButton);

        final Intent directUserHomePage = new Intent(NoteDetailsActivity.this, MainActivity.class);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject parseObject, ParseException e) {
                if (e == null) {
                    updatedNote.setText(parseObject.getString("newNote"));
                    updatedNoteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            parseObject.put("newNote", updatedNote.getText().toString());
                            parseObject.saveInBackground();
                            startActivity(directUserHomePage);
                        }
                    });

                    cancelUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(directUserHomePage);
                        }
                    });

                    deleteNote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(NoteDetailsActivity.this);
                            alert.setMessage("Are you sure you want to delete this note?");
                            alert.setTitle("Warning!");
                            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    parseObject.deleteInBackground(new DeleteCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e==null){
                                                Toast.makeText(NoteDetailsActivity.this, "Note Deleted", Toast.LENGTH_LONG).show();
                                                startActivity(directUserHomePage);
                                            }
                                            else {
                                                AlertDialog.Builder alert = new AlertDialog.Builder(NoteDetailsActivity.this);
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
                                    dialog.dismiss();
                                }
                            });

                            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog dialogAlert = alert.create();
                            dialogAlert.show();
                        }
                    });
                }
            }
        });
    }
}
