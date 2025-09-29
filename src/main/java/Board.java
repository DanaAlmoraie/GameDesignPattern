import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
 import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * @author
 */
public class Board extends JPanel implements Runnable, Commons {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Dimension d;
	private ArrayList<Sprite> aliens; // changed declaration type to Sprite
	private Sprite player;            // changed declaration type to Sprite
	private Sprite shot;              // changed declaration type to Sprite
	private Sprite gameend;           // changed declaration type to Sprite
	private Sprite vunnet;            // changed declaration type to Sprite

	private int alienX = 150;
	private int alienY = 25;
	private int direction = -1;
	private int deaths = 0;

	private boolean ingame = true;
	private boolean havewon = true;
	private final String expl = "/img/explosion.png";
	private final String alienpix = "/img/alien.png";
	private String message = "Seu planeta nos pertence agora...";

	private Thread animator;

	/*
	 * Constructor
	 */
	public Board() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		d = new Dimension(BOARD_WIDTH, BOARD_HEIGTH);
		setBackground(Color.black);

		gameInit();
		setDoubleBuffered(true);
	}

	public void addNotify() {
		super.addNotify();
		gameInit();
	}

	// public void gameInit() {
	// 	aliens = new ArrayList<Sprite>(); // added <Sprite> for generic type

	// 	ImageIcon ii = new ImageIcon(this.getClass().getResource(alienpix));

	// 	for (int i = 0; i < 4; i++) {
	// 		for (int j = 0; j < 6; j++) {
	// 			Sprite alien = SpriteFactory.createSprite("alien", alienX + 18 * j, alienY + 18 * i); // used factory instead of new Alien()
	// 			alien.setImage(ii.getImage());
	// 			aliens.add(alien);
	// 		}
	// 	}

	// 	player = SpriteFactory.createSprite("player", 0, 0); // used factory instead of new Player()
	// 	shot = SpriteFactory.createSprite("shot", 0, 0); // used factory instead of new Shot()

	// 	if (animator == null || !ingame) {
	// 		animator = new Thread(this);
	// 		animator.start();
	// 	}
	// }

