import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Bullet extends GameObject {
	private int x;
	private int y;
	private int age;
	private final int lifespan = 300;
	private final int  speed = 10;
	private int direction;
	private int team;
	private BufferedImage sprite;

	public Bullet(int x, int y,int t, int dir ) {
		this.x=x;
		this.y=y;
		team=t;
		direction = dir;
		age = 0;
		if(team>0){
			try{
				sprite=ImageIO.read(new File("Images\\sprites\\laserGreen.png"));
			}catch (IOException e){
				
			}
		}
		else{
			try{
				sprite=ImageIO.read(new File("Images\\sprites\\laserRed.png"));
			}catch (IOException e){
				
			}
		}
		
	}

	public void run() {
		move();
		age++;
	}

	
	private void move() {
		if (direction == 0) {
			x -= speed;
		} else if (direction == 90) {
			y -= speed;
		} else if (direction == 180) {
			x += speed;
		} else if (direction < 90) {
			x -= Math.sin(Math.toRadians(direction)) * speed;
			y -= Math.cos(Math.toRadians(direction)) * speed;
		} else if(direction<180) {
			x += Math.sin(Math.toRadians(180 - direction)) * speed;
			y -= Math.cos(Math.toRadians(180 - direction)) * speed;
		} else if (direction == 270) {
			y+=speed;
		}
	}
	public int getTeam(){
		return team;
	}
	public int[] getPos() {
		return new int[]{this.x, this.y };
	}
	public void contact(GameObject o) {
		if((team>0&&o.getTeam()<0)||(team<0&&o.getTeam()>0)){
			age=300;
		}
	}
	public boolean alive() {
		if (age < lifespan) {
			return true;
		} else {
			return false;
		}
	}
	public void die(){
		age=300;
	}
	public BufferedImage getSprite(){
		return sprite;
	}
}

