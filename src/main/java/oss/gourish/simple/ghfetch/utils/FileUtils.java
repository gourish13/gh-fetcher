package oss.gourish.simple.ghfetch.utils;

import okhttp3.ResponseBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    private static FileUtils fileUtils = null;

    public static FileUtils getInstance() {
        return fileUtils = fileUtils == null ? new FileUtils() : fileUtils;
    }

    public void saveResponseToFile(ResponseBody body, File path, String fileName) throws IOException {
        if (body != null) {
            // Create all dir in destPath, if not exist already
            path.mkdirs();
            // Get Stream of downloaded data and write to the file creating it, if it doesn't exist
            InputStream inputStream = body.byteStream();
            File file = new File(path.toString() + '/' + fileName);
            try {
                writeStreamToFile(inputStream, file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                inputStream.close();
            }
        }
    }

    private void writeStreamToFile(InputStream inputStream, File file) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) >= 0)
                outputStream.write(buffer, 0, bytesRead);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
