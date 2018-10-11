package gov.nih.nlm.nls.lvg.Lib;
import java.util.*;
/*****************************************************************************
* This class provides a class for all Lvg Flow specific options.  This 
* class is used in the LvgCmdApi.
*
* <p><b>History:</b>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class LvgFlowSpecificOption 
{
    // public constructor
    public LvgFlowSpecificOption()
    {
    }
    /**
    * Set the maximum Permutation number for uninflecting words
    *
    * @param   value  maximum permutation number for uninflecting words
    */
    public void SetMaxPermuteTermNum(int value)
    {
        maxPermuteTermNum_ = value;
    }
    /**
    * Set the maximum Metaphone code length
    *
    * @param   value  the maximum Metaphone code length
    */
    public void SetMaxMetaphoneCodeLength(int value)
    {
        maxMetaphoneCodeLength_ = value;
    }
    /**
    * Set the derivation output filter
    *
    * @param   value  value of the type of derivation output filter
    */
    public void SetDerivationFilter(int value)
    {
        derivationFilter_ = value;
    }
    /**
    * Set the synonym output filter
    *
    * @param   optionStr  synonym source type: CUI, EUI, NLP. etc.
    */
    public void SetSynonymFilter(String optionStr)
    {
        synonymFilter_ = GetSynonymFilterInt(optionStr);
    }
    /**
    * Set the inflection output filter
    *
    * @param   value  value of the type of inflection output filter
    */
    public void SetInflectionFilter(int value)
    {
        inflectionFilter_ = value;
    }
    /**
    * Set the derivation type
    *
    * @param   optionStr  the type of derivation: suffixD, prefixD, zeroD
    */
    public void SetDerivationType(String optionStr)
    {
        derivationType_ = GetDerivationTypeInt(optionStr);
    }
    /**
    * Set the derivation negation
    *
    * @param   optionStr  the negation of derivation: negative or otherwise
    */
    public void SetDerivationNegation(String optionStr)
    {
        derivationNegation_ = GetDerivationNegationInt(optionStr);
    }
    /**
    * Get the maximum permute term number for uninflecting words
    *
    * @return   maximum permute term number for uninflecting words
    */
    public int GetMaxPermuteTermNum()
    {
        return maxPermuteTermNum_;
    }
    /**
    * Get the maximum Metaphone code length
    *
    * @return   the maximum Metaphone code length
    */
    public int GetMaxMetaphoneCodeLength()
    {
        return maxMetaphoneCodeLength_;
    }
    /**
    * Get the derivation output filter
    *
    * @return   value of the type of derivation output filter
    */
    public int GetDerivationFilter()
    {
        return derivationFilter_;
    }
    /**
    * Get the synonym source type output filter
    *
    * @return   value of the type of synonym source output filter
    */
    public int GetSynonymFilter()
    {
        return synonymFilter_;
    }
    /**
    * Get the synonym source type int value from string
    *
    * @param optionStr synonym source type in String
    *
    * @return   value of the synonym source type in integer
    */
    public static int GetSynonymFilterInt(String optionStr)
    {
        int ksInt = OutputFilter.S_SRC_ALL;
        if(optionStr.equals(KS_C) == true)
        {
            ksInt = OutputFilter.S_SRC_CUI;
        }
        else if(optionStr.equals(KS_E) == true)
        {
            ksInt = OutputFilter.S_SRC_EUI;
        }
        else if(optionStr.equals(KS_N) == true)
        {
            ksInt = OutputFilter.S_SRC_NLP;
        }
        else if(optionStr.equals(KS_CE) == true)
        {
            ksInt = OutputFilter.S_SRC_CUI_EUI;
        }
        else if(optionStr.equals(KS_CN) == true)
        {
            ksInt = OutputFilter.S_SRC_CUI_NLP;
        }
        else if(optionStr.equals(KS_EN) == true)
        {
            ksInt = OutputFilter.S_SRC_EUI_NLP;
        }
        return ksInt;
    }
    /**
    * Get the inflection output filter
    *
    * @return   value of the type of inflection output filter
    */
    public int GetInflectionFilter()
    {
        return inflectionFilter_;
    }
    /**
    * Get the derivation type
    *
    * @return   value of the type of derivation: suffixD, prefixD, zeroD
    */
    public int GetDerivationType()
    {
        return derivationType_;
    }
    /**
    * Get the derivation negation
    *
    * @return   value of the negation of derivation: negative or otherwise
    */
    public int GetDerivationNegation()
    {
        return derivationNegation_;
    }
    /**
    * Get the derivation type value from string
    *
    * @param optionStr derivation type in String
    *
    * @return   value of the derivation type in integer
    */
    public static int GetDerivationTypeInt(String optionStr)
    {
        int kdtInt = OutputFilter.D_TYPE_ALL;
        if(optionStr.equals(KDT_Z) == true)
        {
            kdtInt = OutputFilter.D_TYPE_ZERO;
        }
        else if(optionStr.equals(KDT_S) == true)
        {
            kdtInt = OutputFilter.D_TYPE_SUFFIX;
        }
        else if(optionStr.equals(KDT_P) == true)
        {
            kdtInt = OutputFilter.D_TYPE_PREFIX;
        }
        else if(optionStr.equals(KDT_ZS) == true)
        {
            kdtInt = OutputFilter.D_TYPE_ZERO_SUFFIX;
        }
        else if(optionStr.equals(KDT_ZP) == true)
        {
            kdtInt = OutputFilter.D_TYPE_ZERO_PREFIX;
        }
        else if(optionStr.equals(KDT_SP) == true)
        {
            kdtInt = OutputFilter.D_TYPE_SUFFIX_PREFIX;
        }
        return kdtInt;
    }
    /**
    * Get the derivation negation value from string
    *
    * @param optionStr derivation negation in String
    *
    * @return   value of the derivation negation in integer
    */
    public static int GetDerivationNegationInt(String optionStr)
    {
        int kdnInt = OutputFilter.D_NEGATION_OTHERWISE;
        if(optionStr.equals(KDN_N) == true)
        {
            kdnInt = OutputFilter.D_NEGATION_NEGATIVE;
        }
        else if(optionStr.equals(KDN_B) == true)
        {
            kdnInt = OutputFilter.D_NEGATION_BOTH;
        }
        return kdnInt;
    }
    // private method
    // data members
    // from configuration file
    private int maxPermuteTermNum_ = -1;      // Used in uninflected words -f:B
    private int maxMetaphoneCodeLength_ = -1; // Used in Metaphone -f:m
    // from command line option
    private int inflectionFilter_ = OutputFilter.LVG_OR_ALL;    // default
    private int derivationFilter_ = OutputFilter.LVG_ONLY;        // default
    private int synonymFilter_ = OutputFilter.S_SRC_ALL;    // default
    // derivations
    private int derivationType_ = OutputFilter.D_TYPE_ALL;        // default
    private int derivationNegation_ = OutputFilter.D_NEGATION_OTHERWISE;
    public final static String KDT_Z = "Z";
    public final static String KDT_S = "S";
    public final static String KDT_P = "P";
    public final static String KDT_ZS = "ZS";
    public final static String KDT_ZP = "ZP";
    public final static String KDT_SP = "SP";
    public final static String KDT_ZSP = "ZSP";
    public final static String KDN_O = "O";        // otherwise
    public final static String KDN_N = "N";        // negation
    public final static String KDN_B = "B";        // both
    // synonyms
    public final static String KS_C = "C";
    public final static String KS_E = "E";
    public final static String KS_N = "N";
    public final static String KS_CE = "CE";
    public final static String KS_CN = "CN";
    public final static String KS_EN = "EN";
    public final static String KS_CEN = "CEN";
}
