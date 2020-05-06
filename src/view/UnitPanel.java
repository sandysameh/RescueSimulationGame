package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class UnitPanel extends JPanel {
JTextArea txt;
JPanel unitHolder;
public UnitPanel () {
	this.setLayout(new BorderLayout());
	this.txt=new JTextArea();
	this.add(txt,BorderLayout.SOUTH);
	txt.setEditable(false);
	unitHolder=new JPanel();
	unitHolder.setLayout(new GridLayout(0,3));
	this.add(unitHolder,BorderLayout.NORTH);
	this.revalidate();
}

public JTextArea getTxt() {
	return txt;
}

public JPanel getUnitHolder() {
	return unitHolder;
}

public void addUnit(UnitButton unit) {
	unitHolder.add(unit);
}
}


