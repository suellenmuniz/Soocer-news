package me.dio.soocergirls.data;

import androidx.room.Room;

import me.dio.soocergirls.App;
import me.dio.soocergirls.data.Remote.SoocerNewsApi;
import me.dio.soocergirls.data.local.SoocerNewsDb;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoocerNewsRepository {

    private static final String REMOTE_API_URL = "https://github.com/suellenmuniz/soocer-news-api";
    private static final String LOCAL_DB_NAME = "soocer-news";



    private final SoocerNewsApi remoteApi;
    private final SoocerNewsDb localDb;

    public SoocerNewsApi getRemoteApi() {
        return remoteApi;
    }

    public SoocerNewsDb getLocalDb() {
        return localDb;
    }


    private SoocerNewsRepository () {
        remoteApi = new Retrofit.Builder()
                .baseUrl(REMOTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SoocerNewsApi.class);

        localDb = Room.databaseBuilder(App.getInstance(), SoocerNewsDb.class, LOCAL_DB_NAME).build();
    }

    public static SoocerNewsRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final SoocerNewsRepository INSTANCE = new SoocerNewsRepository();
    }
}


