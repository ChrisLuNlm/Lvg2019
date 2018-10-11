package gov.nih.nlm.nls.lvg.Tools.GuiTool.GuiLib;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.text.html.*;
/*****************************************************************************
* This class provides html browser class.
*
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class LvgHtmlBrowser extends JDialog implements ActionListener
{
    public LvgHtmlBrowser(JFrame owner, String title, String url)
    {
        super(owner, title, true);
        setLocationRelativeTo(owner);
        setSize(500, 700);
        // get html content panel
        try
        {
            htmlPane_ = new JEditorPane(url);
            urlHistory_.add(url);
            urlIndex_++;
            homeUrl_ = url;
        }
        catch(IOException e)
        {
            e.printStackTrace(System.err);
            System.exit(1);
        }
        htmlPane_.setEditable(false);
        scrollPane_ = new JScrollPane(htmlPane_);
        // control panel
        JPanel controlP = new JPanel();
        controlP.add(backB_);
        controlP.add(forwardB_);
        controlP.add(reloadB_);
        controlP.add(homeB_);
        controlP.add(closeB_);
        // add Action listener
        backB_.addActionListener(this);
        forwardB_.addActionListener(this);
        reloadB_.addActionListener(this);
        homeB_.addActionListener(this);
        closeB_.addActionListener(this);
        // main panel
        mainPane_ = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        mainPane_.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 100;// resize weight: x size change 100%
        gbc.weighty = 0;  // resize weight: y size change 0% (not change)
        gbc.insets = new Insets(1, 5, 1, 5);   //Pad: Top, Left, Bottom, Right
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        GridBag.SetPosSize(gbc, 0, 0, 1, 1);
        mainPane_.add(controlP, gbc);
        gbc.weighty = 100;  // resize weight: y size change 0% (not change)
        gbc.insets = new Insets(3, 5, 3, 5);   //Pad: Top, Left, Bottom, Right
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        GridBag.SetPosSize(gbc, 0, 1, 1, 1);
        mainPane_.add(scrollPane_, gbc);
        getContentPane().add(mainPane_);
        ActiveHyperLink();
    }
    public LvgHtmlBrowser(JFrame owner, String title, String home, String url)
    {
        this(owner, title, url);
        homeUrl_ = home;
    }
    public LvgHtmlBrowser(JFrame owner, String title, int width, int height, 
        String url)
    {
        this(owner, title, url);
        setSize(width, height);
    }
    public LvgHtmlBrowser(JFrame owner, String title, int width, int height, 
        String home, String url)
    {
        this(owner, title, width, height, url);
        homeUrl_ = home;
    }
    public void SetPage(String url)
    {
        GoToUrl(url);
        
        urlIndex_ = 0;
        urlHistory_.clear();
        urlHistory_.add(url);
    }
    public void SetHome(String home)
    {
        homeUrl_ = home;
    }
    public JEditorPane GetHtmlPane()
    {
        return htmlPane_;
    }
    public JPanel GetMainPane()
    {
        return mainPane_;
    }
    // a must when implementing ActionListener
    public void actionPerformed(ActionEvent evt)
    {
        Object source = evt.getSource();
        if(source == backB_)
        {
            if(urlIndex_ > 0)
            {
                String url = urlHistory_.get(urlIndex_-1);
                GoToUrl(url);
                urlIndex_--;
            }
        }
        else if(source == forwardB_)
        {
            if((urlIndex_+1) < urlHistory_.size())
            {
                String url = urlHistory_.get(urlIndex_+1);
                GoToUrl(url);
                urlIndex_++;
            }
        }
        else if(source == reloadB_)
        {
            String url = urlHistory_.get(urlIndex_);
            GoToUrl(url);
        }
        else if(source == homeB_)
        {
            GoToUrl(homeUrl_);
            urlHistory_.add(homeUrl_);
            urlIndex_++;
        }
        else if(source == closeB_)
        {
            this.setVisible(false);
        }
    }
    // test driver
    public static void main(String[] args)
    {
        LibCloseableFrame frame = new LibCloseableFrame();
        // Frame
        frame.setTitle("Html Browser");
        LvgHtmlBrowser browser = new LvgHtmlBrowser(
        frame, "Lvg Help Document", 500, 700,
        "http://lexsrv3.nlm.nih.gov/LexSysGroup/Projects/lvg/current/docs/designDoc/index.html",
        "http://lexsrv3.nlm.nih.gov/LexSysGroup/Projects/lvg/current/docs/designDoc/flow/lowercase.html");
        browser.setVisible(true);
    }
    
    // private method
    private void ActiveHyperLink()
    {
        // add a hyperlink listener
        htmlPane_.addHyperlinkListener(new HyperlinkListener()
        {
            public void hyperlinkUpdate(HyperlinkEvent ev)
            {
                try
                {
                    if(ev.getEventType() == 
                        HyperlinkEvent.EventType.ACTIVATED)
                    {
                        htmlPane_.setPage(ev.getURL());
                        // update urlHistory_
                        int size = urlHistory_.size();
                        if((urlIndex_+1) < size)
                        {
                            for(int i = (urlIndex_+1); i < size; i++)
                            {
                                urlHistory_.removeLast();
                            }
                        }
                        urlIndex_++;
                        urlHistory_.add(ev.getURL().toString());
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace(System.err);
                }
            }
        });
    }
    private void GoToUrl(String url)
    {
        try
        {
            htmlPane_.setPage(url);
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }
    }
    private JEditorPane htmlPane_ = null;
    private JScrollPane scrollPane_ = null;
    private JPanel mainPane_ = null;
    private JButton backB_ = new JButton("Back");
    private    JButton forwardB_ = new JButton("Forward");
    private    JButton reloadB_ = new JButton("Reload");
    private    JButton homeB_ = new JButton("Home");
    private    JButton closeB_ = new JButton("Close");
    private LinkedList<String> urlHistory_ = new LinkedList<String>();
    private int urlIndex_ = -1;
    private String homeUrl_ = null;
    private final static long serialVersionUID = 5L;
}
