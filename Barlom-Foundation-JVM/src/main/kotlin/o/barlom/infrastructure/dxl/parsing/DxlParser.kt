//
// (C) Copyright 2019 Martin E. Nordberg III
// Apache 2.0 License
//

package o.barlom.infrastructure.dxl.parsing

import i.barlom.infrastructure.dxl.parsing.DxlExpectedTokenBufferImpl
import i.barlom.infrastructure.dxl.scanning.DxlToken
import i.barlom.infrastructure.dxl.scanning.EDxlTokenType.*
import o.barlom.infrastructure.dxl.model.arguments.DxlArgument
import o.barlom.infrastructure.dxl.model.arguments.DxlArguments
import o.barlom.infrastructure.dxl.model.arguments.DxlNoArguments
import o.barlom.infrastructure.dxl.model.arguments.DxlOptArguments
import o.barlom.infrastructure.dxl.model.concepts.DxlConceptDeclaration
import o.barlom.infrastructure.dxl.model.connections.*
import o.barlom.infrastructure.dxl.model.core.DxlFileOrigin
import o.barlom.infrastructure.dxl.model.core.DxlOrigin
import o.barlom.infrastructure.dxl.model.declarations.*
import o.barlom.infrastructure.dxl.model.documentation.DxlDocumentation
import o.barlom.infrastructure.dxl.model.documentation.DxlNoDocumentation
import o.barlom.infrastructure.dxl.model.documentation.DxlOptDocumentation
import o.barlom.infrastructure.dxl.model.elements.DxlElement
import o.barlom.infrastructure.dxl.model.elements.DxlSpecifiedElement
import o.barlom.infrastructure.dxl.model.expressions.DxlExpression
import o.barlom.infrastructure.dxl.model.expressions.DxlNoValue
import o.barlom.infrastructure.dxl.model.expressions.literals.*
import o.barlom.infrastructure.dxl.model.names.*
import o.barlom.infrastructure.dxl.model.parameters.DxlNoParameters
import o.barlom.infrastructure.dxl.model.parameters.DxlOptParameters
import o.barlom.infrastructure.dxl.model.parameters.DxlParameter
import o.barlom.infrastructure.dxl.model.parameters.DxlParameters
import o.barlom.infrastructure.dxl.model.properties.DxlProperties
import o.barlom.infrastructure.dxl.model.properties.DxlProperty
import o.barlom.infrastructure.dxl.model.types.DxlNoTypeRef
import o.barlom.infrastructure.dxl.model.types.DxlOptTypeRef
import o.barlom.infrastructure.dxl.model.types.DxlTypeRef
import o.barlom.infrastructure.dxl.model.uuids.DxlNoUuid
import o.barlom.infrastructure.dxl.model.uuids.DxlOptUuid
import o.barlom.infrastructure.dxl.model.uuids.DxlUuid

//---------------------------------------------------------------------------------------------------------------------

