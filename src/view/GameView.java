package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GameView extends JFrame{
	RescuePanel rescuePanel;
	CyclePanel cyclePanel;
	UnitPanel unitPanel;
	infoPanel infoPanel;
	
	
	
	
	
//	public GameView() {
//		this.setMinimumSize(new Dimension(1500,600));
//		setLocation(200,10);
//		this.setVisible(true);
//		this.setSize(1500, 1000);
//		this.setTitle("Rescue Simulation");
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		this.validate();
//		rescuePanel = new RescuePanel();
//		this.add(rescuePanel,BorderLayout.CENTER);
//		unitPanel=new UnitPanel();
//		this.add(unitPanel,BorderLayout.WEST);
//		infoPanel=new infoPanel();
//		//in.setSize(197,1015);
//	
//		this.add(infoPanel,BorderLayout.EAST);
//		cyclePanel=new CyclePanel();
//		this.add(cyclePanel,BorderLayout.SOUTH);
////		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
////		this.setSize(screenSize);
////		this.setResizable(false);
//		
//		this.revalidate();
//	}
	
public GameView() {
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(1500,600));
		setLocation(200,10);
		
		this.setSize(1500, 1000);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.validate();
		rescuePanel = new RescuePanel();
		this.add(rescuePanel,BorderLayout.CENTER);
		unitPanel=new UnitPanel();
		this.add(unitPanel,BorderLayout.WEST);
		infoPanel=new infoPanel();
		//p.setSize(197,1015);
	
		this.add(infoPanel,BorderLayout.EAST);
		cyclePanel=new CyclePanel();
		this.add(cyclePanel,BorderLayout.SOUTH);
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//this.setSize(screenSize);
		//this.setResizable(false);
		this.setVisible(true);
		this.revalidate();
	}







	public RescuePanel getRescuePanel() {
		return rescuePanel;
	}







	public CyclePanel getCyclePanel() {
		return cyclePanel;
	}







	public UnitPanel getUnitPanel() {
		return unitPanel;
	}







	public infoPanel getInfoPanel() {
		return infoPanel;
	}







	public static void main(String[] args) {
		
	}

}
