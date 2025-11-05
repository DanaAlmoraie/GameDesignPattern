import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author 
*/
public class SpaceInvaders extends JFrame implements Commons {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4905230094675077405L;

	private JButton start, help;
	private JComboBox<String> skinSelector;
	
	/*
	 * Inicio
	 */
	private static final String TOP_MESSAGE = "Space Invaders <br> Java Version";
	private static final String INITIAL_MESSAGE = "Help us, Captain Awesome!!"
			+ "<br>The aliens are trying to invade our planet."
			+ "<br><br><br>Your mission:"
			+ "<br><br>Kill all the invading aliens before they can invade planet Earth."
			+ "<br>And, preferably, don't die during the battle!!"
			+ "<br><br><br>GOOD LUCK!!!";
	/*
	 * Help
	 */
	private static final String HELP_TOP_MESSAGE = "Help";
	private static final String HELP_MESSAGE = "Controls: " 
							+ "<br><br>Movement Left: <br>Left Arrow on the keyboard"
							+ "<br><br>Movement Right: <br>Right Arrow on the keyboard"
							+ "<br><br>Shoot: <br>Space bar";

	JFrame frame = new JFrame("Space Invaders");
	JFrame frame2 = new JFrame("Space Invaders");
	JFrame frame3 = new JFrame("Help");

	/*
	 * Constructor
	 */
	public SpaceInvaders() {
		String topmessage = "<html><br><br>" + TOP_MESSAGE + "</html>";
		String message = "<html>" + INITIAL_MESSAGE + "</html>";

		start = new JButton("Start Mission");
		start.addActionListener(new ButtonListener());
		start.setBounds(800, 800, 200, 100);

		help = new JButton("Help");
		help.addActionListener(new HelpButton());

		JLabel tekst = new JLabel(message, SwingConstants.CENTER);
		JLabel toptekst = new JLabel(topmessage, SwingConstants.CENTER);

		Font font = new Font("Helvetica", Font.BOLD, 12);
		tekst.setFont(font);

		Font font2 = new Font("Helvetica", Font.BOLD, 20);
		toptekst.setFont(font2);

		//List to choose the theme
		String[] skins = {"Classic", "Hello Kitty", "Batman"};
        skinSelector = new JComboBox<>(skins);

        JPanel optionsPanel = new JPanel();
        optionsPanel.add(new JLabel("Choose theme:"));
        optionsPanel.add(skinSelector);

		frame2.setTitle("Space Invaders");

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(tekst, BorderLayout.CENTER);
		centerPanel.add(optionsPanel, BorderLayout.SOUTH);

		centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

		frame2.add(toptekst, BorderLayout.PAGE_START);

		frame2.add(centerPanel, BorderLayout.CENTER);

		JPanel nedredel = new JPanel();
		nedredel.add(help);
		nedredel.add(start);


		frame2.add(nedredel, BorderLayout.PAGE_END);
		frame2.setSize(500, 500);
		frame2.setLocationRelativeTo(null);
		frame2.setVisible(true);
		frame2.setResizable(false);

	}

	public void closeIntro() {
		frame2.dispose();
		frame3.dispose();
	}

	public void closeHelp() {
		frame3.dispose();
	}

	/*
	 * Main
	 */
	public static void main(String[] args) {
		new SpaceInvaders();
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			String selectedTheme = (String) skinSelector.getSelectedItem();


			// Send the selected theme to the Sprite
			if (selectedTheme.equalsIgnoreCase("Hello Kitty")) {
				Sprite.setTheme(new HelloKittyTheme());
			} else if (selectedTheme.equalsIgnoreCase("Classic")) {
				Sprite.setTheme(new ClassicTheme());
			} else if (selectedTheme.equalsIgnoreCase("Batman")) {
				Sprite.setTheme(new BatmanTheme());
			}

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(BOARD_WIDTH, BOARD_HEIGTH);
			frame.getContentPane().add(new Board()); 
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			closeIntro();

		}
	}

	private class CloseHelp implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			closeHelp();
		}
	}

	private class HelpButton implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JButton close = new JButton("Close");
			close.addActionListener(new CloseHelp());

			String topmessage = "<html><br>" + HELP_TOP_MESSAGE + "</html>";
			String message = "<html>" + HELP_MESSAGE + "</html> ";
			JLabel tekst = new JLabel(message, SwingConstants.CENTER);
			JLabel toptekst = new JLabel(topmessage, SwingConstants.CENTER);

			Font font = new Font("Helvetica", Font.BOLD, 12);
			tekst.setFont(font);

			Font font2 = new Font("Helvetica", Font.BOLD, 20);
			toptekst.setFont(font2);

			frame3.add(tekst);

			frame3.add(toptekst, BorderLayout.PAGE_START);

			frame3.add(close, BorderLayout.PAGE_END);
			frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame3.setSize(250, 290);
			frame3.setResizable(false);
			frame3.setLocationRelativeTo(null);
			frame3.setVisible(true);
		}
	}
}