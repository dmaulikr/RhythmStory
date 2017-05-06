public class Beat
{
    int x;
    int y;
    int color; //1 = red 2 = blue 3 = both
    public Beat(int c)
    {
        x = 1024;
        y = 605;
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
        x = x - 10;
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