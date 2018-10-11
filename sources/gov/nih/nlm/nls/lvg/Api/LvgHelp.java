package gov.nih.nlm.nls.lvg.Api;
import java.io.*;
import gov.nih.nlm.nls.lvg.Util.*;
import gov.nih.nlm.nls.lvg.Tools.CmdLineTools.HelpMenu;
/*****************************************************************************
* This class prints out all help menu for LVG command line syntax.
* <p> This class should be modified to become file driven in the future (TBD).
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class LvgHelp
{
    // public methods
    /**
    * Print out Lvg system help menu.  
    *
    * <p> The option flags and command executions
    * are defined in the classes of Flow and LvgCmdApi, respectively.
    *
    * @param bw    buffer writer for Stdout or file out
    * @param fileOutput  a flag to indicate if the bw is a file output
    * @param out out object
    *
    * @see gov.nih.nlm.nls.lvg.Lib.Flow 
    * @see LvgCmdApi 
    * @see <a href="../../../../../../../designDoc/LifeCycle/requirement/LvgOption.html"> 
    * Design Document</a>
    */
    public static void LvgHelp(BufferedWriter bw, boolean fileOutput, Out out)
    {
        HelpMenu helpMenu = new HelpMenu(bw, fileOutput, out);
        
        // print out the usage
        helpMenu.Println("");
        helpMenu.Println("Synopsis:");
        helpMenu.Println("  lvg [options]");
        helpMenu.Println("");
        helpMenu.Println("Description:");
        helpMenu.Println("  Lexical Variant Generator.");
        helpMenu.Println("");
        helpMenu.Println("Options:");
        helpMenu.Println("  -ccgi    Mark the end of the set of variants returned.");
        helpMenu.Println("  -cf:INT  Input category field.");
        helpMenu.Println("  -ci      Show configuration information.");
        helpMenu.Println("  -C:INT   Case setting.");
        helpMenu.Println("  -CR:o    Combine records by output terms.");
        helpMenu.Println("  -CR:oc   Combine records by categories.");
        helpMenu.Println("  -CR:oe   Combine records by EUI (used in flow s with -m option on).");
        helpMenu.Println("  -CR:oi   Combine records by inflections.");
        helpMenu.Println("  -d       Displays details status for each transformation.");
        helpMenu.Println("  -DC:LONG Display variants contain categories specified.");
        helpMenu.Println("  -DI:LONG Display variants contain inflections specified.");
        helpMenu.Println("  -EC:LONG Display variants exclude categories specified.");
        helpMenu.Println("  -EI:LONG Display variants exclude inflections specified.");
        helpMenu.Println("  -f:h     Help information for flow components.");
        helpMenu.Println("  -F:INT   Specified the field for output to display.");
        helpMenu.Println("  -F:h     Help information for specifying output fields.");
        helpMenu.Println("  -h       Display program help information (this is it).");
        helpMenu.Println("  -hs      Display option's hierarchy structure."); 
        helpMenu.Println("  -i:STR   Define input file name.  The default is screen input");
        helpMenu.Println("  -if:INT  Input inflection field");
        helpMenu.Println("  -kd:INT  Restricts the output generated from the derivation morphology (1,2,3)."); 
        helpMenu.Println("  -kdn:STR  derivation negations (O|N|B)."); 
        helpMenu.Println("  -kdt:STR  derivation types (Z|S|P|ZS|ZP|SP|ZSP)."); 
        helpMenu.Println("  -ki:INT  Restricts the output generated from the inflection morphology (1,2,3)."); 
        helpMenu.Println("  -ks:STR  synonym source (C|E|N|CE|CN|EN|CEN)."); 
        helpMenu.Println("  -m       Displays extra information for mutation.");
        helpMenu.Println("  -n       Return a \"-No Output-\" message when an input produces no output.");
        helpMenu.Println("  -o:STR   Define output file name.  The default is screen output");
        helpMenu.Println("  -p       Show the prompt. The default is no prompt.");
        helpMenu.Println("  -R:INT   Restrict the number of variants for one flow.");
        helpMenu.Println("  -s:STR   Defines a field separator.");
        helpMenu.Println("  -SC      Show category in name. The default is in number.");
        helpMenu.Println("  -SI      Show inflection in name. The default is in number.");
        helpMenu.Println("  -St:o    Sort outputs by output terms in an alphabetical order.");
        helpMenu.Println("  -St:oc   Sort outputs by output terms and category.");
        helpMenu.Println("  -St:oci  Sort outputs by output terms, category, and inflection.");
        helpMenu.Println("  -t:INT   Define the field to use as the term field.  The default is 1.");
        helpMenu.Println("  -ti      Display the filtered input term in the output"); 
        helpMenu.Println("  -v       Returns the current version identification of lvg.");
        helpMenu.Println("  -x:STR   Loading an alternative configuration file.");
    }
    /**
    * Print out the Lvg output fields help menu.  The format of Lvg outputs are:
    * in term | out term | categories | inflections | flow history | flow number
    *  | additional mutation information |
    *
    * @param bw    buffer writer for Stdout or file out
    * @param fileOutput  a flag to indicate if the bw is a file output
    * @param out out object
    */
    public static void OutputFieldHelp(BufferedWriter bw, boolean fileOutput,
        Out out)
    {
        HelpMenu helpMenu = new HelpMenu(bw, fileOutput, out);
        helpMenu.Println("  -F:1     Print output field 1 - input term");
        helpMenu.Println("  -F:2     Print output field 2 - output term");
        helpMenu.Println("  -F:3     Print output field 3 - categories");
        helpMenu.Println("  -F:4     Print output field 4 - inflections");
        helpMenu.Println("  -F:5     Print output field 5 - flow history");
        helpMenu.Println("  -F:6     Print output field 6 - flow number");
        helpMenu.Println("  -F:7+    Print output field above 7 - mutate information");
        helpMenu.Println("  -F:1:2:5 Print output fields 1, 2, and 5");
    }
    /**
    * Print out the help menu of Lvg flow components.
    *
    * @param bw    buffer writer for Stdout or file out
    * @param fileOutput  a flag to indicate if the bw is a file output
    * @param out out object
    *
    * @see 
    * <a href="../../../../../../../designDoc/UDF/flow/index.html">
    * Design Document</a>
    */
    public static void FlowHelp(BufferedWriter bw, boolean fileOutput,
        Out out)
    {
        HelpMenu helpMenu = new HelpMenu(bw, fileOutput, out);
        helpMenu.Println("  -f:0       Strip NEC and NOS.");
        helpMenu.Println("  -f:a       Generate known acronym expansions.");
        helpMenu.Println("  -f:A       Generate known acronyms.");
        helpMenu.Println("  -f:An      Generate antiNorm.");
        helpMenu.Println("  -f:b       Uninflect the input term.");
        helpMenu.Println("  -f:B       Uninflect words.");
        helpMenu.Println("  -f:Bn      Normalized Uninflect words.");
        helpMenu.Println("  -f:c       Tokenize.");
        helpMenu.Println("  -f:ca      Tokenize keep all.");
        helpMenu.Println("  -f:ch      Tokenize no hyphens.");
        helpMenu.Println("  -f:C       Canonicalize.");
        helpMenu.Println("  -f:Ct      Lexical name.");
        helpMenu.Println("  -f:d       Generate derivational variants.");
        helpMenu.Println("  -f:dc~LONG Generate derivational variants, specifying output categories");
        helpMenu.Println("  -f:e       Retrieve uninflected spelling variants.");
        helpMenu.Println("  -f:E       Retrieve Eui.");
        helpMenu.Println("  -f:f       Filter output.");
        helpMenu.Println("  -f:fa      Filter out acronyms and abbreviations.");
        helpMenu.Println("  -f:fp      Filter out proper nouns.");
        helpMenu.Println("  -f:g       Remove Genitive.");
        helpMenu.Println("  -f:G       Generate fruitful variants.");
        helpMenu.Println("  -f:Ge      Fruitful variants, enhanced.");
        helpMenu.Println("  -f:Gn      Generate known fruitful variants.");
        helpMenu.Println("  -f:h       Help menu for flow components (this is it).");
        helpMenu.Println("  -f:i       Generate inflectional variants.");
        helpMenu.Println("  -f:ici~LONG+LONG  Generate inflectional variants, specifying output categories and inflections");
        helpMenu.Println("  -f:is      Generate inflectional variants (simple infl).");
        helpMenu.Println("  -f:l       Lowercase the input.");
        helpMenu.Println("  -f:L       Retrieve category and inflection.");
        helpMenu.Println("  -f:Ln      Retrieve category and inflection from database.");
        helpMenu.Println("  -f:Lp      Retrieve category and inflection for all terms begins with the given word.");
        helpMenu.Println("  -f:m       Metaphone.");
        helpMenu.Println("  -f:n       No operation.");
        helpMenu.Println("  -f:nom     Retrieve nominalizations.");
        helpMenu.Println("  -f:N       Normalize.");
        helpMenu.Println("  -f:N3      LuiNormalize.");
        helpMenu.Println("  -f:o       Replace punctuation with space.");
        helpMenu.Println("  -f:p       Strip punctuation.");
        helpMenu.Println("  -f:P       Strip punctuation, enhanced.");
        helpMenu.Println("  -f:q       Strip diacritics.");
        helpMenu.Println("  -f:q0      Map symbols to ASCII.");
        helpMenu.Println("  -f:q1      Map Unicode to ASCII.");
        helpMenu.Println("  -f:q2      Split ligatures.");
        helpMenu.Println("  -f:q3      Get Unicode names.");
        helpMenu.Println("  -f:q4      Get Unicode synonyms.");
        helpMenu.Println("  -f:q5      Norm Unicode to ASCII.");
        helpMenu.Println("  -f:q6      Norm Unicode to ASCII with synonym option.");
        helpMenu.Println("  -f:q7      Unicode core norm.");
        helpMenu.Println("  -f:q8      Strip or map Unicode.");
        helpMenu.Println("  -f:r       Recursive synonyms.");
        helpMenu.Println("  -f:rs      Remove (s), (es), (ies).");
        helpMenu.Println("  -f:R       Recursive derivations.");
        helpMenu.Println("  -f:s       Generate spelling variants.");
        helpMenu.Println("  -f:S       Syntactic uninvert.");
        helpMenu.Println("  -f:Si      Simple inflections.");
        helpMenu.Println("  -f:t       Strip stop words.");
        helpMenu.Println("  -f:T       Strip ambiguity tags.");
        helpMenu.Println("  -f:u       Uninvert phrase around commas.");
        helpMenu.Println("  -f:U       Convert output.");
        helpMenu.Println("  -f:v       Generate fruitful variants from database.");
        helpMenu.Println("  -f:w       Sort by word order.");
        helpMenu.Println("  -f:ws~INT  Word size filter.");
        helpMenu.Println("  -f:y       Generate synonyms.");
    }
}
