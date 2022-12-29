package winter.stopwatches;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

@SuppressWarnings("unchecked")
public class StopwatchesPluginTest {
	public static void main(String[] args) throws Exception {
		ExternalPluginManager.loadBuiltin(StopwatchesPlugin.class);
		RuneLite.main(args);
	}
}