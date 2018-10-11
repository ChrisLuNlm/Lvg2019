package gov.nih.nlm.nls.lvg.Tools.GuiTool.Gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.Global.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib.*;
import gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiComp.*;
/*************************************************************************
* This class show the detail information for a LexItem.
* The constructor is public and a lexItem object needs to be passed in.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class ViewLexItemDialog extends JDialog
{
    public ViewLexItemDialog(JFrame owner, LexItem lexItem, String title,
        String label)
    {
        super(owner, title, false);
        owner_ = owner;
        lexItem_ = lexItem;
        Init();
        // Geometry Setting
        setLocationRelativeTo(owner);
        setSize(450, 500);
        // top panel
        JPanel topP = new JPanel();
        topP.add(new JLabel(label));
        // center
        JPanel centerP = new JPanel();
        centerP.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Input LexItem:"));
        GridBagLayout gbl = new GridBagLayout();
        centerP.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 3, 3, 3);  //Pad: Top, Left, Bottom, Right
        GridBag.SetWeight(gbc, 100, 100);
        
        JButton srcCatB = new JButton("Details");
        JButton tarCatB = new JButton("Details");
        JButton srcInflB = new JButton("Details");
        JButton tarInflB = new JButton("Details");
        for(int i = 0; i < FIELD_NUM; i++)
        {
            GridBag.SetPosSize(gbc, 0, i, 1, 1);
            centerP.add(cb_[i], gbc);
            if(i == SRC_CAT)
            {
                GridBag.SetPosSize(gbc, 1, i, 1, 1);
                centerP.add(valueT_[i], gbc);
                GridBag.SetPosSize(gbc, 2, i, 1, 1);
                centerP.add(srcCatB, gbc);
            }
            else if(i == TAR_CAT)
            {
                GridBag.SetPosSize(gbc, 1, i, 1, 1);
                centerP.add(valueT_[i], gbc);
                GridBag.SetPosSize(gbc, 2, i, 1, 1);
                centerP.add(tarCatB, gbc);
            }
            else if(i == SRC_INFL)
            {
                GridBag.SetPosSize(gbc, 1, i, 1, 1);
                centerP.add(valueT_[i], gbc);
                GridBag.SetPosSize(gbc, 2, i, 1, 1);
                centerP.add(srcInflB, gbc);
            }
            else if(i == TAR_INFL)
            {
                GridBag.SetPosSize(gbc, 1, i, 1, 1);
                centerP.add(valueT_[i], gbc);
                GridBag.SetPosSize(gbc, 2, i, 1, 1);
                centerP.add(tarInflB, gbc);
            }
            else
            {
                GridBag.SetPosSize(gbc, 1, i, 2, 1);
                centerP.add(valueT_[i], gbc);
            }
        }
        // bottom panel
        OkButtonPanel bottomP = new OkButtonPanel();
        JButton okB = bottomP.GetOkButton();
        // top level
        getContentPane().add(topP, "North");
        getContentPane().add(centerP, "Center");
        getContentPane().add(bottomP, "South");
        // inner class for action listener
        okB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                setVisible(false);
            }
        });
        srcCatB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                String name = ViewLexItemsDialog.GetCategoryName(
                    valueT_[SRC_CAT].getText());
                JOptionPane.showMessageDialog(owner_, name,
                    "Category Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        tarCatB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                String name = ViewLexItemsDialog.GetCategoryName(
                    valueT_[TAR_CAT].getText());
                JOptionPane.showMessageDialog(owner_, name,
                    "Category Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        srcInflB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                String name = ViewLexItemsDialog.GetInflectionName(
                    valueT_[SRC_INFL].getText());
                JOptionPane.showMessageDialog(owner_, name,
                    "Inflection Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        tarInflB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                String name = ViewLexItemsDialog.GetInflectionName(
                    valueT_[TAR_INFL].getText());
                JOptionPane.showMessageDialog(owner_, name,
                    "Inflection Details", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    public void SetLexItem(LexItem lexItem)
    {
        lexItem_ = lexItem;
        defaults_[ORG_TERM] = lexItem_.GetOriginalTerm();
        defaults_[FLOW_NUM] = Integer.toString(lexItem_.GetFlowNumber());
        defaults_[FLOW_HIS] = lexItem_.GetFlowHistory();
        defaults_[SRC_TERM] = lexItem_.GetSourceTerm();
        defaults_[TAR_TERM] = lexItem_.GetTargetTerm();
        defaults_[SRC_CAT] = 
            Long.toString(lexItem_.GetSourceCategory().GetValue());
        defaults_[TAR_CAT] = 
            Long.toString(lexItem_.GetTargetCategory().GetValue());
        defaults_[SRC_INFL] = 
            Long.toString(lexItem_.GetSourceInflection().GetValue());
        defaults_[TAR_INFL] = 
            Long.toString(lexItem_.GetTargetInflection().GetValue());
        defaults_[MUTATE_INFO] = lexItem_.GetMutateInformation();
        defaults_[DETAIL_INFO] = lexItem_.GetDetailInformation();
        for(int i = 0; i < FIELD_NUM; i++)
        {
            valueT_[i].setText(defaults_[i]);
        }
    }
    // private methods
    public void Init()
    {
        defaults_[ORG_TERM] = lexItem_.GetOriginalTerm();
        defaults_[FLOW_NUM] = Integer.toString(lexItem_.GetFlowNumber());
        defaults_[FLOW_HIS] = lexItem_.GetFlowHistory();
        defaults_[SRC_TERM] = lexItem_.GetSourceTerm();
        defaults_[TAR_TERM] = lexItem_.GetTargetTerm();
        defaults_[SRC_CAT] = 
            Long.toString(lexItem_.GetSourceCategory().GetValue());
        defaults_[TAR_CAT] = 
            Long.toString(lexItem_.GetTargetCategory().GetValue());
        defaults_[SRC_INFL] = 
            Long.toString(lexItem_.GetSourceInflection().GetValue());
        defaults_[TAR_INFL] = 
            Long.toString(lexItem_.GetTargetInflection().GetValue());
        defaults_[MUTATE_INFO] = lexItem_.GetMutateInformation();
        defaults_[DETAIL_INFO] = lexItem_.GetDetailInformation();
        size_[ORG_TERM] = 15;
        size_[FLOW_NUM] = 2;
        size_[FLOW_HIS] = 15;
        size_[SRC_TERM] = 15;
        size_[TAR_TERM] = 15;
        size_[SRC_CAT] = 4;
        size_[TAR_CAT] = 4;
        size_[SRC_INFL] = 8;
        size_[TAR_INFL] = 8;
        size_[MUTATE_INFO] = 25;
        size_[DETAIL_INFO] = 25;
        for(int i = 0; i < FIELD_NUM; i++)
        {
            cb_[i] = new JLabel(FIELD_STR[i] + ": ");
            setB_[i] = new JButton("Setting");
            valueT_[i] = new JTextField(defaults_[i], size_[i]);
            valueT_[i].setEditable(setBFlag_[i]);
        }
    }
    // private data
    private static final int FIELD_NUM = 11;
    private static final String[] FIELD_STR = 
        {"Original term", 
         "Flow number", "Flow history",
         "Source term", "Target term",
         "Source category", "Target category",
         "Source inflection", "Target inflection",
         "Mutate information", "Detail information"
         };
    private static final int ORG_TERM = 0;
    private static final int FLOW_NUM = 1;
    private static final int FLOW_HIS = 2;
    private static final int SRC_TERM = 3;
    private static final int TAR_TERM = 4;
    private static final int SRC_CAT = 5;
    private static final int TAR_CAT = 6;
    private static final int SRC_INFL = 7;
    private static final int TAR_INFL = 8;
    private static final int MUTATE_INFO = 9;
    private static final int DETAIL_INFO = 10;
    private JLabel[] cb_ = new JLabel[FIELD_NUM];
    private JTextField[] valueT_ = new JTextField[FIELD_NUM];
    private JButton[] setB_ = new JButton[FIELD_NUM];
    private String[] defaults_ = new String[FIELD_NUM];
    private int[] size_ = new int[FIELD_NUM];
    private boolean[] setBFlag_ = {
        false, false, false, false, false,
        false, false, false, false, false, false};
    private JFrame owner_ = null;
    private LexItem lexItem_ = null;
    private final static long serialVersionUID = 5L;
}
