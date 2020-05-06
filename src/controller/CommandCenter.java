package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import exceptions.CannotTreatException;
import exceptions.DisasterException;
import exceptions.IncompatibleTargetException;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Evacuator;
import model.units.Unit;
import simulation.Rescuable;
import simulation.Simulator;
import view.BuildingButton;
import view.CitizenButton;
import view.CycleButton;
import view.GameView;
import view.UnitButton;
import view.occpButton;


public class CommandCenter implements SOSListener,ActionListener,MouseListener{

	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private GameView gameView;
	private ArrayList<UnitButton> unitBTns;
	private ArrayList<BuildingButton> buildingBtns;
	private ArrayList<CitizenButton> citizenBtns;
	private UnitButton firstEntered;
	private JComponent lastEntered;
	private ArrayList<JLabel> unitLabels;
	private ArrayList<Citizen> alreadyAtBase; //new
	private ArrayList<occpButton> occpbtns;
	

	@SuppressWarnings("unused")
	private ArrayList<Unit> emergencyUnits;

	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();
		gameView = new GameView();
		unitBTns= new ArrayList<UnitButton>();
		buildingBtns= new ArrayList<BuildingButton>();
		citizenBtns= new ArrayList<CitizenButton>();
		unitLabels = new ArrayList<JLabel>();
		alreadyAtBase=new ArrayList<Citizen>(); //new 
		occpbtns=new ArrayList<occpButton>();
		
//		for(ResidentialBuilding building :visibleBuildings) {//visible buildings
//			BuildingButton button = new BuildingButton();
//			button.addActionListener(this);
//			button.addMouseListener(this);
//			buildingBtns.add(button);
//			gameView.getRescuePanel().addBuilding(button, building.getLocation().getX(), building.getLocation().getY());
//			//gameView.getRescuePanel().addBuilding(button, 1, 1);
//
//		}
//		for(Citizen citizen : engine.getCitizens()) {//visible buildings
//			CitizenButton button = new CitizenButton();
//			button.addActionListener(this);
//			button.addMouseListener(this);
//			citizenBtns.add(button);
//			gameView.getRescuePanel().addCitizen(button, citizen.getLocation().getX(), citizen.getLocation().getY());
//			//gameView.getRescuePanel().addBuilding(button, 1, 1);
//
//		}
		for(Unit u : emergencyUnits) {
			//System.out.println(u.getUnitID());
			UnitButton button = new UnitButton(u.getType());
			//button.setText("unit");
			button.addActionListener(this);
			button.addMouseListener(this);
			unitBTns.add(button);
			gameView.getUnitPanel().getUnitHolder().add(button);
		}
		
//		gameView.getP().getLog().setText(engine.printDisasters());
//		gameView.getP().getActiveD().setText(engine.printActiveDisasters());
//		
//		gameView.getC().getCasualties().setText("Number of Casualties: "+ engine.calculateCasualties());
//		gameView.getC().getCycleNumber().setText("Current Cycle: "+engine.getCurrentCycle());
		gameView.getCyclePanel().getEndCycle().addActionListener(this);
		//firstEntered.addMouseListener(this);
		//lastEntered.addMouseListener(this);
		gameView.setVisible(true);
		gameView.validate();
		
		

	}
	

	public GameView getGameView() {
		return gameView;
	}


	@Override
	public void receiveSOSCall(Rescuable r) {
		
		if (r instanceof ResidentialBuilding) {
			
			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);
			
		} else {
			
			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}

	}
	public static void main(String[] args) throws Exception {
		new CommandCenter();
//		UnitButton u1 = new JButton("car");
//		c.gameView.getRescuePanel().addUnit(u1,2,2);
//		
//		c.getGameView().getRescuePanel().revalidate();
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() instanceof UnitButton) {
			UnitButton b=(UnitButton)e.getSource();
			int i = unitBTns.indexOf(b);
			Unit u = emergencyUnits.get(i);
			gameView.getUnitPanel().getTxt().setText(u.toString());
	
		}
		else if(e.getSource() instanceof BuildingButton) {
			BuildingButton b=(BuildingButton)e.getSource();
			int i = buildingBtns.indexOf(b);
			ResidentialBuilding u = visibleBuildings.get(i);	//change to visible buildings
			gameView.getInfoPanel().getInfo().setText(u.toString());
	
		}
		else if(e.getSource() instanceof CitizenButton) {
			CitizenButton b=(CitizenButton)e.getSource();
			int i = citizenBtns.indexOf(b);
			Citizen u = visibleCitizens.get(i);	
			gameView.getInfoPanel().getInfo().setText(u.toString());
	
		}
		else if(e.getSource() instanceof occpButton) {
			occpButton b=(occpButton)e.getSource();
			int i=occpbtns.indexOf(b);
			Citizen u =alreadyAtBase.get(i);
			gameView.getInfoPanel().getInfo().setText(u.toString());
			
		}
		
		else if (e.getSource() instanceof CycleButton) {
//			System.out.println("cycle");
			try {
				engine.nextCycle();
			} catch (DisasterException e1) {
				
				JOptionPane.showMessageDialog(gameView,e1.getMessage());
			}
			finally {
			
			UpdateAll();}
			gameView.revalidate();
		}

}
	
	
	
	public void UpdateAll() {
		if(engine.checkGameOver()) {
			JFrame score = new JFrame();
			score.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		    int height = screenSize.height;
		    int width = screenSize.width;
		    score.setSize(width/2, height/2);

		    // center the jframe on screen
		    score.setLocationRelativeTo(null);
			
			score.setBackground(Color.BLACK);
			
			JLabel x=new JLabel("Game Over! Number of casualties: "+engine.calculateCasualties(),SwingConstants.CENTER);
			x.setBounds(60, 5, 250, 30);

			Font newLabelFont=new Font("", Font.ITALIC, 44);  
			  
			 //Set JLabel font using new created font  
			 x.setFont(newLabelFont); 
			x.setForeground(Color.ORANGE);
			score.add(x);
			score.setVisible(true);
//			playSound("end.wav");
			gameView.revalidate();
			gameView.dispose();
			 
			score.revalidate();
			
		}
		else {
			for(Citizen c:visibleCitizens) {		// change dead citizen's tab icon
				if(c.getState()==CitizenState.DECEASED && !(engine.getAlreadyDied().contains(c))) {
					int i = visibleCitizens.indexOf(c);
					CitizenButton button = citizenBtns.get(i);
					JTabbedPane panel =(gameView.getRescuePanel().getPanelHolder())[c.getLocation().getX()][c.getLocation().getY()];
					int tabIndex=panel.indexOfComponent(button);
//					System.out.println(panel.getTabCount());

//					panel.setBackgroundAt(tabIndex,Color.BLACK);
					panel.setIconAt(tabIndex, new ImageIcon("dead.png"));
				}
			}
			for(ResidentialBuilding c:visibleBuildings) {		// change fallen building's tab icon
				if(c.getStructuralIntegrity()==0 && !(engine.getAlreadyCollapsed().contains(c))) {
					int i = visibleBuildings.indexOf(c);
					BuildingButton button = buildingBtns.get(i);
					JTabbedPane panel =(gameView.getRescuePanel().getPanelHolder())[c.getLocation().getX()][c.getLocation().getY()];
					int tabIndex=panel.indexOfComponent(button);
//					System.out.println(panel.getTabCount());

//					panel.setBackgroundAt(tabIndex,Color.BLACK);
					panel.setIconAt(tabIndex, new ImageIcon("fallen.png"));
				}
			}
		for(int i =buildingBtns.size() ; i<visibleBuildings.size();i++ ) {	//*create new building buttons in tabs*
			ResidentialBuilding building = visibleBuildings.get(i);
//		}
//		for(ResidentialBuilding building : visibleBuildings) {//visible buildings
//			if(building.getDisaster()!=null && building.getDisaster().getStartCycle()==engine.getCurrentCycle() && buildingBtns.size()<visibleBuildings.size()) {
			BuildingButton button = new BuildingButton();
			button.addActionListener(this);
			button.addMouseListener(this);
			buildingBtns.add(button);
			gameView.getRescuePanel().addBuilding(button, building.getLocation().getX(), building.getLocation().getY());
			//gameView.getRescuePanel().addBuilding(button, 1, 1);
//			}
		}
		for(int i =citizenBtns.size() ; i<visibleCitizens.size();i++) {	//*create new citizen buttons in tabs*
			Citizen citizen = visibleCitizens.get(i);
			CitizenButton button = new CitizenButton();
			button.addActionListener(this);
			button.addMouseListener(this);
			citizenBtns.add(button);
			gameView.getRescuePanel().addCitizen(button, citizen.getLocation().getX(), citizen.getLocation().getY());
			//gameView.getRescuePanel().addBuilding(button, 1, 1);

		}
		for(int i=0;i<engine.getCitizens().size();i++) {	//*make passengers appear at base*
			Citizen c=engine.getCitizens().get(i);
			if(!(visibleCitizens.contains(c))) {
				if(c.getLocation().getX()==0&&c.getLocation().getY()==0&&!(alreadyAtBase.contains(c))) {
					occpButton button =new occpButton();
					alreadyAtBase.add(c);
					button.addActionListener(this);
					button.addMouseListener(this);
					occpbtns.add(button);
					gameView.getRescuePanel().addOccp(button);					
				}				
			}			
		}
		
		gameView.getCyclePanel().getCasualties().setText("Number of Casualties: "+ engine.calculateCasualties());
		gameView.getCyclePanel().getCycleNumber().setText("Current Cycle: "+engine.getCurrentCycle());
		gameView.getInfoPanel().getActivetxt().setText(engine.printActiveDisasters()+"\n"+"------------"+"\n"+engine.printDisasters());
		gameView.getInfoPanel().getCycletxt().setText(engine.printLog());
//		for(JLabel label: unitLabels) {
//			label.setVisible(false);
//		}
		for(int i=0;i<10;i++) {		// *removes all unit tabs*
			for(int j=0;j<10;j++) {
				for(JLabel label: unitLabels) {
//					if(((gameView.getRescuePanel().getPanelHolder())[i][j]).)
				((gameView.getRescuePanel().getPanelHolder())[i][j]).remove(label);
				}
			}
		}
		
		unitLabels.clear();
		for(Unit u:emergencyUnits) {		// *creates unit tabs*
//			if(u.getTarget()!=null) {
//			if(u.getTarget()!=null&&u.getLocation().getX()==u.getTarget().getLocation().getX() && u.getLocation().getY()==u.getTarget().getLocation().getY()) {
//				JLabel ulabel = new JLabel();
//				unitLabels.add(ulabel);
//				gameView.getRescuePanel().addUnit(ulabel,u.getType(), u.getLocation().getX(), u.getLocation().getY());
//				//System.out.println("unit reached");
//			}
			
			if(u instanceof Evacuator) {
				
				if(u.getLocation().getX()!=0&&u.getLocation().getY()!=0) {
					JLabel ulabel = new JLabel();
					unitLabels.add(ulabel);
					gameView.getRescuePanel().addUnit(ulabel,u.getType(), u.getLocation().getX(), u.getLocation().getY());
				}
				else {
						if(u.getTarget()!=null) {
						JLabel ulabel = new JLabel();
						unitLabels.add(ulabel);
						gameView.getRescuePanel().addUnit(ulabel,u.getType(), 0, 0);
//						playSound("salma.wav");
						}
					}
				
			}
			else {
		
			if(u.getLocation().getX()!=0&&u.getLocation().getY()!=0) {
				JLabel ulabel = new JLabel();
				unitLabels.add(ulabel);
				gameView.getRescuePanel().addUnit(ulabel,u.getType(), u.getLocation().getX(), u.getLocation().getY());
			}}
//			else {
//				JPanel curr=(gameView.getRescuePanel().getPanelHolder())[u.getLocation().getX()][u.getLocation().getY()];
//				for(Component jc: curr.getComponents()) {
//					if(jc instanceof JLabel) {
//						jc.setVisible(false);
//					}
//				}
//			}
//		}
		}
		for(int i=0;i<visibleCitizens.size();i++) {	//*moves citizen buttons to base*
			Citizen c= visibleCitizens.get(i);
			if(c.getLocation().getX()==0 &&c.getLocation().getY()==0) {
				CitizenButton oldButton = citizenBtns.get(i);
				JTabbedPane base = gameView.getRescuePanel().getPanelHolder()[0][0];
				boolean inBase = false;
				for(int j = 0; j < base.getTabCount(); j++)		//*checks if citizen is already at base*
				{
				   if(oldButton == base.getComponentAt(j)) {	//*****remove tab********
					   inBase=true;
//					   System.out.println("already in base");
				   }
				}
				if(!inBase) {
					for(int k=0;k<10;k++) {		// *remove old button*
						for(int j=0;j<10;j++) {
							((gameView.getRescuePanel().getPanelHolder())[k][j]).remove(oldButton);
//							System.out.println("remove old button");
						}
					}
					CitizenButton button = new CitizenButton();		//create new button at base
					button.addActionListener(this);
					button.addMouseListener(this);
					if(c.getState()==CitizenState.DECEASED)
						gameView.getRescuePanel().addDeadCitizen(button, c.getLocation().getX(), c.getLocation().getY());
					else
						gameView.getRescuePanel().addCitizen(button, c.getLocation().getX(), c.getLocation().getY());
					citizenBtns.set(i, button);		//set(i,o) replaces object at index i with o
				}
//				if((gameView.getRescuePanel().getPanelHolder())[0][0].)
			}
		}

		gameView.getInfoPanel().getInfo().setText("");
		gameView.getUnitPanel().getTxt().setText("");
		//System.out.println(visibleBuildings.size()+"  "+ buildingBtns.size());
		//System.out.print(gameView.getP().getSize());
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("enter");
		if(e.getSource() instanceof BuildingButton) {
			lastEntered = (BuildingButton)e.getSource();
	//		lastEntered.addMouseListener(this);				**remove this**
		}
		if(e.getSource() instanceof CitizenButton) {
			lastEntered = (CitizenButton)e.getSource();
	//		lastEntered.addMouseListener(this);			**remove this**
		}
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("pressed");
		lastEntered=null;								//*********ADDED THIS****************
		if(e.getSource() instanceof UnitButton) {
			firstEntered = (UnitButton)e.getComponent();
	//		firstEntered.addMouseListener(this);		**remove this**
		}
		
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if(firstEntered!=null && lastEntered!= null) {
			
			int i = unitBTns.indexOf(firstEntered);
			Unit u = emergencyUnits.get(i);
			Rescuable r = null;
			if(lastEntered instanceof BuildingButton) {
				int j = buildingBtns.indexOf(lastEntered);
				r = visibleBuildings.get(j);			//******changed to visible*****
			}else if (lastEntered instanceof CitizenButton) {
				int j = citizenBtns.indexOf(lastEntered);
				r = visibleCitizens.get(j);				//******changed to visible*****
			}
			try {
				u.respond(r);
				gameView.getUnitPanel().getTxt().setText("");
				playSound("siren.wav");
			} catch (IncompatibleTargetException e2) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(gameView,e2.getMessage());
			}catch (CannotTreatException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(gameView,e1.getMessage());
			}
			lastEntered=null;
			firstEntered=null;
		}
	}
	public void playSound(String soundName)
	 {
	   try 
	   {
	    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( ));
	    Clip clip = AudioSystem.getClip( );
	    clip.open(audioInputStream);
	    clip.start( );
	   }
	   catch(Exception ex)
	   {
	     System.out.println("Error with playing sound.");
	     ex.printStackTrace( );
	   }
	 }



	

}