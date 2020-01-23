//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package i.barlom.infrastructure.dxl.scanning

//---------------------------------------------------------------------------------------------------------------------

internal enum class EDxlTokenType(
    val text: String
) {

    /* Identifiers */
    IDENTIFIER("[identifier]"),

    /* Punctuation */
    AMPERSAND("'&'"),
    ASTERISK("'*'"),
    AT("'@'"),
    BACKSLASH("'\'"),
    CARET("'^'"),
    COLON("':'"),
    COMMA("','"),
    DASH("'-'"),
    DOT("'.'"),
    DOUBLE_DASH("'--'"),
    EQUALS("'='"),
    GREATER_THAN("'>'"),
    HASH("'#'"),
    LEFT_ARROW("'<-'"),
    LEFT_BRACE("'{'"),
    LEFT_BRACKET("'['"),
    LEFT_LINE_BRACKET("'-|'"),
    LEFT_PARENTHESIS("'('"),
    LESS_THAN("'<'"),
    PERCENT("'%'"),
    QUESTION_MARK("'?'"),
    RIGHT_ARROW("'->'"),
    RIGHT_BRACE("'}'"),
    RIGHT_BRACKET("']'"),
    RIGHT_LINE_BRACKET("'|-'"),
    RIGHT_PARENTHESIS("')'"),
    SEMICOLON("';'"),
    SLASH("'/'"),
    TILDE("'~'"),
    VERTICAL_LINE( "'|'" ),

    /* Literals */
    BOOLEAN_LITERAL("[boolean literal]"),
    CHARACTER_LITERAL("[character literal]"),
    FLOATING_POINT_LITERAL("[floating point literal]"),
    INTEGER_LITERAL("[integer literal]"),
    STRING_LITERAL("[string literal]"),
    UUID_LITERAL("[UUID literal]"),
    // TODO: date $...$
    // TODO: date/time $...T...$
    // TODO?: rational number 123r45
    // TODO: regex /.../ig...
    // TODO: time $T...$
    // TODO: unsigned 123u
    // TODO: URL |...|

    /* Documentation */
    DOCUMENTATION("[documentation]"),

    /** Errors */
    INVALID_CHARACTER("[invalid character]"),
    INVALID_UUID_LITERAL("[invalid UUID literal]"),
    UNTERMINATED_CHARACTER_LITERAL("[unterminated character literal]"),
    UNTERMINATED_DOCUMENTATION("[unterminated documentation]"),
    UNTERMINATED_QUOTED_IDENTIFIER("[unterminated quoted identifier]"),
    UNTERMINATED_STRING_LITERAL("[unterminated string literal]"),

    /** End of input. */
    END_OF_INPUT("[end of input]");

    ////

    override fun toString(): String {
        return text
    }

}

//---------------------------------------------------------------------------------------------------------------------

