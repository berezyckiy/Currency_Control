package maks.dev.diplom.api;

import maks.dev.diplom.model.latest_rates.LatestRates;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FixerApi {

    @GET("latest")
    Call<LatestRates> latestRates(@Query("access_key") String accessKey);
}
