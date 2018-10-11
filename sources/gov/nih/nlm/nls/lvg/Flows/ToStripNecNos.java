package gov.nih.nlm.nls.lvg.Flows;
import java.util.*;
import java.io.*;
import gov.nih.nlm.nls.lvg.Util.*;
import gov.nih.nlm.nls.lvg.Lib.*;
/*****************************************************************************
* This class strips NEC and NOS from a specified term.  NOS (Not Otherwise 
* Specified) and NEC (Not Elsewhere Classified) can be found as part of 
* Metathesaurus concept names.  This class has following features:
* <ul>
* <li> strip NEC AND NOS
* <li> Strip NEC/NOS$
* <li> Strip NEC, NOS$
* <li> Strip ,NEC$
* <li> Strip ,NOS$
* <li> Strip , NEC$
* <li> Strip , NOS$
* <li> Strip (NEC)
* <li> Strip (NOS)
* <li> Strip NEC
* <li> Strip NOS
* <li>Remove ',' if it is at the end of the stripped output.
* </ul>
* <p>where $ is the end the string.
* <p>The design and implementation of this class is not general.  More details 
* specification need to be addressed, such as in the case of ", NEC" and 
* ", NOS".
* In addition, the issue of tokenizing and puncuations need to be addessed.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @see <a href="../../../../../../../designDoc/UDF/flow/stripNecNos.html">
* Design Document </a>
* @see <a href="../../../../../../../designDoc/UDF/token/index.html">
* Tokenize and Punctuations</a>
*
* @version    V-2019
****************************************************************************/
public class ToStripNecNos extends Transformation implements Cloneable
{
    // public methods
    /**
    * Performs the mutation of this flow component.
    *
    * @param   in   a LexItem as the input for this flow component
    * @param   detailsFlag   a boolean flag for processing details information
    * @param   mutateFlag   a boolean flag for processing mutate information
    *
    * @return  the results from this flow component - a collection (Vector)
    * of LexItems
    */
    public static Vector<LexItem> Mutate(LexItem in, boolean detailsFlag, 
        boolean mutateFlag)
    {
        // mutate the term
        String term = StripNecNos(in.GetSourceTerm()); 
        // details & mutate
        String details = null;
        String mutate = null;
        if(detailsFlag == true)
        {
            details = INFO;
        }
        if(mutateFlag == true)
        {
            mutate = Transformation.NO_MUTATE_INFO;
        }
        // updatea target 
        Vector<LexItem> out = new Vector<LexItem>();
        LexItem temp = UpdateLexItem(in, term, Flow.STRIP_NEC_NOS, 
            Transformation.UPDATE, Transformation.UPDATE, details, mutate);
        out.addElement(temp);
        return out;
    }
    /**
    * A unit test driver for this flow component.
    *
    * @param args arguments
    */
    public static void main(String[] args)
    {
        String testStr = GetTestStr(args, "Deaf mutism, NEC");
        // mutate
        LexItem in = new LexItem(testStr);
        Vector<LexItem> outs = ToStripNecNos.Mutate(in, true, true);
        PrintResults(in, outs);     // print out results
    }
    // private methods
    private static String StripNecNos(String inStr)
    {
        // strip word if they are in the NEC NOS list
        String tempStr = inStr + " $";        // add $ to the end of the term
        String out = Strip.StripStrings(tempStr, NEC_NOS_LIST, true);
        int length = out.length();
        if(out.charAt(length-1) == '$')
        {
            out = out.substring(0, out.length()-1);        // take out "$"
        }
        out = out.trim();
        // take off ',' if it is at the end
        length = out.length();
        if(out.charAt(length-1) == ',')
        {
            out = out.substring(0, out.length()-1);        // take out ","
        }
        return out.trim();
    }
    // data members
    private final static String INFO = "Strip NEC and NOS";
    private final static Vector<String> NEC_NOS_LIST 
        = new Vector<String>(Arrays.asList(
            "NEC AND NOS", "NEC/NOS $", "NEC NOS $", "NEC, NOS $", ", NEC $", 
            ", NOS $", ",NEC $", ",NOS $", "(NEC)", "(NOS)",
            "NEC", "NOS"));
}
