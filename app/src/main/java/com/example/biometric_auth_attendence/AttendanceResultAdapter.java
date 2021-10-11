package com.example.biometric_auth_attendence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AttendanceResultAdapter extends ArrayAdapter<AttendanceResultList> {
    Context context;
    List<AttendanceResultList> attendanceResultLists;

    public AttendanceResultAdapter(@NonNull Context context, List<AttendanceResultList> attendanceResultLists) {
        super(context, R.layout.attendance_list_item, attendanceResultLists);

        this.context = context;
        this.attendanceResultLists = attendanceResultLists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_list_item, null, true);

        // find view by id listview items value
        TextView tv_day = view.findViewById(R.id.tv_att_list_item_day);
        TextView tv_time = view.findViewById(R.id.tv_att_list_item_time);
        TextView tv_status = view.findViewById(R.id.tv_att_list_item_status);

        // get values by arraylist and set Textview
        // formatting day and time  (2021-10-11 15:00:06)
        //                           0123456789012345678
        tv_day.setText(attendanceResultLists.get(position).getArrivalTime().substring(5,9));
        tv_time.setText(attendanceResultLists.get(position).getArrivalTime().substring(11));
        tv_status.setText(attendanceResultLists.get(position).getStatus());

        return view;
    }
}
