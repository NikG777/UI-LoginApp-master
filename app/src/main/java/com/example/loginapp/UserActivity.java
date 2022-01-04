package com.example.loginapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapp.UI.model.User;
import com.example.loginapp.UI.service.UserClient;
import com.google.common.base.Utf8;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class UserActivity extends AppCompatActivity {
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/").addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);

    private class StorageURl {
        public String url_picture;
    }
    StorageURl storageURl = new StorageURl();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        final String token = "Bearer_"+ arguments.get("token");
        Call<User> call = userClient.getUser(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                   storageURl.url_picture = response.body().getUrl();
                   picture(response.body().getUrl(), token, response.body().getLogin());
                    System.out.println("fdfd"+token);
                } else {
                    try {
                        Toast.makeText(UserActivity.this,response.errorBody().string(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                 Toast.makeText(UserActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void picture(String url_picture, final String tok, String email)
    {
        System.out.println(url_picture);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", tok)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();
        CircleImageView imageView = findViewById(R.id.image);
        TextView textView = findViewById(R.id.etmai);
        textView.append(email);
        try {
            URL url = new URL(url_picture);
            URL new_url = new URL(url.getProtocol()+"://10.0.2.2:8080"+ url.getPath());
            System.out.println(new_url.toString());
            Picasso picasso = new Picasso.Builder(this)
                    .downloader(new OkHttp3Downloader(client))
                    .build();
            picasso.load(new_url.toString()).into(imageView);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}