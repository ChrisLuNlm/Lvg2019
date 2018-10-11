package gov.nih.nlm.nls.lvg.Flows;
import java.util.*;
import gov.nih.nlm.nls.lvg.Lib.*;
import gov.nih.nlm.nls.lvg.Util.*;
/*****************************************************************************
* This class changes the inflection to simple inflections.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @see <a href="../../../../../../../designDoc/UDF/flow/simpleInflections.html">
* Design Document </a>
*
* @version    V-2019
****************************************************************************/
public class ToSimpleInflections extends Transformation implements Cloneable
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
        // mutate the LexItem
        GetSimpleInflectionsFromSource(in);
        // details & mutate
        String details = null;
        String mutate = null;
        if(detailsFlag == true)
        {
            details = INFO;
        }
        if(mutateFlag == true)
        {
            String fs = GlobalBehavior.GetInstance().GetFieldSeparator();
            mutate = in.GetSourceInflection().GetName() + fs
                + in.GetSourceInflection().GetValue() + fs;
        }
        // update target LexItem
        Vector<LexItem> out = new Vector<LexItem>();
        LexItem temp = UpdateLexItem(in, in.GetSourceTerm(), 
            Flow.SIMPLE_INFLECTIONS, in.GetTargetCategory().GetValue(), 
            in.GetTargetInflection().GetValue(), details, mutate);
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
        String testStr = GetTestStr(args, "AIDS");        // get input String
        // Mutate
        LexItem in = new LexItem(testStr);
        Vector<LexItem> outs = ToSimpleInflections.Mutate(in, true, true);
        PrintResults(in, outs);     // print out results
    }
    public static void GetSimpleInflectionsOnTarget(LexItem in)
    {
        in.SetTargetInflection(GetSimpleInflections(
            in.GetTargetInflection().GetValue()));
    }
    // private method
    private static void GetSimpleInflectionsFromSource(LexItem in)
    {
        in.SetTargetCategory(in.GetSourceCategory().GetValue());
        in.SetTargetInflection(GetSimpleInflections(
            in.GetSourceInflection().GetValue()));
    }
    private static long GetSimpleInflections(long infl)
    {
        long simpleInfl = infl;
        for(int i = Inflection.SIMPLE_BITS+1; i < Inflection.TOTAL_BITS; i++)
        {
            if((infl & BitMaskBase.MASK[i]) == BitMaskBase.MASK[i])
            {
                simpleInfl = Bit.Minus(simpleInfl, BitMaskBase.MASK[i]); 
                simpleInfl = Bit.Add(simpleInfl, Inflection.MAP_SIMPLE_INFL[i]);
            }
        }
        return simpleInfl;
    }
    // data members
    private final static String INFO = "Simple Inflections";
}
