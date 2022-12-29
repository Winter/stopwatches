package winter.stopwatches;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "Stopwatches"
)
public class StopwatchesPlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private StopwatchesConfig config;

	@Provides
	StopwatchesConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(StopwatchesConfig.class);
	}
}
