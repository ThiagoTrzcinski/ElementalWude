package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import decorators.CharacterDecorator;
import decorators.DefenseBoost;
import entities.Characters;
import entities.Enemy;
import entities.Entity;
import entities.Player;
import factories.EnemyFactory;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	static int WIDTH = 200;
	static int HEIGHT = 120;
	static int SCALE = 5;

	private BufferedImage image;
	private BufferedImage backgroundImage;
	private boolean playersTurn = true;
	int turns = 1;

	public static String GameState = "Menu"; // Menu | Game | GameOver | Win
	public boolean GameExit = false; // Used to quit the game
	public Menu menu;

	public List<Entity> entities;
	List<Enemy> enemies1 = new ArrayList<>();
	private Characters player;

	public Game() {
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		// Initializing objects
		menu = new Menu();

		EnemyFactory enemyFactory = new EnemyFactory();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();

		player = new Player(-105, -50, 288, 128, "Hero", 50, 20, 10, 1, null, null, null); // Adjusted position
		Enemy hasha = enemyFactory.create("Hashashin");
		hasha.setX(player.getX() + 115); // Adjusted position
		hasha.setY(player.getY() - 7); // Adjusted position

		entities.add(player);
		entities.add(hasha);

		enemies1.add(hasha);

		try {
			backgroundImage = ImageIO.read(getClass().getResource("/battle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initFrame() {
		frame = new JFrame("Elemental Wude");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public void tick() {
		// Game logic, test what GameState the program should be and control player and
		// enemy turns
		if (!player.isAlive()) {
			player.setLife(0);
			GameState = "GameOver";
		}
		if (GameState.equals("Game")) {
			GameExit = false;
			if (!playersTurn && !player.isActing() && enemies1.get(0).isAlive()) {
				if (!enemies1.isEmpty()) {
					enemies1.get(0).act(player);
					playersTurn = true;
				}
			}
			if (player.isAlive() && !enemies1.get(0).isAlive()) {
				enemies1.get(0).setLife(0);
				GameState = "Win";
			}

			for (Entity entity : entities) {
				entity.tick();
			}
		}
		if (GameExit) {
			System.exit(0);
		}
	}

	public void render() {
		// Render logic, draws objects based on the GameState
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Draw the background image
		g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);

		// Draw a semi-transparent rectangle over the background
		g.setColor(new Color(0, 0, 0, 90)); // 128 is the alpha value (0-255)
		g.fillRect(0, 0, WIDTH, HEIGHT);

		for (Entity entity : entities) {
			entity.render(g);
		}

		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

		drawUI(g);

		if (GameState.equals("GameOver")) {
			Graphics g2 = g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			g.setFont(new Font("Arial", Font.BOLD, 48));
			g.setColor(Color.white);
			g.drawString("Game Over", (WIDTH * SCALE) / 2 - 125, (HEIGHT * SCALE) / 2 - 80);
			g.setFont(new Font("Arial", Font.BOLD, 38));
			g.drawString(">Press enter to exit<", (WIDTH * SCALE) / 2 - 183, (HEIGHT * SCALE) / 2);
		} else if (GameState.equals("Win")) {
			Graphics g2 = g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			g.setFont(new Font("Arial", Font.BOLD, 48));
			g.setColor(Color.white);
			g.drawString("You Won!", (WIDTH * SCALE) / 2 - 125, (HEIGHT * SCALE) / 2 - 80);
			g.setFont(new Font("Arial", Font.BOLD, 38));
			g.drawString(">Press enter to exit<", (WIDTH * SCALE) / 2 - 183, (HEIGHT * SCALE) / 2);
		} else if (GameState.equals("Menu")) {
			menu.render(g);
		}

		bs.show();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				frames = 0;
				timer += 1000;
			}
		}
		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Takes input from the keyboard and performs the action according to the button
		// and GameState
		if (e.getKeyCode() == KeyEvent.VK_1) {
			if (GameState.equals("Game")) {
				turns++;
				if (playersTurn && !enemies1.get(0).isActing()) {
					if (player.getAttack().getCost() <= player.getEnergy()) {
						System.out.println(player.getDefense());
						player.setEnergy(player.getEnergy() - player.getAttack().getCost());
						player.attack(enemies1.get(0));
						updatePlayerDecorators();
						if (!(player instanceof CharacterDecorator)) {
							player = new DefenseBoost(player, 2, 2);
						}

						System.out.println(player.getDefense());

						playersTurn = false;
					} else {
						System.out.println("You Don't have enough energy to use " + player.getAttack().getName());
					}
				}
			} else if (GameState.equals("Menu")) {
				GameState = "Game";
			}

		} else if (e.getKeyCode() == KeyEvent.VK_2) {
			if (GameState.equals("Game")) {
				if (playersTurn && !enemies1.get(0).isActing()) {
					turns++;
					if (playersTurn && !enemies1.get(0).isActing()) {
						if (player.getStrongAttack().getCost() <= player.getEnergy()) {
							player.strongAttack(enemies1.get(0));
							updatePlayerDecorators();

							if (!(player instanceof CharacterDecorator)) {
								player = new DefenseBoost(player, 3, 2);
							}

							playersTurn = false;
						} else {
							System.out.println(
									"You Don't have enough energy to use " + player.getStrongAttack().getName());
						}

					}
				}
			} else if (GameState.equals("Menu")) {
				GameExit = true;
			}

		} else if (e.getKeyCode() == KeyEvent.VK_3) {
			if (GameState.equals("Game")) {
				if (playersTurn && !enemies1.get(0).isActing()) {
					turns++;
					if (playersTurn && !enemies1.get(0).isActing()) {
						if (player.getMagicalAttack().getCost() <= player.getEnergy()) {
							player.magicalAttack(enemies1.get(0));
							updatePlayerDecorators();
							playersTurn = false;
						} else {
							System.out.println(
									"You Don't have enough energy to use " + player.getMagicalAttack().getName());
						}

					}
				}
			}

		} else if (e.getKeyCode() == KeyEvent.VK_4) {
			if (GameState.equals("Game")) {
				if (playersTurn && !enemies1.get(0).isActing()) {
					System.out.println("turn: " + turns);
					turns++;
					updatePlayerDecorators();
					player.rest();
					playersTurn = false;
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER && (GameState.equals("GameOver") || GameState.equals("Win"))) {
			GameExit = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	private void updatePlayerDecorators() {
		// Update player decorator, reducing turns and calling the function to remove
		// expired ones
		if (player instanceof CharacterDecorator) {
			List<CharacterDecorator> expiredDecorators = new ArrayList<>();
			Characters current = player;

			while (current instanceof CharacterDecorator) {
				CharacterDecorator decorator = (CharacterDecorator) current;
				decorator.reduceTurn();
				if (decorator.isExpired()) {
					System.out.println("Buff expirou");
					expiredDecorators.add(decorator);
				}
				current = decorator.getDecoratedCharacter();
			}

			for (CharacterDecorator decorator : expiredDecorators) {
				removeDecorator(decorator);
			}
		}
	}

	private void removeDecorator(CharacterDecorator decorator) {
		// Analyzes expired player decorators and removes them, replacing the decorated
		// instance with the DecoratedCharacter
		if (player == decorator) {
			player = decorator.getDecoratedCharacter();
		} else {
			Characters current = player;
			while (current instanceof CharacterDecorator) {
				CharacterDecorator currentDecorator = (CharacterDecorator) current;
				if (currentDecorator.getDecoratedCharacter() == decorator) {
					currentDecorator.decoratedCharacter = decorator.getDecoratedCharacter();
					break;
				}
				current = currentDecorator.getDecoratedCharacter();
			}
		}
	}

	private void drawUI(Graphics g) {
		// Draws the Battle UI
		int uiHeight = (HEIGHT * SCALE / 14) * 5;
		int uiY = HEIGHT * SCALE - uiHeight;

		// Draw UI background for player actions with blue color and transparency
		g.setColor(new Color(0, 0, 255, 128));
		g.fillRect(0, uiY, WIDTH * SCALE / 2, uiHeight);

		// Draw UI background for player and enemy info with blue color and transparency
		g.fillRect(WIDTH * SCALE / 2, uiY, WIDTH * SCALE / 2, uiHeight);

		// Draw turn counter at the top center
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.setColor(Color.WHITE);
		g.drawString("Turn: " + turns, WIDTH * SCALE / 2 - 30, 30);

		// Set font and color for UI text
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.setColor(Color.WHITE);

		int lineHeight = 25;
		int offsetX = 10;

		// Draw player moves on the left with more spacing
		g.drawString("1. Attack", offsetX, uiY + lineHeight);
		g.drawString("2. Strong Attack", offsetX, uiY + lineHeight * 3);
		g.drawString("3. Magical Attack", offsetX, uiY + lineHeight * 5);
		g.drawString("4. Rest", offsetX, uiY + lineHeight * 7);

		// Draw player stats on the right
		int offsetRightX = WIDTH * SCALE / 2 + 10;
		g.drawString("Player:", offsetRightX, uiY + lineHeight);
		drawStatusBar(g, offsetRightX + 60, uiY + lineHeight + 10, player.getLife(), player.getMaxlife(), Color.RED,
				"HP");
		drawStatusBar(g, offsetRightX + 60, uiY + (lineHeight * 2) + 10, player.getEnergy(), player.getMaxenergy(),
				Color.yellow, "Energy");

		// Draw enemy stats on the right
		if (!enemies1.isEmpty()) {
			Enemy enemy = enemies1.get(0);
			g.drawString("Enemy:", offsetRightX, (uiY + lineHeight * 5) - 10);
			drawStatusBar(g, offsetRightX + 60, (uiY + lineHeight * 5), enemy.getLife(), enemy.getMaxlife(), Color.RED,
					"HP");
			drawStatusBar(g, offsetRightX + 60, (uiY + lineHeight * 6), enemy.getEnergy(), enemy.getMaxenergy(),
					Color.yellow, "Energy");
		}

		// Draw dividing lines
		g.setColor(Color.WHITE);
		g.drawLine(WIDTH * SCALE / 2, uiY, WIDTH * SCALE / 2, uiY + uiHeight);
		g.drawLine(0, uiY, WIDTH * SCALE, uiY);
	}

	private void drawStatusBar(Graphics g, int x, int y, int current, int max, Color color, String label) {
		int barWidth = 200;
		int barHeight = 15;
		int filledWidth = (int) ((current / (float) max) * barWidth);

		// Draw label
		g.drawString(label + ": " + current + "/" + max, x - 60, y + barHeight + 5);

		x += 80;
		y += 5;
		// Draw background
		g.setColor(Color.GRAY);
		g.fillRect(x, y, barWidth, barHeight);

		// Draw filled portion
		g.setColor(color);
		g.fillRect(x, y, filledWidth, barHeight);

		// Draw border
		g.setColor(Color.WHITE);
		g.drawRect(x, y, barWidth, barHeight);

	}
}
