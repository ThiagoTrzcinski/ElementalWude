package decorators;

import entities.Characters;

public class HealthPotion extends CharacterDecorator {
	//Decorator that increases health
	private int healthBoost;

	public HealthPotion(Characters decoratedCharacter, int healthBoost, int turns) {
		super(decoratedCharacter, turns);
		this.healthBoost = healthBoost;
	}

	@Override
	public int getLife() {
		if (isExpired()) {
			return decoratedCharacter.getLife();
		}
		return decoratedCharacter.getLife() + healthBoost;
	}
}
