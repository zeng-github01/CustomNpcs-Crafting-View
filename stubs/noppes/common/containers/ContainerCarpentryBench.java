package noppes.common.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Container;

public class ContainerCarpentryBench extends Container {
    public IInventory craftMatrix;

    public boolean canInteractWith(net.minecraft.entity.player.EntityPlayer player) { return true; }
}
