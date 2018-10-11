package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.awt.*;
import javax.swing.*;
/*************************************************************************
* This class show the dialog for inputing argument for word size.
* No constructor is provided since only static methods are used.
* 
* <p><b>History:</b>
* <ul>
* </ul>
* @author NLM NLS Development Team
*
* @version    V-2019
**************************************************************************/
public class WordSizeDialog
{
    // for add, without inserting index
    public static void AddWordSizeDialog(Container owner, 
        DefaultListModel<String> selectedModel)
    {
        // display the word size input panel
        String wordSizeStr = JOptionPane.showInputDialog(
            owner, "Enter minimum word size:",
            "Set Word Size", JOptionPane.QUESTION_MESSAGE);
        // check the input word size and update
        if(wordSizeStr != null)
        {
            try
            {
                int wordSize = Integer.parseInt(wordSizeStr);
                selectedModel.addElement("ws~" + wordSize);
            }
            catch(Exception e)
            {
                String msg = 
                    "Word size must be an integer (" + wordSizeStr + ").";
                JOptionPane.showMessageDialog(owner, msg,
                "Error: Set Word Size", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    // for insert, without inserting index
    public static void InsertWordSizeDialog(Container owner, 
        DefaultListModel<String> selectedModel, int tarIndex)
    {
        // display the word size input panel
        String wordSizeStr = JOptionPane.showInputDialog(
            owner, "Enter minimum word size:",
            "Set Word Size", JOptionPane.QUESTION_MESSAGE);
        // check the input word size and update
        if(wordSizeStr != null)
        {
            try
            {
                int wordSize = Integer.parseInt(wordSizeStr);
                selectedModel.insertElementAt(("ws~" + wordSize), tarIndex);
            }
            catch(Exception e)
            {
                String msg = 
                    "Word size must be an integer (" + wordSizeStr + ").";
                JOptionPane.showMessageDialog(owner, msg,
                "Error: Set Word Size", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
