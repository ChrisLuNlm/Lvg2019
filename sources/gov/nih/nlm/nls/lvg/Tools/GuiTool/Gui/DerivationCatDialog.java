package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.awt.*;
import javax.swing.*;
/*************************************************************************
* This class show the dialog for selecting category for derivation.
* No constructor is provided since only static methods are used.
* 
* <p><b>History:</b>
* <ul>
* </ul>
* @author NLM NLS Development Team
*
* @version    V-2019
**************************************************************************/
public class DerivationCatDialog
{
    public static void AddDerivationCatDialog(JFrame owner, 
        DefaultListModel<String> selectedModel)
    {
        // display the category panel
        String catStr = CategoryDialog.ShowDialog(owner, 0);
        if(catStr != null)
        {
            selectedModel.addElement("dc~" + catStr);
        }
    }
    public static void InsertDerivationCatDialog(JFrame owner, 
        DefaultListModel<String> selectedModel, int tarIndex)
    {
        // display the word size input panel
        String catStr = CategoryDialog.ShowDialog(owner, 0);
        if(catStr != null)
        {
            selectedModel.insertElementAt(("dc~" + catStr), tarIndex);
        }
    }
}
