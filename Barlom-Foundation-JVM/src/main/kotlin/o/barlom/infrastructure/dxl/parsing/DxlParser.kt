//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.parsing

import i.barlom.infrastructure.dxl.parsing.DxlExpectedTokenBufferImpl
import i.barlom.infrastructure.dxl.scanning.DxlToken
import i.barlom.infrastructure.dxl.scanning.EDxlTokenType.*
import o.barlom.infrastructure.dxl.model.annotations.DxlAnnotation
import o.barlom.infrastructure.dxl.model.annotations.DxlAnnotationList
import o.barlom.infrastructure.dxl.model.arguments.*
import o.barlom.infrastructure.dxl.model.connectedelements.DxlConnectedElement
import o.barlom.infrastructure.dxl.model.connectedelements.DxlConnectedElementList
import o.barlom.infrastructure.dxl.model.connectedelements.DxlConnectedQualifiedName
import o.barlom.infrastructure.dxl.model.connectedelements.DxlConnectedUuid
import o.barlom.infrastructure.dxl.model.connections.*
import o.barlom.infrastructure.dxl.model.core.DxlFileOrigin
import o.barlom.infrastructure.dxl.model.documentation.DxlBlockDocumentation
import o.barlom.infrastructure.dxl.model.documentation.DxlDocumentation
import o.barlom.infrastructure.dxl.model.documentation.DxlNullDocumentation
import o.barlom.infrastructure.dxl.model.elements.DxlConcept
import o.barlom.infrastructure.dxl.model.elements.DxlDeclaration
import o.barlom.infrastructure.dxl.model.elements.DxlElement
import o.barlom.infrastructure.dxl.model.expressions.DxlExpression
import o.barlom.infrastructure.dxl.model.expressions.literals.*
import o.barlom.infrastructure.dxl.model.expressions.references.DxlReferenceExpression
import o.barlom.infrastructure.dxl.model.names.DxlName
import o.barlom.infrastructure.dxl.model.names.DxlNullName
import o.barlom.infrastructure.dxl.model.names.DxlQualifiedName
import o.barlom.infrastructure.dxl.model.names.DxlSimpleName
import o.barlom.infrastructure.dxl.model.parameters.DxlNullParameterList
import o.barlom.infrastructure.dxl.model.parameters.DxlParameter
import o.barlom.infrastructure.dxl.model.parameters.DxlParameterList
import o.barlom.infrastructure.dxl.model.parameters.DxlSpecifiedParameterList
import o.barlom.infrastructure.dxl.model.uuids.DxlKnownUuid
import o.barlom.infrastructure.dxl.model.uuids.DxlNullUuid
import o.barlom.infrastructure.dxl.model.uuids.DxlUuid

//---------------------------------------------------------------------------------------------------------------------

