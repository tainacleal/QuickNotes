package tainaleal.quicknotes;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.List;


public class MainActivity extends ListActivity {

    protected List<ParseObject> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
            query.whereEqualTo("user", currentUser.getUsername()).orderByDescending("updatedAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> notesObject, ParseException e) {
                    if (e == null){
                        notes = notesObject;
                        NotesAdapter adapter = new NotesAdapter(getListView().getContext(), notes);
                        setListAdapter(adapter);
                    }
                }
            });
        } else {
            Intent directUserLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(directUserLogin);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.createNote:
                Intent directUserUpdate = new Intent(MainActivity.this, CreateNoteActivity.class);
                startActivity(directUserUpdate);
                break;

            case R.id.logoutUser:
                ParseUser.logOut();
                Intent directUserLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(directUserLogin);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ParseObject noteObject = notes.get(position);
        String objectId = noteObject.getObjectId();

        Intent directUserDetail = new Intent(MainActivity.this, NoteDetailsActivity.class);
        directUserDetail.putExtra("objectId", objectId);
        startActivity(directUserDetail);
    }

    public static class WarningAlert{
        public static void CreateAlert(Context context, String title, String message, String positive){
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setMessage(message);
            alert.setTitle(title);
            alert.setPositiveButton(positive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }
    }
}
