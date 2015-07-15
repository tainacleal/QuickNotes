package tainaleal.quicknotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.parse.ParseObject;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by tainaleal on 1/24/15.
 */
public class NotesAdapter extends ArrayAdapter<ParseObject> {
    protected Context mContext;
    protected List mNotes;

    public NotesAdapter(Context context, List notes) {
        super(context, R.layout.homepage_custom, notes);
        mContext = context;
        mNotes = notes;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.homepage_custom, null);
            holder = new ViewHolder();
            holder.dateHomepage = (TextView) convertView
                    .findViewById(R.id.dateItemList);
            holder.noteHomepage = (TextView) convertView
                    .findViewById(R.id.noteItemList);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        ParseObject noteObject = (ParseObject) mNotes.get(position);

        // date
        String noteDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.US).format(noteObject.getUpdatedAt());
        holder.dateHomepage.setText(noteDate);

        // note
        String note = noteObject.getString("newNote");
        holder.noteHomepage.setText(note);

        return convertView;
    }

    public static class ViewHolder {
        TextView dateHomepage;
        TextView noteHomepage;

    }

}
