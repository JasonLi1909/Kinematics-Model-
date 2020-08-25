import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener; 

public class ControlMenu extends JPanel{

	JButton launch, reset;
	JSlider velocitySlider, angleSlider;
	JLabel labelV, labelAngle, labelMenu, labelProperties; 
	static JTextArea updateMenu, propertyMenu; 
	static double angle, velocityX, velocityY, velocity;
	static int controlMenuCount, propertyMenuCount; 
	int sliderStart = 50;
	cannonBox cannonB; 

	//constructor
	public ControlMenu(cannonBox b){
		this.cannonB = b; 
		initializeComponents(); 
		implementListeners(); 
		formatComponents(); 
		addComponents();
	}

	//adds components to the ControlMenu 
	public void addComponents(){
		this.add(launch); 
		this.add(labelMenu);
		this.add(labelProperties); 
		this.add(labelAngle);
		this.add(labelV);
		this.add(reset); 
		this.add(updateMenu);
		this.add(propertyMenu); 
		this.add(angleSlider);
		this.add(velocitySlider);
	}

	//initializes field variables 
	public void initializeComponents(){
		reset = new JButton("Reset");
		launch = new JButton("Launch");
		updateMenu = new JTextArea(5, 10); 
		propertyMenu = new JTextArea(5, 10); 
		angleSlider = new JSlider(0, 90, 0); 
		velocitySlider  = new JSlider(0, 100, sliderStart); 
		labelV = new JLabel("Launch Velocity(m/s)");
		labelAngle = new JLabel("Initial Angle(Degrees)");
		labelProperties= new JLabel("Object Properties");
		labelMenu = new JLabel("Update Menu");
		velocity = sliderStart; 
		velocityY = velocity * Math.sin(Math.toRadians(angle));
		velocityX = velocity * Math.cos(Math.toRadians(angle));
	}

	//creates listeners and attaches them to components 
	public void implementListeners(){
		ButtonListener buttonListen = new ButtonListener();
		SliderListener sliderListen = new SliderListener(); 
		reset.addActionListener(buttonListen);
		launch.addActionListener(buttonListen);
		angleSlider.addChangeListener(sliderListen);
		velocitySlider.addChangeListener(sliderListen);
	}

	//positions, set bounds for, and modifies components
	public void formatComponents(){
		updateMenu.setEditable(false);
		propertyMenu.setEditable(false);
		updateMenu.setBackground(Color.LIGHT_GRAY);
		propertyMenu.setBackground(Color.LIGHT_GRAY);

		labelAngle.setHorizontalAlignment(JLabel.CENTER);
		labelV.setHorizontalAlignment(JLabel.CENTER);
		labelMenu.setHorizontalAlignment(JLabel.CENTER);
		labelProperties.setHorizontalAlignment(JLabel.CENTER);

		velocitySlider.setMajorTickSpacing(10);
		velocitySlider.setMinorTickSpacing(1);
		velocitySlider.setPaintTicks(true);
		velocitySlider.setPaintLabels(true);

		angleSlider.setMajorTickSpacing(10);
		angleSlider.setMinorTickSpacing(1);
		angleSlider.setPaintTicks(true);
		angleSlider.setPaintLabels(true);
	}

	//maintains updateMenu size 
	public static void checkUMenuField(){
		if(ControlMenu.controlMenuCount >= 4){
			ControlMenu.updateMenu.selectAll();
			ControlMenu.updateMenu.replaceSelection("");
			ControlMenu.controlMenuCount = 0; 
		}
	}

	public static double calculateVelocityY(){
		return KinematicsModel.vertAccel * KinematicsModel.secElap + velocityY; 
	}


	//maintains updateMenu size 
	public static void checkPMenuField(){
		if(ControlMenu.propertyMenuCount >= 5){
			ControlMenu.propertyMenu.selectAll();
			ControlMenu.propertyMenu.replaceSelection("");
			ControlMenu.propertyMenuCount = 0; 
		}
	}

	// responds to action events 
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//different buttons can do different things 
			checkUMenuField(); 
			
			//try reloading cannon instead 
			//make it so that it can't translate if at proper location
			if(e.getSource() == reset){
				int xTranslate =  40 - cannonB.boxX; 
				int yTranslate = 610 - cannonB.boxY; 
				updateMenu.append("Object Reset \n");
				updateMenu.setBackground(Color.LIGHT_GRAY);
				KinematicsModel.boxTimer.stop();
				cannonB.box.translate(xTranslate , yTranslate);
				cannonB.boxX += xTranslate; 
				cannonB.boxY += yTranslate; 
				cannonB.repaint();
				velocityX = velocity * Math.cos(Math.toRadians(angle)); 
				KinematicsModel.secElap = 0; 
				controlMenuCount++;
			}
			//timer start might now be sufficient for relaunch 
			if(e.getSource() == launch){
				updateMenu.append("Object Launched \n");
				KinematicsModel.boxTimer.start(); 
				updateMenu.setBackground(Color.GREEN);
				controlMenuCount++;
			}
		}


	}

	//collects values of sliders 
	private class SliderListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			if(e.getSource() == angleSlider){
				angle = angleSlider.getValue();
				KinematicsModel.cannonTimer.start(); 
				velocityX = velocity * Math.cos(Math.toRadians(angle)); 
				velocityY = velocity * Math.sin(Math.toRadians(angle)); 
				System.out.println(angle);
			}
			if(e.getSource() == velocitySlider){
				velocity = velocitySlider.getValue();
				velocityX = velocity * Math.cos(Math.toRadians(angle)); 
				velocityY = velocity * Math.sin(Math.toRadians(angle)); 
				System.out.println("X: " + velocityX); 
				System.out.println("Y: " + velocityY);
			}
		}

	}


}