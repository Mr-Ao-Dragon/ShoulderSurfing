package com.teamderpy.shouldersurfing.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.teamderpy.shouldersurfing.client.ShoulderRenderer;

import net.minecraft.client.renderer.model.ModelRenderer;

@Mixin(value = ModelRenderer.class, priority = 500 /* apply before sodium and iris */)
public class MixinModelRenderer
{
	@ModifyVariable
	(
		at = @At("HEAD"),
		method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lcom/mojang/blaze3d/vertex/IVertexBuilder;IIFFFF)V",
		index = 8,
		argsOnly = true
	)
	public float render(float alpha)
	{
		return Math.min(alpha, ShoulderRenderer.getInstance().getCameraEntityAlpha());
	}
}