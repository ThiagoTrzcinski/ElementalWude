package entities;

import java.awt.image.BufferedImage;

import behaviors.AggressiveBehavior;
import behaviors.Behavior;
import behaviors.DefensiveBehavior;
import moves.Move;

public class Enemy extends Characters {
	private Behavior behavior;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite, String name, int life, int energy,
			int strength, int defense, int maxIddleindex, int maxAttackindex, int maxStrongAttackindex,
			int maxMagicalAttackindex, BufferedImage[] idleAnimation, BufferedImage[] attackAnimation,
			BufferedImage[] strongattackAnimation, BufferedImage[] magicalattackAnimation, Move attack,
			Move strongAttack, Move magicalAttack) {

		super(x, y, width, height, sprite, name, life, energy, strength, defense, maxIddleindex, maxAttackindex,
				maxStrongAttackindex, maxMagicalAttackindex, idleAnimation, attackAnimation, strongattackAnimation,
				magicalattackAnimation, attack, strongAttack, magicalAttack);
	}

	public void ChangeBeha() {
		// Change which strategy is being used based on the health of the enemy
		if (this.getLife() > (Maxlife * 0.4)) {
			this.behavior = new AggressiveBehavior();
		} else {
			this.behavior = new DefensiveBehavior();
		}
	}

	public void act(Characters target) {
		// Selects the enemy action
		ChangeBeha();
		behavior.act(this, target);
	}
}
