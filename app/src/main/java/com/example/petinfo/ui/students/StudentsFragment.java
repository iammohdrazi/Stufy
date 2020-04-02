package com.example.petinfo.ui.students;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petinfo.AddStudent;
import com.example.petinfo.DatabaseHelperClass;
import com.example.petinfo.MainActivity;
import com.example.petinfo.MainAdapter;
import com.example.petinfo.R;
import com.example.petinfo.Student;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.example.petinfo.DatabaseHelperClass.COL_ATTENDANCE;
import static com.example.petinfo.DatabaseHelperClass.COL_BRANCH;
import static com.example.petinfo.DatabaseHelperClass.COL_EMAIL;
import static com.example.petinfo.DatabaseHelperClass.COL_GENDER;
import static com.example.petinfo.DatabaseHelperClass.COL_ID;
import static com.example.petinfo.DatabaseHelperClass.COL_NAME;
import static com.example.petinfo.DatabaseHelperClass.COL_PHONE;
import static com.example.petinfo.DatabaseHelperClass.COL_YEAR;


public class StudentsFragment extends Fragment {


    private ArrayList<Student> phoneList = new ArrayList();
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ImageView studentEditor;
    private DatabaseHelperClass myDb ;
    private Dialog myDialog;
    private TextView txtClose;
    private TextView studentName;
    private TextView studentId;
    private TextView studentPhone;
    private TextView studentGender;
    private TextView studentEmail;
    private TextView studentBranch;
    private TextView studentYear;

    private ImageView studentPopupCall;
    private ImageView studentPopupMessage;
    private ImageView studentPopupEmail;
    private DrawerLayout drawer;
    private BottomAppBar bottomAppBar;

    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_students, container, false);

        //getSupportActionBar().setTitle("Students");

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        myDb = new DatabaseHelperClass(getActivity()); // dataBase constructor is calling here
        setUpRecycler(root);

        bottomAppBar = root.findViewById(R.id.toolbar);

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_search:
                        Toast.makeText(getActivity(), "Search Student", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_notes:
                        Toast.makeText(getActivity(), "Notes", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Touch Again", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        getStudentData();

        return root;
    }

    void setUpRecycler(View view) {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        mainAdapter=new MainAdapter(getActivity(),this,phoneList);
        recyclerView.setAdapter(mainAdapter);

        fab = (FloatingActionButton)view.findViewById(R.id.add_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddStudent.class);
                startActivityForResult(intent,101);
            }
        });

    }

    public void getStudentData(){
        Cursor res = myDb.getAllData();

        if(res.getCount() == 0){
            //Error Message
            //showMessage("No name Detected","N/A","N/A","N/A","N/A","N/A","N/A");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            phoneList.add(
                    new Student(
                            res.getString(res.getColumnIndex(COL_NAME)),
                            res.getString(res.getColumnIndex(COL_PHONE)),
                            res.getString(res.getColumnIndex(COL_GENDER)),
                            res.getString(res.getColumnIndex(COL_ID)),
                            res.getString(res.getColumnIndex(COL_EMAIL)),
                            res.getString(res.getColumnIndex(COL_BRANCH)),
                            res.getString(res.getColumnIndex(COL_YEAR)),
                            res.getString(res.getColumnIndex(COL_ATTENDANCE))
                    )
            );

        }

        mainAdapter.notifyDataSetChanged();
                /*Intent intent = new Intent(context,StudentEditor.class);
                intent.putExtra("data2",phoneList.get(position).get_id());
                context.startActivity(intent);*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            if(resultCode == RESULT_OK){
                Student student=(Student) data.getSerializableExtra("data");
                phoneList.add(student);
                mainAdapter.notifyDataSetChanged();
            }
        }else if(requestCode == 202){
            if(resultCode == RESULT_OK){
                //(TODO) Refresh Code Here
                phoneList.clear();
                getStudentData();
            }
        }
    }


    public void showPopup(String name, String id, final String phone, String gender, final String email, String branch, String year){
        myDialog = new Dialog(getActivity());
        myDialog.setContentView(R.layout.custompopup);

        studentName = myDialog.findViewById(R.id.tv_student_name);
        studentName.setText(name);

        studentId = myDialog.findViewById(R.id.tv_student_id);
        studentId.setText(id);

        studentPhone = myDialog.findViewById(R.id.tv_student_phone);
        studentPhone.setText(phone);

        studentGender = myDialog.findViewById(R.id.tv_student_gender);
        studentGender.setText(gender);

        studentEmail = myDialog.findViewById(R.id.tv_student_email);
        studentEmail.setText(email);

        studentBranch = myDialog.findViewById(R.id.tv_student_branch);
        studentBranch.setText(branch);

        studentYear = myDialog.findViewById(R.id.tv_student_year);
        studentYear.setText(year);

        txtClose = myDialog.findViewById(R.id.custompopup_close);


        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();

        studentPopupCall = myDialog.findViewById(R.id.student_popUp_call);
        studentPopupCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "tel:" + extractNumber(phone);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(s));
                startActivity(intent);
            }
        });

        studentPopupMessage = myDialog.findViewById(R.id.student_popUp_message);
        studentPopupMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = extractNumber(phone);
                Uri telnumber = Uri.parse("smsto:"+number);
                Intent opensms = new Intent(Intent.ACTION_SENDTO,telnumber);
                startActivity(Intent.createChooser(opensms,"Send message Using"));
            }
        });

        studentPopupEmail = myDialog.findViewById(R.id.student_popUp_email);
        studentPopupEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String uriText = "mailto:" + Uri.encode(email)+
                        "?subject="+Uri.encode("Dear Student")+
                        "&body=" + Uri.encode("Here's an information Regarding -> ");
                emailIntent.setData(Uri.parse(uriText));
                startActivity(Intent.createChooser(emailIntent,"Send Mail via"));
            }
        });
    }

    private static String extractNumber(String phone){

        return phone.replaceAll(" ","")
                .replaceAll("\\(","")
                .replace(")","")
                .replace("-","");
    }


}