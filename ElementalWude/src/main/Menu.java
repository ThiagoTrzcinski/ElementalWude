package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Menu {

	private BufferedImage backgroundMenu;

	public Menu() {
		try {
			backgroundMenu = ImageIO.read(getClass().getResource("/Menubackground.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {
		// Draw the background
		g.drawImage(backgroundMenu, 0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE, null);

		g.setFont(new Font("Arial", Font.BOLD, 60));
		g.setColor(Color.WHITE);

		// Calculates the position of the title and options
		String title = "Elemental Wude";
		String playOption = "1 - Play";
		String exitOption = "2 - Exit";

		int titleWidth = g.getFontMetrics().stringWidth(title);
		int playWidth = g.getFontMetrics().stringWidth(playOption);
		int exitWidth = g.getFontMetrics().stringWidth(exitOption);

		int screenWidth = Game.WIDTH * Game.SCALE;
		int screenHeight = Game.HEIGHT * Game.SCALE;

		int titleX = (screenWidth - titleWidth) / 2;
		int playX = (screenWidth - playWidth) / 2;
		int exitX = (screenWidth - exitWidth) / 2;

		int titleY = screenHeight / 3;
		int playY = titleY + 100;
		int exitY = playY + 60;

		// Draws the shadows in the title
		g.setColor(Color.BLACK);
		g.drawString(title, titleX + 2, titleY + 2);

		// Draws title
		g.setColor(Color.WHITE);
		g.drawString(title, titleX, titleY);

		
		g.setFont(new Font("Arial", Font.BOLD, 40));
		// Draws the shadows in the title
		g.setColor(Color.BLACK);
		g.drawString(playOption, playX + 2, playY + 2);
		g.drawString(exitOption, exitX + 2, exitY + 2);

		// Draws options
		g.setColor(Color.WHITE);
		g.drawString(playOption, playX, playY);
		g.drawString(exitOption, exitX, exitY);
	}
}
