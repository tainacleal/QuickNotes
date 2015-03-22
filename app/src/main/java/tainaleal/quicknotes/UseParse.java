package tainaleal.quicknotes;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by tainaleal on 1/22/15.
 */
public class UseParse extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(getApplicationContext());
        Parse.initialize(this, "HfbG5LRFGD2RfVFIUfMq8EN2jyJrE0zgzvbXhCMJ", "h4VLvZEWddbosW24JvpqzjW6UWd39143OJ975Ttn");
        ParseUser.enableAutomaticUser();
        ParseACL.setDefaultACL(new ParseACL(), true);

    }
}
