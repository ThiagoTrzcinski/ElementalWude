package decorators;

import java.awt.Graphics;
import entities.Characters;

public abstract class CharacterDecorator extends Characters {
	//Base of the Decorator pattern
	public Characters decoratedCharacter; //Character that is going to be decorated, used to remove the decorator after the turns expires
	protected int turnsRemaining;

	public CharacterDecorator(Characters decoratedCharacter, int turns) {
		super(decoratedCharacter);
		this.decoratedCharacter = decoratedCharacter;
		this.turnsRemaining = turns;
	}

	//All base attributes must be called using the decorated Character, to get the decorated version a override is necessary
	@Override
	public void attack(Characters target) {
		decoratedCharacter.attack(target);
	}

	@Override
	public void strongAttack(Characters target) {
		decoratedCharacter.strongAttack(target);
	}

	@Override
	public void magicalAttack(Characters target) {
		decoratedCharacter.magicalAttack(target);
	}

	@Override
	public boolean isActing() {
		return decoratedCharacter.isActing();
	}

	@Override
	public void tick() {
		decoratedCharacter.tick();
	}

	@Override
	public void render(Graphics g) {
		decoratedCharacter.render(g);
	}

	@Override
	public int getLife() {
		return decoratedCharacter.getLife();
	}

	@Override
	public int getMaxlife() {
		return decoratedCharacter.getMaxlife();
	}

	@Override
	public int getEnergy() {
		return decoratedCharacter.getEnergy();
	}

	@Override
	public int getMaxenergy() {
		return decoratedCharacter.getMaxenergy();
	}

	@Override
	public int getStrength() {
		return decoratedCharacter.getStrength();
	}

	@Override
	public int getDefense() {
		return decoratedCharacter.getDefense();
	}

	@Override
	public void takeDamage(int damage) {
		decoratedCharacter.takeDamage(damage);
	}

	@Override
	public void setLife(int life) {
		decoratedCharacter.setLife(life);
	}

	@Override
	public void setEnergy(int energy) {
		decoratedCharacter.setEnergy(energy);
	}

	@Override
	public void rest() {
		decoratedCharacter.rest();
	}

	public boolean isAlive() {
		return decoratedCharacter.getLife() > 0;
	}

	public void reduceTurn() {
		if (turnsRemaining > 0) {
			turnsRemaining--;
		}
	}

	public boolean isExpired() {
		return turnsRemaining <= 0;
	}

	public Characters getDecoratedCharacter() {
		return decoratedCharacter;
	}
}
