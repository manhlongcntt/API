package com.example.api.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.api.R;
import com.example.api.model.Student;

import java.util.List;


public class CustomAdapter extends ArrayAdapter<Student> {
    private Context context;
    private int resource;
    private List<Student> listsudent;
    public CustomAdapter(Context context, int resource, List<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listsudent = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if(convertView == null){

            convertView = LayoutInflater.from(context).inflate(R.layout.items_list_student,parent,false);
            viewHoder      =   new ViewHoder();
            viewHoder.vtId      = convertView.findViewById(R.id.tv_Id);
            viewHoder.vtName    = convertView.findViewById(R.id.tv_name);
            viewHoder.vtNumber  = convertView.findViewById(R.id.tv_number);
            viewHoder.vtClass   = convertView.findViewById(R.id.tv_Class);
            viewHoder.vtEmail   = convertView.findViewById(R.id.tv_email);
            convertView.setTag(viewHoder);

        }else{
            viewHoder = (ViewHoder) convertView.getTag();
        }
        Student student = listsudent.get(position);
        viewHoder.vtId.setText(String.valueOf(student.getmID()));
        viewHoder.vtName.setText(String.valueOf(student.getmName()));
        viewHoder.vtNumber.setText(String.valueOf(student.getmNumber()));
        viewHoder.vtClass.setText(String.valueOf(student.getmClass()));
        viewHoder.vtEmail.setText(String.valueOf(student.getmEmail()));

        return  convertView;
    }
    public class ViewHoder{
        private TextView vtId;
        private TextView vtName;
        private TextView vtNumber;
        private TextView vtClass;
        private TextView vtEmail;
    }

}
