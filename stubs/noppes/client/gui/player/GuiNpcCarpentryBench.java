package noppes.client.gui.player;

import noppes.client.gui.util.GuiContainerNPCInterface;
import noppes.common.containers.ContainerCarpentryBench;

public class GuiNpcCarpentryBench extends GuiContainerNPCInterface {
    public GuiNpcCarpentryBench(ContainerCarpentryBench container) { super(container); }
    public void save() {}
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {}
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        super.drawScreen(mouseX, mouseY, partialTick);
    }
}
