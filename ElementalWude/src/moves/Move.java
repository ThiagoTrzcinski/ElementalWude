package moves;

import entities.Characters;

public abstract class Move {
	// Base of move, set the pattern for all of the attacks in the game
	protected String name;
	protected int cost;
	protected int speed;// Speed is not implemented

	public Move() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public abstract void useAction(Characters user, Characters target);

}
