package oss.gourish.simple.ghfetch.api;

import okhttp3.ResponseBody;
import oss.gourish.simple.ghfetch.utils.FileUtils;
import retrofit2.Call;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;

public class FileDownloadService {
    private static FileDownloadService fileDownloadService = null;

    public static FileDownloadService getInstance() {
        if (fileDownloadService == null)
            fileDownloadService = new FileDownloadService();
        return fileDownloadService;
    }

    public void downloadTarball(String fileUrl, String token, File destPath, String fileName) {
        String authToken = "Bearer " + token;
        FileDownloadAPI fileDownloadAPI = RetrofitClient
                .getRetrofitClient()
                .create(FileDownloadAPI.class);
        Call<ResponseBody> call = fileDownloadAPI.downloadTarball(authToken, fileUrl);
        makeCallAndCollectResponse(call, destPath, fileName);
    }

    public void downloadFileContent(String fileUrl, File destPath, String fileName) {
        FileDownloadAPI fileDownloadAPI = RetrofitClient
                .getRetrofitClient()
                .create(FileDownloadAPI.class);

        String token;
        Call<ResponseBody> call;
        // Retrive query param token
        int index = fileUrl.lastIndexOf("?token");
        if (index == -1)
            call = fileDownloadAPI.downloadFile(fileUrl);
        else {
            token = fileUrl.substring(index).split("=")[1];
            // Remove token from url
            fileUrl = fileUrl.substring(0, index);
            call = fileDownloadAPI.downloadFile(fileUrl, token);
        }
        makeCallAndCollectResponse(call, destPath, fileName);
    }

    private void makeCallAndCollectResponse(Call<ResponseBody> call, File destPath, String fileName) {
        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful())
                FileUtils.getInstance().saveResponseToFile(response.body(), destPath, fileName);
            else
                throw new RuntimeException("Status Code: " + response.code() + "\n" + response +
                        "\nAPI Call Failed with HTTP response code : " + response.code());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
