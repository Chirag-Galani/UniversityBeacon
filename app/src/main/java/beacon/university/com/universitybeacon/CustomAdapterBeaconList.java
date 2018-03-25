package beacon.university.com.universitybeacon;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by satendra on 3/25/2018.
 */

public class CustomAdapterBeaconList extends BaseAdapter{
    Context context;
    ArrayList<String> beacon_info;
    int image_id;
    private static LayoutInflater inflater = null;
    public CustomAdapterBeaconList(Context context, ArrayList<String> beacon_info, int image_id){
        this.context = context;
        this.beacon_info = beacon_info;
        this.image_id = image_id;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_beacon_list,null);
        holder.tv = (TextView) rowView.findViewById(R.id.tvBeaconInfo);
        holder.img = (ImageView) rowView.findViewById(R.id.ivBeaconIcon);
        holder.tv.setText(beacon_info.get(position));
        holder.img.setImageResource(image_id);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Beacon Clicked",Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }
}
