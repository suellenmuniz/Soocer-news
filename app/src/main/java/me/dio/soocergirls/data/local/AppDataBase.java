package me.dio.soocergirls.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import me.dio.soocergirls.domain.News;

@Database(entities = {News.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
