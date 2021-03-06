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
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
public class Main extends JFrame implements KeyListener
{
    boolean isLeftPressed, isRightPressed;
    
    boolean drawn = false; //whether bg drawn or not.
    boolean hit = false; //whether the beat was hit or not
    
    int perfect = 0; //number of 300's
    int bad = 0; //number of 50's
    int good = 0; //number of 100's
    double accuracy = 0; //the player's accuracy
    
    int time = 0; //how much time has passed
    
    Monster currentMonster;
   
    BufferStrategy bs;
    
    DrawPanel panel = new DrawPanel();
    
    int stringTime = 400; //keeps track of how long to display the accuracy string
    
    String accuracyString = ""; //bad/good/perfect
    
    int track1 = 1; //monster
    int track2 = 1; // type
    int currentState = 0;
    int points; //the player's points
    
    int multiplier = 1; //point multiplier
    
    int pressedPosition;
    
    ArrayList<Monster> monsters = new ArrayList<Monster>();
    ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    ArrayList<BufferedImage> numbers = new ArrayList<BufferedImage>();
    ArrayList<BufferedImage> etc = new ArrayList<BufferedImage>();
    ArrayList<Beat> beats = new ArrayList<Beat>(); //arraylist to store all of the beats created
    int[] intervals = new int[1000]; //array to store all the pregenerated intervals
    int beatTime = 0;
    
    int interval = 0;
    
    int intervalCounter = 0; //counter that goes through the intervals array
    
    boolean generating = true; //if a beat has been added yet or not
    
    BufferedImage image; //stores the image
    
    int health = 300; //the player's health
    
    int beatCounter = 0;
    
    Graphics2D g;
    
    int attackSpriteCounter = 9; //determines which sprite to draw;
    int attackSpriteTimer = 0;
    int cooldownTimer = 0; //time to wait until character resets;
    int damageHeight = 500;
    
    ArrayList<BufferedImage> monsterStand = new ArrayList<BufferedImage>();
    ArrayList<BufferedImage> monsterHit = new ArrayList<BufferedImage>();
    
    int monsterCounter = 0;
    
    int combo = 0;
    
