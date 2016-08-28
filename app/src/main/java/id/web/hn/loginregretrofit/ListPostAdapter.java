package id.web.hn.loginregretrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hahn on 8/27/16.
 */
public class ListPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context ctx;
    private ArrayList<Post> posts;
    public ListPostAdapter(Context ctx) {
        this.ctx = ctx;
        this.posts = new ArrayList<>();
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.rv_item_post_list, parent, false);
        holder = new ViewHolderListPost(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderListPost vhl = (ViewHolderListPost) holder;
        Post post = posts.get(position);
//        Log.d(Constants.TAG, "position: " + position);

        if(post != null){
            vhl.getTvTitle().setText(post.getTitle());
            vhl.getTvDate().setText(post.getDateCreated());
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    private class ViewHolderListPost extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDate;

        public ViewHolderListPost(View v) {
            super(v);

            tvDate = (TextView) v.findViewById(R.id.rv_tv_date);
            tvTitle = (TextView) v.findViewById(R.id.rv_tv_title);

        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public void setTvTitle(TextView tvTitle) {
            this.tvTitle = tvTitle;
        }

        public TextView getTvDate() {
            return tvDate;
        }

        public void setTvDate(TextView tvDate) {
            this.tvDate = tvDate;
        }
    }
}
