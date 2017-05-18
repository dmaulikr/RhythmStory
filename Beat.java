public class Beat
{
    int x;
    int y;
    int color; //1 = red 2 = blue 3 = both
    boolean hit = false;
    public Beat(int c)
    {
        x = 900;
        y = 55;
        color = c;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getColor()
    {
        return color;
    }
    public void move()
    {
        x = x - 5;
    }
    public void hit()
    {
        hit = true;
    }
    public boolean getHit()
    {
        return hit;
    }
    public String getFile()
    {
        if (color == 1)
        {
            return "assets\\beatRed.png";
        }
        else if (color == 2)
        {
            return "assets\\beatBlue.png";
        }
        else
        {
            return "assets\\beatBoth.png";
        }
    }
}