package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Entity {
	private int x;
	private int y;
	private int width;
	private int height;

	private BufferedImage sprite;

	// Used to set the speed of the animation
	protected int frames = 0;
	protected int Maxframes = 5;

	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	public void tick() {
		//Basic logic
	}

	public void render(Graphics g) {
		//Basic logic for rendering
		g.drawImage(sprite, this.getX(), this.getY(), null);
	}

}
