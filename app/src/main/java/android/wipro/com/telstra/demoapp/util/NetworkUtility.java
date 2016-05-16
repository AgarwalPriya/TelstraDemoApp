package android.wipro.com.telstra.demoapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
/**
 * Created by priyag on 13-May-16.
 */
public class NetworkUtility {

    private static final String TAG = "NetworkUtility";

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            Log.e(TAG, "check Network Connection");
            return false;
        }
        return true;
    }
}
