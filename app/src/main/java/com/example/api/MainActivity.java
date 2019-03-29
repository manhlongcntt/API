package com.example.api;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.api.adapter.CustomAdapter;
import com.example.api.model.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView lvSV;
    private CustomAdapter customAdapter;
    private List<Student> studentList;
    public static final int REQUEST_CODE = 120;
    public static final String ID = "ID";
    String url = "http://192.168.0.116:8000/getAllsinhvien/";
    String urlDetele = "http://192.168.0.116:8000/detelesinhvien/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniWidget();
        studentList = listStudent();
        setAdapter();
        Onclick();
    }

    public  void iniWidget(){
        lvSV = findViewById(R.id.lv_SV);
    }
    private void setAdapter(){
        if (customAdapter == null){
            customAdapter = new CustomAdapter(this,R.layout.items_list_student,studentList);
            lvSV.setAdapter(customAdapter);
        }else{
            customAdapter.notifyDataSetChanged();
            lvSV.setSelection(customAdapter.getCount()-1);
        }
    }
    private void Onclick(){

        lvSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = studentList.get(position);
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                intent.putExtra(ID,String.valueOf(student.getmID()));
                intent.putExtra("students",student);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        lvSV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("Thông báo");
                dialog.setContentView(R.layout.show_dialog);
                dialog.setCancelable(false);
                Button btn_dong_y = (Button) dialog.findViewById(R.id.btn_dong_y);
                Button btn_bo_qua = (Button) dialog.findViewById(R.id.btn_bo_qua);
                btn_dong_y.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Student student = studentList.get(position);
                        deleteData(student.getmID()+"");

//                        studentList.clear();
//                        studentList = listStudent(url);
//                        if (customAdapter != null){
//                            customAdapter.notifyDataSetChanged();
//                        }

                        dialog.dismiss();
                    }
                });
                btn_bo_qua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Tạo menu
        getMenuInflater().inflate(R.menu.add_student,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // bắt sự kiện khi click vào nút
        if(item.getItemId() == R.id.menuAdd){
            Intent intent = new Intent(MainActivity.this,AddActivity.class);
            intent.putExtra(ID,"");
            startActivityForResult(intent,REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //requestCode trả về bằng hàm khai báo REQUEST_CODE trường hợp có nhiều REQUEST_CODE trả về cần phải kiểm tra
        //resultCode giá trị từ bên ActivityOutput trả về với định nghĩa trước đó là RESULT_PIN = 20
        if(requestCode == REQUEST_CODE ){
            switch (resultCode){
                case AddActivity.RESULT_PIN:
                    studentList.clear();
                    studentList.addAll(listStudent());
                    setAdapter();
                    break;
                case AddActivity.RESULT_PIN1:
                    break;
                default:
                    break;
            }
        }
    }
    // lấy list dang dách
    public List<Student>  listStudent(){
        final List<Student> listStudent = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i< response.length(); i++){
                        try {
                            JSONObject object = response.getJSONObject(i);
                            Student student = new Student();
                            student.setmID(object.getInt("id"));
                            student.setmName(object.getString("name"));
                            student.setmEmail(object.getString("email"));
                            student.setmClass(object.getInt("idCalss"));
                            student.setmNumber(object.getInt("diem"));
                            listStudent.add(student);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        );
        requestQueue.add(jsonArrayRequest);
        return listStudent;
    }

    public void deleteData( final String id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDetele,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("Succecss")){
                        //chuyển về màn hình ban đầu
                        Toast.makeText(MainActivity.this, "Xoá dữ liệu thành công", Toast.LENGTH_SHORT).show();
                        studentList.clear();
                        studentList.addAll(listStudent());
                        if (customAdapter != null){
                            customAdapter.notifyDataSetChanged();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Xoá dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Xẩy ra lỗi yêu cầu thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
