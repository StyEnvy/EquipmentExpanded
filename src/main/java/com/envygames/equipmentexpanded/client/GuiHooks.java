package com.envygames.equipmentexpanded.client;

import com.envygames.equipmentexpanded.EquipmentExpanded;
import com.envygames.equipmentexpanded.menu.ExtendedEquipmentMenu;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.minecraft.client.Minecraft;

@EventBusSubscriber(modid = EquipmentExpanded.MOD_ID, value = Dist.CLIENT)
public final class GuiHooks {

    @SubscribeEvent
    public static void onGuiInit(ScreenEvent.Init.Post evt) {
        if (evt.getScreen() instanceof InventoryScreen screen) {
            int x = screen.getGuiLeft();
            int y = screen.getGuiTop();
            evt.addListener(
                    Button.builder(Component.literal("EE"),
                                    b -> {
                                        Minecraft mc = Minecraft.getInstance();
                                        if (mc.player != null) {
                                            // Open the equipment menu directly.
                                            mc.setScreen(new ExtendedEquipmentScreen(
                                                    new ExtendedEquipmentMenu(0, mc.player.getInventory()),
                                                    mc.player.getInventory(),
                                                    Component.literal("Equipment Extended")
                                            ));
                                        }
                                    }
                            )
                            .pos(x - 24, y)
                            .size(20, 20)
                            .tooltip(Tooltip.create(Component.literal("Equipment Extended")))
                            .build()
            );
        }
    }
}
