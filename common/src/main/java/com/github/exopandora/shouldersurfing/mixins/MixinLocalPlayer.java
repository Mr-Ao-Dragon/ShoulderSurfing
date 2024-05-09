package com.github.exopandora.shouldersurfing.mixins;

import com.github.exopandora.shouldersurfing.client.ShoulderRenderer;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LocalPlayer.class, priority = 500 /* apply before essential client */)
public abstract class MixinLocalPlayer extends AbstractClientPlayer
{
	public MixinLocalPlayer(ClientLevel level, GameProfile gameProfile, @Nullable ProfilePublicKey profilePublicKey)
	{
		super(level, gameProfile, profilePublicKey);
	}
	
	@Override
	public void turn(double yRot, double xRot)
	{
		if(!ShoulderRenderer.getInstance().turn(this, yRot, xRot))
		{
			super.turn(yRot, xRot);
		}
	}
}
