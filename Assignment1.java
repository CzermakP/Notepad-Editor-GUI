/* imports */ 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.Scanner;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Purpose:.  Note Editor GUI program which displays to console and allows the user to 
 *            create a new note, open and existing note file ending with '.txt', save a note as 
 *            a '.txt' file, edit the current note, and exit the GUI. Additional functionality 
 *            has been implemented to change the font type (bold, italic) and to change the font
 *            size(small, medium, large).
 * @author Patrick Czermak 040389514
 * @version 1.0
 */
public class Assignment1 extends JFrame {

    /* declarations */
    JFrame notePadEditor;
    JMenuBar menuBar;
    JMenu fileMenu, formatMenu, sizeMenu;
    JMenuItem newMenuItem, openMenuItem, saveMenuItem, editMenuItem, exitMenuItem;
    JCheckBoxMenuItem boldMenuItem, italicMenuItem;
    JRadioButtonMenuItem smallMenuItem, mediumMenuItem, largeMenuItem, defaultSizeMenuItem;
    JTextArea editorTextArea;
    JScrollPane editorScrollPane;
    ImageIcon imageIcon = new ImageIcon("bear.jpg"); // AWESOME waving bear image :) 

    /**
     * Purpose:. to set the GUI visible for display
     * @param args the command line arguments
     * @author Patrick Czermak 040389514
     * @version 1.0
     */
    public static void main(String[] args) {
        new Assignment1().setVisible(true);
    }

    /* 
    * Purpose:. default constructor - to construct the variables used in the GUI
    * @author Patrick Czermak 040389514
    * @version 1.0
    */
    public Assignment1() {

        menuBar = new JMenuBar();

        // 'FILE' MENU plus all nested options. Creating, setting properties and adding to frame 
        fileMenu = new JMenu("File");

        newMenuItem = new JMenuItem("New");
        KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        newMenuItem.setAccelerator(ctrlN);
        fileMenu.add(newMenuItem); //add NEW to the FILE menu

        openMenuItem = new JMenuItem("Open");
        KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        openMenuItem.setAccelerator(ctrlO);
        fileMenu.add(openMenuItem); // add OPEN to the FILE menu

        saveMenuItem = new JMenuItem("Save");
        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        saveMenuItem.setAccelerator(ctrlS);
        fileMenu.add(saveMenuItem); // add SAVE to the FILE menu

        editMenuItem = new JMenuItem("Edit");
        KeyStroke ctrlE = KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        editMenuItem.setAccelerator(ctrlE);
        fileMenu.add(editMenuItem); // add EDIT to the FILE menu

        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem); // add EXIT to the FILE menu

        menuBar.add(fileMenu); // add FILE menu to the menuBar
        
        // 'FORMAT' MENU plus all nested options. Creating, setting properties and adding to frame
        formatMenu = new JMenu("Format");

