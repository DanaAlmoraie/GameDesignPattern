import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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

	private Thread animator;

    // ====== State pattern fields ======
    private GameState playingState;
    private GameState pausedState;
    private GameState currentState;

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

	public void gameInit() {
		aliens = new ArrayList<Sprite>(); // added <Sprite> for generic type

			// Clone the prototype Alien to fill the game grid
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				Sprite alien = (Alien) Sprite.createSprite("alien", alienX + 18 * j,alienY + 18 * i); // used factory instead of new Alien()
				aliens.add(alien); // add to list
			}
		}
		player = Sprite.createSprite("player", 0, 0); // used factory instead of new Player()
		shot = Sprite.createSprite("shot", 0, 0); // used factory instead of new Shot()

		        // ====== إنشاء الحالات (State objects) ======
			playingState = new PlayingState(this);
			pausedState  = new PausedState(this);
			currentState = playingState;

			ingame = true;
			havewon = true;
			deaths = 0;


		if (animator == null || !animator.isAlive()) {
			animator = new Thread(this);
			animator.start();
		}
	}

	    // ====== State helpers ======
    public void setState(GameState state) {
        this.currentState = state;
    }

    public GameState getPlayingState() {
        return playingState;
    }

    public GameState getPausedState() {
        return pausedState;
    }

    // تُستدعى من TogglePauseCommand
    public void togglePause() {
    if (currentState == playingState) {
			((Player) player).stopMoving(); // added cast (Player)
			setState(pausedState);
		} else if (currentState == pausedState) {
			setState(playingState);
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
			Sprite a = i3.next(); // changed declaration type from to Sprite 

			Sprite b = ((Alien) a).getBomb(); // added cast (Alien) and changed the delare type to sprite

			if (!((Bomb)b).isDestroyed()) { // added the cast (Bomb)
				g.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		//Bridge: set the background color according to theme
		g.setColor(Sprite.getTheme().getBackgroundColor());
		g.fillRect(0, 0, d.width, d.height);
		g.setColor(Color.green);

		if (ingame) {

			g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
			drawAliens(g);
			drawPlayer(g);
			drawShot(g);
			drawBombing(g);

			// عرض كلمة PAUSED لو اللعبة متوقفة
            if (currentState == pausedState) {
                g.setColor(Color.WHITE);
                g.drawString("PAUSED (press P to resume)", 10, 20);
            }

		}

		Toolkit.getDefaultToolkit().sync();
	}

	public void gameOver() {
		Graphics g = this.getGraphics();

		gameend = Sprite.createSprite("gameover", 0, 0);
		vunnet = Sprite.createSprite("won", 0, 0);

		g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGTH);
		if (havewon) {
			g.drawImage(vunnet.getImage(), 0, 0, this);
		} else {
			g.drawImage(gameend.getImage(), 0, 0, this);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}


	public void animationCycle() {
		if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
			ingame = false;
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
			
			// ======= استدعاء update حسب الحالة الحالية (State pattern) =======
		    currentState.update();

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
		private Command command;

		public void keyReleased(KeyEvent e) {
            if (!ingame) return;

			int key = e.getKeyCode();

			switch (key) {
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_RIGHT:
					command = new StopMoveCommand((Player)player);
					break;
				default:
					command = null;
			}

			if (command != null) {
            	currentState.handleInput(command);
        	}

        }


		    public void keyPressed(KeyEvent e) {

				int key = e.getKeyCode();

				if (ingame) {
					// إطلاق النار يبقى كما هو
					int x = player.getX();
					int y = player.getY();

					if (key == KeyEvent.VK_SPACE) {
						if (!shot.isVisible())
							shot = Sprite.createSprite("shot", x, y); // used factory instead of new Shot()
					}

					switch (key) {
						case KeyEvent.VK_LEFT:
							command = new MoveLeftCommand((Player)player);
							break;
						case KeyEvent.VK_RIGHT:
							command = new MoveRightCommand((Player)player);
							break;
						case KeyEvent.VK_P:      // Pause / Resume
							command = new TogglePauseCommand(Board.this);
							break;
						default:
							command = null;
					}

					if (command != null) {
						currentState.handleInput(command);
					}
				}
			}
	}
}