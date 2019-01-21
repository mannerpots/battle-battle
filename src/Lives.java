import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Lives extends GameObject {
	private int x;
	private int y;
	private int team;
	private boolean alive;
	private BufferedImage sprite= new BufferedImage(200, 27,BufferedImage.TYPE_4BYTE_ABGR);
	private BufferedImage single;
	private int lives=3;

	public Lives(int x, int y, int t) {
		this.x = x;
		this.y = y;
		team = t;
		try {
			single = ImageIO.read(new File("Images\\sprites\\life.png"));
		} catch (IOException e) {

		}
	}

	public void run() {
		BufferedImage buffer = new BufferedImage(40 * lives, 27,
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D) buffer.getGraphics();
		g.clearRect(0, 0, sprite.getWidth(), sprite.getHeight());
		int l = lives;
		int c = 0;
		while (l > 0) {
			g.drawImage(single, 0 + 40 * c, 0, null);
			c++;
			l--;
		}
		sprite = buffer;
	}

	public void setLives(int l) {
		lives = l;
	}

	public boolean alive() {
		return alive;
	}

	public int getTeam() {
		return team;
	}

	public int[] getPos() {
		return new int[] { this.x, this.y };
	}

	public void contact(GameObject o) {

	}

	public void die() {

	}

	public BufferedImage getSprite() {
		return sprite;
	}

}
