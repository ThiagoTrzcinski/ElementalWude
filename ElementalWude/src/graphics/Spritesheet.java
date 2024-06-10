package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	private BufferedImage spritesheet;

	public Spritesheet(String path) {
		try {
			spritesheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getSprite(int x, int y, int width, int height) {
		// Check if the coordinates and dimensions are within the image limits
		if (x < 0 || y < 0 || x + width > spritesheet.getWidth() || y + height > spritesheet.getHeight()) {
			throw new IllegalArgumentException("Invalid coordinates or dimensions for subimage.");
		}
		return spritesheet.getSubimage(x, y, width, height);
	}
}
