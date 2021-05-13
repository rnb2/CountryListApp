package com.example.countrylistapp.di;

import com.example.countrylistapp.model.CountriesService;
import com.example.countrylistapp.viewmodel.ListViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {
    //where we want to inject
    void inject(CountriesService countriesService);

    void inject(ListViewModel listViewModel);

}
