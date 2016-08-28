package id.web.hn.loginregretrofit;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsertPostFragment extends Fragment implements View.OnClickListener{

    private TextView tvIsiPost;
    private EditText etTitle, etIsiPost;
    private AppCompatButton btnPublish;
    private SharedPreferences pref;
    private ProgressBar progress;


    public InsertPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert_post, container, false);
        initviews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        pref = getActivity().getPreferences(0);
        tvIsiPost.setText("Silakan Isi, " + pref.getString(Constants.NAME,""));

    }

    private void initviews(View view) {

        tvIsiPost = (TextView) view.findViewById(R.id.tv_isipost);
        etTitle = (EditText) view.findViewById(R.id.et_title_post);
        etIsiPost = (EditText) view.findViewById(R.id.et_isi_post);
        btnPublish = (AppCompatButton) view.findViewById(R.id.btn_publish_post);
        btnPublish.setOnClickListener(this);
        progress = (ProgressBar) view.findViewById(R.id.progress_post);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_publish_post:
                progress.setVisibility(View.VISIBLE);
                publishPost();
                break;
        }
    }

    private void publishPost() {
        String email, title, newspost;
        email = pref.getString(Constants.EMAIL,"");
        title = etTitle.getText().toString();
        newspost = etIsiPost.getText().toString();

        if(title.isEmpty()){
            Snackbar.make(getView(),"Title cannot empty!", Snackbar.LENGTH_LONG).show();
            etTitle.requestFocus();
        }


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        Post post = new Post();
        post.setEmail(email);
        post.setTitle(title);
        post.setNewspost(newspost);

        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.INSERT_POST_OPERATION);
        request.setPost(post);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                progress.setVisibility(View.GONE);

                ServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    goToListPost();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG,"new post failed");
                progress.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });
    }

    public void goToListPost(){
        Fragment listpost = new ListPostFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, listpost);
        ft.commit();
    }
}
