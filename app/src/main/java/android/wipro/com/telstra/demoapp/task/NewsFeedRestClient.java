package android.wipro.com.telstra.demoapp.task;

import android.wipro.com.telstra.demoapp.interfaces.IFeedAPIService;
import android.wipro.com.telstra.demoapp.util.Constants;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by priyag on 14-May-16.
 * Entry point for all requests to NewsFeedRestClient API.
 * Uses Retrofit library to abstract the actual REST API into a service.
 */

public class NewsFeedRestClient {

    private static NewsFeedRestClient instance;
    private IFeedAPIService mFeedService;

    /**
     * Returns the instance of this singleton.
     */
    public static NewsFeedRestClient getInstance() {
        if (instance == null) {
            instance = new NewsFeedRestClient();
        }
        return instance;
    }

    /**
     * Private singleton constructor.
     */
    private NewsFeedRestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.FEED_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mFeedService = retrofit.create(IFeedAPIService.class);
    }

    public IFeedAPIService getService() {
        return mFeedService;
    }
}
