import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends GameObject {
	private Game parent;
	private int movespeed = 5;
	private int lives = 3;
	private int fireSpeed = 15;
	private int cooldown = 0;
	private int x;
	private int y;
	private int team;
	private int[] powerUpDuration = new int[3];
	private BufferedImage sprite;

	public Player(int x, int y, int t, Game parent) {
		this.x = x;
		this.y = y;
		team = t;
		this.parent = parent;
		try {
			sprite = ImageIO.read(new File("Images\\sprites\\player.png"));
		} catch (IOException e) {

		}

	}

	public void run() {
		try {
			sprite = ImageIO.read(new File("Images\\sprites\\player.png"));
		} catch (IOException e) {

		}
		move();
		fire();
		for (int i = 0; i < powerUpDuration.length; i++) {
			powerUpDuration[i]--;
		}
	}

	// TODO:hitests
	public void contact(GameObject o) {

		if (o.getClass().getSuperclass() == Enemy.class
				&& powerUpDuration[2] < 0) {
			lives--;
			o.die();
		}
		if (o.getClass() == Bullet.class && powerUpDuration[2] < 0&&o.getTeam()<1) {
			lives--;
			o.die();

		}
		if (o.getClass() == PowerUp.class) {
			if (((PowerUp) o).getType() == 3) {
				lives++;

			} else {
				powerUpDuration[((PowerUp) o).getType() - 1] = 300;
			}
			o.die();

		}
	}

	private void move() {
		if (team == 1) {
			if (parent.getKeys()[0]) {
				x -= movespeed;
				try {
					sprite = ImageIO.read(new File(
							"Images\\sprites\\playerLeft.png"));
				} catch (IOException e) {

				}
			}
			if (parent.getKeys()[1]) {
				x += movespeed;
				try {
					sprite = ImageIO.read(new File(
							"Images\\sprites\\playerRight.png"));
				} catch (IOException e) {

				}
			}
			if (parent.getKeys()[2]) {
				y -= movespeed;
			}
			if (parent.getKeys()[3]) {
				y += movespeed;
			}
		} else if (team == 2) {
			if (parent.getKeys()[5]) {
				x -= movespeed;
				try {
					sprite = ImageIO.read(new File(
							"Images\\sprites\\playerLeft.png"));
				} catch (IOException e) {

				}
			}
			if (parent.getKeys()[6]) {
				x += movespeed;
				try {
					sprite = ImageIO.read(new File(
							"Images\\sprites\\playerRight.png"));
				} catch (IOException e) {

				}
			}
			if (parent.getKeys()[7]) {
				y -= movespeed;
			}
			if (parent.getKeys()[8]) {
				y += movespeed;
			}
		}
	}

	private void fire() {
		if (team == 1) {
			if (parent.getKeys()[4] && (cooldown < 1)) {
				if (powerUpDuration[0] >= 0) {
					parent.queueSpawn(new Bullet(this.x, this.y, team, 0));
					parent.queueSpawn(new Bullet(this.x, this.y, team, 30));
					parent.queueSpawn(new Bullet(this.x, this.y, team, 60));
					parent.queueSpawn(new Bullet(this.x, this.y, team, 90));
					parent.queueSpawn(new Bullet(this.x, this.y, team, 120));
					parent.queueSpawn(new Bullet(this.x, this.y, team, 150));
					parent.queueSpawn(new Bullet(this.x, this.y, team, 180));
					if (powerUpDuration[1] >= 0) {
						cooldown = fireSpeed / 3;
					} else {
						cooldown = fireSpeed;

					}
				} else {
					parent.queueSpawn(new Bullet(this.x, this.y, team, 90));
					if (powerUpDuration[1] >= 0) {
						cooldown = fireSpeed / 3;
					} else {
						cooldown = fireSpeed;

					}
				}
			}
		}
		else if (team == 2) {
			if (parent.getKeys()[9] && (cooldown < 1)) {
				if (powerUpDuration[0] >= 0) {
					parent.queueSpawn(new Bullet(this.x, this.y, team, 0));
					parent.queueSpawn(new Bullet(this.x, this.y, team, 30));
					parent.queueSpawn(new Bullet(this.x, this.y, team, 60));
					parent.queueSpawn(new Bullet(this.x, this.y, team, 90));
					parent.queueSpawn(new Bullet(this.x, this.y, team, 120));
					parent.queueSpawn(new Bullet(this.x, this.y, team, 150));
					parent.queueSpawn(new Bullet(this.x, this.y, team, 180));
					if (powerUpDuration[1] >= 0) {
						cooldown = fireSpeed / 3;
					} else {
						cooldown = fireSpeed;

					}
				} else {
					parent.queueSpawn(new Bullet(this.x, this.y, team, 90));
					if (powerUpDuration[1] >= 0) {
						cooldown = fireSpeed / 3;
					} else {
						cooldown = fireSpeed;

					}
				}
			}
		}
		cooldown--;

	}

	public int[] getPos() {
		return new int[] { x, y };
	}

	public boolean alive() {
		if (lives > 0) {
			return true;
		} else {
			return false;
		}
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public int getLives() {
		return lives;
	}

	public int getTeam() {
		return team;
	}
}
