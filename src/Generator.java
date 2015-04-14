import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.border.TitledBorder;

//dfdsfasddfdf hhhhh
public class Generator extends JFrame {

	private JFileChooser chooser;
	private File f;
    private FloodFillUI ffui;
    
    private TitledBorder cspBorder;
    private TitledBorder interfaceBorder;
    
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel inputLabel;
    private JTextField inputField;
    private JLabel avatarLabel;
    private JTextField avatarField;
    private JLabel controlLabel;
    private JComboBox controlCombo;
    private JLabel aniLabel;
    private JTextField aniField;
    private JComboBox aniCombo;
    
    private JButton btnChooseFile;
    private JButton btnANI;
    private JButton btnGenerate;
    
    private int aniSeq;
    private boolean choosing;
    
    
    private int avatar;
    private int inputNum;
    
    
    
    
    private CSPFormer cspGenerator;

    public void init() {
    	ffui = new FloodFillUI(new File("blank.png"));
    	aniSeq = 0;
    	choosing = false;
    	
    	
    	
        avatar = 0;
        inputNum = 0;
        
        initLayout();
    }

    private void initLayout() {
    	
    	//set the picture panel
    	interfaceBorder = BorderFactory.createTitledBorder("Game Interface");
    	JPanel panelInterface = new JPanel(new BorderLayout());
    	panelInterface.setBorder(interfaceBorder);
    	JPanel panelBrowse = new JPanel();
    	btnChooseFile = new JButton("Browse");
    	btnChooseFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              chooser = new JFileChooser();  
              chooser.showOpenDialog(null); 
              f = chooser.getSelectedFile();  
              ffui.loadFile(f);
              ffui.repaint();
            }
        });
    	panelBrowse.add(btnChooseFile);
    	JPanel panelPic = new JPanel(new BorderLayout());
    	panelPic.setBackground(Color.WHITE);
    	panelPic.add(ffui);
    	panelInterface.add(panelPic,BorderLayout.CENTER);
    	panelInterface.add(panelBrowse,BorderLayout.SOUTH);
    	
        //
        cspBorder = BorderFactory.createTitledBorder("Game Specification");
        JPanel panelCSP = new JPanel(new GridBagLayout());
        panelCSP.setBorder(cspBorder);
        GridBagConstraints cCSP = new GridBagConstraints();
        cCSP.fill = GridBagConstraints.HORIZONTAL;
        nameLabel = new JLabel("Game Name: ");
        nameLabel.setHorizontalAlignment(JLabel.RIGHT);
        cCSP.gridwidth = 1;
        cCSP.gridx = 0;
        cCSP.gridy = 0;
        panelCSP.add(nameLabel,cCSP);
        nameField = new JTextField("test",10);
        nameField.setHorizontalAlignment(JTextField.RIGHT);
