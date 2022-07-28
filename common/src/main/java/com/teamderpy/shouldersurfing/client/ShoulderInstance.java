package com.teamderpy.shouldersurfing.client;

import com.teamderpy.shouldersurfing.config.Config;
import com.teamderpy.shouldersurfing.config.Perspective;

import net.minecraft.client.Minecraft;

public class ShoulderInstance
{
	private static ShoulderInstance instance = new ShoulderInstance();
	private boolean doShoulderSurfing;
	private boolean doSwitchPerspective;
	private boolean isAiming;
	
	private ShoulderInstance()
	{
		instance = this;
	}
	
	public void tick()
	{
		if(!Perspective.FIRST_PERSON.equals(Perspective.current()))
		{
			this.doSwitchPerspective = false;
		}
		
		this.isAiming = ShoulderHelper.isHoldingSpecialItem();
		
		if(this.isAiming && Config.CLIENT.getCrosshairType().doSwitchPerspective() && this.doShoulderSurfing)
		{
			this.changePerspective(Perspective.FIRST_PERSON);
			this.doSwitchPerspective = true;
		}
		else if(!this.isAiming && Perspective.FIRST_PERSON.equals(Perspective.current()) && this.doSwitchPerspective)
		{
			this.changePerspective(Perspective.SHOULDER_SURFING);
		}
	}
	
	@SuppressWarnings("resource")
	public void changePerspective(Perspective perspective)
	{
		Minecraft.getInstance().options.setCameraType(perspective.getCameraType());
		this.doShoulderSurfing = Perspective.SHOULDER_SURFING.equals(perspective);
	}
	
	public boolean doShoulderSurfing()
	{
		return this.doShoulderSurfing;
	}
	
	public void setShoulderSurfing(boolean doShoulderSurfing)
	{
		this.doShoulderSurfing = doShoulderSurfing;
	}
	
	public boolean isAiming()
	{
		return this.isAiming;
	}
	
	public static ShoulderInstance getInstance()
	{
		return instance;
	}
}