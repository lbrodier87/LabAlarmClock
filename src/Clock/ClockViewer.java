package Clock;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import javax.swing.JFrame;

public class ClockViewer extends Thread{
    Clock clock;
    javax.swing.JFrame f;
    Display d;
    int width=200, height=200;
    java.awt.image.BufferedImage img;    
    static String version = "v1.5";    
    
    public static void main (String arg[]){
        ClockViewer CW = new ClockViewer();
        CW.init2();
    }
    
    //not used, replaced by transparent frame init2()
    public void init(){
        clock = new Clock(true);  
        clock.hColor = Color.black;
        clock.mColor = Color.black;
        clock.sColor = Color.red;
        f = new javax.swing.JFrame("Clock " + version);        
        f.addKeyListener(clock.KL);
        f.addMouseWheelListener(clock.MWL);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE); 
        img = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        d = new Display(width, height);
        f.add(d);
        f.pack();
        f.setVisible(true);
        this.start();        
    }
    
    
    //test translucent
    public void init2(){
        f = new JFrame();
        f.setUndecorated(true);
        f.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2 & e.getButton() == 1) {
                        f.dispose();
                        System.exit(0);
                    }else if(e.getButton() == 3){
                        if(f.getOpacity() == 1f){
                            f.setOpacity(0.25f);
                        }else{
                            f.setOpacity(f.getOpacity()+0.25f);
                        }
                    }
                }
            });
        f.addMouseMotionListener(new java.awt.event.MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    f.setLocation(e.getXOnScreen()-f.getWidth()/2, e.getYOnScreen()-f.getHeight()/2); 
                }
                @Override
                public void mouseMoved(MouseEvent e) {}
            });            
        f.setBackground(new Color(0,0,0,0));
        
        
        f.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_T){
                    f.setAlwaysOnTop(!f.isAlwaysOnTop());
                }
            }
        });
                                
        clock = new Clock(true);  
        clock.hColor = Color.black;
        clock.mColor = Color.black;
        clock.sColor = Color.red;
                    
        f.addKeyListener(clock.KL);
        f.addMouseWheelListener(clock.MWL);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE); 
        img = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
       
        d = new Display(width, height);        
        f.add(d);
        f.pack();
        f.setVisible(true);
        this.start();        
    }   
    
    public void iterate(){
        java.util.Calendar c = java.util.Calendar.getInstance();
        clock.setTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));        
    }
    public void generateGraphics(){
        img = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics g = img.getGraphics();        
        clock.drawHorloge(0, 0, width, height, g);
        d.paintImage(img);
    }
    public void run(){
        while(true){
            iterate();
            generateGraphics();
            try{
                int slp = 100;
                if(clock.chronoON){
                    slp = 10;
                }
                this.sleep(slp); //@10fps or @100fps if chrono ON
            }catch(java.lang.InterruptedException e){
                System.out.println("Error: " + e.getMessage());
            } 
        }
    }
    
}
