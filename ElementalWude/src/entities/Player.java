package entities;

import java.awt.image.BufferedImage;

import graphics.Spritesheet;
import moves.JetDemise;
import moves.MaximumWude;
import moves.Move;
import moves.RockyMountain;

public class Player extends Characters {
	
	private static Spritesheet playersheet = new Spritesheet("/PlayerSheet.png");
	private static BufferedImage[] idlePlayer = new BufferedImage[6];
	private static BufferedImage[] attackPlayer = new BufferedImage[12];
	private static BufferedImage[] StrongattackPlayer = new BufferedImage[23];
	private static BufferedImage[] MagicalattackPlayer = new BufferedImage[23];

	
	public Player(int x, int y, int width, int height, String name, int life, int energy, int strength, int defense, Move attack,Move Strongattack,Move Magicalattack) {		
		super(x, y, width, height, playersheet.getSprite(0, 0, 288, 128), name, life, energy, strength, defense, 5,
				11, 22, 22, idlePlayer, attackPlayer,
				StrongattackPlayer, MagicalattackPlayer, attack, Strongattack, Magicalattack);
		
		setAttack(new RockyMountain());
		setStrongAttack(new JetDemise());
		setMagicalAttack(new MaximumWude());
	    
		for(int i=0; i <6; i++)
		{
			idlePlayer[i] = playersheet.getSprite(i*288, 0, 288, 128);	
		}
		
		for(int i=0; i <12; i++)
		{
			attackPlayer[i] = playersheet.getSprite(i*288, 128*6, 288, 128);	
		}
		for(int i=0; i <23; i++)
		{
			StrongattackPlayer[i] = playersheet.getSprite(i*288, 128*7, 288, 128);	
		}
		for(int i=0; i <23; i++)
		{
			MagicalattackPlayer[i] = playersheet.getSprite(i*288, 128*8, 288, 128);	
		}

		
		
	}
	
		
	
	

	
	
}

	
