package oss.gourish.simple.ghfetch.api;

import oss.gourish.simple.ghfetch.model.GitHubContent;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GitHubContentAPI {
    @GET
    @Headers({
            "Accept: application/vnd.github+json",
            "X-GitHub-Api-Version: 2022-11-28"
    })
    Call<GitHubContent[]> getContentsInfo(@Header("Authorization") String authToken,
                                          @Url String fileUrl);

    @GET
    @Headers({
            "Accept: application/vnd.github+json",
            "X-GitHub-Api-Version: 2022-11-28"
    })
    Call<GitHubContent[]> getContentsInfo(@Header("Authorization") String authToken,
                                          @Url String fileUrl,
                                          @Query("ref") String branchName);
}
