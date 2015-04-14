  
  
import java.awt.Color;
import java.awt.image.BufferedImage;  
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
  
//import com.gloomyfish.filter.study.AbstractBufferedImageOp;  
  
public class FloodFillAlgorithm extends AbstractBufferedImageOp {  
  
    private BufferedImage inputImage;  
    private BufferedImage originImage;
    private int[] inPixels;  
    private int width;  
    private int height;  
      
    //  stack data structure  
    private int maxStackSize = 500; // will be increased as needed  
    private int[] xstack = new int[maxStackSize];  
    private int[] ystack = new int[maxStackSize];  
    private int stackSize;  
  
    public FloodFillAlgorithm(BufferedImage rawImage) {  
        this.inputImage = rawImage;  
        width = rawImage.getWidth();  
        height = rawImage.getHeight();  
        inPixels = new int[width*height];  
        getRGB(rawImage, 0, 0, width, height, inPixels );  
    }  
  
    public BufferedImage getInputImage() {  
        return inputImage;  
    }  
  
    public void setInputImage(BufferedImage inputImage) {  
        this.inputImage = inputImage;  
    }  
      
    public int getColor(int x, int y)  
    {  
        int index = y * width + x;  
        return inPixels[index];  
    }  
      
    public void setColor(int x, int y, int newColor)  
    {  
        int index = y * width + x;  
        inPixels[index] = newColor;  
    }  
      
    public void updateResult() throws IOException  
    {  
        setRGB( inputImage, 0, 0, width, height, inPixels );  
        ImageIO.write(inputImage,"png",new File("tmp.png"));
    }  
      
    /** 
     * it is very low calculation speed and cause the stack overflow issue when fill  
     * some big area and irregular shape. performance is very bad. 
     *  
     * @param x 
     * @param y 
     * @param newColor 
     * @param oldColor 
     */  
    public void floodFill4(int x, int y, int newColor, int oldColor)  
    {  
        if(x >= 0 && x < width && y >= 0 && y < height   
                && getColor(x, y) == oldColor && getColor(x, y) != newColor)   
        {   
            setColor(x, y, newColor); //set color before starting recursion  
            floodFill4(x + 1, y,     newColor, oldColor);  
            floodFill4(x - 1, y,     newColor, oldColor);  
            floodFill4(x,     y + 1, newColor, oldColor);  
            floodFill4(x,     y - 1, newColor, oldColor);  
        }     
    }  
    /** 
     *  
     * @param x 
     * @param y 
     * @param newColor 
     * @param oldColor 
     */  
    public void floodFill8(int x, int y, int newColor, int oldColor)  
    {  
        if(x >= 0 && x < width && y >= 0 && y < height &&   
                getColor(x, y) == oldColor && getColor(x, y) != newColor)   
        {   
            setColor(x, y, newColor); //set color before starting recursion  
            floodFill8(x + 1, y,     newColor, oldColor);  
            floodFill8(x - 1, y,     newColor, oldColor);  
            floodFill8(x,     y + 1, newColor, oldColor);  
            floodFill8(x,     y - 1, newColor, oldColor);  
            floodFill8(x + 1, y + 1, newColor, oldColor);  
            floodFill8(x - 1, y - 1, newColor, oldColor);  
            floodFill8(x - 1, y + 1, newColor, oldColor);  
            floodFill8(x + 1, y - 1, newColor, oldColor);  
        }     
    }  
      
    /** 
     *  
     * @param x 
     * @param y 
     * @param newColor 
     * @param oldColor 
     */  
    public void floodFillScanLine(int x, int y, int newColor, int oldColor)  
    {  
        if(oldColor == newColor) return;  
        if(!simColor(getColor(x, y),oldColor)) return;  
//        System.out.println(getColor(x,y));
//        Color d = new Color(Color.WHITE.getRGB());
//        System.out.println(d);
//        System.out.println(Color.WHITE.getRGB());
//        return;
        
        int y1;  
          
        //draw current scanline from start position to the top  
        y1 = y;  
        while(y1 < height && simColor(getColor(x, y1),oldColor))  
        {  
            setColor(x, y1, newColor);  
            y1++;  
        }      
          
        //draw current scanline from start position to the bottom  
        y1 = y - 1;  
        while(y1 >= 0 && simColor(getColor(x, y1),oldColor))  
        {  
            setColor(x, y1, newColor);  
            y1--;  
        }  
          
        //test for new scanlines to the left  
        y1 = y;  
        while(y1 < height && getColor(x, y1) == newColor)  
        {  
            if(x > 0 && simColor(getColor(x-1, y1),oldColor))   
            {  
                floodFillScanLine(x - 1, y1, newColor, oldColor);  
            }   
            y1++;  
        }  
        y1 = y - 1;  
        while(y1 >= 0 && getColor(x, y1) == newColor)  
        {  
            if(x > 0 && simColor(getColor(x-1, y1),oldColor))   
            {  
                floodFillScanLine(x - 1, y1, newColor, oldColor);  
            }  
            y1--;  
        }   
          
        //test for new scanlines to the right   
        y1 = y;  
        while(y1 < height && getColor(x, y1) == newColor)  
        {  
            if(x < width - 1 && simColor(getColor(x+1, y1),oldColor))   
            {             
                floodFillScanLine(x + 1, y1, newColor, oldColor);  
            }   
            y1++;  
        }  
        y1 = y - 1;  
        while(y1 >= 0 && getColor(x, y1) == newColor)  
        {  
            if(x < width - 1 && simColor(getColor(x+1, y1),oldColor))   
            {  
                floodFillScanLine(x + 1, y1, newColor, oldColor);  
            }  
            y1--;  
        }  
    }  
      
