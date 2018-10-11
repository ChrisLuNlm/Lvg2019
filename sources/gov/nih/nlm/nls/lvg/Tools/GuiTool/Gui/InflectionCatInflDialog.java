package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.awt.*;
import javax.swing.*;
/*************************************************************************
* This class show the dialog for inputing category and inflection for inflection
* No constructor is provided since only static methods are used.
* 
* <p><b>History:</b>
* <ul>
* </ul>
* @author NLM NLS Development Team
*
* @version    V-2019
**************************************************************************/
public class InflectionCatInflDialog
{
    public static void AddInflectionCatInflDialog(JFrame owner, 
        DefaultListModel<String> selectedModel)
    {
        // display the category inflection panel
        CatInflDialog catInflD = new CatInflDialog(owner,
            "Value of selected categories: ",
            "Value of selected inflections: ",
            "Category & Inflection Selection");
        catInflD.setVisible(true);
        // check the input category and inflection, then update
        if((catInflD.GetCategoryStr() != null)
        && (catInflD.GetInflectionStr() != null))
        {
            selectedModel.addElement("ici~" + catInflD.GetCategoryValue() + 
                "+" + catInflD.GetInflectionValue());
        }
    }
    public static void InsertInflectionCatInflDialog(JFrame owner, 
        DefaultListModel<String> selectedModel, int tarIndex)
    {
        // display the category inflection panel
        CatInflDialog catInflD = new CatInflDialog(owner,
            "Value of selected categories: ",
            "Value of selected inflections: ",
            "Category & Inflection Selection");
        catInflD.setVisible(true);
        // check the input category and inflection, then update
        if((catInflD.GetCategoryStr() != null)
        && (catInflD.GetInflectionStr() != null))
        {
            selectedModel.insertElementAt(("ici~" + catInflD.GetCategoryValue()
                + "+" + catInflD.GetInflectionValue()), tarIndex);
        }
    }
}
