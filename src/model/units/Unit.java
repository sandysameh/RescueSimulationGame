package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable, SOSResponder {
	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;

	public Unit(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		this.unitID = unitID;
		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		this.state = UnitState.IDLE;
		this.worldListener = worldListener;
	}

	public void setWorldListener(WorldListener listener) {
		this.worldListener = listener;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public UnitState getState() {
		return state;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
	}

	@Override
	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException{
		
		if(r instanceof Citizen && !(this instanceof MedicalUnit))
			throw new IncompatibleTargetException(this, r, "the target is not compatible with the current unit ");//fix
		if (r instanceof ResidentialBuilding && this instanceof MedicalUnit)
			throw new IncompatibleTargetException(this, r, "the target is not compatible with the current unit ");
		if(!(canTreat(r))){
			if(r instanceof ResidentialBuilding)
				throw new CannotTreatException(this, r, "There is no "+this.whatItTreats()+" on this building");
			if(r instanceof Citizen)
				throw new CannotTreatException(this, r, "There is no "+this.whatItTreats()+" on this citizen");
		}

		if (target != null && state == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);

	}
	public String whatItTreats() {
		String d="";
		if(this instanceof Evacuator) 
			d="Collapse";
		if(this instanceof FireTruck)
			d="Fire";
		if(this instanceof Ambulance)
			d="Injury";
		if(this instanceof DiseaseControlUnit )
			d="Infection";
		if(this instanceof GasControlUnit)
			d="Gas Leak";
		return d;
	}
	
//public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException{
//		
//		if(r instanceof Citizen && !(this instanceof MedicalUnit))
//			throw new IncompatibleTargetException(this, r, "the target is not compatible with the current unit ");//fix
//		if (r instanceof ResidentialBuilding && this instanceof MedicalUnit)
//			throw new IncompatibleTargetException(this, r, "the target is not compatible with the current unit ");
//		if(!possible(r)) {
//			throw new CannotTreatException(this, r,"you are sending the wrong unit");
//		}
//
//		if(!(canTreat(r))){
//			throw new CannotTreatException(this, r, "you are sending the wrong unit");
//		}
//		
//		else if (target != null && state == UnitState.TREATING)
//			reactivateDisaster();
//		finishRespond(r);
//
//	}
//	public boolean possible(Rescuable r) {
//		if(r instanceof Citizen && this instanceof Ambulance && r.getDisaster() instanceof Injury) {
//			
//				return true;
//		}
//		if(r instanceof Citizen && this instanceof DiseaseControlUnit && r.getDisaster() instanceof Infection) {
//			
//				return true;
//		}
//		
//		if(r instanceof ResidentialBuilding) {
//			if(this instanceof FireTruck && r.getDisaster() instanceof Fire) {
//				
//					return true;
//				
//			}
//			if(this instanceof GasControlUnit && r.getDisaster() instanceof GasLeak) {
//				return true;
//				}
//			
//			if(this instanceof Evacuator && r.getDisaster() instanceof Collapse) {
//			
//					return true;
//				
//			}
//		}
//		return false;
//	}

	public void reactivateDisaster() {
		Disaster curr = target.getDisaster();
		curr.setActive(true);
	}

	public void finishRespond(Rescuable r) {
		target = r;
		state = UnitState.RESPONDING;
		Address t = r.getLocation();
		distanceToTarget = Math.abs(t.getX() - location.getX())
				+ Math.abs(t.getY() - location.getY());

	}

	public abstract void treat();

	public void cycleStep() {
		if (state == UnitState.IDLE)
			return;
		if (distanceToTarget > 0) {
			distanceToTarget = distanceToTarget - stepsPerCycle;
			if (distanceToTarget <= 0) {
				distanceToTarget = 0;
				Address t = target.getLocation();
				worldListener.assignAddress(this, t.getX(), t.getY());
			}
		} else {
			state = UnitState.TREATING;
			treat();
		}
	}

	public void jobsDone() {
		target = null;
		state = UnitState.IDLE;

	}
	
	public boolean canTreat(Rescuable r) {
		if(r instanceof Citizen && this instanceof Ambulance) {
			Citizen c = ((Citizen)r);
			if(c.getBloodLoss()!=0)
				return true;
		}
		if(r instanceof Citizen && this instanceof DiseaseControlUnit) {
			Citizen c = ((Citizen)r);
			if(c.getToxicity()!=0)
				return true;
		}
		
		if(r instanceof ResidentialBuilding) {
			if(this instanceof FireTruck) {
				ResidentialBuilding b = (ResidentialBuilding) r;
				if(b.getFireDamage()!=0) {
					return true;
				}
			}
			if(this instanceof GasControlUnit) {
				ResidentialBuilding b = (ResidentialBuilding) r;
				if(b.getGasLevel()!=0) {
					return true;
				}
			}
			if(this instanceof Evacuator) {
				ResidentialBuilding b = (ResidentialBuilding) r;
				if(b.getFoundationDamage()!=0) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String getType() {
		String type="";
		if(this instanceof Ambulance) {
			type="Ambulance";
		}
		if(this instanceof DiseaseControlUnit) {
			type="DiseaseControlUnit";
		}
		if(this instanceof Evacuator) {
			type="Evacuator";
		}
		if(this instanceof FireTruck) {
			type="FireTruck";
		}
		if(this instanceof GasControlUnit) {
			type="GasControlUnit";
		}
		return type;
	}
	
	public String toString() {
		String type="";
		int p=0;
		String pa="";
		if(this instanceof Ambulance) {
			type="Ambulance";
		}
		if(this instanceof DiseaseControlUnit) {
			type="DiseaseControlUnit";
		}
		if(this instanceof Evacuator) {
			type="Evacuator";
			p=((Evacuator)this).getPassengers().size();
			pa="";
			for(int i=0;i<p;i++) {
				pa+=((Evacuator)this).getPassengers().get(i).toString();
			}
		}
		if(this instanceof FireTruck) {
			type="FireTruck";
		}
		if(this instanceof GasControlUnit) {
			type="GasControlUnit";
		}
	
		
		
		String location="Location = ("+this.getLocation().getX()+","+this.getLocation().getY()+")";
		int stps=this.getStepsPerCycle();
		String info="";
		if (target ==null)
			info="NO TARGET";
		if(target instanceof Citizen) {
			info=((Citizen)target).getName()+" "+"("+((Citizen)target).getLocation().getX()+","+((Citizen)target).getLocation().getY()+")";
		}
		if(target instanceof ResidentialBuilding) {
			info="("+((ResidentialBuilding)target).getLocation().getX()+","+((ResidentialBuilding)target).getLocation().getY()+")";
		}
		
	  if(this instanceof Evacuator) {
		  return "UnitID :"+this.unitID +"\n"+"type :"+type +"\n"+"Location: "+location +"\n"+"StepsPerCycle: "+stps +"\n"+"Target: "+info +"\n"+"UnitState "+getState()+"\n"+"number of passengers: "+p+"\n"+"Passengers:"+"\n"+pa;

	  } 
		return "UnitID :"+this.unitID +"\n"+"type :"+type +"\n"+"Location: "+location +"\n"+"StepsPerCycle: "+stps +"\n"+"Target: "+info +"\n"+"UnitState "+getState();
				
	}
}
