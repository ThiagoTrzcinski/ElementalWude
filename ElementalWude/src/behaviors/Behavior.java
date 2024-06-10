package behaviors;

import entities.Characters;

public interface Behavior {
	// Base of the Strategy pattern
	void act(Characters enemy, Characters target);
}
