public class Monster
{
    String name;
    double health;
    public Monster(String n, int h)
    {
        name = n;
        health = h;
    }
    public void takeDamage(double damage)
    {
        health = health - damage;
    }
    public String getName()
    {
        return name;
    }
}