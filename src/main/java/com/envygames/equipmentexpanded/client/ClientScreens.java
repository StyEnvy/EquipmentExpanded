package com.envygames.equipmentexpanded.client;

import com.envygames.equipmentexpanded.menu.ExtendedEquipmentMenu;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

/** Registers the screen factory for ExtendedEquipmentMenu (client side only). */
public final class ClientScreens {
    public static void register(RegisterMenuScreensEvent evt) {
        evt.register(ExtendedEquipmentMenu.MENU_TYPE, ExtendedEquipmentScreen::new);
    }
    private ClientScreens() {}
}
