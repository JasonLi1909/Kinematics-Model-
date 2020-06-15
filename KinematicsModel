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

import java.util.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

public class cannonBox extends JComponent { 
	Rectangle box, cannonBarrel, cannonSupport ;
	int boxY = 600; 
	int boxX = 40; 
	int floorY = boxY + 70; 
	int floorX = boxX + 1860; 
	int cannonBarrelX = 20;
	int cannonBarrelY = 600; 
	int cannonSupportY, count; 
	BufferedImage image; 
	Container contain; 
	double angle; 

	public cannonBox(Container master){ 
		contain = master; 
		box = new Rectangle(boxX, boxY, 30, 30); 
		cannonBarrel = new Rectangle(cannonBarrelX, cannonBarrelY, 110, 60); 
		cannonSupport = new Rectangle(40, 600, 30, 100); 
	}

	//paints cloud background, tilts cannon, and animates box  
	public void paint(Graphics g) {
		Graphics2D gg = (Graphics2D)g.create();

		//paints background  
		try {
			image = ImageIO.read(new File("res/clouds2.jpg"));  
		} catch (IOException e) {
			e.printStackTrace();
		}
		gg.drawImage(image, 0, 0, contain);

		//paints box
		gg.setColor(Color.RED);
		gg.fill(box); 
		gg.dispose();

		//paints cannon support
		gg = (Graphics2D)g.create();
		gg.setColor(Color.GRAY);
		gg.fill(cannonSupport);

		//paints cannon  
		gg = (Graphics2D)g.create();
		gg.rotate(-Math.toRadians(angle), cannonBarrelX + 30, cannonBarrelY + 30); 
		gg.fill(cannonBarrel); 
	}

	//stop animation if box hit floor 
	public void hitFloor(){
		KinematicsModel.boxTimer.stop(); 
		KinematicsModel.cannonTimer.stop(); 
		ControlMenu.updateMenu.append("Object Landed \n");
		ControlMenu.controlMenuCount++; 
		ControlMenu.checkUMenuField(); 
	}
	
	public void hitWall(){
		ControlMenu.velocityX = 0; 
		ControlMenu.updateMenu.append("Object Hit Wall \n");
		ControlMenu.controlMenuCount++; 
		ControlMenu.checkUMenuField(); 
	}

	public void updatePropertyMenu(){
		//change velocityY  
		String properties = String.format("VelocityX: %.2f m/s VelocityY: %.2f m/s Time Elapsed: %.2f sec \n",
					 ControlMenu.velocityX, ControlMenu.calculateVelocityY(), KinematicsModel.secElap); 
		ControlMenu.propertyMenu.append(properties);
		ControlMenu.propertyMenuCount++; 
		ControlMenu.checkPMenuField();
	}

	//animates box motion
	public void move(int x, int y){
		if(count % 5 == 0){
			updatePropertyMenu();
		} 
		if(boxX + 70 >= floorX){
			hitWall(); 
		} 
		if(boxY >= floorY){
			hitFloor(); 
		}else{
			box.translate(x, y);
			boxY += y; 
			boxX += x; 
		}
		count++; 
		repaint(); 
	}

	//animates cannon motion
	public void rotateCannon(){
		angle = ControlMenu.angle; 
		repaint(); 
	}

}

