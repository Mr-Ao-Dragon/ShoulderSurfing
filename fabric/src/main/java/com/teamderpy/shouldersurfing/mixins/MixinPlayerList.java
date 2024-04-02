package com.teamderpy.shouldersurfing.mixins;

import com.teamderpy.shouldersurfing.client.ShoulderInstance;
import com.teamderpy.shouldersurfing.client.ShoulderRenderer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerList.class)
public abstract class MixinPlayerList
{
	@Inject
	(
		method = "respawn",
		at = @At("TAIL")
	)
	private void respawn(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> cir)
	{
		ShoulderRenderer.getInstance().resetCameraRotations(cir.getReturnValue());
		ShoulderInstance.getInstance().resetCameraEntityRotations(cir.getReturnValue());
	}
}