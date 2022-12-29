package winter.stopwatches;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("stopwatches")
public interface StopwatchesConfig extends Config {
	@ConfigItem(
		keyName = "enableStopwatches",
		name = "Enable Stopwatches",
		description = "Whether to enable the Stopwatches plugin"
	)
	default boolean enableStopwatches() {
		return true;
	}
}
