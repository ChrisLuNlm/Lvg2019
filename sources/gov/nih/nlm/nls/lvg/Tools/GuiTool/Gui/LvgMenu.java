package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
/**************************************************************************
*  This class takes care of all menu setup on the top of the LvgFrame.
* The action is performed in LvgFrame
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class LvgMenu
{
    // private constructor
    private LvgMenu()
    {
    }
    public static void AddMenu(JFrame target)
    {
        JMenuBar mbar = new JMenuBar();
        target.setJMenuBar(mbar);
        // Add sub top menu
        AddFileMenu(mbar, target);
        AddFlowMenu(mbar, target);
        AddOptionMenu(mbar, target);
        AddPreferenceMenu(mbar, target);
        AddHelpMenu(mbar, target);
    }
    public void menuSelected(MenuEvent evt)
    {
    }
    public void menuDeselected(MenuEvent evt)
    {
    }
    public void menuCanceled(MenuEvent evt)
    {
    }
    // private methods
    private static void AddPreferenceMenu(JMenuBar mBar, JFrame target)
    {
        // Preferences Menu: mnemonics
        JMenu menu = new JMenu("Preferences");
        menu.setMnemonic('P');
        // look & feel menu
        JRadioButtonMenuItem metalItem = new JRadioButtonMenuItem("Metal");
        JRadioButtonMenuItem motifItem = new JRadioButtonMenuItem("Motif");
        JRadioButtonMenuItem windowItem = new JRadioButtonMenuItem("Window");
        metalItem.setSelected(true);
        ButtonGroup lafGroup = new ButtonGroup();
        lafGroup.add(metalItem);
        lafGroup.add(motifItem);
        lafGroup.add(windowItem);
        Object[] lafItems = {metalItem, motifItem, windowItem};
        // style (font)
        JRadioButtonMenuItem serifItem = new JRadioButtonMenuItem("Serif"); 
        JRadioButtonMenuItem sansSerifItem 
            = new JRadioButtonMenuItem("SansSerif"); 
        JRadioButtonMenuItem monospacedItem 
            = new JRadioButtonMenuItem("Monospaced"); 
        JRadioButtonMenuItem dialogItem = new JRadioButtonMenuItem("Dialog"); 
        JRadioButtonMenuItem dialogInputItem 
            = new JRadioButtonMenuItem("DialogInput"); 
        dialogItem.setSelected(true);
        ButtonGroup styleGroup = new ButtonGroup();
        styleGroup.add(serifItem);
        styleGroup.add(sansSerifItem);
        styleGroup.add(monospacedItem);
        styleGroup.add(dialogItem);
        styleGroup.add(dialogInputItem);
        Object[] styleItems = {serifItem, sansSerifItem, monospacedItem, 
            dialogItem, dialogInputItem};
        // size
        JRadioButtonMenuItem item8 = new JRadioButtonMenuItem("8");
        JRadioButtonMenuItem item10 = new JRadioButtonMenuItem("10");
        JRadioButtonMenuItem item12 = new JRadioButtonMenuItem("12");
        JRadioButtonMenuItem item14 = new JRadioButtonMenuItem("14");
        JRadioButtonMenuItem item16 = new JRadioButtonMenuItem("16");
        JRadioButtonMenuItem item18 = new JRadioButtonMenuItem("18");
        JRadioButtonMenuItem item20 = new JRadioButtonMenuItem("20");
        JRadioButtonMenuItem item24 = new JRadioButtonMenuItem("24");
        JRadioButtonMenuItem item28 = new JRadioButtonMenuItem("28");
        JRadioButtonMenuItem item32 = new JRadioButtonMenuItem("32");
        item14.setSelected(true);
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(item8);
        sizeGroup.add(item10);
        sizeGroup.add(item12);
        sizeGroup.add(item14);
        sizeGroup.add(item16);
        sizeGroup.add(item18);
        sizeGroup.add(item20);
        sizeGroup.add(item24);
        sizeGroup.add(item28);
        sizeGroup.add(item32);
        Object[] sizeItems = {item8, item10, item12, item14, item16,
            item18, item20, item24, item28, item32};
        // check box
        showLvgCmd_ = 
            new JCheckBoxMenuItem("Show Commands", LvgFrame.GetShowCmd());
        setLvgCmd_ = 
            new JCheckBoxMenuItem("Set Commands Editable", 
                LvgFrame.GetSetCmdEditable());
        bold_ = new JCheckBoxMenuItem("Bold", false);
        italic_ = new JCheckBoxMenuItem("Italic", false);
        Object[] items = {showLvgCmd_, setLvgCmd_,
            null,
            bold_, italic_, 
            LibMenu.MakeMenu("Style", styleItems, target),
            LibMenu.MakeMenu("Size", sizeItems, target),
            null,
            LibMenu.MakeMenu("Look and feel", lafItems, target)
            };
        mBar.add(LibMenu.MakeMenu(menu, items, target));
    }
    private static void AddFlowMenu(JMenuBar mBar, JFrame target)
    {
        // Command Menu: mnemonics
        JMenu menu = new JMenu("Flows");
        menu.setMnemonic('w');
        Object[] items = {
            new JMenuItem("Flow Setup", 'F'),
            };
        mBar.add(LibMenu.MakeMenu(menu, items, target));
    }
    private static void AddOptionMenu(JMenuBar mBar, JFrame target)
    {
        // Command Menu: mnemonics
        JMenu menu = new JMenu("Options");
        menu.setMnemonic('T');
        Object[] items = {
            new JMenuItem("Input Options", 'I'),
            new JMenuItem("Mutate Options", 'M'),
            new JMenuItem("Output Options", 'O'),
            };
        mBar.add(LibMenu.MakeMenu(menu, items, target));
    }
    private static void AddFileMenu(JMenuBar mBar, JFrame target)
    {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        // accelerators
        JMenuItem inItem = new JMenuItem("Input File");
        inItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, 
            InputEvent.CTRL_MASK));
        JMenuItem outItem = new JMenuItem("Output File");
        outItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 
            InputEvent.CTRL_MASK));
        JMenuItem exitItem = new JMenuItem("Exit", 'X');
        Object[] fileItems = {inItem, outItem, null, exitItem};
        mBar.add(LibMenu.MakeMenu(fileMenu, fileItems, target));
    }
    private static void AddHelpMenu(JMenuBar mBar, JFrame target)
    {
        // Help Menu: mnemonics
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        Object[] helpItems = {
            new JMenuItem("About", 'A'),
            new JMenuItem("Documents", 'D')
            };
        mBar.add(LibMenu.MakeMenu(helpMenu, helpItems, target));
    }
    // private data members
    private static JCheckBoxMenuItem showLvgCmd_ = null;     // check box item
    private static JCheckBoxMenuItem setLvgCmd_ = null;     // check box item
    private static JCheckBoxMenuItem bold_ = null;            // check box item
    private static JCheckBoxMenuItem italic_ = null;        // check box item
}
