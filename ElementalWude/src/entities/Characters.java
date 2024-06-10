package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import moves.Move;

public abstract class Characters extends Entity {
    protected String name;
    protected int life;
    protected int energy;
    protected int Maxlife;
    protected int Maxenergy;
    protected int strength;
    protected int defense;

    protected Move attack;
    protected Move strongAttack;
    protected Move magicalAttack;

    protected int index = 0;
    private int maxIdleIndex;
    private int maxAttackIndex;
    private int maxStrongAttackIndex;
    private int maxMagicalAttackIndex;
    private BufferedImage[] idleAnimation;
    private BufferedImage[] attackAnimation;
    private BufferedImage[] strongAttackAnimation;
    private BufferedImage[] magicalAttackAnimation;

    public boolean doAttack, doStrongAttack, doMagicalAttack;
    protected boolean fullAnimation;

    // Main constructor
    public Characters(int x, int y, int width, int height, BufferedImage sprite, String name, int life, int energy,
                      int strength, int defense, int maxIdleIndex, int maxAttackIndex, int maxStrongAttackIndex,
                      int maxMagicalAttackIndex, BufferedImage[] idleAnimation, BufferedImage[] attackAnimation,
                      BufferedImage[] strongAttackAnimation, BufferedImage[] magicalAttackAnimation, Move attack,
                      Move strongAttack, Move magicalAttack) {
        super(x, y, width, height, sprite);
        this.name = name;
        this.life = life;
        this.energy = energy;
        this.Maxlife = life;
        this.Maxenergy = energy;
        this.strength = strength;
        this.defense = defense;
        this.maxIdleIndex = maxIdleIndex;
        this.maxAttackIndex = maxAttackIndex;
        this.maxStrongAttackIndex = maxStrongAttackIndex;
        this.maxMagicalAttackIndex = maxMagicalAttackIndex;
        this.idleAnimation = idleAnimation;
        this.attackAnimation = attackAnimation;
        this.strongAttackAnimation = strongAttackAnimation;
        this.magicalAttackAnimation = magicalAttackAnimation;
        this.attack = attack;
        this.strongAttack = strongAttack;
        this.magicalAttack = magicalAttack;
    }

    // Clone constructor
    public Characters(Characters original) {
        this(original.getX(), original.getY(), original.getWidth(), original.getHeight(), original.getSprite(),
             original.name, original.life, original.energy, original.strength, original.defense,
             original.maxIdleIndex, original.maxAttackIndex, original.maxStrongAttackIndex,
             original.maxMagicalAttackIndex, original.idleAnimation, original.attackAnimation,
             original.strongAttackAnimation, original.magicalAttackAnimation, original.attack, original.strongAttack,
             original.magicalAttack);
    }

    // Clone method
    public Characters clone() {
        return this;
    }

    // Setters for the moves
    public void setAttack(Move attack) {
        this.attack = attack;
    }

    public void setStrongAttack(Move strongAttack) {
        this.strongAttack = strongAttack;
    }

    public void setMagicalAttack(Move magicalAttack) {
        this.magicalAttack = magicalAttack;
    }

    // Attack methods
    public void attack(Characters target) {
        index = 0;
        doAttack = true;
        attack.useAction(this, target);
    }

    public void strongAttack(Characters target) {
        index = 0;
        doStrongAttack = true;
        strongAttack.useAction(this, target);
    }

    public void magicalAttack(Characters target) {
        index = 0;
        doMagicalAttack = true;
        magicalAttack.useAction(this, target);
    }

    // Method to handle taking damage
    public void takeDamage(int damage) {
        int finalDamage = Math.max(0, damage);
        life -= finalDamage;
        System.out.println(name + " takes " + finalDamage + " damage, remaining health: " + life);
    }

    // Method for resting and recovering life and energy
    public void rest() {
        int lifeBoost = (int) (Maxlife * 0.1);
        int energyBoost = (int) (Maxenergy * 0.3);

        System.out.println(this.getName() + " rested and recovered " + lifeBoost + " life and " + energyBoost + " energy");

        lifeBoost += life;
        if (lifeBoost < Maxlife)
            life = lifeBoost;
        else
            life = Maxlife;

        energyBoost += this.energy;
        if (energyBoost < this.Maxenergy)
            energy = energyBoost;
        else
            energy = this.Maxenergy;
    }

    // Method to check if the character is acting
    public boolean isActing() {
        return doAttack || doStrongAttack || doMagicalAttack;
    }

    // Method to verify if the animation is complete
    protected void verifyAnimation() {
        if (fullAnimation) {
            doAttack = false;
            doStrongAttack = false;
            doMagicalAttack = false;
        }
        fullAnimation = false;
    }

