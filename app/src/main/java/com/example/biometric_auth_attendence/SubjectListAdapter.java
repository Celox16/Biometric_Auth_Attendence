package com.example.biometric_auth_attendence;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SubjectListAdapter extends BaseAdapter {
    private Context context;
    private List<SubjectList> listViewBtnItems;

    public SubjectListAdapter(Context context, List<SubjectList> listViewBtnItems){
        this.context = context;
        this.listViewBtnItems = listViewBtnItems;
    }

    @Override
    public int getCount() {
        Log.d("dbtest", ""+listViewBtnItems);
        return listViewBtnItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewBtnItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View view1 = View.inflate(context, R.layout.subject_list_item, null);
        TextView tv_subjectCode = (TextView) view1.findViewById(R.id.tv_modifySugang_stuNum);
        TextView tv_subjectName = (TextView) view1.findViewById(R.id.tv_customListview_subjectName);
        TextView tv_dayOfTheWeek = (TextView) view1.findViewById(R.id.tv_customListview_dayOfTheWeek);
        TextView tv_professor = (TextView) view1.findViewById(R.id.tv_customListview_professor);
        TextView tv_startTime = (TextView) view1.findViewById(R.id.tv_customListview_startTime);
        TextView tv_endTime = (TextView) view1.findViewById(R.id.tv_customListview_endTime);

        tv_subjectCode.setText("" + listViewBtnItems.get(position).getSubjectCode());
        tv_subjectName.setText(listViewBtnItems.get(position).getSubjectName());
        tv_dayOfTheWeek.setText(listViewBtnItems.get(position).getDayOfTheWeek());
        tv_professor.setText(listViewBtnItems.get(position).getProfessor());
        tv_startTime.setText(listViewBtnItems.get(position).getStartTime());
        tv_endTime.setText(listViewBtnItems.get(position).getEndTime());

        view1.setTag(listViewBtnItems.get(position).getSubjectCode());

        return view1;
    }
}