package com.example.petinfo;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petinfo.ui.students.StudentsFragment;

import java.util.ArrayList;


public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DatabaseHelperClass myDb;

    private ArrayList<Student> phoneList = new ArrayList();
    private Activity activity;
    private Fragment context;

    public MainAdapter(Activity activity,Fragment context, ArrayList<Student> phoneList){
        this.phoneList = phoneList;
        this.context=context;
        this.activity=activity;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=  LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_view_main,parent,false);
        return new MainViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MainViewHolder mainViewHolder=(MainViewHolder) holder;
        mainViewHolder.tvMain1.setText(phoneList.get(position).getName());
        mainViewHolder.tvMain2.setText(phoneList.get(position).get_id());
        mainViewHolder.studentDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ((MainActivity) context).showMessage(phoneList.get(position).getName(),phoneList.get(position).get_id(),phoneList.get(position).getPhone(),phoneList.get(position).getGender(),phoneList.get(position).getEmail(),phoneList.get(position).getBranch(),phoneList.get(position).getYear());

                ((StudentsFragment) context).showPopup(phoneList.get(position).getName(),phoneList.get(position).get_id(),phoneList.get(position).getPhone(),phoneList.get(position).getGender(),phoneList.get(position).getEmail(),phoneList.get(position).getBranch(),phoneList.get(position).getYear());
            }
        });

        mainViewHolder.studentEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,StudentEditor.class);
                intent.putExtra("data2",phoneList.get(position));
                context.startActivityForResult(intent,202);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneList.size();
    }



    class MainViewHolder extends RecyclerView.ViewHolder{

        private TextView tvMain1,tvMain2;
        private LinearLayout studentDetail;
        private ImageView studentEdit;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMain1=itemView.findViewById(R.id.tvMain1);
            tvMain2=itemView.findViewById(R.id.tvMain2);
            studentDetail=itemView.findViewById(R.id.student_detail);
            studentEdit=itemView.findViewById(R.id.student_editor);
        }
    }


}
