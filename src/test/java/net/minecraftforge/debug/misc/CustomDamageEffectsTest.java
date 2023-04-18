package net.minecraftforge.debug.misc;

import com.mojang.serialization.JsonOps;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
import java.util.Map;

@Mod(CustomDamageEffectsTest.MOD_ID)
public class CustomDamageEffectsTest {
    private static final boolean ENABLED = true;
    public static final String MOD_ID = "custom_damage_effects_test";

    private static final DamageEffects CUSTOM_DAMAGE_EFFECTS = DamageEffects.create(MOD_ID + "_CUSTOM", new ResourceLocation(MOD_ID, "custom"), source -> SoundEvents.ALLAY_DEATH);
    static final ResourceKey<DamageType> CUSTOM_DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(MOD_ID, "custom"));

    public CustomDamageEffectsTest() {
        if (ENABLED) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::datagen);
            MinecraftForge.EVENT_BUS.addListener(this::onPlayerHurt);
        }
    }

    private void datagen(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        gen.addProvider(event.includeServer(), new CustomDamageTypeProvider(gen.getPackOutput(), event.getExistingFileHelper()));
    }

    private void onPlayerHurt(final LivingAttackEvent event) {
        if (event.getEntity() instanceof Player && !event.getSource().is(CUSTOM_DAMAGE_TYPE)) {
            event.setCanceled(true);
            event.getEntity().hurt(new DamageSource(event.getEntity().level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(CUSTOM_DAMAGE_TYPE)), 1f);
        }
    }

    private class CustomDamageTypeProvider extends JsonCodecProvider<DamageType> {

        public CustomDamageTypeProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
            super(output, existingFileHelper, MOD_ID, JsonOps.INSTANCE, PackType.SERVER_DATA, "damage_type", DamageType.CODEC, createMap());
        }

        private static Map<ResourceLocation, DamageType> createMap() {
            return Util.make(new HashMap<>(), map -> {
               map.put(new ResourceLocation(MOD_ID, "custom_damage_type"), new DamageType("test_damage_type_1", DamageScaling.ALWAYS, 1f, CUSTOM_DAMAGE_EFFECTS, DeathMessageType.DEFAULT));
            });
        }
    }
}