        boldMenuItem = new JCheckBoxMenuItem("Bold");
        KeyStroke ctrlB = KeyStroke.getKeyStroke(KeyEvent.VK_B, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        boldMenuItem.setAccelerator(ctrlB);
        formatMenu.add(boldMenuItem); // add BOLD to the FORMAT menu

        italicMenuItem = new JCheckBoxMenuItem("Italic");
        KeyStroke ctrlI = KeyStroke.getKeyStroke(KeyEvent.VK_I, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        italicMenuItem.setAccelerator(ctrlI);
        formatMenu.add(italicMenuItem); // add ITALIC to the FORMAT menu

        formatMenu.addSeparator(); // line seperator between nested FORMAT menu options
        sizeMenu = new JMenu("Size");

        ButtonGroup buttonGroup = new ButtonGroup();
        smallMenuItem = new JRadioButtonMenuItem("Small");
        KeyStroke ctrl1 = KeyStroke.getKeyStroke(KeyEvent.VK_1, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        smallMenuItem.setAccelerator(ctrl1);
        smallMenuItem.setSelected(true); // DEFAULT SIZE - GUI opens with small radio button selected.
        buttonGroup.add(smallMenuItem); // add SMALL to the buttonGroup
        sizeMenu.add(smallMenuItem); // add sub menu option SMALL to the SIZE option within FORMAT menu

        mediumMenuItem = new JRadioButtonMenuItem("Medium");
        KeyStroke ctrl2 = KeyStroke.getKeyStroke(KeyEvent.VK_2, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        mediumMenuItem.setAccelerator(ctrl2);
        buttonGroup.add(mediumMenuItem); // add MEDIUM to the buttonGroup
        sizeMenu.add(mediumMenuItem); // add sub menu option MEDIUM to the SIZE option within FORMAT menu

        largeMenuItem = new JRadioButtonMenuItem("Large");
        KeyStroke ctrl3 = KeyStroke.getKeyStroke(KeyEvent.VK_3, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        largeMenuItem.setAccelerator(ctrl3);
        buttonGroup.add(largeMenuItem); // add LARGE to the buttonGroup
        sizeMenu.add(largeMenuItem); // add sub menu option LARGE to the SIZE option within FORMAT menu

        formatMenu.add(sizeMenu); // add the size and nested radio buttons to the format menu

        menuBar.add(formatMenu); // add the format menu and all nested items into the menu bar 

        setJMenuBar(menuBar); // set the JMenuBar with menu bar and all nested items - default is top 

        // setting frame title and icon and making visible
        this.setTitle("Patrick's Note Editor");
        this.setIconImage(imageIcon.getImage());
        this.setResizable(true);

        // declaring GridBagLayout and using GridBagConstraints for positioning in frame
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();
         
        // creating and setting properties of editorTextArea....gets added into the editorScrollPane
        editorTextArea = new JTextArea();
        editorTextArea.setFont(new Font("Serif", Font.PLAIN, 18));
        editorTextArea.setLineWrap(true); // input text will jump to a new line vs. page shifting right creating lower scroll pane.
        editorTextArea.setWrapStyleWord(true); // input words will NOT be broken up into the new line BUT the entire word will shift

        // creating and setting properties of editorScrollPane....add editorTextArea to it
        editorScrollPane = new JScrollPane(editorTextArea);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(600, 600));
        position.gridx = 0;
        position.gridy = 0;
        
        this.add(editorScrollPane); // adding editorScrollPane to frame 
        this.pack(); // packing everything into the frame all nicely :) 

        setUpListeners(); 
    }

    /* 
    * Purpose: function holds all window and action listeners which define and implement the 
    *          logic as to what happens when any functionality is selected by the user. 
    * @author Patrick Czermak 040389514
    * @version 1.0
    */
    private void setUpListeners() {
        // exit and close the program when top right X clicked 
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("CLOSING PATRICK'S NOTE EDITOR... BYE-BYE !!!"); //output to console/cmd window
                System.exit(EXIT_ON_CLOSE); // close + stop program 
            }
        });

        // NEW note pad, aka. clear/empty the textArea
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editorTextArea.setText("");
            }
        });

        // OPEN a file from within the devices directories
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(false); // allow only 1 file to be opened/choosen by fileChooser
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text"); // must be a '.txt' file ending
                fileChooser.setFileFilter(filter);
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
                int returnValue = fileChooser.showOpenDialog(openMenuItem);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        File selectedFile = fileChooser.getSelectedFile();
                        
                        if (selectedFile.exists()) {
                            String fName = selectedFile.getName();
                            int fNameLength = selectedFile.getName().length();
                            
                            if (!(fName.substring(fNameLength - 4, fNameLength).equalsIgnoreCase(".txt"))) { // check to ensure file name ending is '.txt'
                                JOptionPane.showMessageDialog(openMenuItem, "INVALID File extension, must be .txt", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            Scanner inFile = new Scanner(Paths.get(selectedFile.getAbsolutePath()));
                            while (inFile.hasNextLine()) {
                            editorTextArea.append(inFile.nextLine() + "\n");
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(openMenuItem, "INVALID File Chosen", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(openMenuItem, "NO File Chosen", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //SAVE to a file the contents of the text area
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser saveFileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text"); // must be a '.txt' file ending
                saveFileChooser.setFileFilter(filter);
                saveFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
                int returnValue = saveFileChooser.showSaveDialog(saveMenuItem);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = saveFileChooser.getSelectedFile();
                   
                    try {
                        if (!selectedFile.exists()) {
                            String fName = selectedFile.getName();
                            int fNameLength = selectedFile.getName().length();
                            
                            if (!(fName.substring(fNameLength - 4, fNameLength).equalsIgnoreCase(".txt"))) { // check to ensure file name ending is '.txt'
                                JOptionPane.showMessageDialog(saveMenuItem, "INVALID File extension, must be .txt", "ERROR", JOptionPane.ERROR_MESSAGE);  
                            } else {
                            selectedFile.createNewFile();
                            FileWriter fileWriter = new FileWriter(selectedFile);
                            fileWriter.write(editorTextArea.getText());
                            fileWriter.close();
                            }
                        } 
                    } catch (Exception exx) {
                        JOptionPane.showMessageDialog(saveMenuItem, "UNABLE to save file", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // EDIT the contents of the text area, technically not needed as i can edit the text area before selecting EDIT
        editMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {    
                editorTextArea.setEditable(true); 
            }
        });

        // handles the EXIT menu item closing the program 
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLOSING PATRICK'S NOTE EDITOR... BYE-BYE !!!"); //output to console/cmd window
                System.exit(EXIT_ON_CLOSE);
            }
        });

        // handles the BOLD option and all possibilities 
        boldMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (boldMenuItem.isSelected()) {

                    if (italicMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD + Font.ITALIC));
                    } else if (italicMenuItem.isSelected() && smallMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD + Font.ITALIC, 18));
                    } else if (italicMenuItem.isSelected() && mediumMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD + Font.ITALIC, 30));
                    } else if (italicMenuItem.isSelected() && largeMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD + Font.ITALIC, 42));
                    } else if (smallMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.BOLD, 18));
                    } else if (mediumMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.BOLD, 30));
                    } else if (largeMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.BOLD, 42));
                    } else {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD));
                    }

                } else if (!boldMenuItem.isSelected()) {

                    if (italicMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.ITALIC));
                    } else if (italicMenuItem.isSelected() && smallMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.ITALIC).deriveFont(18));
                    } else if (italicMenuItem.isSelected() && mediumMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.ITALIC).deriveFont(30));
                    } else if (italicMenuItem.isSelected() && largeMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.ITALIC).deriveFont(42));
                    } else if (smallMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.PLAIN, 18));
                    } else if (mediumMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.PLAIN, 30));
                    } else if (largeMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.PLAIN, 42));
                    }

                }
            }
        });

        // handles the ITALIC option and all possibilities
        italicMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (italicMenuItem.isSelected()) {

                    if (boldMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD + Font.ITALIC));
                    } else if (boldMenuItem.isSelected() && smallMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD + Font.ITALIC, 18));
                    } else if (boldMenuItem.isSelected() && mediumMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD + Font.ITALIC, 30));
                    } else if (boldMenuItem.isSelected() && largeMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD + Font.ITALIC, 42));
                    } else if (smallMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.ITALIC, 18));
                    } else if (mediumMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.ITALIC, 30));
                    } else if (largeMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.ITALIC, 42));
                    } else {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.ITALIC));
                    }
                } else if (!italicMenuItem.isSelected()) {

                    if (boldMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD));
                    } else if (boldMenuItem.isSelected() && smallMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD, 18));
                    } else if (boldMenuItem.isSelected() && mediumMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD, 30));
                    } else if (boldMenuItem.isSelected() && largeMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD, 42));
                    } else if (smallMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.PLAIN, 18));
                    } else if (mediumMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.PLAIN, 30));
                    } else if (largeMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.PLAIN, 42));
                    }

                }
            }
        });

        // handles the SMALL submenu of SIZE option and all possibilities
        smallMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (smallMenuItem.isSelected()) {
                
                    if (boldMenuItem.isSelected() && !italicMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD, 18));
                    } else if (italicMenuItem.isSelected() && !boldMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.ITALIC, 18));
                    } else if (boldMenuItem.isSelected() && italicMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD + Font.ITALIC, 18));
                    } else if (!boldMenuItem.isSelected() && !italicMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.PLAIN, 18));
                    }
                    
                } else if (!smallMenuItem.isSelected()) {
                    
                    if (boldMenuItem.isSelected() && !italicMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD, 18));
                    } else if (italicMenuItem.isSelected() && !boldMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.ITALIC, 18));
                    } else if (boldMenuItem.isSelected() && italicMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD + Font.ITALIC, 18));
                    } else if (!boldMenuItem.isSelected() && !italicMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.PLAIN, 18));
                    }
                }
            }
        });

        // handles the MEDIUM submenu of SIZE option and all possibilities
        mediumMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mediumMenuItem.isSelected()) {
                    
                    if (boldMenuItem.isSelected() && !italicMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD, 30));
                    } else if (italicMenuItem.isSelected() && !boldMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.ITALIC, 30));
                    } else if (boldMenuItem.isSelected() && italicMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD + Font.ITALIC, 30));
                    } else if (!boldMenuItem.isSelected() && !italicMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.PLAIN, 30));
                    }
                    
                } else if (!mediumMenuItem.isSelected()) {
                    
                    if (boldMenuItem.isSelected() && !italicMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD, 30));
                    } else if (italicMenuItem.isSelected() && !boldMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.ITALIC, 30));
                    } else if (boldMenuItem.isSelected() && italicMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD + Font.ITALIC, 30));
                    } else if (!boldMenuItem.isSelected() && !italicMenuItem.isSelected()) {
                        editorTextArea.setFont(new Font("Serif", Font.PLAIN, 30));
                    }
                }
            }
        });

        // handles the LARGE submenu of SIZE option and all possibilities
        largeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (largeMenuItem.isSelected()){
                                   
                    if (boldMenuItem.isSelected() && !italicMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD, 42));
                    } else if (italicMenuItem.isSelected() && !boldMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.ITALIC, 42));
                    } else if (boldMenuItem.isSelected() && italicMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD + Font.ITALIC, 42));
                    } else if (!boldMenuItem.isSelected() && !italicMenuItem.isSelected()){
                        editorTextArea.setFont(new Font("Serif", Font.PLAIN, 42));
                    }
                    
                } else if (!largeMenuItem.isSelected()) {
                    
                    if (boldMenuItem.isSelected() && !italicMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD, 42));
                    } else if (italicMenuItem.isSelected() && !boldMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.ITALIC, 42));
                    } else if (boldMenuItem.isSelected() && italicMenuItem.isSelected()) {
                        editorTextArea.setFont(editorTextArea.getFont().deriveFont(Font.BOLD + Font.ITALIC, 42));
                    } else if (!boldMenuItem.isSelected() && !italicMenuItem.isSelected()){
                        editorTextArea.setFont(new Font("Serif", Font.PLAIN, 42));
                    }
                }
            }
        });
    } //end setUpListeners
} //end class

