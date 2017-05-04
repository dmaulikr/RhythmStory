import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Font;
public class Main extends JFrame implements KeyListener
{
    int x = 1024;
    int y = 500;
 
    int x1 = 200;
    int y1 = 500;
    
    boolean hit = false;
    
    int time = 0;
   
    BufferStrategy bs;
    DrawPanel panel = new DrawPanel();
    
    int stringTime = 0;
    String currentString = "";
    
    public Main()
    {
        setIgnoreRepaint(true);
        setTitle("Active Rendering on a JPanel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024,720);
        setResizable(false);
        setVisible(true);
        createBufferStrategy(2);
        bs = getBufferStrategy();
        getContentPane().add(panel);
        panel.setIgnoreRepaint(true);
        addKeyListener(this);
    }
    
    public void startNow()
    {
        panel.drawStuff();
    }
    public void keyTyped(KeyEvent e)
    {
        
    }
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == (KeyEvent.VK_UP))
        {
            
                time = x;
                hit = true;
            
        }
    }
    public void keyReleased(KeyEvent e)
    {
       
    }
        
    public class DrawPanel extends JPanel
    {
        public void drawStuff()
        {
            while(true)
            {
                try
                {
              
                    x=x-20;
                    stringTime = stringTime + 10;
                    
                    Graphics2D g = (Graphics2D)bs.getDrawGraphics();
                    g.setColor(Color.BLACK);
                    g.fillRect(0,0,1024,768);
                     g.setColor(Color.blue);
                     g.fillRect(x1,y1,100,100);
                    if (hit == false)
                    {
                        g.setColor(Color.red);
                        g.fillRect(x,y,100,100);
                         if (stringTime < 100)
                        {
                            Font font = new Font("Serif", Font.PLAIN, 96);
                            g.setFont(font);
                            g.drawString(currentString, 500, 120); 
                            
                        }
                    }
                    else
                    {
                        x = 1024;
                        if (time >= 96 && time <= 204)
                        {
                            currentString = "PERECT";
                        }
                        else
                        {
                            currentString = "BAD";
                        }
                        hit = false;
                        time = 0;
                        stringTime = 0;
                    }
                    if (x <= 0)
                    {
                        x = 1024;
                    }
                    bs.show();
                    Toolkit.getDefaultToolkit().sync();
                    g.dispose();
                    Thread.sleep(20);
                }
                catch (Exception e)
                {
                    System.exit(0);
                }
            }
        }
     }
    
    public static void main(String[] args)
    {
        Main main = new Main();
        main.startNow();
    }

}