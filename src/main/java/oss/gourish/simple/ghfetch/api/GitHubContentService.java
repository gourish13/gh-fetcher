package oss.gourish.simple.ghfetch.api;

import oss.gourish.simple.ghfetch.model.GitHubContent;
import retrofit2.Call;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class GitHubContentService {
    private static GitHubContentService gitHubContentService = null;

    public static GitHubContentService getInstance() {
        return gitHubContentService = gitHubContentService == null
                ? new GitHubContentService()
                : gitHubContentService;
    }

    public void getGitHubContentsInfo(String fileUrl, String token, String branchName, File destPath) {
        String authToken = "Bearer " + token;
        GitHubContentAPI gitHubContentAPI = RetrofitClient
                .getRetrofitClient()
                .create(GitHubContentAPI.class);

        Call<GitHubContent[]> call;
        if (branchName.isEmpty())
            call = gitHubContentAPI.getContentsInfo(authToken, fileUrl);
        else
            call = gitHubContentAPI.getContentsInfo(authToken, fileUrl, branchName);

        try {
            Response<GitHubContent[]> response = call.execute();

            if (response.isSuccessful())
                System.out.println(Arrays.toString(response.body()));
            else
                throw new RuntimeException("Status Code: " + response.code() + "\n" + response +
                        "\nAPI Call Failed with HTTP response code : " + response.code());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
