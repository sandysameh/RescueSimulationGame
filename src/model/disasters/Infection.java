package model.disasters;

import exceptions.CitizenAlreadyDeadException;
import exceptions.DisasterException;
import model.people.Citizen;
import model.people.CitizenState;


public class Infection extends Disaster {

	public Infection(int startCycle, Citizen target) {
		super(startCycle, target);
	}
@Override
public void strike() throws DisasterException
{
	if( ( (Citizen)this.getTarget() ).getState() == CitizenState.DECEASED ) {
		throw new CitizenAlreadyDeadException(this, "The citizen is already dead");
	}

	Citizen target = (Citizen)getTarget();
	target.setToxicity(target.getToxicity()+25);
	super.strike();
}
	@Override
	public void cycleStep() {
		Citizen target = (Citizen)getTarget();
		target.setToxicity(target.getToxicity()+15);
		
	}

}
