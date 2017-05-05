import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Font;
import java.util.*;
public class Main extends JFrame implements KeyListener
{
    boolean isUpPressed, isDownPressed;
    int x1 = 200;
    int y1 = 500;
    
    boolean hit = false;
    
    int time = 0;
   
    BufferStrategy bs;
    DrawPanel panel = new DrawPanel();
    
    int stringTime = 0;
    String currentString = "";
    
    int points;
    
    ArrayList<Beat> beats = new ArrayList<Beat>();
    
    int beatTime = 0;
    
    int interval = 0;
    
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
        
        beats.add(new Beat(true));
    }
    
    public void startNow()
    {
        panel.drawStuff();
    }
    public void keyTyped(KeyEvent e)
    {
        
    }
    public synchronized void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == (KeyEvent.VK_UP))
        {
            time = beats.get(0).getX();
            beats.remove(0);
            hit = true;
        }
        if (e.getKeyCode() == (KeyEvent.VK_DOWN))
        {
            time = beats.get(0).getX();
            beats.remove(0);
            hit = true;
        }
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP: isUpPressed = true; break;
            case KeyEvent.VK_DOWN: isDownPressed = true; break;
        }
        if(isUpPressed && isDownPressed)
        {
            time = beats.get(0).getX();
            beats.remove(0);
            hit = true;
        }
    }
    public void keyReleased(KeyEvent e)
    {
               switch(e.getKeyCode()) {
            case KeyEvent.VK_UP: isUpPressed = false; break;
            case KeyEvent.VK_DOWN: isDownPressed = false; break;
        }
    }
    public class DrawPanel extends JPanel
    {
        public void drawStuff()
        {
            while(true)
            {
                try
                {
                    for (int i = 0; i < beats.size(); i++)
                    {
                        beats.get(i).move();
                    }
                    stringTime = stringTime + 10;
                    beatTime = beatTime + 10;
                    int type = (int)(Math.random()*3 + 1);
                    if (type == 1)
                    {
                        interval = 20;
                    }
                    if (type == 2)
                    {
                        interval = 50;
                    }
                    if (type == 3)
                    {
                        interval = 70;
                    }
                    
                    if (beatTime > interval)
                    {
                        beats.add(new Beat(true));
                        beatTime = 0;
                    }
                    
                    
                    Graphics2D g = (Graphics2D)bs.getDrawGraphics();
                    g.setColor(Color.BLACK);
                    g.fillRect(0,0,1024,768);
                    g.setColor(Color.blue);
                    g.fillRect(x1,y1,100,100);
                     
                     Font font = new Font("Serif", Font.PLAIN, 96);
                     g.setFont(font);
                     
                     g.drawString(points+ "", 200, 120);
                    if (hit == false)
                    {
                        g.setColor(Color.red);
                        for (int i = 0; i < beats.size(); i++)
                        {
                            g.fillRect(beats.get(i).getX(),beats.get(i).getY(),100,100);
                        }
                        if (stringTime < 100)
                        {
                            
                            g.drawString(currentString, 500, 120);
                        }
                    }
                    else
                    {
                      
                        if (time >= 96 && time <= 204)
                        {
                            currentString = "PERECT";
                            points = points + 300;
                        }
                        else
                        {
                            currentString = "BAD";
                            points = points + 100;
                        }
                        hit = false;
                        time = 0;
                        stringTime = 0;
                    }
                    if (beats.get(0).getX() <= 0)
                    {
                        beats.remove(0);
                    }
                    bs.show();
                    Toolkit.getDefaultToolkit().sync();
                    g.dispose();
                    Thread.sleep(30);
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