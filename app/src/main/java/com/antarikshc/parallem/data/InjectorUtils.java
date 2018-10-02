package com.antarikshc.parallem.data;

import android.content.Context;

import com.antarikshc.parallem.data.network.NetworkDataSource;
import com.antarikshc.parallem.ui.dashboard.DashboardViewModelFactory;

public class InjectorUtils {

    public static ParallemRepository provideRepository(Context context) {
        NetworkDataSource networkDataSource =
                NetworkDataSource.getInstance(context.getApplicationContext());

        return ParallemRepository.getInstance(networkDataSource);
    }

    public static NetworkDataSource provideNetworkDataSource(Context context) {
        // This call to provide repository is necessary if the app starts from a service
        // In this case repo will not exist unless it is specifically created
        provideRepository(context.getApplicationContext());

        return NetworkDataSource.getInstance(context.getApplicationContext());
    }

    public static DashboardViewModelFactory provideDashboardViewModelFactory(Context context) {
        ParallemRepository repository = provideRepository(context.getApplicationContext());

        return new DashboardViewModelFactory(repository);
    }
}
