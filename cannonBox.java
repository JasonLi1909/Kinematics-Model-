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
