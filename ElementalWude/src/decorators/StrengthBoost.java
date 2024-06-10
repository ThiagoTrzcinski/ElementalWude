package decorators;

import entities.Characters;

public class StrengthBoost extends CharacterDecorator {
	//Decorator that increases strength
	private int StrengthBoost;

	public StrengthBoost(Characters decoratedCharacter, int StrengthBoost, int turns) {
		super(decoratedCharacter, turns);
		this.StrengthBoost = StrengthBoost;
	}

	@Override
	public int getStrength() {
		if (isExpired()) {
			return decoratedCharacter.getStrength();
		}
		return decoratedCharacter.getStrength() + StrengthBoost;
	}

}