    // Method to handle character state updates
    public void tick() {
        if (doAttack) {
            frames++;
            if (frames > Maxframes) {
                frames = 0;
                index++;
                if (index > getMaxAttackIndex()) {
                    fullAnimation = true;
                    index = 0;
                    verifyAnimation();
                }
            }
        } else if (doStrongAttack) {
            frames++;
            if (frames > Maxframes) {
                frames = 0;
                index++;
                if (index > getMaxStrongAttackIndex()) {
                    fullAnimation = true;
                    index = 0;
                    verifyAnimation();
                }
            }
        } else if (doMagicalAttack) {
            frames++;
            if (frames > Maxframes) {
                frames = 0;
                index++;
                if (index > getMaxMagicalAttackIndex()) {
                    fullAnimation = true;
                    index = 0;
                    verifyAnimation();
                }
            }
        } else {
            frames++;
            if (frames > Maxframes) {
                frames = 0;
                index++;
                if (index > getMaxIdleIndex()) {
                    fullAnimation = true;
                    index = 0;
                    verifyAnimation();
                }
            }
        }
    }

    // Method to render character animations
    public void render(Graphics g) {
        BufferedImage frameToDraw = null;

        if (doAttack) {
            if (index < getAttackAnimation().length) {
                frameToDraw = getAttackAnimation()[index];
            }
        } else if (doStrongAttack) {
            if (index < getStrongAttackAnimation().length) {
                frameToDraw = getStrongAttackAnimation()[index];
            }
        } else if (doMagicalAttack) {
            if (index < getMagicalAttackAnimation().length) {
                frameToDraw = getMagicalAttackAnimation()[index];
            }
        } else {
            if (index < getIdleAnimation().length) {
                frameToDraw = getIdleAnimation()[index];
            }
        }

        if (frameToDraw != null) {
            g.drawImage(frameToDraw, this.getX(), this.getY(), null);
        }
    }

    // Getters and setters for character attributes
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLife() {
        return this.life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getMaxlife() {
        return Maxlife;
    }

    public void setMaxlife(int Maxlife) {
        this.Maxlife = Maxlife;
    }

    public int getMaxenergy() {
        return Maxenergy;
    }

    public void setMaxenergy(int Maxenergy) {
        this.Maxenergy = Maxenergy;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public Move getAttack() {
        return attack;
    }

    public Move getStrongAttack() {
        return strongAttack;
    }

    public Move getMagicalAttack() {
        return magicalAttack;
    }

    public boolean isAlive() {
        return life > 0;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getMaxIdleIndex() {
        return maxIdleIndex;
    }

    public void setMaxIdleIndex(int maxIdleIndex) {
        this.maxIdleIndex = maxIdleIndex;
    }

    public int getMaxAttackIndex() {
        return maxAttackIndex;
    }

    public void setMaxAttackIndex(int maxAttackIndex) {
        this.maxAttackIndex = maxAttackIndex;
    }

    public int getMaxStrongAttackIndex() {
        return maxStrongAttackIndex;
    }

    public void setMaxStrongAttackIndex(int maxStrongAttackIndex) {
        this.maxStrongAttackIndex = maxStrongAttackIndex;
    }

    public int getMaxMagicalAttackIndex() {
        return maxMagicalAttackIndex;
    }

    public void setMaxMagicalAttackIndex(int maxMagicalAttackIndex) {
        this.maxMagicalAttackIndex = maxMagicalAttackIndex;
    }

    public BufferedImage[] getIdleAnimation() {
        return idleAnimation;
    }

    public void setIdleAnimation(BufferedImage[] idleAnimation) {
        this.idleAnimation = idleAnimation;
    }

    public BufferedImage[] getAttackAnimation() {
        return attackAnimation;
    }

    public void setAttackAnimation(BufferedImage[] attackAnimation) {
        this.attackAnimation = attackAnimation;
    }

    public BufferedImage[] getStrongAttackAnimation() {
        return strongAttackAnimation;
    }

    public void setStrongAttackAnimation(BufferedImage[] strongAttackAnimation) {
        this.strongAttackAnimation = strongAttackAnimation;
    }

    public BufferedImage[] getMagicalAttackAnimation() {
        return magicalAttackAnimation;
    }

    public void setMagicalAttackAnimation(BufferedImage[] magicalAttackAnimation) {
        this.magicalAttackAnimation = magicalAttackAnimation;
    }

    public boolean isDoAttack() {
        return doAttack;
    }

    public void setDoAttack(boolean doAttack) {
        this.doAttack = doAttack;
    }

    public boolean isDoStrongAttack() {
        return doStrongAttack;
    }

    public void setDoStrongAttack(boolean doStrongAttack) {
        this.doStrongAttack = doStrongAttack;
    }

    public boolean isDoMagicalAttack() {
        return doMagicalAttack;
    }

    public void setDoMagicalAttack(boolean doMagicalAttack) {
        this.doMagicalAttack = doMagicalAttack;
    }

    public boolean isFullAnimation() {
        return fullAnimation;
    }

    public void setFullAnimation(boolean fullAnimation) {
        this.fullAnimation = fullAnimation;
    }
}
