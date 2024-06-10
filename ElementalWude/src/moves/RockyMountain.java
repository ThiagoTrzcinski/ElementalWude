package moves;

import entities.Characters;

public class RockyMountain extends Move {

	public RockyMountain() {
		name = "Rocky Mountain";
		cost = 0;
		speed = 2;
	}

	@Override
	public void useAction(Characters user, Characters target) {
		System.out.println(user.getName() + " used " + this.getName() + " on " + target.getName());
		System.out.println("FOR KARL!");
		int finaldamage = user.getStrength() - target.getDefense();
		user.setEnergy(user.getEnergy() - this.cost);
		target.takeDamage(finaldamage);

	}
}
