import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class Game implements Runnable 
{
	private ArrayList<GameObject> things = new ArrayList<GameObject>();
	private ArrayList<GameObject> spawnQueue = new ArrayList<GameObject>();
	private boolean[] f = new boolean[10];
	private int width;
	private int height;
	private final float nanoConstant = 1000000000 / 60.0f;
	private long lastTime = System.nanoTime();
	private long milliTime = System.currentTimeMillis();
	private int fpscount = 0;
	private int count = 0;
	private int gameOver = 2;
	private boolean menu=true;
	private BufferedImage background;

	public boolean[] getKeys() {
		return f;
	}

	public int getWidth() {
		return width;
	}

	public void queueSpawn(GameObject o) {
		spawnQueue.add(o);
	}

	private void tick(JPanel screen) {
		lives();
		runThings();
		spawner();
		spawnThings();
		hitTest();
		removeThings();
		drawThings(screen);
		count++;
	}

	private void lives() {
	
		for (GameObject x : things) {
			if (x.getClass() == Player.class) {
				for (GameObject y : things) {
					if (y.getTeam() == x.getTeam()) {
						 if (y.getClass() == Lives.class) {
							((Lives) y).setLives(((Player) x).getLives());
						}
					}
				}
			}
		}
	}

	private void spawnThings() {
		for (GameObject x : spawnQueue) {
			things.add(x);
		}
		spawnQueue.clear();
	}

	private void spawner() {
		if (count % 90 == 0) {
			queueSpawn(new Ship((int) (Math.random() * width), -10, 1, this));
		}
		if (count % 159 == 0) {
			queueSpawn(new UFO((int) (Math.random() * width),
					(int) (Math.random() * height / 10 + 40), 1, this));
		}
		if ((count + 1) % 900 == 0) {
			queueSpawn(new PowerUp((int) (Math.random() * width),
					(int) (Math.random() * height / 2 + height / 2),
					(int) (Math.random() * 4 + 1)));
		}
	}

	private void drawThings(JPanel s) {
		Graphics2D g1 = (Graphics2D) s.getGraphics();
		BufferedImage buffer = new BufferedImage(s.getWidth(), s.getHeight(),
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D) buffer.getGraphics();
		g.drawImage(background, 0, -background.getHeight() + height + count,
				null);
		if (gameOver<=0) {
			BufferedImage lose = null;
			try {
				lose = ImageIO.read(new File(
						"Images\\sprites\\Game_Over_Screen.png"));
			} catch (IOException e) {

			}
			g.drawImage(lose, width / 2 - lose.getWidth() / 2, height / 2
					- lose.getHeight() / 2, null);
		} else {
			for (GameObject x : things) {
				g.drawImage(x.getSprite(), x.getPos()[0]
						- x.getSprite().getWidth() / 2, x.getPos()[1]
						- x.getSprite().getHeight() / 2, null);

			}
		}
		g1.drawImage(buffer, 0, 0, null);
	}

	private void runThings() {
		for (GameObject x : things) {
			x.run();
		}
	}

	private void removeThings() {
		ArrayList<GameObject> temp = new ArrayList<GameObject>();
		for (GameObject x : things) {
			if (!x.alive()) {
				if (x.getClass() == Player.class) {
					gameOver--;
				}
				temp.add(x);
			}
		}
		things.removeAll(temp);
	}

	private void hitTest() {
		for (GameObject o : things) {

			for (GameObject x : things) {
				if (x.getTeam() != o.getTeam()&&o.getClass()!=Lives.class&&x.getClass()!=Lives.class) {
					if ((x.getPos()[0]) < (o.getPos()[0] + o.getSprite()
							.getWidth() / 2)
							&& (x.getPos()[0]) > (o.getPos()[0] - o.getSprite()
									.getWidth() / 2)
							&& (x.getPos()[1]) < (o.getPos()[1] + o.getSprite()
									.getHeight() / 2)
							&& (x.getPos()[1]) > (o.getPos()[1] - o.getSprite()
									.getHeight() / 2)) {

						o.contact(x);
					}

				}
			}
		}
	}

	private void listen(JComponent frame) {
		frame.getInputMap().put(KeyStroke.getKeyStroke("LEFT"),
				"leftarrowPressed");
		frame.getActionMap().put("leftarrowPressed", leftPressed);
		frame.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),
				"rightarrowPressed");
		frame.getActionMap().put("rightarrowPressed", rightPressed);
		frame.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upPressed");
		frame.getActionMap().put("upPressed", upPressed);
		frame.getInputMap().put(KeyStroke.getKeyStroke("DOWN"),
				"downarrowPressed");
		frame.getActionMap().put("downarrowPressed", downPressed);
		frame.getInputMap().put(KeyStroke.getKeyStroke("SLASH"), "slashPressed");
		frame.getActionMap().put("slashPressed", firePressed);

		frame.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"),
				"leftReleased");
		frame.getActionMap().put("leftReleased", leftReleased);
		frame.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"),
				"rightReleased");
		frame.getActionMap().put("rightReleased", rightReleased);
		frame.getInputMap().put(KeyStroke.getKeyStroke("released UP"),
				"upReleased");
		frame.getActionMap().put("upReleased", upReleased);
		frame.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"),
				"downReleased");
		frame.getActionMap().put("downReleased", downReleased);
		frame.getInputMap().put(KeyStroke.getKeyStroke("released SLASH"),
				"slashReleased");
		frame.getActionMap().put("slashReleased", fireReleased);
		frame.getInputMap().put(KeyStroke.getKeyStroke("A"), "aPressed");
		frame.getActionMap().put("aPressed", leftPressed2);
		frame.getInputMap().put(KeyStroke.getKeyStroke("D"), "dPressed");
		frame.getActionMap().put("dPressed", rightPressed2);
		frame.getInputMap().put(KeyStroke.getKeyStroke("W"), "wPressed");
		frame.getActionMap().put("wPressed", upPressed2);
		frame.getInputMap().put(KeyStroke.getKeyStroke("S"), "sPressed");
		frame.getActionMap().put("sPressed", downPressed2);
		frame.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),
				"spacebarPressed");
		frame.getActionMap().put("spacebarPressed", firePressed2);
		frame.getInputMap().put(KeyStroke.getKeyStroke("released A"),
				"aReleased");
		frame.getActionMap().put("aReleased", leftReleased2);
		frame.getInputMap().put(KeyStroke.getKeyStroke("released D"),
				"dReleased");
		frame.getActionMap().put("dReleased", rightReleased2);
		frame.getInputMap().put(KeyStroke.getKeyStroke("released W"),
				"wReleased");
		frame.getActionMap().put("wReleased", upReleased2);
		frame.getInputMap().put(KeyStroke.getKeyStroke("released S"),
				"sReleased");
		frame.getActionMap().put("sReleased", downReleased2);
		frame.getInputMap().put(KeyStroke.getKeyStroke("released SPACE"),
				"spacebarReleased");
		frame.getActionMap().put("spacebarReleased", fireReleased2);

	}

	Action leftPressed = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[0] = true;
		}
	};
	Action rightPressed = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[1] = true;
		}
	};
	Action upPressed = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[2] = true;
		}
	};
	Action downPressed = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[3] = true;
		}
	};
	Action firePressed = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[4] = true;
		}
	};
	Action leftReleased = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[0] = false;
		}
	};
	Action rightReleased = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[1] = false;
		}
	};
	Action upReleased = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[2] = false;
		}
	};
	Action downReleased = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[3] = false;
		}
	};
	Action fireReleased = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[4] = false;
		}
	};
	Action leftPressed2 = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[5] = true;
		}
	};
	Action rightPressed2 = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[6] = true;
		}
	};
	Action upPressed2 = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[7] = true;
		}
	};
	Action downPressed2 = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[8] = true;
		}
	};
	Action firePressed2 = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[9] = true;
		}
	};
	Action leftReleased2 = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[5] = false;
		}
	};
	Action rightReleased2 = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[6] = false;
		}
	};
	Action upReleased2 = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[7] = false;
		}
	};
	Action downReleased2 = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[8] = false;
		}
	};
	Action fireReleased2 = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			f[9] = false;
		}
	};

	public static void main(String[] args) {
		Thread t = new Thread(new Game());
		t.start();
	}

	public void run() {
		JFrame frame = new JFrame("Battle Battle Battle");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double height = screenSize.getHeight();
		double width = screenSize.getWidth();
		frame.setSize((int) (height * 3 / 4), (int) height);
		frame.setPreferredSize(new Dimension((int) (height * 3 / 4),
				(int) height));
		this.width = (int) (height * 3 / 4);
		this.height = (int) height;
		frame.setResizable(false);
		frame.setLocation((int) (width / 2 - frame.getWidth() / 2),
				(int) (height / 2 - frame.getHeight() / 2));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		listen((JPanel) frame.getContentPane());
		JPanel screen = new JPanel();
		frame.add(screen);
		frame.pack();
		queueSpawn(new Player((frame.getWidth() / 2-50),
				(frame.getHeight() / 4 * 3), 2, this));
		queueSpawn(new Player((frame.getWidth() / 2+50),
				(frame.getHeight() / 4 * 3), 1, this));
		queueSpawn(new Lives((20),
				(frame.getHeight() / 12*11), 2));
		queueSpawn(new Lives((frame.getWidth() -220),
				(frame.getHeight() / 12 * 11), 1));

		try {
			background = ImageIO.read(new File(
					"Images\\sprites\\Background\\StarBackgroundBig.png"));
		} catch (IOException e) {

		}
		while (true) {
			long curTime = System.nanoTime();
			long curMilli = System.currentTimeMillis();

			if (curTime - lastTime >= nanoConstant) {
				fpscount++;
				lastTime = curTime;
				tick(screen);
			}
			if (curMilli - milliTime >= 1000) {
				//System.out.println(fpscount);
				fpscount = 0;
				milliTime = curMilli;
			}

		}

	}
}
