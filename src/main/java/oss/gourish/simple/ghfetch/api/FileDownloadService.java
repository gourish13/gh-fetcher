package oss.gourish.simple.ghfetch.api;

import okhttp3.ResponseBody;
import oss.gourish.simple.ghfetch.utils.FileUtils;
import retrofit2.Call;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;

public class FileDownloadService {
    private static FileDownloadService fileDownloadService = null;

    public static FileDownloadService getFileDownloadService() {
        if (fileDownloadService == null)
            fileDownloadService = new FileDownloadService();
        return fileDownloadService;
    }

    public void downloadTarball(String fileUrl, String token, File destPath, String fileName) {
        String authToken = "Bearer " + token;
        FileDownloadAPI fileDownloadAPI = RetrofitClient.getRetrofitClient().create(FileDownloadAPI.class);
        Call<ResponseBody> call = fileDownloadAPI.downloadTarball(authToken, fileUrl);

        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                FileUtils.getFileUtils().saveResponseToFile(response.body(), destPath, fileName);
            } else
                throw new RuntimeException(response +
                        "\nDownload failed with HTTP response code : " + response.code());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
