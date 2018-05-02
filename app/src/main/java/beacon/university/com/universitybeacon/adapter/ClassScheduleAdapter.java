package beacon.university.com.universitybeacon.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import beacon.university.com.universitybeacon.R;
import beacon.university.com.universitybeacon.model.ClassSchedule;

public class ClassScheduleAdapter extends RecyclerView.Adapter<ClassScheduleAdapter.MyViewHolder> {

    private List<ClassSchedule> ClassScheduleList;

    public ClassScheduleAdapter(List<ClassSchedule> ClassScheduleList) {
        this.ClassScheduleList = ClassScheduleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ClassSchedule ClassSchedule = ClassScheduleList.get(position);
        holder.textViewDay.setText(ClassSchedule.getLecDate().substring(0, 10));
        holder.textViewDescription.setText(ClassSchedule.getDescription());
    }

    @Override
    public int getItemCount() {
        return ClassScheduleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDay, textViewDescription;

        public MyViewHolder(View view) {
            super(view);
            textViewDay = view.findViewById(R.id.textViewDay);
            textViewDescription = view.findViewById(R.id.textViewTiming);
        }
    }
}