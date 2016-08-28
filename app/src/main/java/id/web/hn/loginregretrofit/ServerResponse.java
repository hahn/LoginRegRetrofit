package id.web.hn.loginregretrofit;

import java.util.ArrayList;

/**
 * Created by hahn on 8/25/16.
 */
public class ServerResponse {

    private String result;
    private String message;
    private User user;
    private Post post;

    private ArrayList<Post> posts = new ArrayList<>();

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public Post getPost() {
        return post;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}