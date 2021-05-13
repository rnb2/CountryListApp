package com.example.countrylistapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countrylistapp.di.DaggerApiComponent;
import com.example.countrylistapp.model.CountriesService;
import com.example.countrylistapp.model.CountryModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {
    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    //private CountriesService countriesService = CountriesService.getInstance();
    //same as CountriesService.getInstance(), but implemented by injection:
    @Inject
    public CountriesService countriesService;

    public ListViewModel() {
        super();
        DaggerApiComponent.create().inject(this);
    }


    private CompositeDisposable disposable = new CompositeDisposable();

    public void refresh() {
        fetchCountries();
    }

    private void fetchCountries() {
        loading.setValue(true);
        disposable.add(
                countriesService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CountryModel>>(){

                    @Override
                    public void onSuccess(@NonNull List<CountryModel> countryModels) {
                        countries.setValue(countryModels);
                        loadError.setValue(false);
                        loading.setValue(false);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        loadError.setValue(true);
                        loading.setValue(false);
                    }
                })
        );
        loadError.setValue(false);
        loading.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    //temporary data, before countryService use.
    @Deprecated
    private void fetchCountries2() {
        loading.setValue(true);
        CountryModel countryModel1 = new CountryModel("Albania", "Tirana", "");
        CountryModel countryModel2 = new CountryModel("Brazil", "Brasilia", "");
        CountryModel countryModel3 = new CountryModel("Cheh", "Praha", "");
        CountryModel countryModel4 = new CountryModel("Armenia", "Yerevan", "");
        CountryModel countryModel5 = new CountryModel("Australia", "Canberra", "");
        CountryModel countryModel6 = new CountryModel("Austria", "Vienna", "");
        CountryModel countryModel7 = new CountryModel("Azerbaijan", "Baku", "");
        CountryModel countryModel8 = new CountryModel("Bahamas", "Nassau", "");
        CountryModel countryModel9 = new CountryModel("Bahrain", "Manama", "");
        CountryModel countryModel10 = new CountryModel("Bangladesh", "Dhaka", "");
        CountryModel countryModel11 = new CountryModel("Barbados", "Manama", "");
        CountryModel countryModel12 = new CountryModel("Bahrain", "Bridgetown", "");
        CountryModel countryModel13 = new CountryModel("Belarus", "Minsk", "");
        CountryModel countryModel14 = new CountryModel("Belgium", "Brussels", "");
        CountryModel countryModel15 = new CountryModel("Belize", "Belmopan", "");

        List<CountryModel> list = new ArrayList<>();

        list.add(countryModel1);
        list.add(countryModel2);
        list.add(countryModel3);
        list.add(countryModel4);
        list.add(countryModel5);
        list.add(countryModel6);
        list.add(countryModel7);
        list.add(countryModel8);
        list.add(countryModel9);
        list.add(countryModel10);
        list.add(countryModel11);
        list.add(countryModel12);
        list.add(countryModel13);
        list.add(countryModel14);
        list.add(countryModel15);

        countries.setValue(list);
        loadError.setValue(false);
        loading.setValue(false);
    }

}
