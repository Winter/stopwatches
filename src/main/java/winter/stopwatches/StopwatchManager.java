package winter.stopwatches;

import java.util.HashMap;
import javax.inject.Singleton;
import net.runelite.client.config.ConfigManager;

@Singleton
public class StopwatchManager {
    public HashMap<String, Stopwatch> stopwatches = new HashMap<String, Stopwatch>();

    public void loadStopwatches(ConfigManager configManager) {
        configManager.getConfigurationKeys("stopwatches")
            .forEach(wholeKeyName -> {

                if (wholeKeyName.startsWith("stopwatches.stopwatches_stopwatch_elapsed_")) {
                    String key = wholeKeyName.substring(42);
                    Long elapsed = configManager.getConfiguration("stopwatches", "stopwatches_stopwatch_elapsed_" + key, Long.class);

                    if (containsStopwatch(key) || addStopwatch(key)) {
                        setStopwatchElapsed(key, elapsed);
                    }
                }
                else if (wholeKeyName.startsWith("stopwatches.stopwatches_stopwatch_hidden_")) {
                    String key = wholeKeyName.substring(41);
                    Boolean hidden = configManager.getConfiguration("stopwatches", "stopwatches_stopwatch_hidden_" + key, Boolean.class);

                    if (containsStopwatch(key) || addStopwatch(key)) {
                        setStopwatchHidden(wholeKeyName, hidden);
                    }
                }
            });
    }

    public void saveStopwatches(ConfigManager configManager) {
        stopwatches.forEach((key, value) -> {
            configManager.setConfiguration("stopwatches", "stopwatches_stopwatch_elapsed_" + key, (value.Elapsed + (!value.Paused ? System.currentTimeMillis() - value.StartTime : 0)));
            configManager.setConfiguration("stopwatches", "stopwatches_stopwatch_hidden_" + key, value.Hidden);
        });
    }

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

    public void setStopwatchElapsed(String name, Long elapsed) {
        Stopwatch stopwatch = getStopwatch(name);

        if (stopwatch == null) return;

        stopwatch.Elapsed = elapsed;
    }

    public void setStopwatchHidden(String name, Boolean hidden) {
        Stopwatch stopwatch = getStopwatch(name);

        if (stopwatch == null) return;

        stopwatch.Hidden = hidden;
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
