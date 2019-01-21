import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UFO extends Enemy {
	private Game parent;
	private int x;
	private int y;
	private final int speed = 2;
	private int direction;
	private int team;
	private boolean alive;
	private BufferedImage sprite;
	private int fireSpeed = 150;
	private int cooldown = 0;

	public UFO(int x, int y, int d,Game p) {
		super(x, y);
		this.x = x;
		this.y = y;
		team = -1;
		alive = true;
		parent=p;
		direction=d;
		try {
			sprite = ImageIO.read(new File("Images\\sprites\\EnemyUFO.png"));
		} catch (IOException e) {

		}
	}
	public void run() {
		move();
		fire();
	}
	private void move() {
		x+=speed*direction;
		if(x<0){
			direction =1;
		}
		if(x>parent.getWidth()){
			direction=-1;
		}
	}

	private void fire() {
				if(cooldown<0){
					parent.queueSpawn(new Bullet(this.x, this.y, team, 270));
					cooldown = fireSpeed;
		}
		cooldown--;

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
