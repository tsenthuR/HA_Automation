package com.example.senthu.abul_project_01;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {
    Context context;
List<Event>eventList;


    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View eventlayout= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card,parent,false);

        return new EventHolder(eventlayout);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        Event event=eventList.get(position);
        holder.date.setText(event.getDate());
        holder.time.setText(event.getTime());
        holder.type.setText(event.getType());

holder.mcon.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent= new Intent(context,DetailsEventActivity.class);
        intent.putExtra("type",event.getType());
        intent.putExtra("address",event.getAddress());
        intent.putExtra("time",event.getTime());
        intent.putExtra("location",event.getLocation());
        intent.putExtra("date",event.getDate());
        intent.putExtra("event_id",event.getEvent_id());

        context.startActivity(intent);
    }
});

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder{
TextView date,time,type;
LinearLayout mcon;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.rcy_date);
            time=itemView.findViewById(R.id.rcy_time);
            type=itemView.findViewById(R.id.rcy_type);
            mcon=itemView.findViewById(R.id.mcon);
        }
    }
}
