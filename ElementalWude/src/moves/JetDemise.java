package moves;

import entities.Characters;

public class JetDemise extends Move {

	public JetDemise() {
		name = "Jet's Demise";
		cost = 3;
		speed = 2;
	}

	@Override
	public void useAction(Characters user, Characters target) {
		System.out.println(user.getName() + " used " + this.getName() + " on " + target.getName());
		int finaldamage = user.getStrength() + (user.getDefense() * 2) - target.getDefense();
		user.setEnergy(user.getEnergy() - this.cost);
		target.takeDamage(finaldamage);
	}
}