public class Monster
{
    String name;
    int health;
    public Monster(String n, int h)
    {
        name = n;
        health = h;
    }
    public void takeDamage(int damage)
    {
        health = health - damage;
    }
}