class DxlParser(
    private val fileName: String,
    code: String
) {

    private val input = DxlExpectedTokenBufferImpl(code)

    ////

    /**
     * element
     *   : declaration
     *   | expression
     *   ;
     */
    fun parseElement(): DxlElement {

        if (
            input.hasLookAhead(HASH) ||
            input.hasLookAhead(AT) ||
            input.hasLookAhead(2, HASH) ||
            input.hasLookAhead(2, AT)
        ) {
            // declaration
            return parseDeclaration()
        }

        // expression
        return parseExpression()

    }

    ////

    /**
     * Parses one annotation.
     *
     * annotation
     *   : "@" qualifiedName argumentList?
     *   ;
     */
    private fun parseAnnotation(): DxlAnnotation {

        // "@"
        val atToken = input.read(AT)

        // qualifiedName
        val qualifiedName = parseQualifiedName()

        // argumentList?
        val argumentList = parseArgumentListOpt()

        return DxlAnnotation(atToken.origin, qualifiedName, argumentList)

    }

    /**
     * Parses a possibly empty list of annotations.
     *
     * annotationList
     *   : annotation*
     *   ;
     */
    private fun parseAnnotationList(): DxlAnnotationList {

        val annotations = mutableListOf<DxlAnnotation>()

        // annotation*
        while (input.hasLookAhead(AT)) {
            annotations.add(parseAnnotation())
        }

        return DxlAnnotationList(annotations)

    }

    /**
     * Parses one argument.
     *
     * argument
     *   : argumentName? expression
     *   ;
     */
    private fun parseArgument(): DxlArgument {

        val origin = input.lookAhead(1)!!.origin

        // argumentName?
        val argumentName = parseArgumentNameOpt()

        // expression
        val expression = parseExpression()

        return DxlArgument(origin, argumentName, expression)

    }

    /**
     * Parses an argument list.
     *
     * parameterList
     *   : "(" ( argument ("," argument)* )? ")"
     *   ;
     */
    private fun parseArgumentList(): DxlArgumentList {

        // "("
        val leftParenToken = input.read(LEFT_PARENTHESIS)

        val arguments = mutableListOf<DxlArgument>()

        if (!input.consumeWhen(RIGHT_PARENTHESIS)) {

            // argument
            arguments.add(parseArgument())

            // ( "," argument )*
            while (input.consumeWhen(COMMA)) {
                arguments.add(parseArgument())
            }

            // ")"
            input.read(RIGHT_PARENTHESIS)

        }

        return DxlSpecifiedArgumentList(leftParenToken.origin, arguments)

    }

    /**
     * Parses an optional argument list.
     */
    private fun parseArgumentListOpt(): DxlArgumentList {

        if (input.hasLookAhead(LEFT_PARENTHESIS)) {
            return parseArgumentList()
        }

        return DxlNullArgumentList

    }

    /**
     * Parses an optional argument name.
     *
     * argumentName
     *   : (simpleName "=")
     *   ;
     */
    private fun parseArgumentNameOpt(): DxlArgumentName {

        if (input.hasLookAhead(2, EQ) && input.hasLookAhead(IDENTIFIER)) {

            // simpleName
            val name = parseSimpleName()

            // "="
            input.read(EQ)

            return DxlSpecifiedArgumentName(name.origin, name.text)

        }

        return DxlNullArgumentName

    }

    /**
     * Parses a hash tag and qualifiedName signifying a concept.
     *
     * concept
     *   : "#" qualifiedName
     *   ;
     */
    private fun parseConcept(): DxlConcept {

        // "#"
        val hashToken = input.read(HASH)

        // qualifiedName
        val qualifiedName = parseQualifiedName()

        return DxlConcept(hashToken.origin, qualifiedName)

    }

    /**
     * Parses a connected element (to the right of a connector).
     *
     * connectedElement
     *   : qualifiedName
     *   | uuid
     *   | "[" element ("," element)* "]"
     *   ;
     */
    private fun parseConnectedElement(): DxlConnectedElement {

        // qualifiedName
        if (input.hasLookAhead(IDENTIFIER)) {
            return DxlConnectedQualifiedName(parseQualifiedName())
        }

        // uuid
        if (input.hasLookAhead(UUID)) {
            return DxlConnectedUuid(parseUuid())
        }

        // "["
        val leftBracketToken = input.read(LEFT_BRACKET)

        val elements = mutableListOf<DxlElement>()

        // element
        elements.add(parseElement())

        // ("," element)*
        while (input.consumeWhen(COMMA)) {
            elements.add(parseElement())
        }

        // "]"
        input.read(RIGHT_BRACKET)

        return DxlConnectedElementList(leftBracketToken.origin, elements)

    }

    /**
     * Parses a list of connections (that can be empty).
     *
     * connectionList
     *   : implicitConnection+ explicitConnection* (containment | valueAssignment)? semicolonOrNewLine
     *   | explicitConnection+ (containment | valueAssignment)? semicolonOrNewLine
     *   | containment semicolonOrNewLine
     *   | valueAssignment semicolonOrNewLine
     *   ;
     */
    private fun parseConnectionList(): DxlConnectionList {

        val connections = mutableListOf<DxlConnection>()

        while (input.hasLookAhead(COLON)) {
            connections.add(parseImplicitConnection())
        }

        while (input.hasLookAhead(TILDE)) {
            connections.add(parseExplicitConnection())
        }

        if (input.hasLookAhead(LEFT_BRACE)) {
            connections.add(parseContainment())
        }
        else if (input.hasLookAhead(EQ)) {
            connections.add(parseValueAssignment())
        }

        if (!input.hasLookAhead(RIGHT_BRACE)) {
            parseSemicolonOrNewLine()
        }

        return DxlConnectionList(connections)

    }

    /**
     * Parses a list of contained concepts.
     *
     * containment
     *   : "{" element* "}"
     *   ;
     */
    private fun parseContainment(): DxlContainment {

        // "{"
        val leftBrace = input.read(LEFT_BRACE)

        val containedElements = mutableListOf<DxlElement>()

        // element* "}"
        while (!input.hasLookAhead(RIGHT_BRACE)) {
            containedElements.add(parseElement())
        }

        return DxlContainment(leftBrace.origin, containedElements)

    }

    /**
     * Parses a declaration element.
     *
     * declaration
     *   : documentation? annotationList concept qualifiedName? uuid? parameterList? connectionList
     *   ;
     */
    private fun parseDeclaration(): DxlDeclaration {

        // documentation?
        val documentation = parseDocumentationOpt()

        // annotationList
        val annotations = parseAnnotationList()

        // concept
        val concept = parseConcept()

        // qualifiedName?
        val qualifiedName = parseQualifiedNameOpt()

        // uuid?
        val uuid = parseUuidOpt()

        // parameterList?
        val parameterList = parseParameterListOpt()

        // connectionList
        val connectionList = parseConnectionList()

        // Put together the declaration from its pieces.
        return DxlDeclaration(
            documentation,
            annotations,
            concept,
            qualifiedName,
            uuid,
            parameterList,
            connectionList
        )

    }

    /**
     * Parses an optional block of documentation.
     */
    private fun parseDocumentationOpt(): DxlDocumentation {

        if (input.hasLookAhead(DOCUMENTATION)) {
            return parseDocumentation()
        }

        return DxlNullDocumentation

    }

    /**
     * Parses one block of documentation.
     *
     * documentation
     *   : DOCUMENTATION
     *   ;
     */
    private fun parseDocumentation(): DxlBlockDocumentation {

        // DOCUMENTATION
        val token = input.read(DOCUMENTATION)

        return DxlBlockDocumentation(token.origin, token.text)

    }

    /**
     * Parses an explicit connection (one where the connector is spelled out).
     *
     * explicitConnection
     *   : "~" qualifiedName argumentList? "~" connectedElement
     *   | "~" qualifiedName argumentList? "~>" connectedElement
     *   | "<~" qualifiedName argumentList? "~" connectedElement
     *   ;
     */
    private fun parseExplicitConnection(): DxlExplicitConnection {

        val tildeToken1 = if (input.hasLookAhead(TILDE)) {
            // "~"
            input.read(TILDE)
        }
        else {
            // "<~"
            input.read(LEFT_TILDE)
        }

        // qualifiedName
        val qualifiedName = parseQualifiedName()

        // argumentList?
        val arguments = parseArgumentListOpt()

        val tildeToken2 = if (input.hasLookAhead(TILDE)) {
            // "~"
            input.read(TILDE)
        }
        else {
            // "~>"
            input.read(RIGHT_TILDE)
        }

        val direction = when {
            tildeToken1.type == LEFT_TILDE  -> EDxlConnectionDirection.LEFT
            tildeToken2.type == RIGHT_TILDE -> EDxlConnectionDirection.RIGHT
            else                            -> EDxlConnectionDirection.UNDIRECTED
        }

        val connector = DxlConnector(qualifiedName.origin, qualifiedName, arguments, direction)

        // connectedElement
        val connectedElement = parseConnectedElement()

        return DxlExplicitConnection(connector, connectedElement)

    }

    /**
     * Parses an expression.
     *
     * expression
     *   : literalExpression
     *   | referenceExpression
     *   ;
     */
    private fun parseExpression(): DxlExpression {

        if (
            input.hasLookAhead(BOOLEAN_LITERAL) ||
            input.hasLookAhead(CHARACTER_LITERAL) ||
            input.hasLookAhead(FLOATING_POINT_LITERAL) ||
            input.hasLookAhead(INTEGER_LITERAL) ||
            input.hasLookAhead(STRING_LITERAL) ||
            input.hasLookAhead(2, BOOLEAN_LITERAL) ||
            input.hasLookAhead(2, CHARACTER_LITERAL) ||
            input.hasLookAhead(2, FLOATING_POINT_LITERAL) ||
            input.hasLookAhead(2, INTEGER_LITERAL) ||
            input.hasLookAhead(2, STRING_LITERAL)
        ) {
            // literal Expression
            return parseLiteralExpression()
        }

        // referenceExpression
        return parseReferenceExpression()

    }

    /**
     * Parses an implicit connection (just a colon with connection type implied by the concept and connected).
     *
     * implicitConnection
     *   : ":" connectedElement
     *   ;
     */
    private fun parseImplicitConnection(): DxlSpecifiedImplicitConnection {

        // ":"
        val colonToken = input.read(COLON)

        // connectedElement
        val connectedElement = parseConnectedElement()

        return DxlSpecifiedImplicitConnection(colonToken.origin, connectedElement)

    }

    /**
     * Parses an optional implicit connection.
     */
    private fun parseImplicitConnectionOpt(): DxlImplicitConnection {

        if (input.hasLookAhead(COLON)) {
            return parseImplicitConnection()
        }

        return DxlNullImplicitConnection

    }

    /**
     * Parses a literal expression.
     *
     * literalExpression
     *   : documentation?
     *     ( BOOLEAN_LITERAL |
     *       CHARACTER_LITERAL |
     *       FLOATING_POINT_LITERAL |
     *       INTEGER_LITERAL |
     *       STRING_LITERAL )
     *   ;
     */
    private fun parseLiteralExpression(): DxlLiteralExpression {

        val documentation = parseDocumentationOpt()

        val literalToken = input.readOneOf(
            BOOLEAN_LITERAL,
            CHARACTER_LITERAL,
            FLOATING_POINT_LITERAL,
            INTEGER_LITERAL,
            STRING_LITERAL
        )

        return when (literalToken.type) {
            BOOLEAN_LITERAL        -> DxlBooleanLiteral(
                literalToken.origin,
                documentation,
                literalToken.text
            )
            CHARACTER_LITERAL      -> DxlCharacterLiteral(
                literalToken.origin,
                documentation,
                literalToken.text
            )
            FLOATING_POINT_LITERAL -> DxlFloatingPointLiteral(
                literalToken.origin,
                documentation,
                literalToken.text
            )
            INTEGER_LITERAL        -> DxlIntegerLiteral(
                literalToken.origin,
                documentation,
                literalToken.text
            )
            STRING_LITERAL         -> DxlStringLiteral(
                literalToken.origin,
                documentation,
                literalToken.text
            )
            else                   -> throw IllegalStateException("Unexpected token type: ${literalToken.type}.")
        }

    }

    /**
     * Parses one parameter.
     *
     * parameter
     *   : simpleName implicitConnection?
     *   ;
     */
    private fun parseParameter(): DxlParameter {

        // simpleName
        val simpleName = parseSimpleName()

        // implicitConnection
        val implicitConnection = parseImplicitConnectionOpt()

        return DxlParameter(simpleName.origin, simpleName, implicitConnection)

    }

    /**
     * Parses a parameter list.
     *
     * parameterList
     *   : "(" ( parameter ("," parameter)* )? ")"
     *   ;
     */
    private fun parseParameterList(): DxlParameterList {

        // "("
        val leftParenToken = input.read(LEFT_PARENTHESIS)

        val parameters = mutableListOf<DxlParameter>()


        if (!input.consumeWhen(RIGHT_PARENTHESIS)) {

            // parameter
            parameters.add(parseParameter())

            // ("," parameter)*
            while (input.consumeWhen(COMMA)) {
                parameters.add(parseParameter())
            }

            // ")"
            input.read(RIGHT_PARENTHESIS)

        }

        return DxlSpecifiedParameterList(leftParenToken.origin, parameters)

    }

    /**
     * Parses an optional parameter list.
     */
    private fun parseParameterListOpt(): DxlParameterList {

        if (input.hasLookAhead(LEFT_PARENTHESIS)) {
            return parseParameterList()
        }

        return DxlNullParameterList

    }

    /**
     * Parses a qualified name.
     *
     * qualifiedName
     *   : simpleName ("." simpleName)*
     *   ;
     */
    private fun parseQualifiedName(): DxlName {

        val names = mutableListOf<DxlSimpleName>()

        while (true) {

            // simpleName
            val simpleName = parseSimpleName()
            names.add(simpleName)

            // "."?
            if (!input.hasLookAhead(2, IDENTIFIER) || !input.consumeWhen(DOT)) {
                break
            }

        }

        if (names.size > 1) {
            return DxlQualifiedName(names)
        }

        return names[0]

    }

    /**
     * Parses an optional qualified name.
     */
    private fun parseQualifiedNameOpt(): DxlName {

        if (!input.hasLookAhead(IDENTIFIER)) {
            return DxlNullName
        }

        return parseQualifiedName()
    }

    /**
     * Parses a reference expression.
     *
     * referenceExpression
     *   : documentation? (qualifiedName | uuid) argumentList? valueAssignment?
     *   ;
     */
    private fun parseReferenceExpression(): DxlReferenceExpression {

        // documentation
        val documentation = parseDocumentationOpt()

        // qualifiedName
        val qualifiedName = parseQualifiedNameOpt()

        // uuid?
        val uuid = if (qualifiedName is DxlNullName) parseUuid() else DxlNullUuid

        // argumentList?
        val argumentList = parseArgumentListOpt()

        // valueAssignment?
        val valueAssignment = parseValueAssignmentOpt()

        return DxlReferenceExpression(documentation, qualifiedName, uuid, argumentList, valueAssignment)

    }

    /**
     * Parses a semicolon or line break.
     *
     * semicolonOrNewLine
     *   : ";"
     *   | /* next token occurs on new line */
     *   ;
     */
    private fun parseSemicolonOrNewLine() {

        // next token on new line
        if (input.hasLookAheadOnNewLine()) {
            return
        }

        // ";"
        input.read(SEMICOLON)

    }

    /**
     * Parses a simple name (single identifier).
     *
     * simpleName
     *   : IDENTIFIER
     *   ;
     */
    private fun parseSimpleName(): DxlSimpleName {

        // IDENTIFIER
        val nameToken = input.read(IDENTIFIER)

        return DxlSimpleName(nameToken.origin, nameToken.text)

    }

    /**
     * Parses an optional UUID.
     *
     * uuid
     *   : UUID
     *   ;
     */
    private fun parseUuid(): DxlKnownUuid {

        // UUID
        val uuidToken = input.read(UUID)

        return DxlKnownUuid(uuidToken.origin, uuidToken.text)

    }

    /**
     * Parses an optional UUID.
     */
    private fun parseUuidOpt(): DxlUuid {

        if (!input.hasLookAhead(UUID)) {
            return DxlNullUuid
        }

        return parseUuid()

    }

    /**
     * Parses a value assignment.
     *
     * valueAssignment
     *   : "=" literalExpression
     *   ;
     */
    private fun parseValueAssignment(): DxlValueAssignment {

        // "="
        val equals = input.read(EQ)

        // literalExpression
        // TODO: parse an arbitrary expression or just a literal?
        val expression = parseLiteralExpression()

        return DxlLiteralValueAssignment(equals.origin, expression)

    }

    /**
     * Parses an optional value assignment.
     */
    private fun parseValueAssignmentOpt(): DxlValueAssignment {

        if (input.hasLookAhead(EQ)) {
            return parseValueAssignment()
        }

        return DxlNullValueAssignment

    }

    ////

    /**
     * Adds the file name to a token origin.
     */
    private val DxlToken.origin
        get() = DxlFileOrigin(fileName, this.line, this.column)

}

//---------------------------------------------------------------------------------------------------------------------

