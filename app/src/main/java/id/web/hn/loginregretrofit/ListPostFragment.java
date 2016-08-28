package id.web.hn.loginregretrofit;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListPostFragment extends Fragment {

    private RecyclerView rv;
    private LinearLayoutManager llm;
    private ListPostAdapter lp;
    private ArrayList<Post> posts = new ArrayList<>();
    private SharedPreferences pref;

    public ListPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_post, container, false);
        setHasOptionsMenu(true);
        rv = (RecyclerView) view.findViewById(R.id.rv_post);
        lp = new ListPostAdapter(getContext());
        llm = new LinearLayoutManager(getContext());

        rv.setLayoutManager(llm);
        rv.setAdapter(lp);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        pref = getActivity().getPreferences(0);
        getListPosts();

    }

    private void getListPosts() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        String email = pref.getString(Constants.EMAIL,"");

        User user = new User();
        user.setEmail(email);

        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LIST_POST_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Log.d(Constants.TAG, "" + resp.getMessage());
                if(resp.getResult().equals(Constants.SUCCESS)){
                    posts = resp.getPosts();
                    for(Post p: posts){
                        lp.getPosts().add(p);
                        lp.notifyDataSetChanged();
                    }
//                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
