package com.example.api;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.api.model.Student;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    private EditText edtID,edtName,edtNumber,edtClass,edtEmail;
    private Button bntAdd, btnClose;
    public static final int RESULT_PIN = 20;
    public static final int RESULT_PIN1 = 30;
    String urlPost = "http://192.168.0.116:8000/insertsinhvien/";
    String urlUpdate = "http://192.168.0.116:8000/updatesinhvien/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        iniWidget();
        getData();
        OnClick();
    }

    public  void iniWidget(){
        edtID         = findViewById(R.id.edt_Id);
        edtName       = findViewById(R.id.edt_Name);
        edtNumber     = findViewById(R.id.edt_Number);
        edtClass      = findViewById(R.id.edt_Class);
        edtEmail      = findViewById(R.id.edt_Email);
        bntAdd        = findViewById(R.id.btn_add);
        btnClose      = findViewById(R.id.btn_Close);
    }

    public void getData(){
        Intent intent = getIntent();
        String id = intent.getStringExtra(MainActivity.ID).toString();
        if (!id.isEmpty()){
             Student student = (Student) intent.getSerializableExtra("students"); // cách 2 lấy đối tượng
             edtID.setText(id);
             edtName.setText(student.getmName());
             edtEmail.setText(student.getmEmail());
             edtNumber.setText(student.getmNumber()+"");
             edtClass.setText(student.getmClass()+"");
        }
    }

    public void OnClick(){
        bntAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name  = edtName.getText().toString();
                String Email = edtEmail.getText().toString();
                String IdSv  = edtID.getText().toString();
                if (Name.isEmpty() || Email.isEmpty()){
                    Toast.makeText(AddActivity.this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (IdSv.isEmpty()){
                        addData();
                    }else{
                        upDateData();
                    }

                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlPost,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("Succecss")){
                        //chuyển về màn hình ban đầu
                        Toast.makeText(AddActivity.this, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_PIN);
                        finish();
                    }else{
                        Toast.makeText(AddActivity.this, "Thông báo thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddActivity.this, "Xẩy ra lỗi thêm mới yêu cầu thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",edtName.getText().toString());
                params.put("idClass",edtClass.getText().toString());
                params.put("diem",edtNumber.getText().toString());
                params.put("email",edtEmail.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void upDateData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Succecss")){
                            //chuyển về màn hình ban đầu
                            Toast.makeText(AddActivity.this, "Cập nhập dữ liệu thành công", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_PIN);
                            finish();
                        }else{
                            Toast.makeText(AddActivity.this, "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddActivity.this, "Xẩy ra lỗi cập nhập yêu cầu thử lại", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",edtID.getText().toString());
                params.put("name",edtName.getText().toString());
                params.put("idCalss",edtClass.getText().toString());
                params.put("diem",edtNumber.getText().toString());
                params.put("email",edtEmail.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
