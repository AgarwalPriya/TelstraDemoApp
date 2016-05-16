package android.wipro.com.telstra.demoapp.interfaces;

import android.wipro.com.telstra.demoapp.model.NewsFeed;

import retrofit.Call;
import retrofit.http.GET;
/**
 * Created by priyag on 14-May-16.
 * Service interface declaration
 */
public interface IFeedAPIService {
    @GET("/u/746330/facts.json")
    Call<NewsFeed> loadFeeds();
}

