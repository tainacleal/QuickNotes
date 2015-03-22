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

/**
 * Created by tainaleal on 1/24/15.
 */
public class StatusAdapter extends ArrayAdapter<ParseObject> {
    protected Context mContext;
    protected List mStatus;

    public StatusAdapter(Context context, List status) {
        super(context, R.layout.homepage_custom, status);
        mContext = context;
        mStatus = status;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.homepage_custom, null);
            holder = new ViewHolder();
            holder.dateHomepage = (TextView) convertView
                    .findViewById(R.id.dateHP);
            holder.statusHomepage = (TextView) convertView
                    .findViewById(R.id.statusHP);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        ParseObject statusObject = (ParseObject) mStatus.get(position);

        // title
        String noteDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(statusObject.getUpdatedAt());
        holder.dateHomepage.setText(noteDate);

        // content
        String status = statusObject.getString("newStatus");
        holder.statusHomepage.setText(status);

        return convertView;
    }

    public static class ViewHolder {
        TextView dateHomepage;
        TextView statusHomepage;

    }

}
