package tainaleal.quicknotes;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class MainActivity extends ListActivity {

    protected List<ParseObject> status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Status");
            query.whereEqualTo("user", currentUser.getUsername()).orderByDescending("updatedAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> statusObject, ParseException e) {
                    if (e == null){
                        status = statusObject;
                        StatusAdapter adapter = new StatusAdapter(getListView().getContext(), status);
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
            case R.id.updateStatus:
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

        ParseObject statusObject = status.get(position);
        String objectId = statusObject.getObjectId();

        Intent directUserDetail = new Intent(MainActivity.this, NoteDetailsActivity.class);
        directUserDetail.putExtra("objectId", objectId);
        startActivity(directUserDetail);
    }
}
