package factories;

import entities.Enemy;

public interface Factory {
	//Base of the factory pattern
    Enemy createEnemy();
}