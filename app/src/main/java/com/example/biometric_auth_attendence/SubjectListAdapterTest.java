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
    private TextView tv_subjectName;

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

        TextView tv_subjectCode = view.findViewById(R.id.tv_customListview_subjectCode);
        tv_subjectName = view.findViewById(R.id.tv_customListview_subjectName);

        return super.getView(position, convertView, parent);
    }
}
