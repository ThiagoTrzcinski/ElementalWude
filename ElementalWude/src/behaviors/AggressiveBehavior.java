package behaviors;

import java.util.Random;

import entities.Characters;

public class AggressiveBehavior implements Behavior {
	Random random = new Random();

	@Override
	public void act(Characters user, Characters target) {
		// Creates a random number used to define the enemy action
		int randomAction = random.nextInt(101);
		if (randomAction <= 50) {
			if (user.getEnergy() >= user.getAttack().getCost()) {
				user.attack(target);
			} else {
				chooseAlternativeAction(user, target);
			}
		} else if (randomAction > 50 && randomAction <= 75) {
			if (user.getEnergy() >= user.getStrongAttack().getCost()) {
				user.strongAttack(target);
			} else {
				chooseAlternativeAction(user, target);
			}
		} else if (randomAction > 75 && randomAction <= 90) {
			if (user.getEnergy() >= user.getMagicalAttack().getCost()) {
				user.magicalAttack(target);
			} else {
				chooseAlternativeAction(user, target);
			}
		} else {
			user.rest();
		}
	}

	private void chooseAlternativeAction(Characters user, Characters target) {
		// Defines an alternative when the chosen action has a higher cost than the
		// enemy energy
		if (user.getEnergy() >= user.getAttack().getCost()) {
			user.attack(target);
		} else if (user.getEnergy() >= user.getStrongAttack().getCost()) {
			user.strongAttack(target);
		} else if (user.getEnergy() >= user.getMagicalAttack().getCost()) {
			user.magicalAttack(target);
		} else {
			user.rest();
		}
	}
}
