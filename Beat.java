public class Beat
{
    int x;
    int y;
    boolean red;
    public Beat(boolean color)
    {
        x = 1024;
        y = 500;
        red = color;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public boolean getColor()
    {
        return red;
    }
    public void move()
    {
        x = x - 30;
    }
}