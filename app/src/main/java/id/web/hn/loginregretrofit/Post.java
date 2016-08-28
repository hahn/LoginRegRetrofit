package id.web.hn.loginregretrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hahn on 8/27/16.
 */
public class Post implements Serializable{

    private String email;
    private String title;
    private String newspost;

    @SerializedName("created_at")
    @Expose
    private String dateCreated;

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewspost() {
        return newspost;
    }

    public void setNewspost(String newspost) {
        this.newspost = newspost;
    }
}
