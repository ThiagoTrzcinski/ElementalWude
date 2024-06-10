package moves;

import entities.Characters;

public class RisingFalcon extends Move {

	public RisingFalcon() {
		name = "Rising Falcon";
		cost = 4;
		speed = 2;
	}

	@Override
	public void useAction(Characters user, Characters target) {
		System.out.println(user.getName() + " used " + this.getName() + " on " + target.getName());
		int finaldamage = user.getStrength() + user.getEnergy() - target.getDefense();
		user.setEnergy(user.getEnergy() - this.cost);
		target.takeDamage(finaldamage);
	}
}
