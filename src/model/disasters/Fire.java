package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.DisasterException;
import model.infrastructure.ResidentialBuilding;


public class Fire extends Disaster {

	public Fire(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
		
	}
	@Override
	public void strike()throws DisasterException
	{
		if( ( (ResidentialBuilding)this.getTarget() ).getStructuralIntegrity()== 0) {
			throw new BuildingAlreadyCollapsedException(this, "The building already collapsed");
		}
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setFireDamage(target.getFireDamage()+10);
		super.strike();
	}

	@Override
	public void cycleStep() {
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setFireDamage(target.getFireDamage()+10);
		
	}

}
