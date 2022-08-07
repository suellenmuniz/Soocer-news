package me.dio.soocergirls.ui.Favorites;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import me.dio.soocergirls.data.SoocerNewsRepository;
import me.dio.soocergirls.domain.News;

public class FavoritesViewModel extends ViewModel {

    public FavoritesViewModel() {
    }

    public LiveData<List<News>> loadFavoriteNews() {
        return SoocerNewsRepository.getInstance().getLocalDb().newsDao().getFavoriteNews();

    }

    public void saveNews(News news) {
        AsyncTask.execute(() ->
                SoocerNewsRepository.getInstance().getLocalDb().newsDao().save(news));
    }
}
