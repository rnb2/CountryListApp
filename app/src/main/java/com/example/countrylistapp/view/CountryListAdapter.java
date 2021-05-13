package com.example.countrylistapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countrylistapp.R;
import com.example.countrylistapp.model.CountryModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {

    private final List<CountryModel> countries;

    public CountryListAdapter(List<CountryModel> countries){
        this.countries = countries;
    }

    public void updateCountries(List<CountryModel> newCountries) {
        countries.clear();
        countries.addAll(newCountries);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        holder.bind(countries.get(position));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.imageCountry)
        protected ImageView imageCountry;

        @BindView(R.id.name)
        protected TextView countryName;

        @BindView(R.id.capital)
        protected TextView capital;


        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(CountryModel countryModel){
            countryName.setText(countryModel.getCountryName());
            capital.setText(countryModel.getCapital());
            Util.loadImage(imageCountry, countryModel.getFlag(), Util.getCircularProgressDrawable(imageCountry.getContext()));
        }
    }
}
