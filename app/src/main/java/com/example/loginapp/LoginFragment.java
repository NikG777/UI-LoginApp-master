package com.example.loginapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginapp.UI.model.Login;
import com.example.loginapp.UI.model.User;
import com.example.loginapp.UI.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/").addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);
    private String token;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // if (!getToken().isEmpty()){
       //     Intent intent = new Intent(getContext(), UserActivity.class);
       //     intent.putExtra("token", getToken());
      //      startActivity(intent);
      //  }
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        view.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textLogin = (EditText) getView().findViewById(R.id.et_email);
                EditText textPassword = (EditText) getView().findViewById(R.id.et_password);
                if (textLogin == null) {
                }
                if (textPassword == null) {
                }
                Login login = new Login(textLogin.getText().toString().trim(), textPassword.getText().toString().trim());
                Call<User> call = userClient.login(login);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            token = response.body().getToken();
                            saveToken(token);
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                        // Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }

    private void saveToken(String token) {
        SharedPreferences prefs;
        SharedPreferences.Editor edit;
        prefs = getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        edit = prefs.edit();
        edit.putString("token", token);
        Log.i("Login", token);
        edit.apply();
    }

    private String getToken() {
        SharedPreferences prefs;
        prefs = getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        return prefs.getString("token", "").trim();
    }
}
