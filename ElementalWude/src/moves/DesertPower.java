package moves;

import decorators.StrengthBoost;
import entities.Characters;

public class DesertPower extends Move {

	public DesertPower() {
		name = "Desert Power";
		cost = 7;
		speed = 2;
	}

	@Override
	public void useAction(Characters user, Characters target) {
		System.out.println(user.getName() + " used " + this.getName() + " on " + target.getName());
		int finaldamage = user.getStrength() + user.getEnergy();
		user.setEnergy(user.getEnergy() - this.cost);
		target.takeDamage(finaldamage);
		user = new StrengthBoost(user, 2, 3);
	}
}
