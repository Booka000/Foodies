package com.albara.foodis.di

import android.app.Application
import android.provider.ContactsContract.Contacts
import androidx.room.Room
import com.albara.foodis.data.local.CartDatabase
import com.albara.foodis.data.remote.TestServerApi
import com.albara.foodis.data.repository.RepositoryImpl
import com.albara.foodis.domain.repository.Repository
import com.albara.foodis.util.Constants
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