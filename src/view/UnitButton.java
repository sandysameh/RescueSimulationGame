package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;

public class UnitButton extends JButton {
	
public UnitButton(String id) {
	switch(id) {
	case "Ambulance":{
		this.setIcon(new ImageIcon("amb.png"));

	}
	break;
	case "DiseaseControlUnit": {
	
		this.setIcon(new ImageIcon("disCont.png"));
	}
		break;
	case "Evacuator": {
		this.setIcon(new ImageIcon("evc.png"));
	}
		break;
	case "FireTruck": {
		this.setIcon(new ImageIcon("ftk.png"));
	} 
		break;
	case "GasControlUnit": {
		this.setIcon(new ImageIcon("gcu.png"));
	}
		break;
		default: this.setText("unit");
	}
}
}
