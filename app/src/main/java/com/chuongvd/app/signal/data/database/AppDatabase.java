/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chuongvd.app.signal.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.VisibleForTesting;
import com.chuongvd.app.signal.AppExecutors;
import com.chuongvd.app.signal.data.database.converter.DateConverter;
import com.chuongvd.app.signal.data.database.dao.SignalDao;
import com.chuongvd.app.signal.data.database.dao.UserDao;
import com.chuongvd.app.signal.data.entity.SignalEntity;
import com.chuongvd.app.signal.data.entity.UserEntity;

@Database(entities = { UserEntity.class, SignalEntity.class }, version = 3)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "rocket-db";

    public abstract UserDao userDao();

    public abstract SignalDao signalDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(),
                            AppExecutors.getInstance());
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext,
            final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                //                .addCallback(new Callback() {
                //                    @Override
                //                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                //                        super.onCreate(db);
                //                        executors.diskIO().execute(() -> {
                //                            // Add a delay to simulate a long-running operation
                //                            //                            addDelay();
                //                            // Generate the data for pre-population
                //                            AppDatabase database = AppDatabase.getInstance(appContext);
                //                            //                            List<ProductEntity> products = DataGenerator.generateProducts();
                //                            //                            List<CommentEntity> comments =
                //                            //                                    DataGenerator.generateCommentsForProducts(products);
                //                            //
                //                            //                            insertData(database, products, comments);
                //                            // notify that the database was created and it's ready to be used
                //                            database.setDatabaseCreated();
                //                        });
                //                    }
                //                })
                .fallbackToDestructiveMigration().build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
