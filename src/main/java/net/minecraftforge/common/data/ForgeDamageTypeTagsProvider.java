package net.minecraftforge.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public final class ForgeDamageTypeTagsProvider extends DamageTypeTagsProvider {

    public ForgeDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, "forge", existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider)
    {
        tag(Tags.DamageTypes.IS_MAGIC).add(DamageTypes.MAGIC, DamageTypes.INDIRECT_MAGIC);
        tag(Tags.DamageTypes.IS_ENVIRONMENT).add(DamageTypes.IN_FIRE, DamageTypes.ON_FIRE, DamageTypes.LAVA, DamageTypes.HOT_FLOOR, DamageTypes.DROWN, DamageTypes.STARVE, DamageTypes.DRY_OUT, DamageTypes.FREEZE, DamageTypes.LIGHTNING_BOLT, DamageTypes.CACTUS, DamageTypes.STALAGMITE, DamageTypes.FALLING_STALACTITE);
        tag(Tags.DamageTypes.IS_PHYSICAL).add(DamageTypes.CACTUS, DamageTypes.FALL, DamageTypes.FLY_INTO_WALL, DamageTypes.SWEET_BERRY_BUSH, DamageTypes.STALAGMITE, DamageTypes.FALLING_BLOCK, DamageTypes.FALLING_ANVIL, DamageTypes.FALLING_STALACTITE, DamageTypes.STING, DamageTypes.MOB_ATTACK, DamageTypes.MOB_ATTACK_NO_AGGRO, DamageTypes.PLAYER_ATTACK, DamageTypes.ARROW, DamageTypes.TRIDENT, DamageTypes.MOB_PROJECTILE, DamageTypes.FIREBALL, DamageTypes.UNATTRIBUTED_FIREBALL, DamageTypes.WITHER_SKULL, DamageTypes.THROWN);
    }
}
