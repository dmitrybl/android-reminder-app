package reminder.application.belyaev.dmitry.ru.reminder;

import android.app.Application;

/**
 * Created by dmitrybelyaev on 17.10.2018.
 */

public class MyApplication extends Application {

    private static boolean activityVisible;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }


}
