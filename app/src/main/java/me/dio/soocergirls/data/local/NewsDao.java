package me.dio.soocergirls.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import me.dio.soocergirls.domain.News;

@Dao
public interface NewsDao {


    @Query("SELECT * FROM news WHERE favorite = 1")
    LiveData<List<News>> getFavoriteNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Void save(News news);


    @Delete
    void delete(News news);

}
