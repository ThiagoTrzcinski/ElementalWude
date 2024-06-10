package moves;

import decorators.StrengthBoost;
import entities.Characters;

public class Shariha extends Move {

	public Shariha() {
		name = "Shariha";
		cost = 0;
		speed = 2;
	}

	@Override
	public void useAction(Characters user, Characters target) {
		System.out.println(user.getName() + " used " + this.getName() + " on " + target.getName());
		int finaldamage = user.getStrength() - target.getDefense();
		user.setEnergy(user.getEnergy() - this.cost);
		target.takeDamage(finaldamage);
		user = new StrengthBoost(user, 1, 1);
	}
}