    public void floodFillScanLineWithStack(int x, int y, int newColor, int oldColor)  
    {  
        if(oldColor == newColor) {  
            System.out.println("do nothing !!!, filled area!!");  
            return;  
        }  
        emptyStack();  
          
        int y1;   
        boolean spanLeft, spanRight;  
        push(x, y); 
        int minX,minY,maxX,maxY;
        minX = x;
        maxX = x;
        minY = y;
        maxY = y;
          
        while(true)  
        {      
            x = popx();  
//            if(x>maxX)
//            	maxX = x;
//            if(x<minX)
//            	minX = x;
            if(x == -1){
            	System.out.println(minX);
            	System.out.println(minY);
            	System.out.println(maxX);
            	System.out.println(maxY);
            	return;  
            }
            y = popy(); 
//            if(y>maxY)
//            	maxY = y;
//            if(y<minY)
//            	minY = y;
            y1 = y;  
            while(y1 >= 0 && getColor(x, y1) == oldColor) y1--; // go to line top/bottom  
            y1++; // start from line starting point pixel  
            spanLeft = spanRight = false;  
            while(y1 < height && getColor(x, y1) == oldColor)  
            {  
                setColor(x, y1, newColor);  
                if(!spanLeft && x > 0 && getColor(x - 1, y1) == oldColor)// just keep left line once in the stack  
                {  
                    push(x - 1, y1);  
                    spanLeft = true;  
                }  
                else if(spanLeft && x > 0 && getColor(x - 1, y1) != oldColor)  
                {  
                    spanLeft = false;  
                }  
                if(!spanRight && x < width - 1 && getColor(x + 1, y1) == oldColor) // just keep right line once in the stack  
                {  
                    push(x + 1, y1);  
                    spanRight = true;  
                }  
                else if(spanRight && x < width - 1 && getColor(x + 1, y1) != oldColor)  
                {  
                    spanRight = false;  
                }   
                y1++;  
            }  
        }  
          
    }  
      
    private void emptyStack() {  
        while(popx() != - 1) {  
            popy();  
        }  
        stackSize = 0;  
    }  
  
    final void push(int x, int y) {  
        stackSize++;  
        if (stackSize==maxStackSize) {  
            int[] newXStack = new int[maxStackSize*2];  
            int[] newYStack = new int[maxStackSize*2];  
            System.arraycopy(xstack, 0, newXStack, 0, maxStackSize);  
            System.arraycopy(ystack, 0, newYStack, 0, maxStackSize);  
            xstack = newXStack;  
            ystack = newYStack;  
            maxStackSize *= 2;  
        }  
        xstack[stackSize-1] = x;  
        ystack[stackSize-1] = y;  
    }  
      
    final int popx() {  
        if (stackSize==0)  
            return -1;  
        else  
            return xstack[stackSize-1];  
    }  
  
    final int popy() {  
        int value = ystack[stackSize-1];  
        stackSize--;  
        return value;  
    }  
  
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {  
        // TODO Auto-generated method stub  
        return null;  
    }  
    
    public boolean simColor(int c1, int c2){
    	if(c1==c2)
    		return true;
    	else
    		return false;

    	//System.out.println(c1+" "+c2);
//    	Color color1 = new Color(c1);
//    	Color color2 = new Color(c2);
//    	if(c1==0)
//    		color1 = Color.WHITE;
//    	if(c2==0)
//    		color2 = Color.WHITE;
//    	int c1r,c1g,c1b,c2r,c2g,c2b;
//    	c1r = color1.getRed()==0 ? 1 : color1.getRed();
//    	c1g = color1.getGreen()==0 ? 1 : color1.getGreen();
//    	c1b = color1.getBlue()==0 ? 1 : color1.getBlue();
//    	c2r = color2.getRed()==0 ? 1 : color2.getRed();
//    	c2g = color2.getGreen()==0 ? 1 : color2.getGreen();
//    	c2b = color2.getBlue()==0 ? 1 : color2.getBlue();
//    	
//    	double inter = c1r*c2r+c1g*c2g+c1b*c2b;
//    	double a = Math.sqrt(Math.pow(c1r,2)+Math.pow(c1g,2)+Math.pow(c1b,2));
//    	double b = Math.sqrt(Math.pow(c2r,2)+Math.pow(c2g,2)+Math.pow(c2b,2));
//    	double sim = inter/(a*b);
//		
//    	if(sim>=0.9997){
//    		System.out.println(sim);
//    		return true;
//    	}
//    	else
//    		return false;
    }
  
}  