//        nameField.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if("test".equals(nameField.getText()))
//                		nameField.setText("");
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//            	if("".equals(nameField.getText()))
//            		nameField.setText("test");
//            }
//        });
        cCSP.gridwidth = 4;
        cCSP.gridx = 1;
        cCSP.gridy = 0;
        panelCSP.add(nameField,cCSP);
        
        inputLabel = new JLabel("Inputs: ");
        inputLabel.setHorizontalAlignment(JLabel.RIGHT);
        cCSP.gridwidth = 1;
        cCSP.gridx = 0;
        cCSP.gridy = 1;
        panelCSP.add(inputLabel,cCSP);
        inputField = new JTextField(10);
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        cCSP.gridwidth = 4;
        cCSP.gridx = 1;
        cCSP.gridy = 1;
        panelCSP.add(inputField,cCSP);
        
        avatarLabel = new JLabel("Avatars: ");
        avatarLabel.setHorizontalAlignment(JLabel.RIGHT);
        cCSP.gridwidth = 1;
        cCSP.gridx = 0;
        cCSP.gridy = 2;
        panelCSP.add(avatarLabel,cCSP);
        avatarField = new JTextField(10);
        avatarField.setHorizontalAlignment(JTextField.RIGHT);
        cCSP.gridwidth = 4;
        cCSP.gridx = 1;
        cCSP.gridy = 2;
        panelCSP.add(avatarField,cCSP);

        controlLabel = new JLabel("Controlling Type: ");
        controlLabel.setHorizontalAlignment(JLabel.RIGHT);
        cCSP.gridwidth = 1;
        cCSP.gridx = 0;
        cCSP.gridy = 3;
        panelCSP.add(controlLabel,cCSP);
        String[] options = {"1-1 location", "1-1 step"};
        controlCombo = new JComboBox(options);
        cCSP.gridwidth = 4;
        cCSP.gridx = 1;
        cCSP.gridy = 3;
        panelCSP.add(controlCombo,cCSP);
        
        aniLabel = new JLabel("Animations: ");
        aniLabel.setHorizontalAlignment(JLabel.RIGHT);
        cCSP.gridwidth = 1;
        cCSP.gridx = 0;
        cCSP.gridy = 4;
        panelCSP.add(aniLabel,cCSP);
        aniField = new JTextField(2);
        aniField.setHorizontalAlignment(JTextField.RIGHT);
        cCSP.gridwidth = 1;
        cCSP.gridx = 1;
        cCSP.gridy = 4;
        panelCSP.add(aniField,cCSP);
        btnANI = new JButton("animation "+(aniSeq+1));
        btnANI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(!choosing){
            		choosing = true;
            		btnANI.setText("Choosing...");
            		btnANI.setEnabled(false);
            		aniCombo.setEnabled(true);
            		//..set other buttons unable
            	}
            }
        });
        cCSP.gridwidth = 1;
        cCSP.gridx = 2;
        cCSP.gridy = 4;
        panelCSP.add(btnANI,cCSP);
        String[] aniOptions = {"tracing", "un-tracing"};
        aniCombo = new JComboBox(aniOptions);
        aniCombo.setEnabled(false);
        cCSP.gridwidth = 1;
        cCSP.gridx = 3;
        cCSP.gridy = 4;
        panelCSP.add(aniCombo,cCSP);
         
        
        btnGenerate = new JButton("Export CSP");
        btnGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	cspGenerator = new CSPFormer(nameField.getText());
            	cspGenerator.setInputs(Integer.parseInt(inputField.getText()));
            	cspGenerator.setAvas(Integer.parseInt(avatarField.getText()));
            	cspGenerator.setControlType(controlCombo.getSelectedItem().equals("1-1 step"));
            	cspGenerator.setAniPart(Integer.parseInt(aniField.getText()));
            	
            	cspGenerator.generateCSP();
            }
	   	
        });
        
        JPanel panelControl = new JPanel();
//      panelControl.setBackground(new Color(0xFF, 0xFF, 0xFF));
        panelControl.add(btnGenerate);
        
        
    	JPanel panelWhole = new JPanel(new BorderLayout());
    	panelWhole.add(panelCSP,BorderLayout.WEST);
        panelWhole.add(panelInterface,BorderLayout.CENTER);
//        panelWhole.add(panelInputs,BorderLayout.CENTER);
//        panelWhole.add(panelControl,BorderLayout.SOUTH);
    	panelWhole.add(panelControl,BorderLayout.SOUTH);
        
    	getContentPane().add(panelWhole);
    }
 
    public static void main(String[] args) {
    	Generator g = new Generator();
    	g.init();
    	g.setLocation(200, 100);           
////    	digi.setLocationRelativeTo(null); //本语句实现窗口居屏幕中央
    	g.setSize(1024,600);            //设置窗体的大小为300*200像素大小
    	g.setResizable(true);       //设置窗体是否可以调整大小，参数为布尔值
    	g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	g.setVisible(true);//设置窗体可见，没有该语句，窗体将不可见，此语句必须有，否则没有界面就没有如何意义了
        
    }
}
