public class Monster
{
    String name;
    double health;
    double maxHealth;
    public Monster(String n, int h)
    {
        name = n;
        health = h;
        maxHealth = h;
    }
    public void takeDamage(double damage)
    {
        health = health - damage;
    }
    public String getName()
    {
        return name;
    }
    public double getHealth()
    {
        return health;
    }
    public double getMaxHealth()
    {
        return maxHealth;
    }
}