package com.envygames.equipmentexpanded.menu;

import com.envygames.equipmentexpanded.player.ExtendedEquipmentData;
import com.envygames.equipmentexpanded.player.ExtendedEquipmentDataProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ExtendedEquipmentMenu extends AbstractContainerMenu {

    public static final MenuType<ExtendedEquipmentMenu> MENU_TYPE =
            new MenuType<>(ExtendedEquipmentMenu::new, FeatureFlags.DEFAULT_FLAGS);

    // Constructor when called via a FriendlyByteBuf.
    public ExtendedEquipmentMenu(int id, Inventory inv, FriendlyByteBuf ignored) {
        this(id, inv);
    }

    // Main constructor used on the server.
    public ExtendedEquipmentMenu(int id, Inventory inv) {
        super(MENU_TYPE, id);

        // Retrieve the persistent equipment data safely.
        ExtendedEquipmentData equipmentData = ExtendedEquipmentDataProvider.getEquipmentData(inv.player);

        // Add custom equipment slots (18 slots arranged in 2 rows).
        int rowX = 8, rowY = 18;
        for (int i = 0; i < 18; ++i) {
            int x = rowX + (i % 9) * 18;
            int y = rowY + (i / 9) * 18;
            addSlot(new ValidatedSlot(equipmentData, i, x, y));
        }

        // Add the player's inventory slots (3×9).
        int invY = rowY + 36 + 4; // 4-pixel gap after equipment rows
        for (int r = 0; r < 3; ++r) {
            for (int c = 0; c < 9; ++c) {
                addSlot(new Slot(inv, c + r * 9 + 9, 8 + c * 18, invY + r * 18));
            }
        }

        // Add the player's hotbar slots.
        for (int c = 0; c < 9; ++c) {
            addSlot(new Slot(inv, c, 8 + c * 18, invY + 58));
        }
    }

    // Internal slot implementation that validates item placement.
    private static final class ValidatedSlot extends SlotItemHandler {
        public ValidatedSlot(net.neoforged.neoforge.items.ItemStackHandler handler, int idx, int x, int y) {
            super(handler, idx, x, y);
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return getItemHandler().isItemValid(getSlotIndex(), stack);
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int idx) {
        Slot slot = getSlot(idx);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        final int EQUIP_START = 0, EQUIP_END = 18;
        final int INV_START = 18, INV_END = 54;

        if (idx < EQUIP_END) { // Moving from equipment to inventory.
            if (!moveItemStackTo(stack, INV_START, INV_END, false)) {
                return ItemStack.EMPTY;
            }
        } else { // Moving from inventory to equipment.
            if (!moveItemStackTo(stack, EQUIP_START, EQUIP_END, false)) {
                return ItemStack.EMPTY;
            }
        }
        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        return copy;
    }
}
