package winter.stopwatches;

import java.util.HashMap;
import javax.inject.Singleton;

@Singleton
public class StopwatchManager {
    public final HashMap<String, Stopwatch> stopwatches = new HashMap<String, Stopwatch>();

    public boolean containsStopwatch(String name) {
        return stopwatches.containsKey(name);
    }

    public boolean addStopwatch(String name) {
        if (containsStopwatch(name)) return false;

        stopwatches.put(name, new Stopwatch());

        return true;
    }

    public boolean removeStopwatch(String name) {
        if (!containsStopwatch(name)) return false;

        stopwatches.remove(name);

        return true;
    }

    public Stopwatch getStopwatch(String name) {
        if (!containsStopwatch(name)) return null;

        return stopwatches.get(name);
    }

    public void startStopwatch(String name) {
        Stopwatch stopwatch = getStopwatch(name);

        if (stopwatch == null) return;

        stopwatch.Start();
    }

    public void pauseStopwatch(String name) {
        Stopwatch stopwatch = getStopwatch(name);

        if (stopwatch == null) return;

        stopwatch.Pause();
    }

    public void resetStopwatch(String name) {
        Stopwatch stopwatch = getStopwatch(name);

        if (stopwatch == null) return;

        stopwatch.Reset();
    }

    public void hideStopwatch(String name) {
        Stopwatch stopwatch = getStopwatch(name);

        if (stopwatch == null) return;

        stopwatch.Hidden = true;
    }

    public void showStopwatch(String name) {
        Stopwatch stopwatch = getStopwatch(name);

        if (stopwatch == null) return;

        stopwatch.Hidden = false;
    }
}