public void gameInit() {
    aliens = new ArrayList<Sprite>(); // added <Sprite> for generic type

    ImageIcon ii = new ImageIcon(this.getClass().getResource(alienpix));
    Image alienImg = ii.getImage();

    SpriteFactory.initPrototypeAlien(alienX, alienY, alienImg);

    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 6; j++) {
            Sprite alien = (Alien) SpriteFactory.createSprite(
                "alien",
                alienX + 18 * j,
                alienY + 18 * i
            ); // used factory instead of new Alien()
            aliens.add(alien); 
        }
    }
	player = SpriteFactory.createSprite("player", 0, 0); // used factory instead of new Player()
	shot = SpriteFactory.createSprite("shot", 0, 0); // used factory instead of new Shot()

		if (animator == null || !ingame) {
			animator = new Thread(this);
			animator.start();
		}
}

	public void drawAliens(Graphics g) {
		Iterator<Sprite> it = aliens.iterator(); // added <Sprite> for casting

		while (it.hasNext()) {
			Sprite alien = it.next(); // changed declaration type to Sprite

			if (alien.isVisible()) {
				g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
			}

			if (alien.isDying()) {
				alien.die();
			}
		}
	}

	public void drawPlayer(Graphics g) {
		if (player.isVisible()) {
			g.drawImage(player.getImage(), player.getX(), player.getY(), this);
		}

		if (player.isDying()) {
			player.die();
			havewon = false;
			ingame = false;
		}
	}

	public void drawGameEnd(Graphics g) {
		g.drawImage(gameend.getImage(), 0, 0, this);
	}

	public void drawShot(Graphics g) {
		if (shot.isVisible())
			g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
	}

	public void drawBombing(Graphics g) {
		Iterator<Sprite> i3 = aliens.iterator(); // added <Sprite> for casting

		while (i3.hasNext()) {
			Sprite a = i3.next(); // changed declaration type to Sprite

			Sprite b = ((Alien) a).getBomb(); // added cast (Alien) and changed the delare type to sprite

			if (!((Bomb)b).isDestroyed()) { // added the cast (Bomb)
				g.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		g.setColor(Color.black);
		g.fillRect(0, 0, d.width, d.height);
		g.setColor(Color.green);

		if (ingame) {

			g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
			drawAliens(g);
			drawPlayer(g);
			drawShot(g);
			drawBombing(g);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void gameOver() {
		Graphics g = this.getGraphics();

		gameend = SpriteFactory.createSprite("gameover", 0, 0); // used factory instead of new GameOver()
		vunnet = SpriteFactory.createSprite("won", 0, 0); // used factory instead of new Won()

		// g.setColor(Color.black);
		g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGTH);
		if (havewon == true) {
			g.drawImage(vunnet.getImage(), 0, 0, this);
		} else {
			g.drawImage(gameend.getImage(), 0, 0, this);
		}
		g.setColor(new Color(0, 32, 48));
		g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
		g.setColor(Color.white);
		g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
				BOARD_WIDTH / 2);
	}

	public void animationCycle() {
		if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
			ingame = false;
			message = "Parab�ns! Voc� salvou a gal�xia!";
		}

		// player

		((Player) player).act(); // added cast (Player)

		// shot
		if (shot.isVisible()) {
			Iterator<Sprite> it = aliens.iterator(); // added <Sprite> for casting
			int shotX = shot.getX();
			int shotY = shot.getY();

			while (it.hasNext()) {
				Sprite alien = it.next(); // changed declaration type to Sprite
				int alienX = alien.getX();
				int alienY = alien.getY();

				if (alien.isVisible() && shot.isVisible()) {
					if (shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH)
							&& shotY >= (alienY)
							&& shotY <= (alienY + ALIEN_HEIGHT)) {
						ImageIcon ii = new ImageIcon(getClass().getResource(
								expl));
						alien.setImage(ii.getImage());
						alien.setDying(true);
						deaths++;
						shot.die();
					}
				}
			}

			int y = shot.getY();
			y -= 8;
			if (y < 0)
				shot.die();
			else
				shot.setY(y);
		}

		// aliens

		Iterator<Sprite> it1 = aliens.iterator(); // added <Sprite> for casting

		while (it1.hasNext()) {
			Sprite a1 = it1.next(); // changed declaration type to Sprite
			int x = a1.getX();

			if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
				direction = -1;
				Iterator<Sprite> i1 = aliens.iterator(); // added <Sprite> for casting
				while (i1.hasNext()) {
					Sprite a2 = i1.next(); // changed declaration type to Sprite
					a2.setY(a2.getY() + GO_DOWN);
				}
			}

			if (x <= BORDER_LEFT && direction != 1) {
				direction = 1;

				Iterator<Sprite> i2 = aliens.iterator(); // added <Sprite> for casting
				while (i2.hasNext()) {
					Sprite a = i2.next(); // changed declaration type to Sprite
					a.setY(a.getY() + GO_DOWN);
				}
			}
		}

		Iterator<Sprite> it = aliens.iterator(); // added <Sprite> for casting

		while (it.hasNext()) {
			Sprite alien = it.next(); // changed declaration type to Sprite
			if (alien.isVisible()) {

				int y = alien.getY();

				if (y > GROUND - ALIEN_HEIGHT) {
					havewon = false;
					ingame = false;
					message = "Aliens est�o invadindo a gal�xia!";
				}

				((Alien) alien).act(direction); // added cast (Alien)
			}
		}

		// bombs

		Iterator<Sprite> i3 = aliens.iterator(); // added <Sprite> for casting
		Random generator = new Random();

		while (i3.hasNext()) {
			int shot = generator.nextInt(15);
			Sprite a = i3.next(); // changed declaration type to Sprite
			Sprite b = ((Alien) a).getBomb();  // added cast (Alien)
			if (shot == CHANCE && a.isVisible() && ((Bomb)b).isDestroyed()) { // added the cast (Bomb)

				((Bomb)b).setDestroyed(false); // added the cast (Bomb)
				b.setX(a.getX());
				b.setY(a.getY());
			}

			int bombX = b.getX();
			int bombY = b.getY();
			int playerX = player.getX();
			int playerY = player.getY();

			if (player.isVisible() && !((Bomb)b).isDestroyed()) { // added the cast (Bomb)
				if (bombX >= (playerX) && bombX <= (playerX + PLAYER_WIDTH)
						&& bombY >= (playerY)
						&& bombY <= (playerY + PLAYER_HEIGHT)) {
					ImageIcon ii = new ImageIcon(this.getClass().getResource(
							expl));
					player.setImage(ii.getImage());
					player.setDying(true);
					((Bomb)b).setDestroyed(true); // added the cast (Bomb)
					;
				}
			}

			if (!((Bomb)b).isDestroyed()) { // added the cast (Bomb)
				b.setY(b.getY() + 1);
				if (b.getY() >= GROUND - BOMB_HEIGHT) {
					((Bomb)b).setDestroyed(true); // added the cast (Bomb)
				}
			}
		}
	}

	public void run() {
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (ingame) {
			repaint();
			animationCycle();

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep < 0)
				sleep = 1;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
			beforeTime = System.currentTimeMillis();
		}
		gameOver();
	}

	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			((Player) player).keyReleased(e); // added cast (Player)
		}

		public void keyPressed(KeyEvent e) {

			((Player) player).keyPressed(e); // added cast (Player)

			int x = player.getX();
			int y = player.getY();

			if (ingame) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_SPACE) {

					if (!shot.isVisible())
						shot = SpriteFactory.createSprite("shot", x, y); // used factory instead of new Shot()
				}
			}
		}
	}
}