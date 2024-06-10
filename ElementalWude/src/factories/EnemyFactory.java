package factories;

import entities.Enemy;

public class EnemyFactory {
	// Receive the name of the enemy and return its factory
	Factory HashashinFactory = new HashashinFactory();

	public Enemy create(String type) {
		if (type.equals("Hashashin")) {
			return HashashinFactory.createEnemy();
		}
		return null;
	}

}
