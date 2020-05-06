package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class unitScroll extends JPanel{
	JTextArea txt;
	JScrollPane scroll;
	public unitScroll() {
		
		
		txt= new JTextArea(16,16);
		scroll= new JScrollPane(txt);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		txt.setEditable(false);
		//txt.setSize(400,100);
		txt.setWrapStyleWord(true);
		txt.setLineWrap(true);
		this.add(scroll);
		scroll.setVisible(true);
		revalidate();
		
		
		
//		
//		middlePanel=new JPanel();
//	    middlePanel.setBorder(new TitledBorder(new EtchedBorder(), "Display Area"));
//
//	    // create the middle panel components
//
//	    display = new JTextArea(16, 58);
//	    display.setEditable(false); // set textArea non-editable
//	    scroll = new JScrollPane(display);
//	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//
//	    //Add Textarea in to middle panel
//	    middlePanel.add(scroll);
//		
		
	}
	public JTextArea getTxt() {
		return txt;
	}
	public void setTxt(JTextArea txt) {
		this.txt = txt;
	}
	public JScrollPane getScroll() {
		return scroll;
	}
	public void setScroll(JScrollPane scroll) {
		this.scroll = scroll;
	}


}
