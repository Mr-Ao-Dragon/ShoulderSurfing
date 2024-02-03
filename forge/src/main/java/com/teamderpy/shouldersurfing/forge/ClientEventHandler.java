package com.teamderpy.shouldersurfing.forge;

import com.teamderpy.shouldersurfing.client.KeyHandler;
import com.teamderpy.shouldersurfing.client.ShoulderInstance;
import com.teamderpy.shouldersurfing.client.ShoulderRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler
{
	@SubscribeEvent
	public static void clientTickEvent(ClientTickEvent event)
	{
		if(Phase.START.equals(event.phase))
		{
			ShoulderInstance.getInstance().tick();
		}
	}
	
	@SubscribeEvent
	public static void preRenderLivingEntityEvent(RenderLivingEvent.Pre<LivingEntity, ?> event)
	{
		if(event.isCancelable() && Minecraft.getInstance().screen == null && event.getEntity() == Minecraft.getInstance().getCameraEntity() && ShoulderRenderer.getInstance().preRenderCameraEntity(event.getEntity(), event.getPartialRenderTick()))
		{
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void postRenderLivingEntityEvent(RenderLivingEvent.Post<LivingEntity, ?> event)
	{
		if(event.getEntity() == Minecraft.getInstance().getCameraEntity())
		{
			ShoulderRenderer.getInstance().postRenderCameraEntity(event.getEntity(), event.getPartialRenderTick(), event.getBuffers());
		}
	}
	
	@SubscribeEvent
	public static void preRenderGuiOverlayEvent(RenderGameOverlayEvent.Pre event)
	{
		if(event.getType().equals(RenderGameOverlayEvent.ElementType.CROSSHAIRS))
		{
			ShoulderRenderer.getInstance().offsetCrosshair(event.getMatrixStack(), event.getWindow(), event.getPartialTicks());
		}
		//Using BOSS_EVENT_PROGRESS to pop matrix because when CROSSHAIR is cancelled it will not fire RenderGuiOverlayEvent.Post and cause a stack overflow
		else if(event.getType().equals(RenderGameOverlayEvent.ElementType.BOSSHEALTH))
		{
			ShoulderRenderer.getInstance().clearCrosshairOffset(event.getMatrixStack());
		}
	}
	
	@SubscribeEvent
	@SuppressWarnings("resource")
	public static void computeCameraAnglesEvent(CameraSetup event)
	{
		ShoulderRenderer.getInstance().offsetCamera(event.getInfo(), Minecraft.getInstance().level, (float) event.getRenderPartialTicks());
	}
	
	@SubscribeEvent
	@SuppressWarnings("resource")
	public static void renderLevelStageEvent(RenderWorldLastEvent event)
	{
		ShoulderRenderer.getInstance().updateDynamicRaytrace(Minecraft.getInstance().gameRenderer.getMainCamera(), event.getMatrixStack().last().pose(), event.getProjectionMatrix(), event.getPartialTicks());
	}
	
	@SubscribeEvent
	public static void keyInputEvent(InputEvent event)
	{
		KeyHandler.onInput();
	}
}
