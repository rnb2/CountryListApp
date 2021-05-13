package com.example.countrylistapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.countrylistapp.R;
import com.example.countrylistapp.model.CountryModel;
import com.example.countrylistapp.viewmodel.ListViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.countriesList)
    RecyclerView countriesList;

    @BindView(R.id.listError)
    TextView errors;

    @BindView(R.id.loadingProgress)
    ProgressBar progress;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private ListViewModel viewModel;
    private CountryListAdapter countryListAdapter = new CountryListAdapter(new ArrayList<CountryModel>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //populate all elements: countriesList, errors, progress
        ButterKnife.bind(this);

        //initial view model:
        //activity(MainActivity) is transient and can be destroyed by the system at any point.
        //and view model has the larger size then the activity
        //This is the root cause that we can not do simple like this: new ListViewModel();
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.refresh();

        countriesList.setLayoutManager(new LinearLayoutManager(this));
        countriesList.setAdapter(countryListAdapter);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh();
                swipeLayout.setRefreshing(false);
            }
        });

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.countries.observe(this, countryModels -> {
            if (countryModels != null){
                countriesList.setVisibility(View.VISIBLE);
                countryListAdapter.updateCountries(countryModels);
            }
        });

        viewModel.loadError.observe(this, isError -> {
            if (isError != null)
            {
                errors.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });

        viewModel.loading.observe(this, isLoading -> {
            if (isLoading != null){
                progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading){
                    errors.setVisibility(View.GONE);
                    countriesList.setVisibility(View.GONE);
                }
            }
        });
    }
}