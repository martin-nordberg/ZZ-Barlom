//
// (C) Copyright 2017-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package /*js*/x.barlom.infrastructure.uuids

/**
 * JavaScript implementation of UUID as simple string container.
 */
class Uuid(

    val uuidStr: String

) : Comparable<Uuid> {

    /**
     * Compares this UUID with the specified UUID. The first of two UUIDs is greater than the second if the most
     * significant field in which the UUIDs differ is greater for the first UUID.
     * @param  other `UUID` to which this `UUID` is to be compared
     * @return  -1, 0 or 1 as this `UUID` is less than, equal to, or greater than `other`
     */
    override operator fun compareTo(other: Uuid): Int {

        // The ordering is intentionally set up so that the UUIDs
        // can simply be numerically compared as two numbers
        return when {
            this.uuidStr < other.uuidStr -> -1
            this.uuidStr > other.uuidStr -> 1
            else                         -> 0
        }

    }

    /**
     * Compares this object to the specified object.  The result is `true` if and only if the argument is not `null`, is a `UUID`
     * object, has the same variant, and contains the same value, bit for bit,
     * as this `UUID`.
     * @param  other The object to be compared
     * @return  `true` if the objects are the same; `false` otherwise
     */
    override fun equals(other: Any?): Boolean {

        if (null == other || other !is Uuid) {
            return false
        }

        return uuidStr == other.uuidStr

    }

    /**
     * Returns a hash code for this `UUID`.
     * @return  A hash code value for this `UUID`
     */
    override fun hashCode(): Int {
        return uuidStr.hashCode()
    }

    fun hasNextInReservedBlock(): Boolean {

        val nybble7 = uuidStr[7]
        val nybble6 = uuidStr[6]

        return nybble6 != 'F' || nybble7 != 'F'

    }

    fun nextInReservedBlock(): Uuid {

        check(hasNextInReservedBlock()) { "Reserved block exhausted." }

        val nybble7 = uuidStr[7]
        when (nybble7) {
            '0' -> return Uuid.fromString(uuidStr.substring(0, 7) + '1' + uuidStr.substring(8))
            '1' -> return Uuid.fromString(uuidStr.substring(0, 7) + '2' + uuidStr.substring(8))
            '2' -> return Uuid.fromString(uuidStr.substring(0, 7) + '3' + uuidStr.substring(8))
            '3' -> return Uuid.fromString(uuidStr.substring(0, 7) + '4' + uuidStr.substring(8))
            '4' -> return Uuid.fromString(uuidStr.substring(0, 7) + '5' + uuidStr.substring(8))
            '5' -> return Uuid.fromString(uuidStr.substring(0, 7) + '6' + uuidStr.substring(8))
            '6' -> return Uuid.fromString(uuidStr.substring(0, 7) + '7' + uuidStr.substring(8))
            '7' -> return Uuid.fromString(uuidStr.substring(0, 7) + '8' + uuidStr.substring(8))
            '8' -> return Uuid.fromString(uuidStr.substring(0, 7) + '9' + uuidStr.substring(8))
            '9' -> return Uuid.fromString(uuidStr.substring(0, 7) + 'A' + uuidStr.substring(8))
            'A' -> return Uuid.fromString(uuidStr.substring(0, 7) + 'B' + uuidStr.substring(8))
            'B' -> return Uuid.fromString(uuidStr.substring(0, 7) + 'C' + uuidStr.substring(8))
            'C' -> return Uuid.fromString(uuidStr.substring(0, 7) + 'D' + uuidStr.substring(8))
            'D' -> return Uuid.fromString(uuidStr.substring(0, 7) + 'E' + uuidStr.substring(8))
            'E' -> return Uuid.fromString(uuidStr.substring(0, 7) + 'F' + uuidStr.substring(8))
        }

        val nybble6 = uuidStr[6]
        when (nybble6) {
            '0' -> return Uuid.fromString(uuidStr.substring(0, 6) + "10" + uuidStr.substring(8))
            '1' -> return Uuid.fromString(uuidStr.substring(0, 6) + "20" + uuidStr.substring(8))
            '2' -> return Uuid.fromString(uuidStr.substring(0, 6) + "30" + uuidStr.substring(8))
            '3' -> return Uuid.fromString(uuidStr.substring(0, 6) + "40" + uuidStr.substring(8))
            '4' -> return Uuid.fromString(uuidStr.substring(0, 6) + "50" + uuidStr.substring(8))
            '5' -> return Uuid.fromString(uuidStr.substring(0, 6) + "60" + uuidStr.substring(8))
            '6' -> return Uuid.fromString(uuidStr.substring(0, 6) + "70" + uuidStr.substring(8))
            '7' -> return Uuid.fromString(uuidStr.substring(0, 6) + "80" + uuidStr.substring(8))
            '8' -> return Uuid.fromString(uuidStr.substring(0, 6) + "90" + uuidStr.substring(8))
            '9' -> return Uuid.fromString(uuidStr.substring(0, 6) + "A0" + uuidStr.substring(8))
            'A' -> return Uuid.fromString(uuidStr.substring(0, 6) + "B0" + uuidStr.substring(8))
            'B' -> return Uuid.fromString(uuidStr.substring(0, 6) + "C0" + uuidStr.substring(8))
            'C' -> return Uuid.fromString(uuidStr.substring(0, 6) + "D0" + uuidStr.substring(8))
            'D' -> return Uuid.fromString(uuidStr.substring(0, 6) + "E0" + uuidStr.substring(8))
            'E' -> return Uuid.fromString(uuidStr.substring(0, 6) + "F0" + uuidStr.substring(8))
        }

        throw IllegalArgumentException("Reserved block exhausted.")

    }

    /**
     * Returns a `String` object representing this `UUID`.
     *
     * The UUID string representation is as described by this BNF:
     * UUID                   = <time_low> "-" <time_mid> "-"
     * <time_high_and_version> "-"
     * <variant_and_sequence> "-"
     * <node>
     * time_low               = 4*<hexOctet>
     * time_mid               = 2*<hexOctet>
     * time_high_and_version  = 2*<hexOctet>
     * variant_and_sequence   = 2*<hexOctet>
     * node                   = 6*<hexOctet>
     * hexOctet               = <hexDigit><hexDigit>
     * hexDigit               =
     * "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     * | "a" | "b" | "c" | "d" | "e" | "f"
     * | "A" | "B" | "C" | "D" | "E" | "F"
     * @return  A string representation of this `UUID`
     */
    override fun toString(): String {
        return uuidStr
    }


    companion object {

        /**
         * Creates a `UUID` from the string standard representation as described in the [.toString] method.
         * @param uuidStr A string that specifies a `UUID`.
         * @return  A `UUID` with the specified value.
         * TODO: check the format of the input
         */
        fun fromString(uuidStr: String): Uuid {
            return Uuid(uuidStr)
        }

    }

}
