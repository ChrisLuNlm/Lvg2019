package gov.nih.nlm.nls.lvg.Lib;
import java.util.*;
/*****************************************************************************
* This class provides methods of combining Lvg output records by specifying 
* different rules.  It is utilized under Lvg Output filter options.
*
* <p><b>History:</b>
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class Flow extends BitMaskBase
{
    // public constructor
    /**
    * Create a Flow object
    */
    public Flow()
    {
        super(ALL_BIT_VALUE, BIT_STR);
    }
    /**
    * Create a Flow object, using an integer value
    *
    * @param   intValue  number for finding it's combined name
    */
    public Flow(int intValue)
    {
        super(intValue, ALL_BIT_VALUE, BIT_STR);
    }
    /**
    * Convert a combined value string to a long gender value.
    *
    * @param   valueStr  combined name in String format
    *
    * @return  a long value of the specified name
    */
    public static long ToValue(String valueStr)
    {
        return ToValue(valueStr, BIT_STR);
    }
    /**
    * Convert a long gender value to a combined string (abbreviation).
    *
    * @param   value  number for finding it's combined name
    *
    * @return  name in a String format
    */
    public static String ToName(long value)
    {
        return ToName(value, ALL_BIT_VALUE, BIT_STR);
    }
    /**
    * Get the name (first in the name list) of a specified bit (single).
    * The first one in LVG is the abbreviation name.
    *
    * @param   bitValue  bit nubmer for finding it's name
    *
    * @return  name of the bit specified
    */
    public static String GetBitName(int bitValue)
    {
        return GetBitName(bitValue, 0);
    }
    /**
    * Get the name at index order of a specified bit (single).
    *
    * @param   bitValue  bit nubmer for finding it's name
    * @param   index   the order index of the name in BIT_STR[]
    *
    * @return  name at index order of the bit specified
    */
    public static String GetBitName(int bitValue, int index)
    {
        return GetBitName(bitValue, index, BIT_STR);
    }
    /**
    * Get the long value for one single name (no combine names of bits).
    *
    * @param   valueStr  name of a bit for finding it's long value
    *
    * @return  a long value of the specified name
    *
    */
    public static long Enumerate(String valueStr)
    {
        return Enumerate(valueStr, BIT_STR);
    }
    // public methods
    /** 
    * Test driver for this Flow class
    *
    * @param args arguments
    */
    public static void main(String[] args)
    {
        // static function
        System.out.println("------------------------------" );
        System.out.println(" - ToValue(l): " + Flow.ToValue("l")); 
        System.out.println(" - ToValue(i): " + Flow.ToValue("i")); 
        System.out.println(" - ToValue(l+i): " + Flow.ToValue("l+i")); 
        Flow f = new Flow();
        System.out.println("------------------------------" );
        System.out.println(" - GetBitName(LOWER_CASE): " 
            + Flow.GetBitName(LOWER_CASE)); 
        System.out.println(" - GetBitName(INFLECTION): " 
            + Flow.GetBitName(INFLECTION)); 
        System.out.println("------------------------------" );
        System.out.println(" - Enumerate(ToLowerCase): " + 
            Flow.Enumerate("ToLowerCase")); 
        System.out.println(" - Enumerate(l): " + Flow.Enumerate("l")); 
        System.out.println(" - Enumerate(L): " + Flow.Enumerate("L")); 
        
        System.out.println("------------------------------" );
        f.SetValue(3);
        System.out.println(" - GetBit(LOWER_CASE): " 
            + f.GetBitFlag(LOWER_CASE)); 
        System.out.println(" - GetBit(INFLECTION): " 
            + f.GetBitFlag(INFLECTION)); 
        System.out.println("------------------------------" );
        System.out.println(" - GetValue(): " + f.GetValue()); 
        f.SetBitFlag(LOWER_CASE, false); 
        System.out.println(" - GetValue(): " + f.GetValue()); 
        f.SetBitFlag(LOWER_CASE, true); 
        System.out.println(" - GetValue(): " + f.GetValue()); 
        System.out.println("------------------------------" );
        f.SetBitFlag(INFLECTION, false); 
        System.out.println(" - GetValue(): " + f.GetValue()); 
        f.SetBitFlag(INFLECTION, true); 
        System.out.println(" - GetValue(): " + f.GetValue()); 
    }
    // data members
    /** None bit for flow */
    public final static int NONE                           = 0;
    /** inflection flow component bit for flow */
    public final static int INFLECTION                     = 1;
    /** strip stop word flow component bit for flow */
    public final static int STRIP_STOP_WORDS               = 2;
    /** remove genitive flow component bit for flow */
    public final static int REMOVE_GENITIVE                = 3;
    /** replace punctuation with space flow component bit for flow */
    public final static int REPLACE_PUNCTUATION_WITH_SPACE = 4;
    /** strip punctuation flow component bit for flow */
    public final static int STRIP_PUNCTUATION              = 5;
    /** strip punctuation, enhanced, flow component bit for flow */
    public final static int STRIP_PUNCTUATION_ENHANCED     = 6;
    /** sort by word order flow component bit for flow */
    public final static int SORT_BY_WORD_ORDER             = 7;
    /** uninflect words flow component bit for flow */
    public final static int UNINFLECT_WORDS                = 8;
    /** normalize flow component bit for flow */
    public final static int NORMALIZE                       = 9;
    /** canonicalize flow component bit for flow */
    public final static int CANONICALIZE                   = 10;
    /** lui nomalize flow component bit for flow */
    public final static int LUI_NORMALIZE                   = 11;
    /** strip NEC and NOS flow component bit for flow */
    public final static int STRIP_NEC_NOS                   = 12;
    /** uninflect term flow component bit for flow */
    public final static int UNINFLECT_TERM                 = 13;
    /** generate spelling variants flow component bit for flow */
    public final static int GENERATE_SPELLING_VARIANTS     = 14;
    /** no operatin flow component bit for flow */
    public final static int NO_OPERATION                   = 15;
    /** generate acronyms flow component bit for flow */
    public final static int ACRONYMS                       = 16;
    /** generate expansions flow component bit for flow */
    public final static int EXPANSIONS                     = 17;
    /** generate derivation flow component bit for flow */
    public final static int DERIVATION                     = 18;
    /** generate derivation by category flow component bit for flow */
    public final static int DERIVATION_BY_CATEGORY         = 19;
    /** derivation by category and inlfection flow component bit for flow */
    public final static int INFLECTION_BY_CAT_INFL         = 20;
    /** tokenize flow component bit for flow */
    public final static int TOKENIZE                       = 21;
    /** tokenize without hyphens flow component bit for flow */
    public final static int TOKENIZE_NO_HYPHENS            = 22;
    /** generate base spelling variants flow component bit for flow */
    public final static int BASE_SPELLING_VARIANTS         = 23;
    /** retrieve EUI flow component bit for flow */
    public final static int RETRIEVE_EUI                   = 24;
    /** retrieve Category and inflection flow component bit for flow */
    public final static int RETRIEVE_CAT_INFL              = 25;
    /** retrieve category and inflectin from LVG flow component bit for flow */
    public final static int RETRIEVE_CAT_INFL_DB           = 26;
    /** retrieve category and inflection for term begin with a given pattern 
    flow component bit for flow */
    public final static int RETRIEVE_CAT_INFL_BEGIN        = 27;
    /** generate synonyms flow component bit for flow */
    public final static int SYNONYMS                       = 28;
    /** filter flow component bit for flow */
    public final static int FILTER                         = 29;
    /** filter out proper nouns flow component bit for flow */
    public final static int FILTER_PROPER_NOUN             = 30;
    /** filter out acronym flow component bit for flow */
    public final static int FILTER_ACRONYM                 = 31;
    /** strip ambiguity tags flow component bit for flow */
    public final static int STRIP_AMBIGUITY_TAGS           = 32;
    /** uninvert flow component bit for flow */
    public final static int UNINVERT                       = 33;
    /** convert output flow component bit for flow */
    public final static int CONVERT_OUTPUT                 = 34;
    /** generate synonyms recursively flow component bit for flow */
    public final static int RECURSIVE_SYNONYMS             = 35;
    /** generate derivations recursively flow component bit for flow */
    public final static int RECURSIVE_DERIVATIONS          = 36;
    /** lower case flow component bit for flow */
    public final static int LOWER_CASE                     = 37;
    /** citation term flow component bit for flow */
    public final static int CITATION                       = 38;
    /** normalize uninflected words flow component bit for flow */
    public final static int NORM_UNINFLECT_WORDS           = 39;
    /** strip diacritics flow component bit for flow */
    public final static int STRIP_DIACRITICS               = 40;
    /** metaphone flow component bit for flow */
    public final static int METAPHONE                      = 41;
    /** fruitful variants flow component bit for flow */
    public final static int FRUITFUL_VARIANTS              = 42;
    /** tokenize, keep all flow component bit for flow */
    public final static int TOKENIZE_KEEP_ALL              = 43;
    /** syntactic uninvert flow component bit for flow */
    public final static int SYNTACTIC_UNINVERT             = 44;
    /** fruitful variants known to Lexicon flow component bit for flow */
    public final static int FRUITFUL_VARIANTS_LEX          = 45;
    /** fruitful variants by database flow component bit for flow */
    public final static int FRUITFUL_VARIANTS_DB           = 46;
    /** antiNorm flow component bit for flow */
    public final static int ANTINORM                       = 47;
    /** word size filter flow component bit for flow */
    public final static int WORD_SIZE                      = 48;
    /** enhanced fruitful variants flow component bit for flow */
    public final static int FRUITFUL_ENHANCED              = 49;
    /** simple inflection flow component bit for flow */
    public final static int SIMPLE_INFLECTIONS             = 50;
    /** inflection simple flow component bit for flow */
    public final static int INFLECTION_SIMPLE              = 51;
    /** splite ligature flow component bit for flow */
    public final static int SPLIT_LIGATURES                = 52;
    /** get symbol name flow component bit for flow */
    public final static int GET_UNICODE_NAME               = 53;
    /** get synonyms of symbol name flow component bit for flow */
    public final static int GET_UNICODE_SYNONYM            = 54;
    /** normalize characters in a string flow component bit for flow */
    public final static int NORM_UNICODE                   = 55;
    /** normalize characters with symbol synonyms flow component bit for flow */
    public final static int NORM_UNICODE_WITH_SYNONYM      = 56;
    /** nominalization flow component bit for flow */
    public final static int NOMINALIZATION                 = 57;
    /** removeS flow component bit for flow */
    public final static int REMOVE_S                       = 58;
    /** punctuation Symbol mapping flow component bit for flow */
    public final static int MAP_SYMBOL_TO_ASCII            = 59;
    /** unicdoe mapping flow component bit for flow */
    public final static int MAP_UNICODE_TO_ASCII           = 60;
    /** Unicode coreNorm flow component bit for flow */
    public final static int UNICODE_CORE_NORM              = 61;
    /** Unicode strip or mapping flow component bit for flow */
    public final static int STRIP_MAP_UNICODE              = 62;
    // private data member
    private final static long ALL_BIT_VALUE                = 0;    // dummy
    private final static ArrayList<Vector<String>> BIT_STR 
        = new ArrayList<Vector<String>>(MAX_BIT);  // Must have
    // Init static data member, BIT_STR.
    static
    {
        for(int i = 0; i < MAX_BIT; i++)
        {
            BIT_STR.add(i, new Vector<String>());
        }
        BIT_STR.get(NONE).addElement("");
        BIT_STR.get(NONE).addElement("");        // Need to be duplicated
        BIT_STR.get(INFLECTION).addElement("Inflection");
        BIT_STR.get(INFLECTION).addElement("i");
        BIT_STR.get(STRIP_STOP_WORDS).addElement("StripStopWords");
        BIT_STR.get(STRIP_STOP_WORDS).addElement("t");
        BIT_STR.get(REMOVE_GENITIVE).addElement("RemoveGenitive");
        BIT_STR.get(REMOVE_GENITIVE).addElement("g");
        BIT_STR.get(REPLACE_PUNCTUATION_WITH_SPACE).addElement(
            "ReplacePunctuationWithSpace");
        BIT_STR.get(REPLACE_PUNCTUATION_WITH_SPACE).addElement("o");
        BIT_STR.get(STRIP_PUNCTUATION).addElement("StripPunctuation");
        BIT_STR.get(STRIP_PUNCTUATION).addElement("p");
        BIT_STR.get(STRIP_PUNCTUATION_ENHANCED).addElement(
            "StripPunctuationEnhanced");
        BIT_STR.get(STRIP_PUNCTUATION_ENHANCED).addElement("P");
        BIT_STR.get(SORT_BY_WORD_ORDER).addElement("SortByWordOrder");
        BIT_STR.get(SORT_BY_WORD_ORDER).addElement("w");
        BIT_STR.get(UNINFLECT_WORDS).addElement("UninflectWords");
        BIT_STR.get(UNINFLECT_WORDS).addElement("B");
        BIT_STR.get(NORMALIZE).addElement("Normalize");
        BIT_STR.get(NORMALIZE).addElement("N");
        BIT_STR.get(CANONICALIZE).addElement("Canonicalize");
        BIT_STR.get(CANONICALIZE).addElement("C");
        BIT_STR.get(LUI_NORMALIZE).addElement("LuiNormalize");
        BIT_STR.get(LUI_NORMALIZE).addElement("N3");
        BIT_STR.get(STRIP_NEC_NOS).addElement("StripNecNos");
        BIT_STR.get(STRIP_NEC_NOS).addElement("0");
        BIT_STR.get(UNINFLECT_TERM).addElement("UninflectTerm");
        BIT_STR.get(UNINFLECT_TERM).addElement("b");
        BIT_STR.get(GENERATE_SPELLING_VARIANTS).addElement(
            "GenerateSpellingVariants");
        BIT_STR.get(GENERATE_SPELLING_VARIANTS).addElement("s");
        BIT_STR.get(NO_OPERATION).addElement("NoOperation");
        BIT_STR.get(NO_OPERATION).addElement("n");
        BIT_STR.get(ACRONYMS).addElement("Acronyms");
        BIT_STR.get(ACRONYMS).addElement("A");
        BIT_STR.get(EXPANSIONS).addElement("Expansions");
        BIT_STR.get(EXPANSIONS).addElement("a");
        BIT_STR.get(DERIVATION).addElement("Derivation");
        BIT_STR.get(DERIVATION).addElement("d");
        BIT_STR.get(DERIVATION_BY_CATEGORY).addElement("DerivationByCategory");
        BIT_STR.get(DERIVATION_BY_CATEGORY).addElement("dc");
        BIT_STR.get(INFLECTION_BY_CAT_INFL).addElement("InflectionByCatInfl");
        BIT_STR.get(INFLECTION_BY_CAT_INFL).addElement("ici");
        BIT_STR.get(TOKENIZE).addElement("Tokenize");
        BIT_STR.get(TOKENIZE).addElement("c");
        BIT_STR.get(TOKENIZE_NO_HYPHENS).addElement("TokenizeNoHyphens");
        BIT_STR.get(TOKENIZE_NO_HYPHENS).addElement("ch");
        BIT_STR.get(BASE_SPELLING_VARIANTS).addElement("BaseSpellingVariants");
        BIT_STR.get(BASE_SPELLING_VARIANTS).addElement("e");
        BIT_STR.get(RETRIEVE_EUI).addElement("RetrieveEui");
        BIT_STR.get(RETRIEVE_EUI).addElement("E");
        BIT_STR.get(RETRIEVE_CAT_INFL).addElement("RetrieveCatInfl");
        BIT_STR.get(RETRIEVE_CAT_INFL).addElement("L");
        BIT_STR.get(RETRIEVE_CAT_INFL_DB).addElement("RetrieveCatInflDb");
        BIT_STR.get(RETRIEVE_CAT_INFL_DB).addElement("Ln");
        BIT_STR.get(RETRIEVE_CAT_INFL_BEGIN).addElement("RetrieveCatInflBegin");
        BIT_STR.get(RETRIEVE_CAT_INFL_BEGIN).addElement("Lp");
        BIT_STR.get(SYNONYMS).addElement("GenerateSynonyms");
        BIT_STR.get(SYNONYMS).addElement("y");
        BIT_STR.get(FILTER).addElement("Filter");
        BIT_STR.get(FILTER).addElement("f");
        BIT_STR.get(FILTER_PROPER_NOUN).addElement("FilterProperNoun");
        BIT_STR.get(FILTER_PROPER_NOUN).addElement("fp");
        BIT_STR.get(FILTER_ACRONYM).addElement("FilterAcronym");
        BIT_STR.get(FILTER_ACRONYM).addElement("fa");
        BIT_STR.get(STRIP_AMBIGUITY_TAGS).addElement("StripAmbiguityTags");
        BIT_STR.get(STRIP_AMBIGUITY_TAGS).addElement("T");
        BIT_STR.get(UNINVERT).addElement("Uninvert");
        BIT_STR.get(UNINVERT).addElement("u");
        BIT_STR.get(CONVERT_OUTPUT).addElement("ConvertOutput");
        BIT_STR.get(CONVERT_OUTPUT).addElement("U");
        BIT_STR.get(RECURSIVE_SYNONYMS).addElement("RecursiveSynonyms");
        BIT_STR.get(RECURSIVE_SYNONYMS).addElement("r");
        BIT_STR.get(RECURSIVE_DERIVATIONS).addElement("RecursiveDerivations");
        BIT_STR.get(RECURSIVE_DERIVATIONS).addElement("R");
        BIT_STR.get(LOWER_CASE).addElement("LowerCase");
        BIT_STR.get(LOWER_CASE).addElement("l");
        BIT_STR.get(CITATION).addElement("Citation");
        BIT_STR.get(CITATION).addElement("Ct");
        BIT_STR.get(NORM_UNINFLECT_WORDS).addElement("NormUninflectWords");
        BIT_STR.get(NORM_UNINFLECT_WORDS).addElement("Bn");
        BIT_STR.get(STRIP_DIACRITICS).addElement("StripDiacritics");
        BIT_STR.get(STRIP_DIACRITICS).addElement("q");
        BIT_STR.get(METAPHONE).addElement("Metaphone");
        BIT_STR.get(METAPHONE).addElement("m");
        BIT_STR.get(FRUITFUL_VARIANTS).addElement("FruitfulVariants");
        BIT_STR.get(FRUITFUL_VARIANTS).addElement("G");
        BIT_STR.get(TOKENIZE_KEEP_ALL).addElement("TokenizeKeepAll");
        BIT_STR.get(TOKENIZE_KEEP_ALL).addElement("ca");
        BIT_STR.get(SYNTACTIC_UNINVERT).addElement("SyntacticUninvert");
        BIT_STR.get(SYNTACTIC_UNINVERT).addElement("S");
        BIT_STR.get(FRUITFUL_VARIANTS_LEX).addElement("FruitfulVariantsLex");
        BIT_STR.get(FRUITFUL_VARIANTS_LEX).addElement("Gn");
        BIT_STR.get(FRUITFUL_VARIANTS_DB).addElement("FruitfulVariantsDb");
        BIT_STR.get(FRUITFUL_VARIANTS_DB).addElement("v");
        BIT_STR.get(ANTINORM).addElement("AntiNorm");
        BIT_STR.get(ANTINORM).addElement("An");
        BIT_STR.get(WORD_SIZE).addElement("WordSize");
        BIT_STR.get(WORD_SIZE).addElement("ws");
        BIT_STR.get(FRUITFUL_ENHANCED).addElement("FruitfulEnhanced");
        BIT_STR.get(FRUITFUL_ENHANCED).addElement("Ge");
        BIT_STR.get(SIMPLE_INFLECTIONS).addElement("SimpleInflections");
        BIT_STR.get(SIMPLE_INFLECTIONS).addElement("Si");
        BIT_STR.get(INFLECTION_SIMPLE).addElement("InflectionSimple");
        BIT_STR.get(INFLECTION_SIMPLE).addElement("is");
        BIT_STR.get(SPLIT_LIGATURES).addElement("SplitLigature");
        BIT_STR.get(SPLIT_LIGATURES).addElement("q2");
        BIT_STR.get(GET_UNICODE_NAME).addElement("GetUnicodeName");
        BIT_STR.get(GET_UNICODE_NAME).addElement("q3");
        BIT_STR.get(GET_UNICODE_SYNONYM).addElement("GetUnicodeSynonym");
        BIT_STR.get(GET_UNICODE_SYNONYM).addElement("q4");
        BIT_STR.get(NORM_UNICODE).addElement("NormUnicode");
        BIT_STR.get(NORM_UNICODE).addElement("q5");
        BIT_STR.get(NORM_UNICODE_WITH_SYNONYM).addElement("NormUnicodeWithSynonym");
        BIT_STR.get(NORM_UNICODE_WITH_SYNONYM).addElement("q6");
        BIT_STR.get(NOMINALIZATION).addElement("RetrieveNominalization");
        BIT_STR.get(NOMINALIZATION).addElement("nom");
        BIT_STR.get(REMOVE_S).addElement("RemoveS");
        BIT_STR.get(REMOVE_S).addElement("rs");
        BIT_STR.get(MAP_SYMBOL_TO_ASCII).addElement("MapSymbolToAscii");
        BIT_STR.get(MAP_SYMBOL_TO_ASCII).addElement("q0");
        BIT_STR.get(MAP_UNICODE_TO_ASCII).addElement("MapUnicodeToAscii");
        BIT_STR.get(MAP_UNICODE_TO_ASCII).addElement("q1");
        BIT_STR.get(UNICODE_CORE_NORM).addElement("UnicodeCoreNorm");
        BIT_STR.get(UNICODE_CORE_NORM).addElement("q7");
        BIT_STR.get(STRIP_MAP_UNICODE).addElement("StripMapUnicode");
        BIT_STR.get(STRIP_MAP_UNICODE).addElement("q8");
    }
}
