package com.envygames.equipmentexpanded;

import com.envygames.equipmentexpanded.client.ClientScreens;
import com.envygames.equipmentexpanded.player.ExtendedEquipmentDataProvider;
import com.envygames.equipmentexpanded.player.PlayerEagerAttachment;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

public final class EquipmentExpanded {
    public static final String MOD_ID = "equipmentexpanded";
    public static final Logger LOG = LogUtils.getLogger();

    public EquipmentExpanded(IEventBus modBus, ModContainer container) {
        // Register the persistent equipment data attachment via NeoForge's data attachment system.
        ExtendedEquipmentDataProvider.ATTACHMENT_TYPES.register(modBus);

        // On the client side, register GUI screens.
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modBus.addListener(ClientScreens::register);
        }

        // Register our player login event listener to force early attachment.
        modBus.addListener(PlayerEagerAttachment::onPlayerLoggedIn);

        modBus.addListener(this::commonSetup);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOG.info("Equipment Expanded initialised");
    }
}
