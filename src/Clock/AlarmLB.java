package Clock;
import java.awt.Color;

public class AlarmLB extends Thread{
    public Sound sound;  
    public javax.swing.JDialog JD;
    public Color color;
    private int beep = 2;
    private int beepAt = 2;
    boolean soundOK = false;
    
    public AlarmLB(){}
    public AlarmLB(javax.swing.JDialog j){
        JD = j;
        color = new Color(JD.getBackground().getRGB());    
        try{
            sound = new Sound("audio/Alarm02.wav"); //"C:/javaImg/Alarm02.wav"
            soundOK = true;
        }catch(java.lang.Exception e){
            System.out.println(e.getMessage());
            soundOK = false;
        }
    }
    public void run(){     
        try{
            sound.loop();
        }catch(java.lang.Exception e){
            System.out.println(e.getMessage());
        }
        while(true){  
            //EMIT BEEP if unable to load sound
            if(soundOK == false){                
                beep++;
                if(beep>=beepAt){
                    java.awt.Toolkit.getDefaultToolkit().beep();            
                    beep = 0;
                }
            }
            
            //FLASH JDialog LIGHTS
            if(JD!=null){
                if(JD.getContentPane().getBackground() != Color.red){
                    JD.getContentPane().setBackground(Color.red);                    
                    JD.repaint();
                }else{
                    JD.getContentPane().setBackground(color);
                    JD.repaint();
                }
            }
            
            //WAIT
            try{
                this.sleep(500); //@100fps
            }catch(java.lang.InterruptedException e){
                System.out.println("Error: " + e.getMessage());
            } 
        }
    }
}
