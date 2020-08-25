import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.Border;

public class KinematicsModel extends JFrame{
	static Timer boxTimer, cannonTimer;
	int timerDelay = 1;
	final int pixelsPerMeter = 5; 	
	static double secElap; 
	int count; 
	double secInc = (double)((timerDelay) + 40) / 1000;
	final static double vertAccel = -9.81; 
	cannonBox box; 
	
	//constructs Kinematics Model 
	public KinematicsModel(){
		//initialize variables
		secElap = 0; 
		count = 0; 
		
		// sets Frame attributes
		setTitle("2D Kinematics Model");
		setSize(1900, 900);
		setLocationRelativeTo(null); 
		setResizable(false); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//create top level Container
		Container master = this.getContentPane(); 
		master.setLayout(new BorderLayout());
		
		//adds animationPanel to Container
		box = new cannonBox(master);
		master.add(box, BorderLayout.CENTER); 
		
		//constructs Control Menu 
		ControlMenu control = new ControlMenu(box);
		control.setLayout(new GridLayout(2,4)); 
		master.add(control, BorderLayout.SOUTH); 
		
		//frame design aesthetics
		master.setBackground(Color.BLACK);
		Border border = BorderFactory.createLineBorder(Color.BLACK); 
		control.setBackground(new Color(147, 232, 212));
		control.setBorder(border);
		
		//creates timers and attaches timer listeners for animation
		implementTimeListeners(); 
		
		setVisible(true);
	}
	
	public void implementTimeListeners(){
		//implement boxtimeListener for box animation
		ActionListener boxListener = new boxTimeListener(); 
		boxTimer = new Timer(timerDelay, boxListener); 
		
		//implement cannonTimeListener for cannon animation
		ActionListener cannonListener = new cannonTimeListener(); 
		cannonTimer = new Timer(0, cannonListener); 
		
	}
	
	//actionListener for cannon Timer
	class cannonTimeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			box.rotateCannon();
		}
	}
	
	//actionListener for box timer 
	class boxTimeListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				count++; 
				secElap += secInc; 
				double metersDeltaX = ControlMenu.velocityX * secInc; 
				double metersDeltaY = ((vertAccel / 2) * Math.pow(secElap, 2) + 
										ControlMenu.velocityY * secElap) - 
										((vertAccel/2) * Math.pow(secElap - secInc, 2) + 
										ControlMenu.velocityY * (secElap - secInc) ) ;
				System.out.println("TimeElapsed: " + secElap);
				System.out.println("DeltaX: " + metersDeltaX);
				System.out.println("DeltaY: " + metersDeltaY);
				System.out.println("CallCount: " + count);
				
				//approximations due to casting 
				box.move((int)(metersDeltaX * pixelsPerMeter) , -(int)(metersDeltaY * pixelsPerMeter));
			}
		}

	public static void main(String[] args){
		new KinematicsModel();  
	}

}

