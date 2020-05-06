package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class infoPanel extends JPanel{
	JTextArea cycletxt;
	JScrollPane scroll;
	JTextArea activetxt; 
	JScrollPane scroll1;
	
	
	JTextArea info;
	JScrollPane scroll2;
		
	public infoPanel() {
		this.setPreferredSize(new Dimension(250,1015));
		this.setLayout(new BorderLayout());
		cycletxt =new JTextArea();
		scroll= new JScrollPane(cycletxt);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		cycletxt.setEditable(false);
		//cycletxt.setPreferredSize(new Dimension(240,100));
		cycletxt.setWrapStyleWord(true);
		this.add(scroll,BorderLayout.SOUTH);
		scroll.setVisible(true);
		
		
		
		
		

		
		activetxt=new JTextArea();
		scroll1=new JScrollPane(activetxt);
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);	
		activetxt.setEditable(false);
		activetxt.setWrapStyleWord(true);
		this.add(scroll1,BorderLayout.NORTH);
		scroll1.setVisible(true);
		
		
		
		info=new JTextArea();
		info.setEditable(false);
		scroll2=new JScrollPane(info);
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);	
		info.setEditable(false);
		//info.setSize(200,100);
		info.setWrapStyleWord(true);
		this.add(scroll2,BorderLayout.CENTER);
		scroll1.setVisible(true);
		
		
		
		
		
		this.revalidate();
		
		
		
	}

	public JScrollPane getScroll() {
		return scroll;
	}

	public JTextArea getActivetxt() {
		return activetxt;
	}

	public JScrollPane getScroll1() {
		return scroll1;
	}

	public JTextArea getCycletxt() {
		return cycletxt;
	}

	public JTextArea getInfo() {
		return info;
	}
		
}