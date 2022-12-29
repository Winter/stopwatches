package winter.stopwatches;

public class Stopwatch {
    public boolean Hidden;
    public boolean Paused = true;
    public long Elapsed;
    public long LastElapsed;
    public long StartTime;

    public String GetElapsed() {
        if (Elapsed == 0 && StartTime == 0) {
            return "00:00:00";
        }
        
        if (Paused) {
            return String.format("%02d:%02d:%02d", Elapsed / 3600000, (Elapsed / 60000) % 60, (Elapsed / 1000) % 60);
        }

        long elapsed = Elapsed + System.currentTimeMillis() - StartTime;
        
        LastElapsed = System.currentTimeMillis();

        return String.format("%02d:%02d:%02d", elapsed / 3600000, (elapsed / 60000) % 60, (elapsed / 1000) % 60);
    }

    public void Start() {
        StartTime = System.currentTimeMillis();
        Paused = false;
    }

    public void Pause() {
        Paused = true;
        Elapsed += System.currentTimeMillis() - StartTime;
    }

    public void Reset() {
        StartTime = 0;
        Elapsed = 0;
        Paused = true;
    }
}
