package oss.gourish.simple.ghfetch.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import oss.gourish.simple.ghfetch.consts.Endpoints;
import oss.gourish.simple.ghfetch.json.GitHubContentDeserializer;
import oss.gourish.simple.ghfetch.model.GitHubContent;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addDeserializer(GitHubContent[].class, new GitHubContentDeserializer());
            objectMapper.registerModule(simpleModule);

            retrofit = new Retrofit.Builder()
                    .baseUrl(Endpoints.BASEURL.value())
                    .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                    .build();
        }
        return retrofit;
    }
}