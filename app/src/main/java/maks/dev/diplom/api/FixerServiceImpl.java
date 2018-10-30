package maks.dev.diplom.api;

import maks.dev.diplom.BuildConfig;
import maks.dev.diplom.model.latest_rates.LatestRates;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FixerServiceImpl implements FixerService {

    private final FixerApi mFixerApi;

    public FixerServiceImpl() {
        mFixerApi = initFixerApi();
    }

    private FixerApi initFixerApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.FIXER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(FixerApi.class);
    }

    @Override
    public Call<LatestRates> getLatestRates() {
        return mFixerApi.latestRates(BuildConfig.FIXER_ACCESS_KEY);
    }
}
