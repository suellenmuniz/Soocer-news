package me.dio.soocergirls.data.Remote;

import java.util.List;

import me.dio.soocergirls.domain.News;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SoocerNewsApi {

    @GET("News.json")
    Call<List<News>> getNews();

}
