//package snek;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;


public class Lexer_Window {

    private JFrame f;
    private JPanel p=new JPanel();
    private FileNameExtensionFilter filter=new FileNameExtensionFilter("Snek Files *.sss, *.snek","sss","snek");
    public Lexer_Window(File file) throws IOException {
        String outputFileName=null;
        while(outputFileName==null) {
            outputFileName = file.getName().substring(0, file.getName().lastIndexOf('.')) + ".sss";
        }
        File outputFile=new File(file.getParent()+"\\"+outputFileName+" (Snek Lexical Analyzer)");
        f=new JFrame("neker: "+outputFile.getName());
        f.setIconImage(Toolkit.getDefaultToolkit().getImage(Window.class.getResource("/snek_icon.jpg")));
        //f.setAlwaysOnTop(true);
        f.setVisible(true);
        f.setBounds(100, 100, 450, 300);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JEditorPane textArea = new JEditorPane();
        textArea.setForeground(new Color(255, 255, 255));
        textArea.setBackground((Color.darkGray));
        textArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        f.getContentPane().add(textArea);

        JScrollPane scrollPane = new JScrollPane(textArea);
        GroupLayout groupLayout = new GroupLayout(f.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
        );
        f.getContentPane().setLayout(groupLayout);

        FileReader fr=new FileReader(file.getParent()+"\\"+outputFileName);
        BufferedReader br=new BufferedReader(fr);
        textArea.read(br,null);
        fr.close(); br.close();
    }

}
