package id.web.hn.loginregretrofit;

/**
 * Created by hahn on 8/25/16.
 */
public class ServerRequest {

    private String operation;
    private User user;

    private Post post;

    public void setPost(Post post) {
        this.post = post;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(User user) {
        this.user = user;
    }
}