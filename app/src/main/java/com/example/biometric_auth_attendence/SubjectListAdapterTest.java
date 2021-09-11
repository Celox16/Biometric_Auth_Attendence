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

public class SubjectListAdapterTest extends ArrayAdapter<SubjectList> {
    private TextView tv_subjectCode, tv_subjectName, tv_dayOfTheWeek, tv_professor, tv_startTime, tv_endTime;
    private String bluetoothName;
    Context context;
    List<SubjectList> arrayListSubjectList;

    public SubjectListAdapterTest(@NonNull Context context, List<SubjectList> arrayListSubjectList) {
        super(context, R.layout.subject_list_item, arrayListSubjectList);

        this.context = context;
        this.arrayListSubjectList = arrayListSubjectList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list_item, null, true);

        // find view by id listview items value
        tv_subjectCode = view.findViewById(R.id.tv_customListview_subjectCode);
        tv_subjectName = view.findViewById(R.id.tv_customListview_subjectName);
        tv_dayOfTheWeek = view.findViewById(R.id.tv_customListview_dayOfTheWeek);
        tv_professor = view.findViewById(R.id.tv_customListview_professor);
        tv_startTime = view.findViewById(R.id.tv_customListview_startTime);
        tv_endTime = view.findViewById(R.id.tv_customListview_endTime);

        // get values by arraylist and set Textview, find bluetoothName
        tv_subjectCode.setText(arrayListSubjectList.get(position).getSubjectCode());
        tv_subjectName.setText(arrayListSubjectList.get(position).getSubjectName());
        tv_dayOfTheWeek.setText(arrayListSubjectList.get(position).getDayOfTheWeek());
        tv_professor.setText(arrayListSubjectList.get(position).getProfessor());
        tv_startTime.setText(arrayListSubjectList.get(position).getStartTime());
        tv_endTime.setText(arrayListSubjectList.get(position).getEndTime());
        bluetoothName = arrayListSubjectList.get(position).getBluetoothName();

        return view;
    }
}
