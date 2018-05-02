package beacon.university.com.universitybeacon.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import beacon.university.com.universitybeacon.R;
import beacon.university.com.universitybeacon.activities.AnnouncementActivity;
import beacon.university.com.universitybeacon.activities.AttendancePage;
import beacon.university.com.universitybeacon.activities.CafeActivity;
/**
 * Created by satendra on 3/25/2018.
 */

public class BeaconListAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> beacon_info;
    ArrayList<Integer> image_id;
    ArrayList<String> beacon_urls;
    String userEmail;
    private static LayoutInflater inflater = null;

    public BeaconListAdapter(Context context, ArrayList<String> beacon_info, ArrayList<Integer> image_id, ArrayList<String> beacon_urls, String userEmail) {
        this.context = context;
        this.beacon_info = beacon_info;
        this.image_id = image_id;
        this.beacon_urls = beacon_urls;
        this.userEmail = userEmail;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return beacon_info.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_beacon_list,null);
        holder.tv = rowView.findViewById(R.id.tvBeaconInfo);
        holder.img = rowView.findViewById(R.id.ivBeaconIcon);
        holder.tv.setText(beacon_info.get(position));
        holder.img.setImageResource(image_id.get(position));
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Beacon Type from Beacon Names and Start respective activities
                String beacon_name = beacon_info.get(position).split(" ")[1];
                switch (beacon_name) {
                    case "ATT" :
                        Intent intent = new Intent(context,AttendancePage.class);
                        intent.putExtra("USER_EMAIL", userEmail);
                        intent.putExtra("URL",beacon_urls.get(position));
                        context.startActivity(intent);
                        break;
                    case "CAFE" :
                        Intent intentOne = new Intent(context,CafeActivity.class);
                        intentOne.putExtra("URL",beacon_urls.get(position));
                        context.startActivity(intentOne);
                        break;
                    case "INFO" :
                        Intent intentTwo = new Intent(context,AnnouncementActivity.class);
                        intentTwo.putExtra("USER_EMAIL", userEmail);
                        intentTwo.putExtra("URL",beacon_urls.get(position));
                        context.startActivity(intentTwo);
                        break;

                }
            }
        });
        return rowView;
    }
}
