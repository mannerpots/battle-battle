import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ship extends Enemy {
	private Game parent;
	private int x;
	private int y;
	private final int speed = 2;
	private int direction;
	private int team;
	private boolean alive;
	private BufferedImage sprite;
	private int cooldown=45;
	private int dirMove;


	public Ship(int x, int y, int d,Game p) {
		super(x, y);
		this.x = x;
		this.y = y;
		team = -1;
		alive = true;
		parent=p;
		direction=d;
		dirMove=0;
		try {
			sprite = ImageIO.read(new File("Images\\sprites\\EnemyShip.png"));
		} catch (IOException e) {

		}
	}
	public void run() {
		move();
	}
	private void move() {
		dirMove++;
		if(Math.random()*dirMove>cooldown){
			direction*=-1;
			dirMove=0;
		}
		x+=speed*direction;
		y+=speed;
		if(x<0){
			x=2;
		}
		if(x>parent.getWidth()){
			x=parent.getWidth()-1;
		}
	}
	public void contact(GameObject o) {
		if(o.getTeam()>0){
			die();
		}
	}

	public void die() {
		alive = false;
	}

	public boolean alive() {
		return alive;
	}

	public int[] getPos() {
		return new int[] { this.x, this.y };
	}

	public BufferedImage getSprite() {
		return sprite;
	}
	public int getTeam() {
		return team;
	}
	
}