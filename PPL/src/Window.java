//package snek;

import Miscellaneous.TextLineNumber;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class Window{
    private JPanel contentPane;
    private JFrame frame;
    private JFileChooser open=new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()); //desktop path
    private FileNameExtensionFilter filter=new FileNameExtensionFilter("Snek Files *.snek","snek");
    private String filecontents=" ";
    private File file=null;
    private boolean isUnsaved=true;
    public Window() {
        frame=new JFrame();
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Window.class.getResource("/snek_icon.jpg")));
        frame.setFont(new Font("Unispace", Font.PLAIN, 12));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(100, 100, 450, 300);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        mnFile.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
        menuBar.add(mnFile);

        JMenuItem mntmNew = new JMenuItem("New");
        mntmNew.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
        mnFile.add(mntmNew);

        JMenuItem mntmOpen = new JMenuItem("Open");
        mntmOpen.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
        mnFile.add(mntmOpen);

        JMenuItem mntmSave = new JMenuItem("Save");
        mntmSave.setFont(new Font("Trebuchet MS", Font.PLAIN,11));
        mnFile.add(mntmSave);

        JMenuItem mntmSaveAs = new JMenuItem("Save as");
        mntmSaveAs.setFont(new Font("Trebuchet MS", Font.PLAIN,11));
        mnFile.add(mntmSaveAs);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
        mnFile.add(mntmExit);

        JMenu mnLexer = new JMenu("Lexer");
        mnLexer.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
        menuBar.add(mnLexer);

        JMenuItem mntmGenerate = new JMenuItem("Generate");
        mntmGenerate.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
        mnLexer.add(mntmGenerate);

        JMenu mnHelp = new JMenu("Help");
        mnHelp.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
        menuBar.add(mnHelp);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);

        JMenuItem mntmAbout=new JMenuItem("About");
        mntmAbout.setFont(new Font("Trebuchet MS", Font.PLAIN,11));
        mnHelp.add(mntmAbout);
        mntmAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,"<html>" +
                                "This Text editor supports Snek's <br>source code: " +
                                "<font color=green>.snek</font> files.<br>" +
                                "After Lexical Analyzing, a <font color=green>.sss</font><br>" +
                                "file is generated.<br>" +
                                "For convenience, drag-and-dropping <br>" +
                                "source code to the text area is implemented." +
                                "</html>",
                        "About",JOptionPane.INFORMATION_MESSAGE);
            }
        });


        JEditorPane taEditor = new JEditorPane();
        taEditor.setBackground(Color.WHITE);
        taEditor.setFont(new Font("Consolas", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(taEditor,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        TextLineNumber tln=new TextLineNumber(taEditor);
        scrollPane.setRowHeaderView(tln);

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))
        );

        mntmNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                file=null;
                taEditor.setText("");
                frame.setTitle("nek IDE: ");
                isUnsaved=true;
            }
        });
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mntmSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(file!=null)
                try {
                    save(taEditor.getText());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                else saveAs(taEditor.getText());
            }
        });

        mntmSaveAs.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                saveAs(taEditor.getText());
            }
        });
        mntmOpen.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                }catch(Exception x){
                    x.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(open);
                open.setFileFilter(filter);
                int returnVal=open.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    file=open.getSelectedFile();
                    FileReader reader = null;
                    try {
                        reader = new FileReader(file);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    BufferedReader br=new BufferedReader(reader);
                    frame.setTitle("nek IDE: "+file.getName());
                    try {
                        taEditor.read(br,null);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    };
                }
            }
        });
        mntmGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //filecontents=taEditor.getText();
                if(file==null) try{
                    saveAs(taEditor.getText());
                    new FiniteStateAutomaton(file);
                    new Lexer_Window(file);
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }
                else try {
                    save(taEditor.getText());
                    new FiniteStateAutomaton(file);
                    new Lexer_Window(file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        //Adds drag and drop to textarea
		//little buggy, sudden freeze when drag n dropping randomly
		taEditor.setDropTarget(new DropTarget() {
			public void drop(DropTargetDropEvent e) {
				try {
					e.acceptDrop(DnDConstants.ACTION_COPY);
					List<File> droppedFiles = (List<File>) e.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					for (File FILE : droppedFiles) {
						if (FILE.getName().lastIndexOf(".snek") == FILE.getName().lastIndexOf('.')) {
						    file=FILE;
							FileReader fr = new FileReader(file);
							//BufferedReader br=new BufferedReader(fr);
							taEditor.read(fr, null);
							e.dropComplete(true);
                            frame.setTitle("nek IDE: "+file.getName());
						}
						else e.rejectDrop();
					}
				} catch (Exception ex) {
					e.dropComplete(true);
					ex.printStackTrace();
				}
			}
		});

        //================================================
        contentPane.setLayout(gl_contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("nek IDE");
        frame.setVisible(true);

    }
    void save(String str) throws IOException {
        if(file.exists()) {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write(str);
            bw.close();
            isUnsaved=false;
        }
        else {
            saveAs(str);
        }
    }
    JFileChooser saveAs=new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
    void saveAs(String str){
        //default path to user Desktop
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch(Exception x){
            x.printStackTrace();
        }
        saveAs.setFileFilter(filter);
        SwingUtilities.updateComponentTreeUI(saveAs);
        saveAs.showSaveDialog(null);
        System.out.println("\n\n"+saveAs.getSelectedFile().toString());

        String path=saveAs.getSelectedFile().toString();    //returns directory plus the name of selected file/contents of file name(textfield)
        String name=saveAs.getSelectedFile().getParent();   //returns directory
        File f=new File(path); //+".snek
        if(f.getName().indexOf(".snek")==-1){
            try {
                f=new File(f.getAbsoluteFile()+".snek");
                FileWriter fw=new FileWriter(f.getAbsoluteFile());
                BufferedWriter bw=new BufferedWriter(fw);
                bw.write(str);
                bw.close(); fw.close();
                isUnsaved=false;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        else if(f.exists()){
            int j=JOptionPane.showConfirmDialog(null,"File already exists. Overwrite?","Overwrite",JOptionPane.YES_NO_OPTION);
            if (j==JOptionPane.YES_OPTION){  //Yes
                try {
                    FileWriter fw=new FileWriter(f.getAbsoluteFile());
                    BufferedWriter bw=new BufferedWriter(fw);
                    bw.write(str);
                    bw.close(); fw.close();
                    isUnsaved=false;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
            else if(!f.exists()){
                try {
                    FileWriter fw=new FileWriter(f.getAbsoluteFile());
                    BufferedWriter bw=new BufferedWriter(fw);
                    bw.write(str);
                    bw.close(); fw.close();
                    isUnsaved=false;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        file=f;
        frame.setTitle("nek IDE: "+file.getName());
    }
}
