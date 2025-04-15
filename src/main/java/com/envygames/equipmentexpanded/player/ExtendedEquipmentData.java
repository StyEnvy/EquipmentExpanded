package com.envygames.equipmentexpanded.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.minecraft.core.RegistryAccess.ImmutableRegistryAccess;
import org.jetbrains.annotations.NotNull;

public class ExtendedEquipmentData extends ItemStackHandler {

    public static final int
            SLOT_RELIC         = 0,
            SLOT_RING_1        = 1,
            SLOT_RING_2        = 2,
            SLOT_AMULET        = 3,
            SLOT_BELT          = 4,
            SLOT_QUIVER        = 5,
            SLOT_ELYTRA        = 6,
            SLOT_BACKPACK      = 7,
            SLOT_MAGNET        = 8,
            SLOT_STORAGE_DEV   = 9,
            SLOT_CRAFTING_DEV  = 10,
            SLOT_SPARE_0       = 11,
            SLOT_SPARE_1       = 12,
            SLOT_SPARE_2       = 13,
            SLOT_SPARE_3       = 14,
            SLOT_SPARE_4       = 15,
            SLOT_SPARE_5       = 16,
            SLOT_SPARE_6       = 17;

    public static final int SIZE = 18;

    public ExtendedEquipmentData() {
        super(SIZE);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return switch (slot) {
            case SLOT_RELIC -> stack.is(EquipmentTags.RELICS);
            case SLOT_RING_1, SLOT_RING_2 -> stack.is(EquipmentTags.RINGS);
            case SLOT_AMULET -> stack.is(EquipmentTags.AMULETS);
            case SLOT_BELT -> stack.is(EquipmentTags.BELTS);
            case SLOT_QUIVER -> stack.is(EquipmentTags.QUIVERS) || stack.is(Items.BOW) || stack.is(Items.CROSSBOW);
            case SLOT_ELYTRA -> stack.is(Items.ELYTRA);
            case SLOT_BACKPACK -> stack.is(EquipmentTags.BACKPACKS);
            case SLOT_MAGNET -> stack.is(EquipmentTags.MAGNET_ITEMS);
            case SLOT_STORAGE_DEV -> stack.is(EquipmentTags.STORAGE_DEVICES);
            case SLOT_CRAFTING_DEV -> stack.is(EquipmentTags.CRAFTING_DEVICES);
            default -> false;
        };
    }

    public ItemStack relic() {
        return getStackInSlot(SLOT_RELIC);
    }

    public void write(FriendlyByteBuf buf) {
        CompoundTag tag = this.serializeNBT(ImmutableRegistryAccess.EMPTY);
        buf.writeNbt(tag);
    }

    public static ExtendedEquipmentData read(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        ExtendedEquipmentData data = new ExtendedEquipmentData();
        if (tag != null) {
            data.deserializeNBT(ImmutableRegistryAccess.EMPTY, tag);
        }
        return data;
    }

    public void copyFrom(ExtendedEquipmentData other) {
        this.deserializeNBT(ImmutableRegistryAccess.EMPTY, other.serializeNBT(ImmutableRegistryAccess.EMPTY));
    }
}
