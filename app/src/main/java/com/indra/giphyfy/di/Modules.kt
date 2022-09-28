package com.indra.giphyfy.di

import com.google.gson.GsonBuilder
import com.indra.giphyfy.data.GiphyRepository
import com.indra.giphyfy.data.GiphyRepositoryImpl
import com.indra.giphyfy.data.source.DataSource
import com.indra.giphyfy.data.source.RemoteDataSourceImpl
import com.indra.giphyfy.network.BASE_URL
import com.indra.giphyfy.network.GiphyApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiKey(): String {
        return "sfZNxqjaGq3jhKaiSBfPGed0rJaiF2no"
    }

    @Provides
    @Singleton
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
object AppModule {
    @Provides
    @ActivityRetainedScoped
    fun provideGiphyApiService(retrofit: Retrofit): GiphyApiService {
        return retrofit.create(GiphyApiService::class.java)
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class AppBinderModule {
    @Binds
    @ActivityRetainedScoped
    abstract fun bindRepository(impl: GiphyRepositoryImpl): GiphyRepository

    @Binds
    @ActivityRetainedScoped
    abstract fun bindRemoteDataSource(impl: RemoteDataSourceImpl): DataSource
}
