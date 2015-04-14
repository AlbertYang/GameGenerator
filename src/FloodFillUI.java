  
  
import java.awt.Color;  
import java.awt.Dimension;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.MediaTracker;  
import java.awt.event.MouseEvent;  
import java.awt.event.MouseListener;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.IOException;  
  
import javax.imageio.ImageIO;  
import javax.swing.JComponent;  
import javax.swing.JFileChooser;  
import javax.swing.JFrame;  
  
public class FloodFillUI extends JComponent implements MouseListener{  
      
    /** 
     *  
     */  
    private static final long serialVersionUID = 1L;  
    private BufferedImage rawImg;  
    private MediaTracker tracker;  
    private Dimension mySize;  
    public int clickNum;
    FloodFillAlgorithm ffa;  
    public FloodFillUI(File f)  
    {  
        try {  
            rawImg = ImageIO.read(f);  
        } catch (IOException e1) {  
            e1.printStackTrace();  
        }  
          
        tracker = new MediaTracker(this);  
        tracker.addImage(rawImg, 1);  
          
        // blocked 10 seconds to load the image data  
        try {  
            if (!tracker.waitForID(1, 1000)) {  
                System.out.println("Load error.");  
                System.exit(1);  
            }// end if  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
            System.exit(1);  
        }// end catch  
          
        mySize = new Dimension(rawImg.getWidth(), rawImg.getHeight());  
        this.addMouseListener(this);  
        ffa = new FloodFillAlgorithm(rawImg); 
        clickNum = 0;
    }  
    
    public void loadFile(File f){
    	try {  
            rawImg = ImageIO.read(f);  
        } catch (IOException e1) {  
            e1.printStackTrace();  
        }  
          
        tracker = new MediaTracker(this);  
        tracker.addImage(rawImg, 1);  
          
        // blocked 10 seconds to load the image data  
        try {  
            if (!tracker.waitForID(1, 1000)) {  
                System.out.println("Load error.");  
                System.exit(1);  
            }// end if  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
            System.exit(1);  
        }// end catch  
          
        mySize = new Dimension(rawImg.getWidth(), rawImg.getHeight());  
        this.addMouseListener(this);  
        ffa = new FloodFillAlgorithm(rawImg); 
        clickNum = 0;
    }
    public void paint(Graphics g) {  
        Graphics2D g2 = (Graphics2D) g;  
        g2.drawImage(rawImg, 0, 0, rawImg.getWidth(), rawImg.getHeight(), null);  
    }  
    public Dimension getPreferredSize() {  
        return mySize;  
    }  
      
    public Dimension getMinimumSize() {  
        return mySize;  
    }  
      
    public Dimension getMaximumSize() {  
        return mySize;  
    }  

    @Override  
    public void mouseClicked(MouseEvent e) {  
    	clickNum++;
//        System.out.println("Mouse Clicked Event!!");  
        int x = (int)e.getPoint().getX();  
        int y = (int)e.getPoint().getY();  
//        System.out.println("mouse location x = " + x); // column  
//        System.out.println("mouse location y = " + y); // row  
//        System.out.println();  
        long startTime = System.nanoTime();  
        // ffa.floodFill4(x, y, Color.GREEN.getRGB(), ffa.getColor(x, y));  
        // ffa.floodFill8(x, y, Color.GREEN.getRGB(), ffa.getColor(x, y));  
        // ffa.floodFillScanLine(x, y, Color.GREEN.getRGB(), ffa.getColor(x, y)); // 13439051  
        ffa.floodFillScanLine(x, y, Color.GREEN.getRGB(), ffa.getColor(x, y)); // - 16660142 
        
        long endTime = System.nanoTime() - startTime;  
//        System.out.println("run time = " + endTime);  
        try {
			ffa.updateResult();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
        this.repaint();  
//        ImageIO.write(bufferedImage,"jpeg",file);
    }  
  
    @Override  
    public void mousePressed(MouseEvent e) {  
        // TODO Auto-generated method stub  
          
    }  
  
    @Override  
    public void mouseReleased(MouseEvent e) {  
        // TODO Auto-generated method stub  
          
    }  
  
    @Override  
    public void mouseEntered(MouseEvent e) {  
        // TODO Auto-generated method stub  
          
    }  
  
    @Override  
    public void mouseExited(MouseEvent e) {  
        // TODO Auto-generated method stub  
          
    }  
    
//    public static void main(String[] args) {  
//        JFileChooser chooser = new JFileChooser();  
//        chooser.showOpenDialog(null);  
//        File f = chooser.getSelectedFile();  
//        FloodFillUI ffui = new FloodFillUI(f);  
//        JFrame imageFrame = new JFrame("Flood Fill Algorithm ");  
//        imageFrame.getContentPane().add(ffui);  
//        imageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
//        imageFrame.pack();  
//        imageFrame.setVisible(true); 
//    } 
  
}  