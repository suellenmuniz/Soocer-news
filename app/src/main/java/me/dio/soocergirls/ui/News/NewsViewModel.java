package me.dio.soocergirls.ui.News;


import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import me.dio.soocergirls.data.SoocerNewsRepository;
import me.dio.soocergirls.domain.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    public enum State {
        DOING, DONE, ERROR
    }

    private final MutableLiveData<List<News>> news = new MutableLiveData<>();
    private final MutableLiveData<State> state = new MutableLiveData<>();


    public NewsViewModel() {
        this.findNews();

    }

    public void findNews() {
        state.setValue(State.DOING);
        SoocerNewsRepository.getInstance().getRemoteApi().getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(@NonNull Call<List<News>> call, @NonNull Response<List<News>> response) {
                if (response.isSuccessful()) {
                    news.setValue(response.body());
                    state.setValue(State.DONE);
                } else {
                    state.setValue(State.ERROR);
                    Log.e("NewsViewModel", "Error getting data onResponse");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<News>> call, Throwable t) {
                state.setValue(State.ERROR);
                Log.e("NewsViewModel", "Error getting data onFailure");
            }
        });
    }

    public MutableLiveData<List<News>> getNews() {
        return this.news;
    }

    public MutableLiveData<State> getState() {
        return this.state;
    }

    public void saveNews(News news) {
        AsyncTask.execute(() ->
                SoocerNewsRepository.getInstance().getLocalDb().newsDao().save(news));
    }
}





