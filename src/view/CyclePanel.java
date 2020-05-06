package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CyclePanel extends JPanel {
	JLabel casualties;
	JLabel cycleNumber;
	CycleButton endCycle;
	
	public CyclePanel() {
		// TODO Auto-generated constructor stub
		
		this.setLayout(new FlowLayout());
		casualties= new JLabel();
		cycleNumber=new JLabel();
		endCycle=new CycleButton();
		
		
		casualties.setText("Number of Casualties :");
		casualties.setPreferredSize(new Dimension(190,85));
		casualties.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		cycleNumber.setText("Current Cycle :");
		cycleNumber.setPreferredSize(new Dimension(190,85));
		cycleNumber.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		
		endCycle.setText("End Cycle");
		endCycle.setPreferredSize(new Dimension(190,85));
		endCycle.setBackground(Color.red);
		endCycle.setForeground(Color.white);
		
		this.add(casualties);
		this.add(cycleNumber);
		this.add(endCycle);
		
		this.revalidate();
	}

	public JLabel getCasualties() {
		return casualties;
	}

	public JLabel getCycleNumber() {
		return cycleNumber;
	}

	public CycleButton getEndCycle() {
		return endCycle;
	}
	
	

}










	
	


