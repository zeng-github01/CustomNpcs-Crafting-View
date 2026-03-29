package noppes.client.gui.util;

import net.minecraft.inventory.Container;
import net.minecraft.client.gui.inventory.GuiContainer;

public abstract class GuiContainerNPCInterface extends GuiContainer {
    public GuiContainerNPCInterface(Container container) { super(container); }
    public abstract void save();
}
