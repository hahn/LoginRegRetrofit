package id.web.hn.loginregretrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by hahn on 8/25/16.
 */
public interface RequestInterface {

    @POST("t/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}