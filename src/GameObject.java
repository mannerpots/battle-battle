import java.awt.image.BufferedImage;

public class GameObject {
	private int x;
	private int y;
	private int team;
	private boolean alive;
	private BufferedImage sprite;
	public void run() {

	}

	public boolean alive() {
		return alive;
	}
	public int getTeam() {
		return team;
	}
	public int[] getPos() {
		return new int[]{this.x, this.y };
	}
	public void contact(GameObject o){
		
	}
	public void die(){
		alive=false;
	}
	public BufferedImage getSprite(){
		return sprite;
	}
}
