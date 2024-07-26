package com.albara.foodies.di

import android.app.Application
import androidx.room.Room
import com.albara.foodies.data.local.CartDatabase
import com.albara.foodies.data.remote.TestServerApi
import com.albara.foodies.data.repository.RepositoryImpl
import com.albara.foodies.domain.repository.Repository
import com.albara.foodies.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTestServerApi() : TestServerApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TestServerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCartDatabase(app : Application) : CartDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            CartDatabase::class.java,
            "cart_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(
        db : CartDatabase,
        api : TestServerApi
    ) : Repository {
        return RepositoryImpl(api, db.dao)
    }
}