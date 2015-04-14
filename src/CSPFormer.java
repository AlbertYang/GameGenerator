import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class CSPFormer {
	private String GameName;
	private String cspStr;
	
	private int numOfinput;
	private int numOfava;
	private boolean controlType;//0 for 1-1 location, 1 for 1-1 step
	
	private int aniParts;
	private int[] animations;
	
	
	public int life;
	
	public int numOfani;
	public int level;
	public boolean animeType;// positive or negative
	public boolean missType;
	
	public CSPFormer(String game){
		GameName = game;
		cspStr = "";
		aniParts = 1;
	}
	
	public void setInputs(int i){
		numOfinput = i;
	}
	public void setAvas(int a){
		numOfava = a;
	}
	public void setControlType(boolean option){
		controlType = option;
	}
	public void setAniPart(int a){
		aniParts = a;
		animations = new int[aniParts];
	}
	public void setAnimations(int i, int j){
		animations[i] = j;
	}
	
	private void generateVars(){
		//generate inputs
		cspStr += "var I : [ 0, "+numOfinput+" ];\n";
		
		//generate avas
		for(int i=0;i<numOfava;i++){
			cspStr += "var AVA"+i+" : [ 0, 1 ];\n";
		}
	}
	
	private void generateConstraints(){
		//control type
		cspStr +="\n";
		if(controlType){
			cspStr += "next AVA0 == if I eq 0 then AVA0 else if I eq 2 then 0 else if AVA0 ne 1 then AVA1 else AVA0;\n";
			for(int i=1;i<numOfava-1;i++)
				cspStr += "next AVA"+i+" == if I eq 0 then AVA"+i+" else if I eq 1 then AVA"+(i+1)+" else AVA"+(i-1)+";\n";
			cspStr += "next AVA"+(numOfava-1)+" == if I eq 0 then AVA"+(numOfava-1)+" else if I eq 1 then 0 else if AVA"+(numOfava-1)+" ne 1 then AVA"+(numOfava-2)+" else AVA"+(numOfava-1)+";\n";				
		}else{
			for(int i=0;i<numOfava;i++)
			cspStr += "next AVA"+i+" == if I eq "+(i+1)+" then 1 else 0;\n";
		}
	}
	
	public void generateCSP(){
		generateVars();
		generateConstraints();
    	File dest = new File(GameName+".csp");  
    	BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(dest));
			writer.write(cspStr);
			writer.flush();  
    	    writer.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}     
	}
	
	

}
