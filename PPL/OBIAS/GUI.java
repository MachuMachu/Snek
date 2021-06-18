import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener {
    File inputFile;

    JPanel panelTitle, panelButton;
    JButton buttonFile, buttonAnalyze;
    JLabel labelTitle, labelFile;

    GUI () {
        labelTitle = new JLabel ("Lexical Analyzer", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Bradley Hand ITC",Font.BOLD, 20));
        labelTitle.setPreferredSize(new Dimension(300, 50));
        labelFile = new JLabel ("No File", SwingConstants.CENTER);
        labelFile.setFont(new Font("Bradley Hand ITC",Font.BOLD, 20));
        labelFile.setPreferredSize(new Dimension(300, 50));

        buttonFile = new JButton ("Choose File");
        buttonFile.setFont(new Font("Bradley Hand ITC", Font.BOLD, 20));
        buttonFile.addActionListener(this);
        buttonAnalyze = new JButton ("Analyze");
        buttonAnalyze.setFont(new Font("Bradley Hand ITC", Font.BOLD, 20));
        buttonAnalyze.addActionListener(this);

        panelTitle = new JPanel();
        panelTitle.setPreferredSize(new Dimension(300,150));
        panelTitle.add(labelTitle);
        panelTitle.add(labelFile);
        panelButton = new JPanel();
        panelButton.setPreferredSize(new Dimension(300,150));
        panelButton.add(buttonFile);
        panelButton.add(buttonAnalyze);

        this.setTitle("Lexical Analyzer");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(panelTitle, BorderLayout.NORTH);
        this.add(panelButton, BorderLayout.SOUTH);

        this.setVisible(true);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonFile) {
            FileFilter filter = new FileNameExtensionFilter("Text File", "txt");
            JFileChooser chooseFile = new JFileChooser();
            chooseFile.setFileFilter(filter);
            if(chooseFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {    //select file to open
                inputFile = new File(chooseFile.getSelectedFile().getAbsolutePath());
                labelFile.setText(inputFile.getName());
            }
        }else if(e.getSource() == buttonAnalyze) {
            try {
                new FiniteStateAutomaton(inputFile);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}