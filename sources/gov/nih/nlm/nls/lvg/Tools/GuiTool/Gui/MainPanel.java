package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
/*************************************************************************
* This class show the main panel of LVG GUI tool.
* It include three sub-panel, input, mutate, and output
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class MainPanel extends JPanel
{
    // public constructor
    public MainPanel(JFrame owner)
    {
        // Panels
        JPanel inputP = InputPanel.GetPanel(owner);
        JPanel mutateP = MutatePanel.GetPanel(owner);
        JPanel outputP = OutputPanel.GetPanel(owner);
        // Layout
        JPanel centerP = new JPanel();
        centerP.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);    //Pad: Top, Left, Bottom, Right
        GridBag.SetWeight(gbc, 100, 100);
        // add inputP, mutateP, outputP to centerP
        GridBag.SetPosSize(gbc, 1, 1, 1, 1);
        centerP.add(inputP, gbc);
        GridBag.SetPosSize(gbc, 1, 2, 1, 1);
        centerP.add(mutateP, gbc);
        GridBag.SetPosSize(gbc, 1, 3, 1, 1);
        centerP.add(outputP, gbc);
        add(centerP, "Center");
    }
    private final static long serialVersionUID = 5L;
}
