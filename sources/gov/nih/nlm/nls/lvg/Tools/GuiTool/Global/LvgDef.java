package gov.nih.nlm.nls.lvg.Tools.GuiTool.Global;
/*****************************************************************************
* This class provides LVG Gui Tool variable defition.
* <p><b>History:</b>
* <ul>
* </ul>
*
* @author NLM NLS Development Team
*
* @version    V-2019
****************************************************************************/
public class LvgDef
{
    private LvgDef()
    {
    }
    // public define varaiable
    // input options
    public final static int IN_CAT = 0;
    public final static int IN_INFL = 1;
    public final static int IN_t = 2;
    public final static int IN_cf = 3;
    public final static int IN_if = 4;
    public final static int IN_i = 5;   // from global behavior
    public final static String[] INPUT_OPT = 
        {"Category", 
         "Inflection", 
         "Term field filter", 
         "Category field filter", 
         "Inflection field filter",
         "File",
         };
    public final static String[] INPUT_OPT_DOC = 
        {"none", 
         "none", 
         "inputTerm.html", 
         "inputCategory.html", 
         "inputInflection.html",
         "inputOption.html",
         };
    public final static String[] INPUT_OPT_FLAG = 
        {"", "", "t:INT", "cf:INT", "if:INT", "i:STR"};
    public final static String[] INPUT_PURE_OPT_FLAG = 
        {"", "", "t", "cf", "if", "i"};
    public final static int INPUT_OPT_NUM = INPUT_OPT.length;
    // global behavior options
    public final static int GB_m = 0;
    public final static int GB_d = 1;
    public final static int GB_s = 2;
    public final static String[] GLOBAL_BEHAVIOR_OPT = 
        {"Mutate information",
         "Detail information",
         "Feild separator",
         };
    public final static String[] GLOBAL_BEHAVIOR_OPT_DOC = 
        {"mutateOption.html",
         "detailOption.html",
         "separatorOption.html",
         };
    public final static String[] GLOBAL_BEHAVIOR_OPT_FLAG = 
        {"m", "d", "s:STR"};
    public final static String[] GLOBAL_BEHAVIOR_PURE_OPT_FLAG = 
        {"m", "d", "s"};
    public final static int GLOBAL_BEHAVIOR_OPT_NUM = 
        GLOBAL_BEHAVIOR_OPT.length;
    // output options
    public final static int OUT_SC = 0;
    public final static int OUT_SI = 1;
    public final static int OUT_ti = 2;
    public final static int OUT_ccgi = 3;
    public final static int OUT_n = 4;
    public final static int OUT_R = 5;
    public final static int OUT_St = 6;
    public final static int OUT_C = 7;
    public final static int OUT_CR = 8;
    public final static int OUT_DC = 9;
    public final static int OUT_DI = 10;
    public final static int OUT_EC = 11;
    public final static int OUT_EI = 12;
    public final static int OUT_F = 13;
    public final static int OUT_o = 14;
    public final static String[] OUT_OPT = 
        {
         "Show categories in name",
         "Show inflections in name",
         "Filter input and only show the term used",
         "Mark the end of the set of variant returned",
         "Mark no output message",
         "Number of variants returned",
         "Sort outputs by term",
         "Case modifycation",
         "Combine records",
         "Display variants contains categories specified",
         "Display variants contains inflections specified",
         "Display variants excludes categories specified",
         "Display variants excludes inflections specified",
         "Output fields specifier",
         "Output file name",
         };
    public final static String[] OUT_OPT_DOC = 
        {
         "catStrOption.html",
         "inflStrOption.html",
         "filterInputOption.html",
         "endMarkOption.html",
         "noOutputOption.html",
         "numberOption.html",
         "sortOption.html",
         "caseOption.html",
         "combRecordOption.html",
         "catOption.html",
         "inflOption.html",
         "excludeCatOption.html",
         "excludeInflOption.html",
         "outFieldOption.html",
         "outputOption.html",
         };
    public final static String[] OUT_OPT_FLAG = 
        { "SC", "SI", "ti",
          "ccgi", "n", "R:INT",
          "St", "C:INT", "CR:o, oc, oe, oi", 
          "DC:LONG", "DI:LONG", "EC:LONG", "EI:LONG", "F:INT:INT:...", 
          "o:STR",
         };
    public final static String[] OUT_PURE_OPT_FLAG = 
        { "SC", "SI", "ti",
          "ccgi", "n", "R",
          "St", "C", "CR", 
          "DC", "DI", "EC", "EI", "F",
          "o",
        };
    public final static int OUT_OPT_NUM = OUT_OPT.length;
    // flow components
    public final static int F_0 = 0;
    public final static int F_a = 1;
    public final static int F_A = 2;
    public final static int F_An = 3;
    public final static int F_b = 4;
    public final static int F_B = 5;
    public final static int F_Bn = 6;
    public final static int F_c = 7;
    public final static int F_ca = 8;
    public final static int F_ch = 9;
    public final static int F_C = 10;
    public final static int F_Ct = 11;
    public final static int F_d = 12;
    public final static int F_dc = 13;
    public final static int F_e = 14;
    public final static int F_E = 15;
    public final static int F_f = 16;
    public final static int F_fa = 17;
    public final static int F_fp = 18;
    public final static int F_g = 19;
    public final static int F_G = 20;
    public final static int F_Ge = 21;
    public final static int F_Gn = 22;
    public final static int F_i = 23;
    public final static int F_ici = 24;
    public final static int F_is = 25;
    public final static int F_l = 26;
    public final static int F_L = 27;
    public final static int F_Ln = 28;
    public final static int F_Lp = 29;
    public final static int F_m = 30;
    public final static int F_n = 31;
    public final static int F_nom = 32;
    public final static int F_N = 33;
    public final static int F_N3 = 34;
    public final static int F_o = 35;
    public final static int F_p = 36;
    public final static int F_P = 37;
    public final static int F_q = 38;
    public final static int F_q0 = 39;
    public final static int F_q1 = 40;
    public final static int F_q2 = 41;
    public final static int F_q3 = 42;
    public final static int F_q4 = 43;
    public final static int F_q5 = 44;
    public final static int F_q6 = 45;
    public final static int F_q7 = 46;
    public final static int F_q8 = 47;
    public final static int F_r = 48;
    public final static int F_rs = 49;
    public final static int F_R = 50;
    public final static int F_s = 51;
    public final static int F_S = 52;
    public final static int F_Si = 53;
    public final static int F_t = 54;
    public final static int F_T = 55;
    public final static int F_u = 56;
    public final static int F_U = 57;
    public final static int F_v = 58;
    public final static int F_w = 59;
    public final static int F_ws = 60;
    public final static int F_y = 61;
    public final static String[] FLOW = 
        {"Strip NEC and NOS",
         "Return known acronym expansions",
         "Return known acronyms",
         "Return antiNorm",
         "Uninflect a term",
         "Uninflect words in a term",
         "Normalized uninflect words in a term",
         "Tokenize a term into words",
         "Tokenize, keep everything",
         "Tokenize without breaking hyphens",
         "Canonicalize",
         "Retrieve the citaiton form",
         "Generate derivational variants",
         "Generate derivational variants with output categories",
         "Generate known base spelling variants",
         "Retrieve EUI",
         "Filter output to contain only forms from lexicon",
         "Filter out acronyms and abbreviations from the output",
         "Filter out proper nouns from the output",
         "Remove genetive",
         "Generate all fruitful variants",
         "Generate fruitful variants, enhanced",
         "Generate known fruitful variants",
         "Generate inflectional variants",
         "Generate inflectional variants with output categories and inflections",
         "Generate inflectional variants with simple inflections",
         "Lowercase",
         "Retrieve categories and inflections for a term",
         "Retrieve categories and inflections for a term from Lexicon",
         "Retrieve categories and inflections for terms begin with a given word",
         "Generate Metaphone spelling normalized form",
         "No operation",
         "Retrieve nominalizations",
         "Normalize in a non-canoncial way",
         "LuiNorm, canonical way normalization",
         "Replace punctuations with spaces",
         "Strip punctuation",
         "Strip punctuation, enhanced",
         "Strip diacritics",
         "Map symbols or punctuation to ASCII",
         "Map Unicode to ASCII",
         "Split ligatures",
         "Get Unicode names",
         "Get Unicode synonym",
         "Norm Unicode to ASCII",
         "Norm Unicode to ASCII with synonym option",
         "Unicode core norm",
         "Strip or map Unicode",
         "Generate synonyms, recursively",
         "Remove (s), (es), (ies)",
         "Generate derivational variants, recursively",
         "Generate known spelling variants",
         "Syntactic uninvert",
         "Map inflections into simple inflections",
         "Strip stop words",
         "Strip ambiguity tags",
         "Uninvert phrase around commas",
         "Convert Xerox Parc stocastic tagger into lvg style pipe delimited format",
         "Retrieve known fruitful variants from precomputed data",
         "Sort words by order",
         "Filter words by size",
         "Generate synonyms"
        };
    public final static String[] FLOW_DOC = 
        {"f0.html",
         "fa.html",
         "fA.html",
         "fAn.html",
         "fb.html",
         "fB.html",
         "fBn.html",
         "fc.html",
         "fca.html",
         "fch.html",
         "fC.html",
         "fCt.html",
         "fd.html",
         "fdc.html",
         "fe.html",
         "fE.html",
         "ff.html",
         "ffa.html",
         "ffp.html",
         "fg.html",
         "fG.html",
         "fGe.html",
         "fGn.html",
         "fi.html",
         "fici.html",
         "fis.html",
         "fl.html",
         "fL.html",
         "fLn.html",
         "fLp.html",
         "fm.html",
         "fn.html",
         "fnom.html",
         "fN.html",
         "fN3.html",
         "fo.html",
         "fp.html",
         "fP.html",
         "fq.html",
         "fq0.html",
         "fq1.html.html",
         "fq2.html",
         "fq3.html",
         "fq4.html",
         "fq5.html",
         "fq6.html",
         "fq7.html",
         "fq8.html",
         "fr.html",
         "frs.html",
         "fR.html",
         "fs.html",
         "fS.html",
         "fSi.html",
         "ft.html",
         "fT.html",
         "fu.html",
         "fU.html",
         "fv.html",
         "fw.html",
         "fws.html",
         "fy.html"
        };
    public final static String[] FLOW_FLAG = 
        {"0", "a", "A", "An", "b", "B", "Bn", "c", "ca", "ch", 
         "C", "Ct", "d", "dc~LONG", "e", "E", "f", "fa", "fp", "g", 
         "G", "Ge", "Gn", "i", "ici~LONG+LONG", "is", "l", "L", "Ln", "Lp", 
         "m", "n", "nom", "N", "N3", "o", "p", "P", "q", "q0", 
         "q1", "q2", "q3", "q4", "q5", "q6", "q7", "q8", "r", "rs", 
         "R", "s", "S", "Si", "t", "T", "u", "U", "v", "w", 
         "ws~INT", "y"
         };
    public final static int FLOW_NUM = FLOW.length; 
    // category
    public final static String[] CATEGORY = 
        {"Adjective", "Adverb", "Auxilary", "Complementizer", "Conjunction",
         "Determiner", "Modal", "Noun", "Preposition", "Pronoun",
         "Verb"};
    public final static long[] CATEGORY_VALUE = 
        {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024};
    public final static int CATEGORY_NUM = CATEGORY.length; 
    public final static long ALL_CATEGORY_VALUE = 2047; 
    // inflection
    public final static String[] INFLECTION = 
        {"Base", "Comparative", "Superlative", "Plural", "Present participle",
         "Past", "Past participle", "Present 3ps", "Positive", "Singular",
         "Infinitive", "Present (1_2_3p)", "Past negative", 
         "Present Negative (1_2_3p)", "Present (1s)",
         "Past Negative (1p_2_3p)", "Past (1p_2_3p)", "Past Negative (1s_3s)",
         "Present (1p_2_3p)", "Present Negative (1p_2_3p)",
         "Past (1s_3s)", "Present", "Present Negative (3s)",
         "Present Negative"};
    public final static long[] INFLECTION_VALUE = 
        {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 
         1024, 2048, 4096, 8192, 16384, 
         32768, 65536, 131072, 262144, 524288,
         1048576, 2097152, 4194304, 8388608};
    public final static int INFLECTION_NUM = INFLECTION.length; 
    public final static long ALL_INFLECTION_VALUE = 16777215; 
    // flow specific options
    public final static int FO_MIN_TERM_LENGTH = 0;
    public final static int FO_MAX_PERMUTE_TERM = 1;
    public final static int FO_MAX_METAPHONE = 2;
    public final static int FO_kd = 3;
    public final static int FO_kdn = 4;    // derivation negation
    public final static int FO_kdt = 5;    // derivation type
    public final static int FO_ki = 6;
    public final static int FO_ks = 7;
    public final static String[] FLOW_SPECIFIC_OPT = 
        {"Min. length of a term",
         "Max. permute numbers for inflecting terms",
         "Max. length of Metaphone codes",
         "Derivation morphology restriction",
         "Derivation negations", 
         "Derivation types", 
         "Inflection morphology restriction",
         "Synonym source restriction"};
    public final static String[] FLOW_SPECIFIC_OPT_DOC = 
        {"None",
         "None", 
         "None",
         "derivationOption.html", 
         "derivationNegation.html", 
         "derivationType.html", 
         "inflectionOption.html",
         "synonymOption.html"};
    public final static String[] FLOW_SPECIFIC_OPT_FLAG = 
        {"", "", "", "kd:INT", "kdn:STR", "kdt:STR", "ki:INT", "ks:STR"};
    public final static String[] FLOW_SPECIFIC_PURE_OPT_FLAG = 
        {"", "", "", "kd", "kdn", "kdt", "ki", "ks"};
    public final static int FLOW_SPECIFIC_OPT_NUM = FLOW_SPECIFIC_OPT.length;
    // config
    public final static int CONF_MIN_TERM_LENGTH = 0;
    public final static int CONF_MAX_PERMUTE_TERM = 1;
    public final static int CONF_MAX_METAPHONE = 2;
    public final static int CONF_CGI_EOP = 3;
    public final static int CONF_NO_OUTPUT = 4;
    public final static int CONF_TRUNCATED_NUM = 5;
    public final static int CONF_LVG_DIR = 6;
    public final static String[] CONFIG_VARS =
        {"Min. term length",
         "Max. rule terms",
         "Max. Metaphone",
         "CGI EOP",
         "No output string",
         "Truncated size",
         "Lvg directory",
         };
    public final static int CONFIG_VARS_NUM = CONFIG_VARS.length;
}
