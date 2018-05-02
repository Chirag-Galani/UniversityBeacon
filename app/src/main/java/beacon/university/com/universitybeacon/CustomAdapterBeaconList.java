package beacon.university.com.universitybeacon;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

/**
 * Created by satendra on 3/25/2018.
 */

public class CustomAdapterBeaconList extends BaseAdapter{
    Context context;
    ArrayList<String> beacon_info;
    ArrayList<Integer> image_id;
    ArrayList<String> beacon_urls;
    private static LayoutInflater inflater = null;
    public CustomAdapterBeaconList(Context context, ArrayList<String> beacon_info, ArrayList<Integer> image_id, ArrayList<String> beacon_urls){
        this.context = context;
        this.beacon_info = beacon_info;
        this.image_id = image_id;
        this.beacon_urls = beacon_urls;
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
        holder.tv = (TextView) rowView.findViewById(R.id.tvBeaconInfo);
        holder.img = (ImageView) rowView.findViewById(R.id.ivBeaconIcon);
        holder.tv.setText(beacon_info.get(position));
        holder.img.setImageResource(image_id.get(position));
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Beacon Type from Beacon Names and Start respective activities
                String beacon_name = beacon_info.get(position).split(" ")[1];
                switch (beacon_name) {
                    case "ATT" :
                        Toasty.info(context, "Attendance Beacon", Toast.LENGTH_SHORT, true).show();
                        Intent intent = new Intent(context,AttendancePage.class);
                        intent.putExtra("URL",beacon_urls.get(position));
                        context.startActivity(intent);
                        break;
                    case "INFO" :
                        Toasty.info(context, "Information Beacon", Toast.LENGTH_SHORT, true).show();
                        Intent intentOne = new Intent(context,InformationPage.class);
                        intentOne.putExtra("URL",beacon_urls.get(position));
                        context.startActivity(intentOne);
                        break;
                }
            }
        });
        return rowView;
    }
}
