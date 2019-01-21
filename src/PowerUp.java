import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PowerUp extends GameObject {
	private int x;
	private int y;
	private int team;
	private boolean alive;
	private BufferedImage sprite;
	private int type;//1 is fan, 2 is rapid fire, 3 is invincibility, 4 is life
	public PowerUp(int x, int y, int type) {
		this.x=x;
		this.y=y;
		this.type=type;
		team=0;
		alive=true;
		if(type==1){
			try{
				sprite=ImageIO.read(new File("Images\\sprites\\powerupRed_star.png"));
			}catch (IOException e){
				
			}
		}
		else if(type==2){
			try{
				sprite=ImageIO.read(new File("Images\\sprites\\powerupRed_bolt.png"));
			}catch (IOException e){
				
			}
		}
		else if(type==3){
			try{
				sprite=ImageIO.read(new File("Images\\sprites\\powerupRed_shield.png"));
			}catch (IOException e){
				
			}
		}
		else if(type==4){
			try{
				sprite=ImageIO.read(new File("Images\\sprites\\powerupRed_1.png"));
			}catch (IOException e){
				
			}
		}
	}
	public void contact(GameObject o) {
	}
	public void die(){
		alive = false;
	}
	public boolean alive() {
		return alive;
	}
	public int getTeam() {
		return team;
	}
	public int getType() {
		return type;
	}
	public int[] getPos() {
		return new int[]{this.x, this.y };
	}
	public BufferedImage getSprite(){
		return sprite;
	}
}