    boolean battle = true;
    public Main()
    {
        loadMonsters();
        currentMonster=monsters.get(0);
        setIgnoreRepaint(true);
        setTitle("RhythmStory");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,720);
        setResizable(false);
        setVisible(true);
        createBufferStrategy(2);
        bs = getBufferStrategy();
        getContentPane().add(panel);
        panel.setIgnoreRepaint(true);
        addKeyListener(this);
        generateBeats();
        storeImages();
        //storeImages2(track1,track2,"Standing","Snail");
        g = (Graphics2D)bs.getDrawGraphics();
        beats.add(new Beat(1));
        storeNumbers();
        storeEtc();
        storeStandHit();
    }
    public void loadMonsters()
    {
        Monster snail= new Monster("Snail",100);
        Monster slime= new Monster("Slime",250);
        Monster mushroom= new Monster("Mushroom",400);
        Monster mano= new Monster("Mano", 600);
        Monster golem= new Monster("Golem",900);
        Monster yeti = new Monster("Yeti", 1300);
        Monster griffey= new Monster("Griffey",1800);
        Monster manon= new Monster("Manon",2500);
        Monster pink= new Monster("Pink",5000);
        monsters.add(snail);
        monsters.add(slime);
        monsters.add(mushroom);
        monsters.add(mano);
        monsters.add(golem);
        monsters.add(yeti);
        monsters.add(griffey);
        monsters.add(manon);
        monsters.add(pink);
    }
    public void startNow()
    {
        //playMusic(); 
        loadBackground();
        panel.drawStuff();
    }
    public void keyTyped(KeyEvent e)
    {
        
    }
    public synchronized void keyPressed(KeyEvent e)
    { 
       stringTime = 0;
       if (e.getKeyCode() == (KeyEvent.VK_LEFT))
        {
            if (beats.get(beatCounter).getColor() != 1 || isRightPressed == true)
            {
                accuracyString = "Miss";
                combo = 0;
                health = health - 10;
            }
            else
            {
                ++combo;
                if (combo % 20 == 0 && combo != 0)
                {
                    health = health + 20;
                }
                monsters.get(monsterCounter).takeDamage(getPlayerDamage());
                time = beats.get(beatCounter).getX();
                int position = calculatePosition();
                if (position <= 205 && position >= 125)
                {
                    beats.get(beatCounter).hit();
                    ++beatCounter;
                }
                hit = true;
            }
        }
        if (e.getKeyCode() == (KeyEvent.VK_RIGHT))
        {  
            if (beats.get(beatCounter).getColor() != 2 || isLeftPressed == true)
            {
                accuracyString = "Miss";
                combo = 0;
                health = health - 10;
            }
            else
            {
                ++combo;
                if (combo % 20 == 0 && combo != 0)
                {
                    health = health + 20;
                }
                monsters.get(monsterCounter).takeDamage(getPlayerDamage());
                time = beats.get(beatCounter).getX();
                int position = calculatePosition();
                if (position <= 205 && position >= 125)
                {
                    beats.get(beatCounter).hit();
                    ++beatCounter;
                }
                hit = true;
            }
        
            }
        switch(e.getKeyCode()) 
        {
            case KeyEvent.VK_LEFT: isLeftPressed = true; break;
            case KeyEvent.VK_RIGHT: isRightPressed = true; break;
        }
        if(isLeftPressed && isRightPressed)
        {
             if (beats.get(beatCounter).getColor() != 3)
            {
                combo = 0;
                accuracyString = "Miss";
                health = health - 10;
            }
            else
            {
                ++combo;
                if (combo % 20 == 0 && combo != 0)
                {
                    health = health + 20;
                }
                health = health + 20;
                monsters.get(monsterCounter).takeDamage(getPlayerDamage());
                time = beats.get(0).getX();
                int position = calculatePosition();
                if (position <= 205 && position >= 125)
                {
                    beats.get(beatCounter).hit();
                    ++beatCounter;
                    
                }
                hit = true;
            }
        }   
    }
    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyCode()) 
        {
            case KeyEvent.VK_LEFT: isLeftPressed = false; break;
            case KeyEvent.VK_RIGHT: isRightPressed = false; break;
        }
    }
    public class DrawPanel extends JPanel
    {
        public void drawStuff()
        {
            while(battle)
            {
                try
                {
                     //refreshes the screen
                    g = (Graphics2D)bs.getDrawGraphics();
                    Font font = new Font("Serif", Font.PLAIN, 96);
                    g.setFont(font);
                    for (int i = beatCounter; i < beats.size(); i++) //moves every beat across the screen
                    {
                        beats.get(i).move();
                    }
          
                    stringTime = stringTime + 20; //increments how long the string has been displayed for
                   
                    nextBeat(); //creates the next beat
                    
                    g.setColor(Color.BLACK);
                    g.fillRect(0,0,900,178);
                    g.drawImage(images.get(3), 0, 178 ,null); //draws top of background
                    g.drawImage(images.get(4), 140, 50,null); //draws the target
                    g.drawImage(images.get(getAccuracy() + 4), 0, 0,null); 
                    
                    g.setColor(Color.GREEN);
                    g.fillRect(0, 170, (int)(health*1.4), 10);
                    g.setColor(Color.RED);
                    g.fillRect(600, 170, (int)((monsters.get(monsterCounter).getHealth()*300)/monsters.get(monsterCounter).getMaxHealth()), 10);
                    if (hit == true)
                    {
                        cooldownTimer = cooldownTimer + 10;
                        attackSpriteTimer = attackSpriteTimer + 10;
                        if (attackSpriteTimer == 80 && attackSpriteCounter <= 12 && cooldownTimer < 320)
                        {
                            g.drawImage(images.get(12), 0, 303,null); //draws middle of background;
                            g.drawImage(images.get(attackSpriteCounter), 350, 570,null);
                            if (attackSpriteCounter >= 10)
                            {
                                g.drawImage(monsterHit.get(monsterCounter), 405, 360,null);
                             
                                damageHeight = damageHeight - 20;
                            }
                            else
                            {
                                g.drawImage(monsterStand.get(monsterCounter), 405, 360,null);
                            }
                            attackSpriteTimer = 0;
                            if (attackSpriteCounter < 12)
                            {
                                ++attackSpriteCounter;
                            }
                        }
                        int d = (int)getPlayerDamage();
                        int counter = 0;
                        while (d > 0)
                        {
                            int digit = d % 10;
                            g.drawImage(numbers.get(digit + 30), 450 - (counter * 20), damageHeight,null);
                            d = d /10;
                            ++counter;
                                }
                        if (cooldownTimer == 320)
                        {
                            g.drawImage(images.get(12), 0, 303,null);
                            g.drawImage(images.get(15), 350, 570,null);
                            g.drawImage(monsterStand.get(monsterCounter), 405, 360,null);
                            cooldownTimer = 0;
                            damageHeight = 500;
                            attackSpriteTimer = 0;
                            hit = false;
                            attackSpriteCounter = 9;
                        }
                    }//draws the attack sprite
                    for (int i = beatCounter; i < beats.size(); i++) //draws each beat in the beat list
                    {
                        if (beats.get(i).getHit() == false)
                        {
                            g.drawImage(images.get(beats.get(i).getColor() - 1), beats.get(i).getX(), beats.get(i).getY(),null);
                        }
                    }             
                    if (beats.get(beatCounter).getX() <= 0)
                    {
                        beats.get(beatCounter).hit();
                        accuracyString = "Miss";
                        health = health - 10;
                        stringTime = 0;
                        multiplier=1;
                        ++beatCounter;
                    }
                    int p = points;
                    int counter = 0;
                    while (p > 0)
                    {
                        int digit = p%10;
                        g.drawImage(numbers.get(digit), 850 - (counter * 20), 180,null);
                        p = p / 10;
                        ++counter;
                    }
                    int b = multiplier;
                    counter = 0;
                    while (b > 0)
                    {
                        int digit = b % 10;
                        g.drawImage(numbers.get(digit+20), 85 - (counter * 20), 190,null);
                        b = b / 10;
                        ++counter;
                    }
                    int a = (int)(getPlayerAccuracy() * 100);
                    counter = 0;
                    while (a > 0)
                    {
                        int digit = a % 10;
                        g.drawImage(numbers.get(digit+10), 800 - (counter * 35), 240,null);
                        a = a / 10;
                        ++counter;
                    }
                    g.drawImage(etc.get(1), 840, 245,null);
                    g.drawImage(etc.get(2), 120, 190,null);
                    if (checkDead() == true)
                    {
                        ++monsterCounter;
                    }
                    if(monsterCounter>9)
                    {
                        battle = false;
                        g.drawImage(etc.get(3), 0, 0,null);
                        g.setColor(Color.BLACK);
                        g.drawString("VICTORY", 130, 360);
                    }
                    if (health < 0)
                    {
                        battle = false;
                        g.drawImage(etc.get(3), 0, 0,null);
                        g.setColor(Color.BLACK);
                        g.drawString("GAME OVER", 130, 360);
                    }
                    bs.show();
                    Toolkit.getDefaultToolkit().sync();
                    g.dispose();
                    Thread.sleep(15);
                }
                catch (Exception e)
                {
                    System.exit(0);
                }
            }
        }
     }
    public void generateBeats()  //fills in the interval array with random inervals
    {
        for (int i = 0; i < intervals.length; i++)
        {
            int roll = (int)(Math.random()*10 + 1);
            if (roll >= 8)
            {
                intervals[i] = 20;
            }
            if (roll < 4)
            {
                intervals[i] = 100;
            }
            else
            {
                intervals[i] = 200;
            }
        }
    }
    public int generateColor() //when making a beat, this determines if it's red blue or both
    {
        int roll = (int)(Math.random()*10 + 1);
        if (roll < 5)
        {
            return 1;
        }
        else if (roll > 8)
        {
            return 3;
        }
        else
        {
            return 2;
        }
    }
    public void nextBeat() //adds a beat to the beatlist
    {
        beatTime = beatTime + 5;
        if (beatTime == intervals[intervalCounter])
        {
            beats.add(new Beat(generateColor()));
            beatTime = 0;
            ++intervalCounter;
        }
    }
    public int calculatePosition() //all the calculations when a button is pressed
    {
        int position = beats.get(beatCounter).getX();
        multiplier++;
        if (position <= 145 && position >= 135)
        {
            perfect++;
            accuracyString = "Perfect";
            points = points + (300*multiplier);
        }
        else if ((position > 145 && position <= 155) || (position >= 130 && position < 135))
        {
            good++;
            accuracyString = "Good";
            points = points + (100*multiplier);
        }
        else if ((position < 130 && position >= 125) || (position > 155 && position <= 205))
        {
            bad++;
            points = points + (50*multiplier);
            accuracyString = "Bad";
        }
        else
        {
            multiplier=1;
            accuracyString = "Miss";
        }
        return position;
    }
    public double getPlayerDamage()
    {
        double x = 0;
        if(getAccuracy()==1)
        x=100;
        if(getAccuracy()==2)
        x=50;
        if(getAccuracy()==3)
        x=20;
        double damage = x + (((double)multiplier/100)*x);
        return damage;
    }
    public int getAccuracy() //returns the accuray of the note
    {
        if (accuracyString.equals("Miss"))
        {
            return 4;
        }
        else if (accuracyString.equals("Good"))
        {
            return 2;
        }
        else if (accuracyString.equals("Bad"))
        {
            return 3;
        }
        return 1;
    }
    public double getPlayerAccuracy()
    {
        double total=beatCounter;
        accuracy=((bad*50)+(good*100)+(perfect*300))/(total*300);
        return accuracy;
    }
    public void playMusic()
    {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("assets\\music\\TimeTemple.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } 
        catch(Exception ex) 
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
    public void storeImages()
    {
        try
        {
            images.add(ImageIO.read(new File("assets\\beatRed.png"))); //0
            images.add(ImageIO.read(new File("assets\\beatBlue.png"))); //1
            images.add(ImageIO.read(new File("assets\\beatBoth.png"))); //2
            images.add(ImageIO.read(new File("assets\\backgroundTop.jpg"))); //3
            images.add(ImageIO.read(new File("assets\\target.png"))); //4
            images.add(ImageIO.read(new File("assets\\facePerfect.png"))); //5
            images.add(ImageIO.read(new File("assets\\faceGood.png"))); //6
            images.add(ImageIO.read(new File("assets\\faceBad.png"))); //7
            images.add(ImageIO.read(new File("assets\\faceMiss.png"))); //8
            images.add(ImageIO.read(new File("assets\\attack1.png"))); //9
            images.add(ImageIO.read(new File("assets\\attack2.png"))); //10
            images.add(ImageIO.read(new File("assets\\attack3.png"))); //11
            images.add(ImageIO.read(new File("assets\\backgroundMid.jpg"))); //12
            images.add(ImageIO.read(new File("assets\\snailStand.png"))); //13
            images.add(ImageIO.read(new File("assets\\snailHit.png"))); //14
            images.add(ImageIO.read(new File("assets\\stand.png"))); //15
        }
        catch (Exception e)
        {
        }
    }
    /*
    public void storeImages2(int track1, int track2, String type, String monster)
    {
        try
        {
            String path = "assets\\Animation\\" + type + "\\" + type + monster;
            int i = 1;
            while(i>0)
            {
                String a = "" + i;
                images.add(ImageIO.read(new File(path + a + ".png")));
                i++;
            }
        }
        catch(Exception e)
        {
            track1++;
            String a = "";
            String b = "";
            if(track1==1)
            a="Snail";
            if(track1==2)
            a="Slime";
            if(track1==3)
            a="Mushroom";  
            if(track1==4)
            a="Mano";
            if(track1==5)
            a="Golem";
            if(track1==6)
            a="Balrog";
            if(track1==7)
            a="Griffey";
            if(track1==8)
            a="Manon";
            if(track1==9)
            a="Pink";
            if(track1==10)
            {
                track2++;
                track1=1;
            }
            if(track2==1)
            b="Standing";
            if(track2==2)
            b="Death";
            if(track2==3)
            b="Damaged";
            if(track2==4)
            b="Attack";
            if(track2!=5)
            storeImages2(track1,track2,b,a);
        }
    }
    */
    public void storeStandHit()
    {
        try
        {
            for (int i = 0; i < 9; i++)
            {
                monsterStand.add(ImageIO.read(new File("Monsters\\Standing\\" + (monsters.get(i).getName())  + ".png")));
                monsterHit.add(ImageIO.read(new File("Monsters\\Damaged\\" + (monsters.get(i).getName())  + ".png")));
            }
        }
        catch (Exception e)
        {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
    public void storeNumbers()
    {
        try
        {
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 10; j++)
                {
                    if (i == 0)
                    {
                        numbers.add(ImageIO.read(new File("BetterNumbers\\Score" + j + ".png")));
                    }
                    else if(i == 1)
                    {
                        numbers.add(ImageIO.read(new File("BetterNumbers\\Accuracy" + j + ".png")));
                    }
                    else if(i == 2)
                    {
                        numbers.add(ImageIO.read(new File("BetterNumbers\\Multiplier" + j + ".png")));
                    }
                    else
                    {
                        numbers.add(ImageIO.read(new File("BetterNumbers\\CritDamage" + j + ".png")));
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("ha");
        }
    }
    public void storeEtc()
    {
        try
        {
                etc.add(ImageIO.read(new File("BetterNumbers\\DecimalPoint.png")));
                etc.add(ImageIO.read(new File("BetterNumbers\\Percent.png")));
                etc.add(ImageIO.read(new File("BetterNumbers\\Combo.png")));
                etc.add(ImageIO.read(new File("ETC\\Defeat.png")));
                etc.add(ImageIO.read(new File("ETC\\Victory.png")));
                etc.add(ImageIO.read(new File("ETC\\Failed1.png")));//5
                etc.add(ImageIO.read(new File("ETC\\Failed2.png")));
                etc.add(ImageIO.read(new File("ETC\\Failed3.png")));
                etc.add(ImageIO.read(new File("ETC\\Failed4.png")));
                etc.add(ImageIO.read(new File("ETC\\Failed5.png")));
                etc.add(ImageIO.read(new File("ETC\\Failed6.png")));
                etc.add(ImageIO.read(new File("ETC\\Failed7.png")));
                etc.add(ImageIO.read(new File("ETC\\Failed8.png")));
        }
        catch(Exception e)
        {
        }
    }
    public boolean checkDead()
    {
        if (monsters.get(monsterCounter).getHealth() <= 0)
        {
            return true;
        }
        return false;
    }
    public static void main(String[] args)
    {
        Main main = new Main();
        main.startNow();
    }
    public void loadBackground() //stores an image 
    {
        try
        {
            g.drawImage(ImageIO.read(new File("assets\\background.jpg")), 0, 178,null);
            g.drawImage(ImageIO.read(new File("assets\\floor.png")), 0, 622,null);
            g.drawImage(images.get(15), 350, 570,null);
            g.drawImage(monsterStand.get(monsterCounter), 405, 360,null);
            /*
            Image icon = new ImageIcon(getClass().getResource("assets\\zakum.gif")).ge‌​tImage();
            g.drawImage(icon, 100, 200, this);
            */
            
        }
        catch (Exception e)
        {
            System.out.println("lol");
        }
    }
}