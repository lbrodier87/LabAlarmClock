package Clock;

import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Clock{
    int hour, m, s;
    DecimalFormat df = new DecimalFormat("00");
    Color hColor=Color.RED, mColor=Color.GREEN, sColor=Color.BLUE.brighter();
    java.awt.image.BufferedImage clockImg;    
    String imgName[] = {"img/Clock6.png", "img/Clock1.png", "img/Clock2.png", "img/Clock3.png", "img/Clock4.png", "img/Clock5.png", "img/Clock7.png", "img/Clock8.png", "img/Clock9.png", "img/Clock10.png", "img/Clock11.png", "img/Clock12.png", "img/Clock13.png"};
    int index = 0;
    boolean imageLoaded = false; 
    boolean background = false;
    java.awt.event.KeyListener KL = new java.awt.event.KeyListener(){
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyPressed(KeyEvent e) {}
        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_H){
                switchImage();
            }
            if(e.getKeyCode() == KeyEvent.VK_S){
                SA = new SetAlarm(Clock.this);
                SA.setLocationRelativeTo(null);
                SA.setVisible(true);
            }
            if(e.getKeyCode() == KeyEvent.VK_A){
                alarm = !alarm;
                SA.updateDisplay();
            }
            if(e.getKeyCode() == KeyEvent.VK_C){
                chronoON = !chronoON;
                chrono.pauseChronometer();
            }            
            if(e.getKeyCode() == KeyEvent.VK_X){
                if(chronoON){
                    startChrono();
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_R){
                if(chronoON){
                    resetChrono();
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_F1){                
                help.setLocationRelativeTo(null);
                help.setVisible(true);
            }
            if(e.getKeyCode() == KeyEvent.VK_D){                
                displayHour = !displayHour;
            }
            if(e.getKeyCode() == KeyEvent.VK_B){                
                background = !background;
            } 
            if(e.getKeyCode() == KeyEvent.VK_M){                
                countdown = !countdown;
            }  
        }
    };
    java.awt.event.MouseWheelListener MWL = new java.awt.event.MouseWheelListener() {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if(alarm){
                if(!e.isShiftDown()){         
                    alarmM -= e.getWheelRotation();
                    if(alarmM>=60){
                        alarmM = 0;
                        alarmH += 1;
                        if(alarmH>=24){
                            alarmH = 0;
                        }
                    }else if(alarmM<0){
                        alarmM = 59;
                        alarmH -= 1;
                        if(alarmH<0){
                            alarmH = 23;
                        }
                    }
                }else{
                    alarmH -= e.getWheelRotation();
                    if(alarmH>=24){
                        alarmH = 0;
                    }else if(alarmH<0){
                        alarmH = 23;
                    }
                }
                SA.updateDisplay();
            }
        }
    };
    
    boolean alarm = false;
    int alarmH=0, alarmM=0;
    Color alarmColor = Color.orange;
    SetAlarm SA =  new SetAlarm(this);    
    TimerDialog jDialog1 = new TimerDialog(new java.awt.Frame("Alarm !"), true);
    AlarmLB alarmlb = new AlarmLB(jDialog1);
    
    Chronometer chrono = new Chronometer();
    boolean chronoON = false;
    Color chronoColor = Color.GREEN.darker();
    
    HelpDialog help = new HelpDialog(new javax.swing.JFrame(), false);
    boolean displayHour = false;
    
    boolean countdown = false; //new 
    int h_r, m_r, s_r;
    
    Clock(boolean loadImg){
        if(loadImg){
            try{
               clockImg = javax.imageio.ImageIO.read(getClass().getResource(imgName[index])); 
               imageLoaded = true;
            }catch(java.io.IOException e){
               System.out.println("Error reading image...");
               imageLoaded = false;
            }  
        }else{
            imageLoaded = false;
        }        
        jDialog1.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent we) {
                alarmlb.stop();
                try{
                    alarmlb.sound.stop();
                }catch(java.lang.Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });
        jDialog1.addMouseListener(new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                alarmlb.stop();
                alarmlb.JD.getContentPane().setBackground(alarmlb.color);
                try{
                    alarmlb.sound.stop();
                }catch(java.lang.Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });  
        jDialog1.setModal(false); 
        help.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        help.setResizable(false);
    }    
    
    public void switchImage(){     
        index++;
        if(index>imgName.length-1){
            index = 0;
        }
        try{
           clockImg = javax.imageio.ImageIO.read(getClass().getResource(imgName[index]));
           imageLoaded = true;
        }catch(java.io.IOException e){
           System.out.println("Error reading image...");
           imageLoaded = false;
        } 
    }
    public void setAlarm(int AH, int AM, boolean alarmBool){
        alarmH = AH;
        alarmM = AM;        
        alarm = alarmBool;
    }
    
    public void setTime(int hour, int min, int sec){
        this.hour = hour;        
        m = min;
        s = sec;
        
        if(alarm && (hour==alarmH || hour==alarmH-12 || hour==alarmH+12) && m==alarmM && s==0 && !alarmlb.isAlive()){            
            jDialog1.setLocationRelativeTo(null);
            jDialog1.setAlwaysOnTop(true);            
            jDialog1.jLabel11.setText("Le timer est terminé!");
            jDialog1.jLabel13.setText("Il est " + hour + " h " + m + " min "); 
            try{
                jDialog1.jLabel12.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("img/Alarm2.png"))));
            }catch(java.io.IOException e){}
            alarmlb = new AlarmLB(jDialog1);
            alarmlb.start();
            jDialog1.setVisible(true); 
        }
        
    }
    public void drawHorloge(int x, int y, int w, int h, java.awt.Graphics g){
        java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        
        int Cx = (int)(x+(double)w/2);
        int Cy = (int)(y+(double)h/2);    
        
//******CLOCK BACKGROUND
        if(imageLoaded == false){
            g.setColor(Color.darkGray);
            g.fillRect(x, y, w, h);         

            g.setColor(Color.black);        
            g.fillOval(x, y, w, h);
            g.setColor(Color.lightGray);
            g.fillOval(x+2, y+2, w-4, h-4);
       
            //checkmarks min
            g.setColor(Color.darkGray);
            for(double i=0; i<360; i+=6){
                double angle = i+90;
                double sX = Math.cos(Math.toRadians(angle));
                double sY = Math.sin(Math.toRadians(angle));
                g2.setStroke(new java.awt.BasicStroke(1));
                g.drawLine((int)(Cx-sX*(w)/2.5), (int)(Cy-sY*(h)/2.5), (int)(Cx-sX*(w-4)/2), (int)(Cy-sY*(h-4)/2));
            }
            //checkmarks Hours
            g.setColor(Color.black);
            for(double i=0; i<359; i+=30){
                double angle = i+90;
                double sX = Math.cos(Math.toRadians(angle));
                double sY = Math.sin(Math.toRadians(angle));
                g2.setStroke(new java.awt.BasicStroke(6));
                g.drawLine((int)(Cx-sX*(w)/2.8), (int)(Cy-sY*(h)/2.8), (int)(Cx-sX*(w-4)/2), (int)(Cy-sY*(h-4)/2));
            }
        }else{  
            if(background){
                g.setColor(Color.white);
                g.fillOval(0,0, w, h);
            }
            g.drawImage(clockImg, 0, 0, null);
        }  
        
//******ALARM
        if(alarm == true){
            //HOUR ALARM
            g.setColor(alarmColor);
            double angle = (alarmH*30)+90 +((double)alarmM/60*30);
            double sX = Math.cos(Math.toRadians(angle));
            double sY = Math.sin(Math.toRadians(angle));
            g2.setStroke(new java.awt.BasicStroke(6));
            g2.drawLine(Cx, Cy, (int)(Cx-sX*(w-30)/3), (int)(Cy-sY*(h-30)/3));  
            
            //MIN ALARM
            g.setColor(alarmColor);
            angle = (alarmM*6)+90; 
            sX = Math.cos(Math.toRadians(angle));
            sY = Math.sin(Math.toRadians(angle));    
            g2.setStroke(new java.awt.BasicStroke(4));
            g.drawLine(Cx, Cy, (int)(Cx-sX*(w-8)/2.5), (int)(Cy-sY*(h-8)/2.5));
        }
        
//******CHRONO
        int chronoH=0;
        int chronoM=0;
        int chronoS=0;
        int chronoMS=0;
        if(chronoON==true){
            long ms = chrono.getChronoVal();
            chronoH = (int)Math.floor(ms/(1000*60*60));
            chronoM = (int)Math.floor(ms/(1000*60) - chronoH*60);
            chronoS = (int)Math.floor(ms/(1000) - chronoH*60*60 - chronoM*60);
            chronoMS = (int)Math.floor(ms - chronoH*60*60*1000 - chronoM*60*1000 - chronoS*1000);
            //HOUR CHRONO
            g.setColor(chronoColor);
            double angle = (chronoH*30)+90;
            double sX = Math.cos(Math.toRadians(angle));
            double sY = Math.sin(Math.toRadians(angle));
            g2.setStroke(new java.awt.BasicStroke(4));
            g2.drawLine(Cx, Cy, (int)(Cx-sX*(w-30)/3), (int)(Cy-sY*(h-30)/3));  
            
            //MIN CHRONO
            g.setColor(chronoColor);
            angle = (chronoM*6)+90; 
            sX = Math.cos(Math.toRadians(angle));
            sY = Math.sin(Math.toRadians(angle));    
            g2.setStroke(new java.awt.BasicStroke(2));
            g.drawLine(Cx, Cy, (int)(Cx-sX*(w-8)/2.5), (int)(Cy-sY*(h-8)/2.5));
            
            //SEC CHRONO
            g.setColor(chronoColor);
            angle = (chronoS*6)+90 + ((double)chronoMS/1000*6); 
            sX = Math.cos(Math.toRadians(angle));
            sY = Math.sin(Math.toRadians(angle));    
            g2.setStroke(new java.awt.BasicStroke(1));
            g.drawLine(Cx, Cy, (int)(Cx-sX*(w-8)/2.25), (int)(Cy-sY*(h-8)/2.25));            
        }
        
        
//******MAIN CLOCK !!
        // hour needle
        g.setColor(hColor);
        double angle = (hour*30)+90 +((double)m/60*30);
        double sX = Math.cos(Math.toRadians(angle));
        double sY = Math.sin(Math.toRadians(angle));
        g2.setStroke(new java.awt.BasicStroke(4));
        g2.drawLine(Cx, Cy, (int)(Cx-sX*(w-30)/3), (int)(Cy-sY*(h-30)/3));        
        angle+=180;
        sX = Math.cos(Math.toRadians(angle));
        sY = Math.sin(Math.toRadians(angle));
        g2.setStroke(new java.awt.BasicStroke(4));
        g2.drawLine(Cx, Cy, (int)(Cx-sX*(w-30)/9), (int)(Cy-sY*(h-30)/9));
        
        // min needle
        g.setColor(mColor);
        angle = (m*6)+90; 
        sX = Math.cos(Math.toRadians(angle));
        sY = Math.sin(Math.toRadians(angle));    
        g2.setStroke(new java.awt.BasicStroke(2));
        g.drawLine(Cx, Cy, (int)(Cx-sX*(w-8)/2.5), (int)(Cy-sY*(h-8)/2.5));
        angle+=180;
        sX = Math.cos(Math.toRadians(angle));
        sY = Math.sin(Math.toRadians(angle));
        g2.setStroke(new java.awt.BasicStroke(2));
        g.drawLine(Cx, Cy, (int)(Cx-sX*(w-8)/8), (int)(Cy-sY*(h-8)/8));
        
        // sec needle
        g.setColor(sColor);
        angle = (s*6)+90; 
        sX = Math.cos(Math.toRadians(angle));
        sY = Math.sin(Math.toRadians(angle));    
        g2.setStroke(new java.awt.BasicStroke(1));
        g.drawLine(Cx, Cy, (int)(Cx-sX*(w-8)/2.25), (int)(Cy-sY*(h-8)/2.25));
        angle+=180;
        sX = Math.cos(Math.toRadians(angle));
        sY = Math.sin(Math.toRadians(angle));
        g2.setStroke(new java.awt.BasicStroke(1));
        g.drawLine(Cx, Cy, (int)(Cx-sX*(w-8)/7), (int)(Cy-sY*(h-8)/7));
        
        //center button
        g.setColor(Color.black);        
        g.fillOval(Cx-8, Cy-8, 16, 16); 
        g.setColor(Color.lightGray);
        g.fillOval(Cx-2, Cy-2, 4, 4); 
        
        if(displayHour){
            g.setColor(Color.black);
            g.setFont(new java.awt.Font("f2", java.awt.Font.PLAIN, 12));
            g.drawString(df.format(hour) + "h "+ df.format(m) + "m "+ df.format(s) + "s", w/2 - g.getFontMetrics().stringWidth(df.format(hour) + "h "+ df.format(m) + "m "+ df.format(s) + "s")/2, h/2 - h/12);
        }
        
        if(displayHour && countdown && !chronoON){ //new
            g.setColor(alarmColor.darker());
            calcRemeiningTime();
            g.setFont(new java.awt.Font("f2", java.awt.Font.PLAIN, 12));
            g.drawString(df.format(h_r) + "h "+ df.format(m_r) + "m "+ df.format(s_r) + "s", w/2 - g.getFontMetrics().stringWidth(df.format(h_r) + "h "+ df.format(m_r) + "m "+ df.format(s_r) + "s")/2, h/2 + h/12+10);
        }

        if(displayHour && chronoON){
            g.setColor(chronoColor.darker());
            g.setFont(new java.awt.Font("f2", java.awt.Font.PLAIN, 12));
            g.drawString(chronoH + "h "+ df.format(chronoM) + "m "+ df.format(chronoS) + "s " + df.format(chronoMS/10) + "ms", w/2 - g.getFontMetrics().stringWidth(chronoH + "h "+ df.format(chronoM) + "m "+ df.format(chronoS) + "s " + df.format(chronoMS/10) + "ms")/2, h/2 + h/12+10);
        }
    }
    
    public void startChrono(){   
        if(chrono.isRunning){
            chrono.pauseChronometer();
        }else{
            chrono.startChronometer();
        }
    }    
    public void resetChrono(){
        chrono.resetChronometer();
    }
    
    public void calcRemeiningTime(){
        int total_sec_diff = hour*60*60 + m*60 + s - alarmH*60*60 - alarmM*60;
        s_r = (int)((double)total_sec_diff % 60);
        double mins_diff = (double)total_sec_diff/60;
        if(mins_diff > 0){
            mins_diff = Math.floor(mins_diff);
        }else{
            mins_diff = Math.ceil(mins_diff);
        }
        m_r = (int)(mins_diff % 60);
        h_r = (int)mins_diff / 60;
        
        
    }
}
