package winter.stopwatches;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuOpened;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.chatbox.ChatboxPanelManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Stopwatches"
)
public class StopwatchesPlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject 
	private StopwatchManager stopwatchManager;

	@Inject 
	private StopwatchesOverlay stopwatchesOverlay;

	@Inject 
	private ChatboxPanelManager chatboxPanelManager;

	@Override
	protected void startUp() throws Exception {
		overlayManager.add(stopwatchesOverlay);
	}

	@Override
	protected void shutDown() throws Exception {
		overlayManager.remove(stopwatchesOverlay);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged) {
		GameState gameState = gameStateChanged.getGameState();

		if (gameState == GameState.LOGGED_IN || gameState == GameState.LOGIN_SCREEN) {
			stopwatchesOverlay.loggedIn = gameState == GameState.LOGGED_IN;
		}
	}

	@Subscribe
    public void onMenuOpened(MenuOpened event) {
		client.createMenuEntry(1)
			.setOption("Add")
            .setTarget("<col=ff9040>Stopwatch</col>")
            .setType(MenuAction.RUNELITE)
			.onClick(e -> { 
				chatboxPanelManager.openTextInput("Stopwatch Name")
					.onDone(name -> { stopwatchManager.addStopwatch(name); })
					.build();
			});

		MenuEntry removeMenuEntry = client.createMenuEntry(1)
            .setOption("Remove")
            .setTarget("<col=ff9040>Stopwatch</col>")
            .setType(MenuAction.RUNELITE_SUBMENU);

        MenuEntry startMenuEntry = client.createMenuEntry(1)
            .setOption("Start")
            .setTarget("<col=ff9040>Stopwatch</col>")
            .setType(MenuAction.RUNELITE_SUBMENU);

		MenuEntry pauseMenuEntry = client.createMenuEntry(1)
            .setOption("Pause")
            .setTarget("<col=ff9040>Stopwatch</col>")
            .setType(MenuAction.RUNELITE_SUBMENU);

		MenuEntry resetMenuEntry = client.createMenuEntry(1)
            .setOption("Reset")
            .setTarget("<col=ff9040>Stopwatch</col>")
            .setType(MenuAction.RUNELITE_SUBMENU);

		stopwatchManager.stopwatches.forEach(
			(key, value) -> {
				client.createMenuEntry(1)
					.setOption(key)
					.setTarget("<col=ff9040>Stopwatch</col>")
					.setType(MenuAction.RUNELITE)
					.setParent(removeMenuEntry)
					.onClick((e) -> stopwatchManager.removeStopwatch(key));

				if (value.Paused) {
					client.createMenuEntry(1)
						.setOption(key)
						.setTarget("<col=ff9040>Stopwatch</col>")
						.setType(MenuAction.RUNELITE)
						.setParent(startMenuEntry)
						.onClick((e) -> value.Start());
				}
				else {
					client.createMenuEntry(1)
						.setOption(key)
						.setTarget("<col=ff9040>Stopwatch</col>")
						.setType(MenuAction.RUNELITE)
						.setParent(pauseMenuEntry)
						.onClick((e) -> value.Pause());
				}

				if (value.StartTime > 0 || value.Elapsed > 0) {
					client.createMenuEntry(1)
						.setOption(key)
						.setTarget("<col=ff9040>Stopwatch</col>")
						.setType(MenuAction.RUNELITE)
						.setParent(resetMenuEntry)
						.onClick((e) -> value.Reset());
				}
			});
    }

	@Provides
	StopwatchesConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(StopwatchesConfig.class);
	}
}
