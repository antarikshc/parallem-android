package com.antarikshc.parallem.data;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.antarikshc.parallem.data.network.NetworkDataSource;
import com.antarikshc.parallem.ui.authentication.AddProfileViewModelFactory;
import com.antarikshc.parallem.ui.dashboard.DashboardViewModelFactory;
import com.antarikshc.parallem.util.VolleySingleton;

public class InjectorUtils {

    public static ParallemRepository provideRepository(Context context) {
        // Get Singleton Volley Request Queue Instance
        RequestQueue requestQueue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();

        NetworkDataSource networkDataSource =
                NetworkDataSource.getInstance(context.getApplicationContext(), requestQueue);

        return ParallemRepository.getInstance(networkDataSource);
    }

    public static NetworkDataSource provideNetworkDataSource(Context context) {
        // This call to provide repository is necessary if the app starts from a service
        // In this case repo will not exist unless it is specifically created
        provideRepository(context.getApplicationContext());

        // Get Singleton Volley Request Queue Instance
        RequestQueue requestQueue = VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue();

        return NetworkDataSource.getInstance(context.getApplicationContext(), requestQueue);
    }

    public static DashboardViewModelFactory provideDashboardViewModelFactory(Context context) {
        ParallemRepository repository = provideRepository(context.getApplicationContext());

        return new DashboardViewModelFactory(repository);
    }

    public static AddProfileViewModelFactory provideAddProfileViewModelFactory(Context context) {
        ParallemRepository repository = provideRepository(context.getApplicationContext());

        return new AddProfileViewModelFactory(repository);
    }
}
