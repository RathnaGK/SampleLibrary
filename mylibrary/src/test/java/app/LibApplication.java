package app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class LibApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("Location Realm")
                .schemaVersion(0)
                //.deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);


    }
}
