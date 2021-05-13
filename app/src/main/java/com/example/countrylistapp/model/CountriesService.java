package com.example.countrylistapp.model;

import com.example.countrylistapp.di.DaggerApiComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;

public class CountriesService {
   // public static final String BASE_URL = "https://raw.githubusercontent.com";

    private static CountriesService instance;


    //create the service object
//    private CountriesApi api = new Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build()
//            .create(CountriesApi.class);

    //create the service object from separate class with dagger 2 help in the ".di.*" package
    @Inject
    public CountriesApi api;

    private CountriesService(){
        DaggerApiComponent.create().inject(this);
    }

    public static CountriesService getInstance(){
        if (instance == null){
            instance = new CountriesService();
        }
        return instance;
    }

    //use the service object
    public Single<List<CountryModel>> getCountries(){
        return api.getCountries();
    };

    //!
    //with dagger2 we can separate creating and using service object. This can be done with dependency injection
    //commented rows for createing private CountriesApi api object
}
