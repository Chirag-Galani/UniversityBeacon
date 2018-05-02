package beacon.university.com.universitybeacon.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import beacon.university.com.universitybeacon.R;
import beacon.university.com.universitybeacon.model.CafeSchedule;

public class CafeScheduleAdapter extends RecyclerView.Adapter<CafeScheduleAdapter.MyViewHolder> {

    private List<CafeSchedule> cafeScheduleList;

    public CafeScheduleAdapter(List<CafeSchedule> cafeScheduleList) {
        this.cafeScheduleList = cafeScheduleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CafeSchedule cafeSchedule = cafeScheduleList.get(position);
        holder.textView_day.setText(cafeSchedule.getDayString());
        holder.textView_timing.setText(cafeSchedule.getTimings());
    }

    @Override
    public int getItemCount() {
        return cafeScheduleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_day, textView_timing;

        public MyViewHolder(View view) {
            super(view);
            textView_day = view.findViewById(R.id.textViewDay);
            textView_timing = view.findViewById(R.id.textViewTiming);
        }
    }
}