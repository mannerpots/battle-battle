import java.awt.image.BufferedImage;


public class Enemy extends GameObject {
	private int x;
	private int y;
	private final int  speed = 10;
	private int direction;
	private int team;
	private boolean alive;
	private BufferedImage sprite;
	public Enemy(int x,int y) {
		this.x=x;
		this.y=y;
		team=-1;
		alive = true;
	}
	public void contact(GameObject o) {
		if(o.getTeam()==1){
			die();
		}
	}
	public void die(){
		alive = false;
	}
	public boolean alive() {
		return alive;
	} 
	public int[] getPos() {
		return new int[]{this.x, this.y };
	}
	public BufferedImage getSprite(){
		return sprite;
	}
}
