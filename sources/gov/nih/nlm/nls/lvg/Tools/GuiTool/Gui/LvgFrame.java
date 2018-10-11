package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import java.util.jar.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui.*;
/*************************************************************************
*  This class is the top class for Lvg GUI tool.
*
* <p><b>History:</b>
* <ul>
* </ul>
* 
* @author NLM NLS Development Team
*
* @version    V-2019
**************************************************************************/
public class LvgFrame extends LibCloseableFrame implements ActionListener 
{
    // public constructor
    public LvgFrame()
    {
        lvgFrame_ = this;
        // get screen size
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        // init frame
        setTitle("Lexical Tools");
        setSize(600, 650);
        //setLocation(d.width/2-250, d.height/2-400);
        setLocation(5, 10);
        setResizable(true);
        // set minized icon image
        Image img = tk.getImage("./data/image/lvg.jpg");
        setIconImage(img);
        
        // popup menu
        LvgPopupMenu.AddPopupMenu(this);
        // menu
        LvgMenu.AddMenu(this);
        // main panel
        mainPanel_ = new MainPanel(this);
        getContentPane().add(mainPanel_);
    }
    // action listener for Menu Selection
    public void actionPerformed(ActionEvent evt)
    {
        String arg = evt.getActionCommand();    // get action command
        if(arg.equals("Exit"))        // exit this tool
        {
            if(LvgGlobal.lvg_ != null)
            {
                LvgGlobal.lvg_.CleanUp();
            }
            System.exit(0);
        }
        else if(arg.equals("Input File"))
        {
            // display input file selection dialog
            JFileChooser dialog = new JFileChooser();
            dialog.setSelectedFile(LvgGlobal.inFile_);
            dialog.setDialogTitle("Choose the input file");
            dialog.setMultiSelectionEnabled(false);
            if(dialog.showDialog(this, "Select") == JFileChooser.APPROVE_OPTION)
            {
                LvgGlobal.inFile_ = dialog.getSelectedFile();
                InputPanel.SetFile(LvgGlobal.inFile_.getPath());
            }
        }
        else if(arg.equals("Output File"))
        {
            // display output file selection dialog
            JFileChooser dialog = new JFileChooser();
            dialog.setSelectedFile(LvgGlobal.outFile_);
            dialog.setDialogTitle("Choose the output file");
            dialog.setMultiSelectionEnabled(false);
            if(dialog.showDialog(this, "Select") == JFileChooser.APPROVE_OPTION)
            {
                LvgGlobal.outFile_ = dialog.getSelectedFile();
                OutputOptionDialog.SetOutputFile(LvgGlobal.outFile_);
            }
        }
        else if(arg.equals("Flow Setup"))
        {
            FlowSetupDialog.ShowDialog(this);
        }
        else if(arg.equals("Input Options"))
        {
            InputOptionDialog.ShowDialog(lvgFrame_);
        }
        else if(arg.equals("Mutate Options"))
        {
            MutateOptionDialog.ShowDialog(lvgFrame_);
        }
        else if(arg.equals("Output Options"))
        {
            OutputOptionDialog.ShowDialog(lvgFrame_);
        }
        else if(arg.equals("Show Commands"))
        {
            showCmd_ = !showCmd_;    
            MutatePanel.SetShowCommand(showCmd_); // triggle show lvg command
        }
        else if(arg.equals("Set Commands Editable"))
        {
            setCmdEditable_ = !setCmdEditable_;    
            MutatePanel.SetCommandEditable(setCmdEditable_); // triggle command
        }
        // font (Style), bold,  itatlic, size
        else if((arg.equals("Serif"))
        || (arg.equals("SansSerif"))
        || (arg.equals("Monospaced"))
        || (arg.equals("Dialog"))
        || (arg.equals("DialogInput")))
        {
            style_ = arg;
            UpdateFont();
        }
        else if(arg.equals("Bold"))
        {
            bold_ = !bold_;
            UpdateFont();
        }
        else if(arg.equals("Italic"))
        {
            italic_ = !italic_;
            UpdateFont();
        }
        else if((arg.equals("8")) || (arg.equals("10"))
        || (arg.equals("12")) || (arg.equals("14"))
        || (arg.equals("16")) || (arg.equals("18"))
        || (arg.equals("20")) || (arg.equals("24"))
        || (arg.equals("28")) || (arg.equals("32")))
        {
            size_ = Integer.parseInt(arg);
            UpdateFont();
        }
        // look and Feel
        else if(arg.equals("Metal"))
        {
            String laf = "javax.swing.plaf.metal.MetalLookAndFeel";
            UpdateLookAndFeel(laf, this);     // change look and feel
        }
        else if(arg.equals("Motif"))
        {
            String laf = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            UpdateLookAndFeel(laf, this);     // change look and feel
        }
        else if(arg.equals("Window"))
        {
            String laf = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
            UpdateLookAndFeel(laf, this);     // change look and feel
        }
        else if(arg.equals("About"))
        {
            try
            {
                JarFile jarFile = new JarFile(FindJarPath(
                    GlobalBehavior.LVG_JAR_FILE));
                Manifest manifest = jarFile.getManifest();
                String buildDate = manifest.getMainAttributes().getValue(
                    "Build-Time");
                    //"Implementation-TIME");
                String releaseStr = "Lexical Tools " + GlobalBehavior.YEAR
                    + GlobalBehavior.LS_STR + "Build: " + buildDate;
                JOptionPane.showMessageDialog(this, releaseStr, 
                    "About Lexical Tool", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception e)
            {
                System.err.println("** Err@LvgFrame.About( ) " + e.toString());
            }
        }
        else if(arg.equals("Documents"))
        {
            String lvgDir = LvgGlobal.config_.GetConfiguration(
                Configuration.LVG_DIR);
            String docUrl = "file:" + lvgDir 
                + "docs/designDoc/UDF/gui/index.html";
            if(helpDoc_ == null)
            {
                String title = "Lexical GUI Tool Documents";
                String homeUrl = "file:" + lvgDir + 
                    "docs/designDoc/UDF/gui/index.html";
                helpDoc_ = new LvgHtmlBrowser(this, title, 600, 800, 
                    homeUrl, docUrl);
            }
            else
            {
                helpDoc_.SetPage(docUrl);
            }
            helpDoc_.setVisible(true);
        }
        // update swing component Tree: for bold, size, and showCmd
        SwingUtilities.updateComponentTreeUI(this);
    }
    public static boolean GetShowCmd()
    {
        return showCmd_;
    }
    public static boolean GetSetCmdEditable()
    {
        return setCmdEditable_;
    }
    // private methods
    private void UpdateFont()
    {
        Font font = new Font(style_, 
            (bold_? Font.BOLD:0)+(italic_? Font.ITALIC:0), size_);
        InputPanel.GetTermField().setFont(font);
        MutatePanel.GetCmdField().setFont(font);
        OutputPanel.GetOutputList().setFont(font);
        repaint();
    }
    private void UpdateLookAndFeel(String laf, JFrame frame)
    {
        // change look and feel
        try
        {
            UIManager.setLookAndFeel(laf);
            SwingUtilities.updateComponentTreeUI(frame);
        }
        catch(Exception e)
        {
            System.out.println("-- This platform does not support: " + laf);
        }
    }
    private static String FindJarPath(String jarFileName)
    {
        String jarFilePath = jarFileName;
        String classpath = System.getProperty("java.class.path");
        // tokenize all path
        Vector<String> classpathList = new Vector<String>();
        StringTokenizer buf = new StringTokenizer(classpath, ":;");
        while(buf.hasMoreTokens() == true)
        {
            String temp = buf.nextToken();
            classpathList.add(temp);
        }
        // find the first classpath
        for(int i = 0; i < classpathList.size(); i++)
        {
            String cur = classpathList.elementAt(i);
            if((cur.endsWith(jarFileName) == true)
            && ((new File(cur).exists()) == true))
            {
                jarFilePath = cur;
                break;
            }
        }
        return jarFilePath;
    }
    // data members: all static (unique) GUI component 
    private static boolean showCmd_ = true;
    private static boolean setCmdEditable_ = false;
    private static boolean bold_ = false;
    private static boolean italic_ = false;
    private static String style_ = "Dialog";
    private static int size_ = 12;
    private static MainPanel mainPanel_ = null;
    private static JFrame lvgFrame_ = null;
    private static LvgHtmlBrowser helpDoc_ = null;
    private final static long serialVersionUID = 5L;
}
