package com.envygames.equipmentexpanded.player;

import com.envygames.equipmentexpanded.EquipmentExpanded;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.minecraft.world.entity.player.Player;

public final class ExtendedEquipmentDataProvider {

    // Create a DeferredRegister for attachment types.
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, EquipmentExpanded.MOD_ID);

    // Register the attachment type using the serializable builder.
    // The copyOnDeath flag ensures the data is persisted (copied) on player death.
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ExtendedEquipmentData>> EQUIPMENT =
            ATTACHMENT_TYPES.register("extended_equipment", () ->
                    AttachmentType.serializable(ExtendedEquipmentData::new)
                            .copyOnDeath()
                            .build()
            );

    // Eager getter that forces the attachment if it is missing or unbound.
    public static ExtendedEquipmentData getEquipmentData(Player player) {
        try {
            return player.getData(EQUIPMENT);
        } catch (NullPointerException e) {
            EquipmentExpanded.LOG.error("Attachment for ExtendedEquipmentData not bound for player {}. Manually attaching default instance.",
                    player.getName().getString(), e);
            ExtendedEquipmentData fallback = new ExtendedEquipmentData();
            player.setData(EQUIPMENT, fallback);
            return fallback;
        }
    }

    private ExtendedEquipmentDataProvider() {}
}
