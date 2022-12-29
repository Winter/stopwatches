package winter.stopwatches;

import java.awt.Dimension;
import java.awt.Graphics2D;
import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class StopwatchesOverlay extends OverlayPanel {
    public final StopwatchesPlugin plugin;
    public final StopwatchManager stopwatchManager;
    public final Client client;

    public TitleComponent titleComponent;

    public boolean loggedIn = false;


    @Inject
    private StopwatchesOverlay(StopwatchesPlugin plugin, final Client client, final StopwatchManager stopwatchManager) {
        super(plugin);
        
        this.plugin = plugin;
        this.stopwatchManager = stopwatchManager;
        this.client = client;

        setPosition(OverlayPosition.BOTTOM_LEFT);
		setLayer(OverlayLayer.ABOVE_WIDGETS);

        titleComponent = TitleComponent.builder()
            .text("Stopwatches")
            .build();
    }

    @Override
	public Dimension render(final Graphics2D graphics) {
        graphics.setFont(FontManager.getRunescapeFont());

        panelComponent.getChildren()
            .add(titleComponent);

        stopwatchManager.stopwatches.forEach(
            (key, value) -> panelComponent
                .getChildren()
                    .add(LineComponent.builder()
                        .left(key)
                        .right(value.GetElapsed())
                        .build())
        );

        return super.render(graphics);
    }
}
