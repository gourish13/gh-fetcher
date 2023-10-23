package oss.gourish.simple.ghfetch.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface FileDownloadAPI {
    @GET
    @Headers({
            "Accept: application/vnd.github+json",
            "X-GitHub-Api-Version: 2022-11-28"
    })
    Call<ResponseBody> downloadTarball(@Header("Authorization") String authToken,
                                       @Url String fileUrl);

    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl,
                                    @Query("token") String token);

}