package com.example.loginapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginapp.UI.model.Login;
import com.example.loginapp.UI.model.Register;
import com.example.loginapp.UI.model.User;
import com.example.loginapp.UI.service.UserClient;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/").addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);
    public RegisterFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        view.findViewById(R.id.btn_register).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textLogin = (EditText) getView().findViewById(R.id.et_name);
                EditText textPassword =  (EditText) getView().findViewById(R.id.et_password);
                EditText textEmail = (EditText)  getView().findViewById(R.id.et_email);

                if(textLogin == null) {}
                if(textPassword == null){}
                if (textEmail == null) {}

                Register register = new Register(textEmail.getText().toString().trim(),textLogin.getText().toString().trim(),textPassword.getText().toString().trim());
                System.out.println(register.getEmail().toString());
                Login login = new Login(textLogin.toString().trim(),textPassword.getText().toString().trim());
                Call<ResponseBody> call = userClient.register(register);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful() && response.body() != null)
                        {
                            Toast.makeText(getActivity(),"Подтвердите аккаунт на вашей почте",Toast.LENGTH_SHORT).show();
                        }
                        else
                            if(response.errorBody()!=null)
                        {
                            try {
                                Toast.makeText(getActivity(),new JSONObject(response.errorBody().string()).toString(),Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                            if(response.body() == null && response.errorBody() == null)
                            {
                                Toast.makeText(getActivity(),"Сервер не отвечает",Toast.LENGTH_SHORT).show();
                            }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                         Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }
}
