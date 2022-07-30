package me.dio.soocergirls.ui.News;


import android.os.AsyncTask;

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
        DOING, DONE, ERROR;
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
                    state.setValue(State.DOING);
                } else {
                    state.setValue(State.ERROR);
                }
            }
 
            @Override
            public void onFailure(@NonNull Call<List<News>> call, Throwable error) {
                //FIXME Tirar o printStackTrace quando pormos para produção!!
                error.printStackTrace();
                state.setValue(State.ERROR);
            }
        });
    }

    public void saveNews(News news) {
        AsyncTask.execute(() -> SoocerNewsRepository.getInstance().getLocalDb().newsDao().save(news));

    }

    public LiveData<List<News>> getNews() {
        return this.news;
    }

    public LiveData<State> getState() {
        return this.state;
    }

}


