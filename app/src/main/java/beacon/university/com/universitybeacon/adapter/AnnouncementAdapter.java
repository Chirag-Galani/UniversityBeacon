package beacon.university.com.universitybeacon.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import beacon.university.com.universitybeacon.R;
import beacon.university.com.universitybeacon.model.Announcement;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder> {

    private List<Announcement> announcementList;

    public AnnouncementAdapter(List<Announcement> announcementList) {
        this.announcementList = announcementList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.announcement_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Announcement announcement = announcementList.get(position);
//        String displayText=(position+1)+". "+announcement.getNotice();
        holder.textViewAnnouncement.setText(announcement.getNotice());
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewAnnouncement;

        public MyViewHolder(View view) {
            super(view);
            textViewAnnouncement = view.findViewById(R.id.textViewAnnouncement);
        }
    }
}