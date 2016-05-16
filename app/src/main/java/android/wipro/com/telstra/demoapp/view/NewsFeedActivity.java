package android.wipro.com.telstra.demoapp.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.wipro.com.telstra.demoapp.R;
import android.wipro.com.telstra.demoapp.adapter.NewsFeedAdapter;
import android.wipro.com.telstra.demoapp.interfaces.IFeedAPIService;
import android.wipro.com.telstra.demoapp.model.NewsFeed;
import android.wipro.com.telstra.demoapp.task.NewsFeedRestClient;
import android.wipro.com.telstra.demoapp.util.NetworkUtility;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by priyag on 13-May-16.
 */
public class NewsFeedActivity extends AppCompatActivity {

    private static final String TAG = "NewsFeedActivity";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsFeedAdapter mFeedAdapter;
    private String title = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mFeedAdapter = new NewsFeedAdapter(this);
        mRecyclerView.setAdapter(mFeedAdapter);

        setSupportActionBar(mToolbar);

        FloatingActionButton refresh = (FloatingActionButton) findViewById(R.id.refresh);
        if (refresh != null) {
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fetchNewsFeed();
                }
            });
        }
        fetchNewsFeed();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setTitle(title);
    }

    /**
     * Check network availability, if available fetch the news feed
     * else display error message
     */
    private void fetchNewsFeed() {
        if (!NetworkUtility.isNetworkAvailable(this)) {
            displayMessage(R.string.no_network);
        } else {
            displayMessage(R.string.wait);
            displayFeeds();
        }
    }

    /**
     * fectch the feeds using retrofit Call.
     * display the feeds fetched in the Callback method.
     */

    private void displayFeeds() {
        IFeedAPIService service = NewsFeedRestClient.getInstance().getService();
        if (service != null) {
            Call<NewsFeed> call = service.loadFeeds();
            if (call != null) {
                //Async call of retrofit library which will download and parse the JSON
                call.enqueue(new Callback<NewsFeed>() {
                    @Override
                    public void onResponse(Response<NewsFeed> response, Retrofit retrofit) {
                        //Check whether response recieved is valid
                        if (response != null && response.body() != null) {
                            try {
                                title = response.body().title;
                                setTitle(title);

                                if (response.body().rows != null && response.body().rows.size() > 0) {
                                    for (int i = 0; i < response.body().rows.size(); i++) {
                                        if (response.body().rows.get(i).title == null ||
                                                response.body().rows.get(i).title.length() == 0)
                                            response.body().rows.remove(i);
                                    }
                                    if (mFeedAdapter != null) {
                                        mFeedAdapter.setDataSet(response.body().rows);
                                        mFeedAdapter.notifyDataSetChanged();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                displayMessage(R.string.download_err_msg);
                            }
                        } else {
                            displayMessage(R.string.download_err_msg);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "Callback failure");
                        displayMessage(R.string.download_err_msg);
                    }
                });
            } else {
                displayMessage(R.string.download_err_msg);
            }
        } else {
            displayMessage(R.string.download_err_msg);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            NewsFeedActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * API to display relevant messages to the user for an action.
     * @param string_res string to be displayed as message
     */
    private void displayMessage(int string_res) {
        Snackbar.make(mRecyclerView, string_res, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