class DxlParser(
    private val fileName: String,
    code: String
) {

    private val input = DxlExpectedTokenBufferImpl(code)

    ////

    /**
     * topLevel
     *   : namespace? alias* declaration+
     *   ;
     */
    fun parseTopLevel(): DxlTopLevel {

        val aliases = parseAliases()

        val declarations = parseDeclarations()

        return DxlTopLevel( aliases, declarations)

    }

    ////

    /**
     * Parses zero or more alias declarations.
     *
     * aliases
     *   : "alias" simpleName "=" qualifiedName
     *   ;
     */
    private fun parseAliases(): DxlAliases {

        val aliases = mutableListOf<DxlAlias>()

        while (input.hasLookAhead(ALIAS)) {
            val aliasToken = input.read()
            val name = parseSimpleName()
            input.read(EQUALS)
            val qualifiedName = parseQualifiedName()

            aliases.add(DxlAlias(aliasToken.origin,name,qualifiedName))
        }

        return DxlAliases(aliases)

    }

    /**
     * Parses one argument.
     *
     * parameter
     *   : (simpleName "=")? expression
     *   ;
     */
    private fun parseArgument(): DxlArgument {

        // (simpleName "=")?
        val name = if (input.hasLookAhead(IDENTIFIER) && input.hasLookAhead(2,EQUALS)) {
            // simpleName
            val simpleName = parseSimpleName()

            input.read(EQUALS)

            simpleName
        }
        else {
            DxlNoName
        }

        // expression
        val expression = parseExpression()

        return DxlArgument(name, expression)

    }

    /**
     * Parses an argument list.
     *
     * parameters
     *   : "(" ( argument ("," argument)* )? ")"
     *   ;
     */
    private fun parseArguments(): DxlArguments {

        // "("
        val leftParenToken = input.read(LEFT_PARENTHESIS)

        val arguments = mutableListOf<DxlArgument>()

        if (!input.consumeWhen(RIGHT_PARENTHESIS)) {

            // parameter
            arguments.add(parseArgument())

            // ("," parameter)*
            while (input.consumeWhen(COMMA)) {
                arguments.add(parseArgument())
            }

            // ")"
            input.read(RIGHT_PARENTHESIS)

        }

        return DxlArguments(leftParenToken.origin, arguments)

    }

    /**
     * Parses an optional argument list.
     */
    private fun parseArgumentsOpt(): DxlOptArguments {

        if (input.hasLookAhead(LEFT_PARENTHESIS)) {
            return parseArguments()
        }

        return DxlNoArguments

    }

    /**
     * conceptDeclaration
     *   : "[" element "]" connections? content?
     */
    private fun parseConceptDeclaration(documentation: DxlOptDocumentation): DxlConceptDeclaration {

        // "["
        val leftBracketToken = input.read(LEFT_BRACKET)

        // element
        val element = parseElement(leftBracketToken.origin)

        // "]"
        input.read(RIGHT_BRACKET)

        // connections?
        val connections = mutableListOf<DxlConnection>()

        while (input.hasLookAhead(DOUBLE_DASH) || input.hasLookAhead(LEFT_ARROW)) {
            connections.add(parseConnection())
        }

        // content?
        val content = if (input.hasLookAhead(LEFT_BRACE)) {
            parseContent()
        }
        else {
            DxlNoContent
        }

        return DxlConceptDeclaration(leftBracketToken.origin, documentation, element, DxlConnections(connections), content)
    }

    /**
     * connection
     *   : ("<-" | "--") "-|" element "|-" ("--" | "->") "[" element "]"
     */
    private fun parseConnection(): DxlConnection {

        // ("<-" | "--")
        val arrowStart = input.readOneOf(DOUBLE_DASH, LEFT_ARROW)

        // "-|"
        val leftLineBracketToken = input.read(LEFT_LINE_BRACKET)

        // element
        val element = parseElement(leftLineBracketToken.origin)

        // "|-"
        input.read(RIGHT_LINE_BRACKET)

        // ("--" | "->")
        val arrowEnd = input.readOneOf(DOUBLE_DASH, RIGHT_ARROW)

        // "["
        val leftBracketToken = input.read(LEFT_BRACKET)

        // element
        val connectedElement = parseElement(leftBracketToken.origin)

        // "]"
        input.read(RIGHT_BRACKET)

        // Determine the direction of the connection from the arrow tokens.
        val direction = if ( arrowStart.type == DOUBLE_DASH ) {
            if ( arrowEnd.type == DOUBLE_DASH) {
                EDxlConnectionDirection.UNDIRECTED
            }
            else {
                EDxlConnectionDirection.DIRECTED_RIGHT
            }
        }
        else {
            if ( arrowEnd.type == DOUBLE_DASH) {
                EDxlConnectionDirection.DIRECTED_LEFT
            }
            else {
                EDxlConnectionDirection.BIDIRECTIONAL
            }
        }

        return DxlConnection(arrowStart.origin, direction, element, connectedElement)

    }

    /**
     * connectionDeclaration
     *   : "-|" element "|-"
     */
    private fun parseConnectionDeclaration(documentation: DxlOptDocumentation): DxlConnectionDeclaration {

        // "-|"
        val leftBracketToken = input.read(LEFT_LINE_BRACKET)

        // element
        val element = parseElement(leftBracketToken.origin)

        // "|-"
        input.read(RIGHT_LINE_BRACKET)

        return DxlConnectionDeclaration(leftBracketToken.origin, documentation, element)

    }

    /**
     * content
     *   : "{" declarations "}"
     */
    private fun parseContent(): DxlContent {

        val leftBraceToken = input.read(LEFT_BRACE)

        val declarations = parseDeclarations()

        input.read(RIGHT_BRACE)

        return DxlContent(leftBraceToken.origin, declarations)

    }

    /**
     * declarations
     *   : (conceptDeclaration | connectionDeclaration)*
     *   ;
     */
    private fun parseDeclarations(): DxlDeclarations {

        val declarations = mutableListOf<DxlDeclaration>()

        while (true) {

            val documentation = parseDocumentationOpt()

            if (input.hasLookAhead(LEFT_BRACKET)) {
                declarations.add(parseConceptDeclaration(documentation))
            }
            else if (input.hasLookAhead(LEFT_LINE_BRACKET)) {
                declarations.add(parseConnectionDeclaration(documentation))
            }
            else {
                break
            }

        }

        if (declarations.isEmpty()) {
            input.expected("Concept or connection declaration")
        }

        return DxlDeclarations(declarations)

    }

    /**
     * Parses one block of documentation.
     *
     * documentation
     *   : DOCUMENTATION
     *   ;
     */
    private fun parseDocumentation(): DxlDocumentation {

        // DOCUMENTATION
        val token = input.read(DOCUMENTATION)

        return DxlDocumentation(token.origin, token.text)

    }

    /**
     * Parses an optional block of documentation.
     */
    private fun parseDocumentationOpt(): DxlOptDocumentation {

        if (input.hasLookAhead(DOCUMENTATION)) {
            return parseDocumentation()
        }

        return DxlNoDocumentation

    }

    /**
     * element
     *   : uuid? (qualifiedName parameters?)? typeRef? properties
     */
    private fun parseElement(origin: DxlOrigin): DxlElement {

        // uuid?
        val uuid = parseUuidOpt()

        // qualifiedName?
        val name = parseQualifiedNameOpt()

        // parameters?
        val parameters = if (name is DxlNoName) {
            DxlNoParameters
        }
        else {
            parseParametersOpt()
        }

        // typeRef?
        val typeRef = parseTypeRefOpt(name is DxlNoName)

        // properties
        val properties = parseProperties()

        return DxlSpecifiedElement(origin, uuid, name, parameters, typeRef, properties)
    }

    private fun parseExpression(): DxlExpression {

        val token = input.read()

        when (token.type) {
            BOOLEAN_LITERAL        -> return DxlBooleanLiteral(token.origin, token.text)
            CHARACTER_LITERAL      -> return DxlCharacterLiteral(token.origin, token.text)
            FLOATING_POINT_LITERAL -> return DxlFloatingPointLiteral(token.origin, token.text)
            INTEGER_LITERAL        -> return DxlIntegerLiteral(token.origin, token.text)
            STRING_LITERAL         -> return DxlStringLiteral(token.origin, token.text)
            else                   -> input.expected("expression")
        }

        // TODO: more than just literals

    }

    /**
     * Parses one parameter.
     *
     * parameter
     *   : simpleName typeRef? ( "=" expression )?
     *   ;
     */
    private fun parseParameter(): DxlParameter {

        // simpleName
        val simpleName = parseSimpleName()

        // typeRef?
        val typeRef = parseTypeRefOpt(false)

        // ( "=" expression )?
        val expression = if (input.consumeWhen(EQUALS)) {
            parseExpression()
        }
        else {
            DxlNoValue
        }

        return DxlParameter(simpleName.origin, simpleName, typeRef, expression)

    }

    /**
     * Parses a parameter list.
     *
     * parameters
     *   : "(" ( parameter ("," parameter)* )? ")"
     *   ;
     */
    private fun parseParameters(): DxlParameters {

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

        return DxlParameters(leftParenToken.origin, parameters)

    }

    /**
     * Parses an optional parameter list.
     */
    private fun parseParametersOpt(): DxlOptParameters {

        if (input.hasLookAhead(LEFT_PARENTHESIS)) {
            return parseParameters()
        }

        return DxlNoParameters

    }

    /**
     * Parses one property.
     *
     * property
     *   : name typeRef? "=" expression
     *   ;
     */
    private fun parseProperty(): DxlProperty {

        // simpleName
        val simpleName = parseSimpleName()

        // typeRef?
        val typeRef = parseTypeRefOpt(false)

        // "="
        input.read(EQUALS)

        // expression
        val expression = parseExpression()

        return DxlProperty(simpleName, typeRef, expression)

    }

    /**
     * Parses a property list. (It could be empty.)
     *
     * properties
     *   : ( "~" property )*
     *   ;
     */
    private fun parseProperties(): DxlProperties {

        val properties = mutableListOf<DxlProperty>()

        while (input.consumeWhen(TILDE)) {
            properties.add(parseProperty())
        }

        return DxlProperties(properties)

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
    private fun parseQualifiedNameOpt(): DxlOptName {

        if (!input.hasLookAhead(IDENTIFIER)) {
            return DxlNoName
        }

        return parseQualifiedName()
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
     * Parses a type reference.
     *
     * parameters
     *   : ":" qualifiedName arguments?
     *   ;
     */
    private fun parseTypeRef(isForUnnamedElement: Boolean): DxlTypeRef {

        // ":"
        val colonToken = input.read(COLON)

        // qualifiedName
        val typeName = parseQualifiedName()

        // arguments?
        val arguments = parseArgumentsOpt()

        return DxlTypeRef(colonToken.origin, typeName, arguments, isForUnnamedElement)

    }

    /**
     * Parses an optional type spec.
     */
    private fun parseTypeRefOpt(isForUnnamedElement: Boolean): DxlOptTypeRef {

        if (input.hasLookAhead(COLON)) {
            return parseTypeRef(isForUnnamedElement)
        }

        return DxlNoTypeRef

    }

    /**
     * Parses an optional UUID.
     *
     * uuid
     *   : UUID
     *   ;
     */
    private fun parseUuid(): DxlUuid {

        // UUID
        val uuidToken = input.read(UUID_LITERAL)

        return DxlUuid(uuidToken.origin, uuidToken.text)

    }

    /**
     * Parses an optional UUID.
     */
    private fun parseUuidOpt(): DxlOptUuid {

        if (!input.hasLookAhead(UUID_LITERAL)) {
            return DxlNoUuid
        }

        return parseUuid()

    }

    ////

    /**
     * Adds the file name to a token origin.
     */
    private val DxlToken.origin
        get() = DxlFileOrigin(fileName, this.line, this.column)

}

//---------------------------------------------------------------------------------------------------------------------

