package Clock;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Display extends javax.swing.JPanel{
    //INSTANCE VARIABLES
    protected java.awt.Image img; //the image that is drawn in paintComponnent method
    protected int width, height; //size of the graphic panel
    
    //CONSTRUCTOR
    Display(int w, int h){
        super();
        width = w;
        height = h;
        img = new java.awt.image.BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); //adapt image to size
        this.setPreferredSize(new java.awt.Dimension(width, height)); //set panel size (the class calling this method should use: jFrame.add(display); jFrame.pack();
        this.setDoubleBuffered(true);
        this.setOpaque(false);     
    }
    
    public void paintImage(java.awt.Image I){
        img = I;        
        this.repaint();
    } //receive an image as argument and paint it on componnent using repaint() that calls paintComponnent() that itself draw the image img
    public java.awt.Image getImage(){
        return img;
    }
    public void resetImg(){
        Graphics g = img.getGraphics();
        g.setColor(Color.black);
        g.fillRect(1, 1, width, height);
    }
    public void resizeDisplay(int w, int h){
        width = w;
        height = h;
        img = new java.awt.image.BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.setPreferredSize(new java.awt.Dimension(width, height));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);                   
        g.drawImage(img, 0, 0, this);              
    }
}
