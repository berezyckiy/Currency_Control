package maks.dev.diplom.api;

import maks.dev.diplom.model.latest_rates.LatestRates;
import retrofit2.Call;

public interface FixerService {

    Call<LatestRates> getLatestRates();
}
