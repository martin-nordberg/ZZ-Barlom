//
// (C) Copyright 2017 Martin E. Nordberg III
// Apache 2.0 License
//

package org.barlom.infrastructure.uuids


/**
 * Cross-platform implementation of UUID loosely based on OpenJDK UUID.
 */
class Uuid (

    /** The most significant 64 bits of this UUID. */
    private val mostSignificantBits: Long,

    /** The least significant 64 bits of this UUID. */
    private val leastSignificantBits: Long

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
            this.mostSignificantBits < other.mostSignificantBits   -> -1
            this.mostSignificantBits > other.mostSignificantBits   -> 1
            this.leastSignificantBits < other.leastSignificantBits -> -1
            this.leastSignificantBits > other.leastSignificantBits -> 1
            else                                                   -> 0
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

        return mostSignificantBits == other.mostSignificantBits && leastSignificantBits == other.leastSignificantBits

    }

    /**
     * Returns a hash code for this `UUID`.
     * @return  A hash code value for this `UUID`
     */
    override fun hashCode(): Int {
        val hilo = mostSignificantBits xor leastSignificantBits
        return (hilo shr 32).toInt() xor hilo.toInt()
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

        /** Returns val represented by the specified number of hex digits.  */
        fun digits(value: Long, digits: Int): String {
            val hi = 1L shl digits * 4
            return longToHexString(hi or (value and hi - 1)).substring(1)
        }

        return digits(mostSignificantBits shr 32, 8) + "-" +
            digits(mostSignificantBits shr 16, 4) + "-" +
            digits(mostSignificantBits, 4) + "-" +
            digits(leastSignificantBits shr 48, 4) + "-" +
            digits(leastSignificantBits, 12)

    }


    companion object {

        /**
         * Creates a `UUID` from the string standard representation as described in the [.toString] method.
         * @param uuidStr A string that specifies a `UUID`.
         * @return  A `UUID` with the specified value.
         * @throws  IllegalArgumentException If name does not conform to the string representation as
         *          described in [.toString]
         */
        fun fromString(uuidStr: String): Uuid {

            val components = uuidStr.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            if (components.size != 5) {
                throw IllegalArgumentException("Invalid UUID string: " + uuidStr)
            }

            var mostSigBits = components[0].toLong(16)
            mostSigBits = mostSigBits shl 16
            mostSigBits = mostSigBits or components[1].toLong(16)
            mostSigBits = mostSigBits shl 16
            mostSigBits = mostSigBits or components[2].toLong(16)

            var leastSigBits = components[3].toLong(16)
            leastSigBits = leastSigBits shl 48
            leastSigBits = leastSigBits or components[4].toLong(16)

            return Uuid(mostSigBits, leastSigBits)

        }

    }

}
