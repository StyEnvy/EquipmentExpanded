package com.envygames.equipmentexpanded.player;

import com.envygames.equipmentexpanded.EquipmentExpanded;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.world.entity.player.Player;

public final class PlayerEagerAttachment {

    // This method is called when a player logs in.
    // It forces the equipment data attachment by calling getData.
    public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        ExtendedEquipmentDataProvider.getEquipmentData(player);
        EquipmentExpanded.LOG.info("Eager attachment of ExtendedEquipmentData completed for player {}",
                player.getName().getString());
    }
}
