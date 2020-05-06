package model.disasters;

import exceptions.DisasterException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Disaster implements Simulatable{
	private int startCycle;
	private Rescuable target;
	private boolean active;
	public Disaster(int startCycle, Rescuable target) {
		this.startCycle = startCycle;
		this.target = target;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getStartCycle() {
		return startCycle;
	}
	public Rescuable getTarget() {
		return target;
	}
	public void strike() throws DisasterException
	{
		
		target.struckBy(this);
		active=true;
	}
	public String toString() {
		String d="";
		String t="";
		if (this instanceof Fire)
			d="DISASTER : Fire";
		if(this instanceof Collapse)
			d="DISASTER : Collapse";
		if(this instanceof GasLeak)
			d="DISASTER : Gasleak";
		if(this instanceof Infection)
			d="DISASTER : Infection";
		if(this instanceof Injury)
			d="DISASTER : Injury";
		if(target instanceof Citizen)
			t="Disaster Targeting : "+((Citizen)target).getName(); 
		if(target instanceof ResidentialBuilding)
			t="Disaster Targeting Building at : ("+target.getLocation().getX()+","+target.getLocation().getY()+")";
		return d+"\n"+t;
		
	}
}
