package com.sh1nylabs.bonesupdate.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.sh1nylabs.bonesupdate.init.BonesEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.UUID;

public class HaunterSpearItem extends Item {
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    protected static final UUID BASE_KNOCKBACK_UUID = UUID.fromString("00000037-73D4-4F60-4A7C-BF5CF930186D");

    public HaunterSpearItem(Properties properties) {
        super(properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 5.0D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -1.4D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(BASE_KNOCKBACK_UUID, "Tool modifier", 3.0D, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    /**
     *
     * @param stack the item stack
     * @param target enemy hurt
     * @param user entity attacking
     * @return true: when used, this item hurt the enemy.
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity user) {
        stack.hurtAndBreak(1, user, (entity)->{entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);});
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getAttributeModifiers(slot,stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    /**
     * Enchantments applicable on the Necromancer scepter:
     * Category "WEAPON": "Looting"
     * Category "BREAKABLE": "Mending", "Unbreaking"
     *
     * For enchantment compatibility, check enchantments classes.
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)  {
        return enchantment.category == EnchantmentCategory.WEAPON
                || enchantment.category == EnchantmentCategory.BREAKABLE;
    }
}
