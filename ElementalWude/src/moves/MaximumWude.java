package moves;

import entities.Characters;

public class MaximumWude extends Move {

	public MaximumWude() {
		name = "Maximum Wude";
		cost = 8;
		speed = 2;
	}

	@Override
	public void useAction(Characters user, Characters target) {
		System.out.println(user.getName() + " used " + this.getName() + " on " + target.getName());
		int finaldamage = user.getStrength() + (user.getLife() / 3);
		user.setEnergy(user.getEnergy() - this.cost);
		target.takeDamage(finaldamage);
	}
}