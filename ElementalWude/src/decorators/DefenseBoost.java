package decorators;

import entities.Characters;

public class DefenseBoost extends CharacterDecorator {
	//Decorator that increases defense
	private int DefenseBoost;

	public DefenseBoost(Characters decoratedCharacter, int DefenseBoost, int turns) {
		super(decoratedCharacter, turns);
		this.DefenseBoost = DefenseBoost;
	}

	@Override
	public int getDefense() {
		if (isExpired()) {
			return decoratedCharacter.getDefense();
		}
		return decoratedCharacter.getDefense() + DefenseBoost;
	}

}
