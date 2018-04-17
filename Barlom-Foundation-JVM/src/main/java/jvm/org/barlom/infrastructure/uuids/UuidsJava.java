//
// (C) Copyright 2015-2018 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.org.barlom.infrastructure.uuids;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Low level Java static utility code for bit-fiddling to construct UUIDs.
 * TODO: translate this to Kotlin when Kotlin adds unsigned bit fiddling capabilities
 */
public class UuidsJava {

    /** Static utility class. */
    private UuidsJava() {
        throw new UnsupportedOperationException( "Static utility class only." );
    }

    /**
     * Computes a clock sequence and node value.
     *
     * @return the low-order 64 bits of the UUID
     */
    private static long determineClockSeqAndNode() {

        // node
        byte[] macAddress = UuidsJava.determineMacAddress();
        long result = 0xFFL & macAddress[5];
        result |= ( 0xFFL & macAddress[4] ) << 8;
        result |= ( 0xFFL & macAddress[3] ) << 16;
        result |= ( 0xFFL & macAddress[2] ) << 24;
        result |= ( 0xFFL & macAddress[1] ) << 32;
        result |= ( 0xFFL & macAddress[0] ) << 40;

        // clock sequence - TBD: currently random; store & retrieve a value instead
        result |= (long) ( Math.random() * (double) 0x3FFF ) << 48;

        // reserved bits
        result |= 0x8000000000000000L;

        return result;

    }

    /**
     * Determines the MAC address of the host.
     *
     * @return six bytes of the MAC address
     */
    private static byte[] determineMacAddress() {

        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if ( networkInterfaces != null ) {
                while ( networkInterfaces.hasMoreElements() ) {
                    NetworkInterface networkInterface = networkInterfaces.nextElement();

                    byte[] result = networkInterface.getHardwareAddress();

                    if ( result != null && result.length == 6 && result[1] != (byte) 0xff ) {
                        return result;
                    }
                }
            }
        }
        catch ( SocketException ex ) {
            // ignore
        }

        return new byte[]{ (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5 };

    }

    /**
     * Computes the next time field from the current system time plus any counter increment needed.
     *
     * @param reservedTimeBlock If true, ensure that the next time returned after this one will be 256 * 100ns later
     *
     * @return the high 64 bits of the next UUID (version 1)
     */
    public static long getNextTimeAndVersion( boolean reservedTimeBlock ) {

        // retrieve system time (UTC)
        long timeMs = System.currentTimeMillis();

        // convert to 100ns units
        long time100ns = timeMs * 10000L;

        // convert to UUID time (from Gregorian start)
        time100ns += 0x01B21DD213814000L;

        // get the next time value to use, ensuring uniqueness and reserving 256 values when required
        while ( true ) {
            // for blocks of UUIDs use 00 as last byte after rounding up
            if ( reservedTimeBlock ) {
                time100ns += 0xFFL;
                time100ns &= 0xFFFFFFFFFFFFFF00L;
            }

            long last = UuidsJava.prevTime100ns.get();

            if ( time100ns > last ) {
                if ( reservedTimeBlock ) {
                    if ( UuidsJava.prevTime100ns.compareAndSet( last, time100ns + 0xFFL ) ) {
                        break;
                    }
                }
                else if ( UuidsJava.prevTime100ns.compareAndSet( last, time100ns ) ) {
                    break;
                }
            }
            else {
                // go around again with a time bigger than last
                time100ns = last + 1L;
            }
        }

        // time low
        long result = time100ns << 32;

        // time mid
        result |= ( time100ns & 0xFFFF00000000L ) >> 16;

        // time hi and version 1
        result |= time100ns >> 48 & 0x0FFFL;

        // version 1
        result |= 0x1000L;

        return result;

    }

    /**
     * The clock sequence and node value.
     */
    public static final long CLOCK_SEQ_AND_NODE = UuidsJava.determineClockSeqAndNode();

    /**
     * The last used time value. Tracks time but with atomic increments when needed to avoid duplicates.
     */
    private static AtomicLong prevTime100ns = new AtomicLong( Long.MIN_VALUE );

}
