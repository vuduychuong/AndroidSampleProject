package com.chuongvd.app.signal.data.database

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.chuongvd.app.signal.AppExecutors
import com.chuongvd.app.signal.data.database.converter.DateConverter
import com.chuongvd.app.signal.data.database.dao.SignalDao
import com.chuongvd.app.signal.data.database.dao.UserDao
import com.chuongvd.app.signal.data.entity.SignalEntity
import com.chuongvd.app.signal.data.entity.UserEntity

@Database(entities = arrayOf(UserEntity::class, SignalEntity::class), version = 3)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

  private val mIsDatabaseCreated = MutableLiveData<Boolean>()

  val databaseCreated: LiveData<Boolean>
    get() = mIsDatabaseCreated

  abstract fun userDao(): UserDao

  abstract fun signalDao(): SignalDao

  /**
   * Check whether the database already exists and expose it via [.getDatabaseCreated]
   */
  private fun updateDatabaseCreated(context: Context) {
    if (context.getDatabasePath(DATABASE_NAME).exists()) {
      setDatabaseCreated()
    }
  }

  private fun setDatabaseCreated() {
    mIsDatabaseCreated.postValue(true)
  }

  companion object {

    private var sInstance: AppDatabase? = null

    @VisibleForTesting
    val DATABASE_NAME = "rocket-db"

    fun getInstance(context: Context): AppDatabase {
      if (sInstance == null) {
        synchronized(AppDatabase::class.java) {
          if (sInstance == null) {
            sInstance = buildDatabase(context.applicationContext,
                AppExecutors.instance)
            sInstance!!.updateDatabaseCreated(context.applicationContext)
          }
        }
      }
      return this.sInstance!!
    }

    /**
     * Build the database. [Builder.build] only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private fun buildDatabase(appContext: Context,
        executors: AppExecutors?): AppDatabase {
      return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
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
          .fallbackToDestructiveMigration().build()
    }

    private fun addDelay() {
      try {
        Thread.sleep(4000)
      } catch (ignored: InterruptedException) {
      }

    }
  }
}
