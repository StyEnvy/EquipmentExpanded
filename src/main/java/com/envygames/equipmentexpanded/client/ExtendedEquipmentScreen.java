package com.envygames.equipmentexpanded.client;

import com.envygames.equipmentexpanded.menu.ExtendedEquipmentMenu;
import com.envygames.equipmentexpanded.player.ExtendedEquipmentData;
import com.envygames.equipmentexpanded.player.ExtendedEquipmentDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class ExtendedEquipmentScreen extends AbstractContainerScreen<ExtendedEquipmentMenu> {

    private static final ResourceLocation SLOT_SPRITE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "container/slot");

    private static final int EQUIP_TOP_Y = 18;   // first equipment row Y

    public ExtendedEquipmentScreen(ExtendedEquipmentMenu menu, Inventory inv, Component ignored) {
        super(menu, inv, Component.translatable("Equipment Extended"));
        imageWidth  = 176;
        imageHeight = 144;
        inventoryLabelY = -1000;   // hide vanilla label
    }

    @Override
    protected void init() {
        super.init();
        leftPos = (width  - imageWidth)  / 2;
        topPos  = (height - imageHeight) / 2;
        addRenderableWidget(
                Button.builder(Component.literal("⮐ Inv"),
                                b -> {
                                    Minecraft mc = Minecraft.getInstance();
                                    if (mc.player != null)
                                        mc.setScreen(new InventoryScreen(mc.player));
                                })
                        .pos(leftPos - 28, topPos - 2)
                        .size(28, 18)
                        .tooltip(Tooltip.create(Component.literal("Back to Inventory")))
                        .build()
        );
    }

    @Override
    protected void renderBg(GuiGraphics g, float pt, int mouseX, int mouseY) {
        // Panel background.
        g.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xFFCCCCCC);

        // Slot outlines for custom equipment slots.
        int baseX = leftPos + 7;
        int baseY = topPos  + EQUIP_TOP_Y - 1;   // 1-pixel alignment tweak
        for (int i = 0; i < 18; ++i) {
            int x = baseX + (i % 9) * 18;
            int y = baseY + (i / 9) * 18;
            g.blitSprite(SLOT_SPRITE, x, y, 18, 18);
        }

        // Slot outlines for player inventory.
        int invY = baseY + 36 + 4;
        for (int r = 0; r < 3; ++r) {
            for (int c = 0; c < 9; ++c) {
                g.blitSprite(SLOT_SPRITE, baseX + c * 18, invY + r * 18, 18, 18);
            }
        }
        // Hotbar outlines.
        int barY = invY + 58;
        for (int c = 0; c < 9; ++c) {
            g.blitSprite(SLOT_SPRITE, baseX + c * 18, barY, 18, 18);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics g, int mouseX, int mouseY, float pt) {
        super.render(g, mouseX, mouseY, pt);

        // Show a custom tooltip when hovering an empty equipment slot.
        Slot slot = getSlotUnderMouse();
        if (slot == null || slot.hasItem()) return;

        var player = Minecraft.getInstance().player;
        ExtendedEquipmentData data = player != null
                ? ExtendedEquipmentDataProvider.getEquipmentData(player)
                : null;

        Component tip = switch (slot.getSlotIndex()) {
            case ExtendedEquipmentData.SLOT_RELIC        -> Component.literal("Relic slot");
            case ExtendedEquipmentData.SLOT_RING_1,
                 ExtendedEquipmentData.SLOT_RING_2       -> Component.literal("Ring slot");
            case ExtendedEquipmentData.SLOT_AMULET       -> Component.literal("Amulet slot");
            case ExtendedEquipmentData.SLOT_BELT         -> Component.literal("Belt slot");
            case ExtendedEquipmentData.SLOT_QUIVER       -> Component.literal("Quiver slot");
            case ExtendedEquipmentData.SLOT_ELYTRA       -> Component.literal("Elytra slot");
            case ExtendedEquipmentData.SLOT_BACKPACK     -> Component.literal("Backpack slot");
            case ExtendedEquipmentData.SLOT_MAGNET       -> Component.literal("Magnet slot");
            case ExtendedEquipmentData.SLOT_STORAGE_DEV  -> Component.literal("Storage-device slot");
            case ExtendedEquipmentData.SLOT_CRAFTING_DEV -> Component.literal("Crafting-device slot");
            default -> null;
        };

        if (tip != null) {
            g.renderTooltip(font, tip, mouseX, mouseY);
        }
    }
}
