// HashashinFactory.java
package factories;

import java.awt.image.BufferedImage;
import java.util.Random;

import entities.Enemy;
import graphics.Spritesheet;
import moves.DesertPower;
import moves.Move;
import moves.RisingFalcon;
import moves.Shariha;

public class HashashinFactory implements Factory {
	//Used to create the enemy Hashashin
	Random rand = new Random();

	//Set the spritesheet and the size of each animations 
	private static Spritesheet Hashashinsheet = new Spritesheet("/HashashinSheet.png");
	private static BufferedImage[] idleHashashin = new BufferedImage[8];
	private static BufferedImage[] attackHashashin = new BufferedImage[8];
	private static BufferedImage[] StrongattackHashashin = new BufferedImage[18];
	private static BufferedImage[] MagicalattackHashashin = new BufferedImage[26];

	@Override
	public Enemy createEnemy() {
		//Set the attacks
		Move attack = new Shariha();
		Move Strongattack = new RisingFalcon();
		Move Magicalattack = new DesertPower();

		//Set the atributes
		int life = rand.nextInt(51 - 40) + 35;
		int energy = rand.nextInt(21 - 10) + 10;
		int strength = rand.nextInt(11 - 6) + 6;
		int defense = rand.nextInt(5 - 3) + 3;

		//Get each animation in the spritesheet
		for (int i = 0; i < 8; i++) {
			idleHashashin[i] = Hashashinsheet.getSprite((29 * 288) - (i * 288), 0, 288, 128);
		}

		for (int i = 0; i < 8; i++) {
			attackHashashin[i] = Hashashinsheet.getSprite((29 * 288) - (i * 288), 128 * 6, 288, 128);
		}
		for (int i = 0; i < 18; i++) {
			StrongattackHashashin[i] = Hashashinsheet.getSprite((29 * 288) - (i * 288), 128 * 7, 288, 128);
		}
		for (int i = 0; i < 26; i++) {
			MagicalattackHashashin[i] = Hashashinsheet.getSprite((29 * 288) - (i * 288), 128 * 8, 288, 128);
		}

		return new Enemy(-10, -50, 288, 128, Hashashinsheet.getSprite((29 * 288), 0, 288, 128), "Hashashin", life,
				energy, strength, defense, 7, 7, 17, 25, idleHashashin, attackHashashin, StrongattackHashashin,
				MagicalattackHashashin, attack, Strongattack, Magicalattack);
	}
}
