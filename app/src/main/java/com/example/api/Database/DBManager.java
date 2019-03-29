package com.example.api.Database;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.api.MainActivity;
import com.example.api.model.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    public List<Student> listStudent(String url ){
        final List<Student> listStudent = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue( null);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i =0; i< response.length(); i++){
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
                        //Toast.makeText("Long", "lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
        return listStudent;
    }

}
