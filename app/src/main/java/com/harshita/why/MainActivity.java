package com.harshita.why;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResults;

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResults = findViewById(R.id.text_result_view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getPosts();

        //getComments();

        createPost();
    }

    private void getPosts(){
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(new Integer[]{2,3}, "id", "desc ");

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful()){
                    textViewResults.setText("Code :"+ response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts){
                    String content = "";
                    content += "ID :" + post.getId() +"\nUserID :" + post.getUserId() + "\nTitle : " + post.getTitle() + "\nText :" + post.getText() +"\n\n";

                    textViewResults.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResults.setText(t.getMessage());
            }
        });
    }

    private void getComments(){

        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(3);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if(!response.isSuccessful()){
                    textViewResults.setText("Code :" + response.code());
                    return;
                }

                List<Comment> comments = response.body();

                for (Comment comment : comments){

                    String content = "";

                    content += "PostId :" + comment.getPostId() + "\nId :" + comment.getId() + "\nName :" + comment.getName() + "\nEmail :" + comment.getEmail() +  "\nText :" + comment.getText() + "\n\n";

                    textViewResults.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

                textViewResults.setText(t.getMessage());

            }
        });
    }

    private void createPost(){
        Post post = new Post (23,"new title", "new text");

        Call<Post> call = jsonPlaceHolderApi.createPost(23, "new title","new text");

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()){
                    textViewResults.setText("Code : " + response.code());
                    return;
                }

                Post post1 = response.body();

                String content = "";
                content +="Code :" + response.code() + "\nID :" + post1.getId() +"\nUserID :" + post1.getUserId() + "\nTitle : " + post1.getTitle() + "\nText :" + post1.getText() +"\n\n";

                textViewResults.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                textViewResults.setText(t.getMessage());
            }
        });
    }

}
