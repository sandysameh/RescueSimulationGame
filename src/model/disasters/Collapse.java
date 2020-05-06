package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.DisasterException;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;


public class Collapse extends Disaster {

	public Collapse(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
		
	}
	public void strike() throws DisasterException
	{
		if( ( (ResidentialBuilding)this.getTarget() ).getStructuralIntegrity()== 0) {
			throw new BuildingAlreadyCollapsedException(this, "The building already collapsed");
		}
		ResidentialBuilding target= (ResidentialBuilding)getTarget();	
		target.setFoundationDamage(target.getFoundationDamage()+10);
		super.strike();
	}
	public void cycleStep()
	{
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setFoundationDamage(target.getFoundationDamage()+10);
	}

}
