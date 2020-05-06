package view;

import java.awt.*;
import java.util.Random;

import javax.swing.*;

public class RescuePanel extends JPanel {
	JTabbedPane[][] panelHolder;
	
	public RescuePanel() {
		this.setLayout(new GridLayout(10, 10));
		panelHolder = new JTabbedPane[10][10]; 
		
		for(int i = 0; i < 10; i++) {
			   for(int j = 0; j < 10; j++) {
				  
				      panelHolder[i][j] = new JTabbedPane();
				    //  panelHolder[i][j].setLayout(new BorderLayout());
				   
				   //   Random rand = new Random();
				   //   panelHolder[i][j].setBackground(new Color(rand.nextInt(100)+1));
				   //   panelHolder[i][j].setBackground(Color.BLACK);
				      panelHolder[i][j].setTabPlacement(JTabbedPane.LEFT);
				      add(panelHolder[i][j]);
				   
			   }
			}
		
	}
public void addBuilding(BuildingButton b,int x,int y) {
	panelHolder[x][y].addTab("",new ImageIcon("buildingIcon.png"),b);
	b.setIcon(new ImageIcon("b3.png"));
//	panelHolder[x][y].setBackgroundAt(0,Color.BLACK);
//	revalidate();
}
public void addUnit(JLabel unit,String id,int x,int y) {
	//JLabel unit= new JLabel();
	//unit.setText(type);
	//unit.setForeground(Color.WHITE);
	ImageIcon icon = new ImageIcon("ambIcon.png");
	switch(id) {
	case "Ambulance":{
		unit.setIcon(new ImageIcon("amb.png"));
		icon = new ImageIcon("ambIcon.png");	//
	}
	break;
	case "DiseaseControlUnit": {
	
		unit.setIcon(new ImageIcon("disCont.png"));
		icon = new ImageIcon("ambIcon.png");	//
	}
		break;
	case "Evacuator": {
		unit.setIcon(new ImageIcon("evc.png"));
		icon = new ImageIcon("ambIcon.png");
	}
		break;
	case "FireTruck": {
		unit.setIcon(new ImageIcon("ftk.png"));
		icon = new ImageIcon("ambIcon.png");
	} 
		break;
	case "GasControlUnit": {
		unit.setIcon(new ImageIcon("gcu.png"));
		icon = new ImageIcon("ambIcon.png");
	}
		break;
		default: unit.setText("unit");
	}
	panelHolder[x][y].addTab("",icon,unit);
}

public JTabbedPane[][] getPanelHolder() {
	return panelHolder;
}
public void setPanelHolder(JTabbedPane[][] panelHolder) {
	this.panelHolder = panelHolder;
}
public void addCitizen(CitizenButton citizen,int x,int y) {
	citizen.setIcon(new ImageIcon("citizen.png"));
	panelHolder[x][y].addTab("",new ImageIcon("citIcon.png"),citizen);
}
public void addDeadCitizen(CitizenButton citizen,int x,int y) {
	citizen.setIcon(new ImageIcon("citizen.png"));
	panelHolder[x][y].addTab("",new ImageIcon("dead.png"),citizen);
}

public void addOccp(occpButton occp) {
	occp.setIcon(new ImageIcon("citizen.png"));
	panelHolder[0][0].addTab("",new ImageIcon("citIcon.png"),occp);
}

}
