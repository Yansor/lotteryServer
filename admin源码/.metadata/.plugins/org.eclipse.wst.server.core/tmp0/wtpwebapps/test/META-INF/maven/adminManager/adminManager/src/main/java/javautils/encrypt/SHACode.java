package javautils.encrypt;

public class SHACode
{
    private static final long SH0 = 1732584193L;
    private static final long SH1 = 4023233417L;
    private static final long SH2 = 2562383102L;
    private static final long SH3 = 271733878L;
    private static final long SH4 = 3285377520L;
    private static final long K0 = 1518500249L;
    private static final long K1 = 1859775393L;
    private static final long K2 = 2400959708L;
    private static final long K3 = 3395469782L;
    private static long[] W;
    private static byte[] BW;
    private static int p0;
    private static int p1;
    private static int p2;
    private static int p3;
    private static int p4;
    private static long A;
    private static long B;
    private static long C;
    private static long D;
    private static long E;
    private static long temp;
    
    private static long f0(final long x, final long y, final long z) {
        return z ^ (x & (y ^ z));
    }
    
    private static long f1(final long x, final long y, final long z) {
        return x ^ y ^ z;
    }
    
    private static long f2(final long x, final long y, final long z) {
        return (x & y) | (z & (x | y));
    }
    
    private static long f3(final long x, final long y, final long z) {
        return x ^ y ^ z;
    }
    
    private static long S(final int n, long X) {
        long tem = 0L;
        X &= -1L;
        if (n == 1) {
            tem = (X >> 31 & 0x1L);
        }
        if (n == 5) {
            tem = (X >> 27 & 0x1FL);
        }
        if (n == 30) {
            tem = (X >> 2 & 0x3FFFFFFFL);
        }
        return X << n | tem;
    }
    
    private static void r0(final int f, final long K) {
        SHACode.temp = S(5, SHACode.A) + f0(SHACode.B, SHACode.C, SHACode.D) + SHACode.E + SHACode.W[SHACode.p0++] + K;
        SHACode.E = SHACode.D;
        SHACode.D = SHACode.C;
        SHACode.C = S(30, SHACode.B);
        SHACode.B = SHACode.A;
        SHACode.A = SHACode.temp;
    }
    
    private static void r1(final int f, final long K) {
        SHACode.temp = (SHACode.W[SHACode.p1++] ^ SHACode.W[SHACode.p2++] ^ SHACode.W[SHACode.p3++] ^ SHACode.W[SHACode.p4++]);
        switch (f) {
            case 0: {
                final long n = S(5, SHACode.A) + f0(SHACode.B, SHACode.C, SHACode.D) + SHACode.E;
                final long[] w = SHACode.W;
                final int n2 = SHACode.p0++;
                final long s = S(1, SHACode.temp);
                w[n2] = s;
                SHACode.temp = n + s + K;
                break;
            }
            case 1: {
                final long n3 = S(5, SHACode.A) + f1(SHACode.B, SHACode.C, SHACode.D) + SHACode.E;
                final long[] w2 = SHACode.W;
                final int n4 = SHACode.p0++;
                final long s2 = S(1, SHACode.temp);
                w2[n4] = s2;
                SHACode.temp = n3 + s2 + K;
                break;
            }
            case 2: {
                final long n5 = S(5, SHACode.A) + f2(SHACode.B, SHACode.C, SHACode.D) + SHACode.E;
                final long[] w3 = SHACode.W;
                final int n6 = SHACode.p0++;
                final long s3 = S(1, SHACode.temp);
                w3[n6] = s3;
                SHACode.temp = n5 + s3 + K;
                break;
            }
            case 3: {
                final long n7 = S(5, SHACode.A) + f3(SHACode.B, SHACode.C, SHACode.D) + SHACode.E;
                final long[] w4 = SHACode.W;
                final int n8 = SHACode.p0++;
                final long s4 = S(1, SHACode.temp);
                w4[n8] = s4;
                SHACode.temp = n7 + s4 + K;
                break;
            }
        }
        SHACode.E = SHACode.D;
        SHACode.D = SHACode.C;
        SHACode.C = S(30, SHACode.B);
        SHACode.B = SHACode.A;
        SHACode.A = SHACode.temp;
    }
    
    public static long getCode(final String mem) {
        int length = mem.toCharArray().length;
        int sp = 0;
        SHACode.W = new long[80];
        SHACode.BW = new byte[320];
        int padded = 0;
        char[] s = mem.toCharArray();
        long h0 = 1732584193L;
        long h2 = 4023233417L;
        long h3 = 2562383102L;
        long h4 = 271733878L;
        long h5 = 3285377520L;
        int hi_length;
        int lo_length = hi_length = 0;
        int nread;
        do {
            if (length < 64) {
                nread = length;
            }
            else {
                nread = 64;
            }
            length -= nread;
            for (int m = 0; m < nread; ++m) {
                SHACode.BW[m] = (byte)s[sp++];
            }
            if (nread < 64) {
                final int nbits = nread * 8;
                if ((lo_length += nbits) < (long)nbits) {
                    ++hi_length;
                }
                if (nread < 64 && padded == 0) {
                    SHACode.BW[nread++] = -128;
                    padded = 1;
                }
                for (int i = nread; i < 64; ++i) {
                    SHACode.BW[i] = 0;
                }
                final byte[] tar = new byte[4];
                for (int z = 0; z < 64; z += 4) {
                    for (int y = 0; y < 4; ++y) {
                        tar[y] = SHACode.BW[z + y];
                    }
                    SHACode.W[z >> 2] = 0L;
                    SHACode.W[z >> 2] = (SHACode.W[z >> 2] | (long)tar[0]) << 8;
                    SHACode.W[z >> 2] = (SHACode.W[z >> 2] | (long)(tar[1] & 0xFF)) << 8;
                    SHACode.W[z >> 2] = ((SHACode.W[z >> 2] | (long)(tar[2] & 0xFF)) << 8 | (long)(tar[3] & 0xFF));
                }
                if (nread <= 56) {
                    SHACode.W[14] = hi_length;
                    SHACode.W[15] = lo_length;
                }
            }
            else {
                lo_length += 512;
                if (lo_length < 512) {
                    ++hi_length;
                }
                final byte[] tar = new byte[4];
                for (int z = 0; z < 64; z += 4) {
                    for (int y = 0; y < 4; ++y) {
                        tar[y] = SHACode.BW[z + y];
                    }
                    SHACode.W[z >> 2] = 0L;
                    SHACode.W[z >> 2] = (SHACode.W[z >> 4] | (long)tar[0]) << 8;
                    SHACode.W[z >> 2] = (SHACode.W[z >> 2] | (long)(tar[1] & 0xFF)) << 8;
                    SHACode.W[z >> 2] = ((SHACode.W[z >> 2] | (long)(tar[2] & 0xFF)) << 8 | (long)(tar[3] & 0xFF));
                }
            }
            SHACode.p0 = 0;
            SHACode.A = h0;
            SHACode.B = h2;
            SHACode.C = h3;
            SHACode.D = h4;
            SHACode.E = h5;
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            r0(0, 1518500249L);
            SHACode.p1 = 13;
            SHACode.p2 = 8;
            SHACode.p3 = 2;
            r1(SHACode.p4 = 0, 1518500249L);
            r1(0, 1518500249L);
            r1(0, 1518500249L);
            r1(0, 1518500249L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(1, 1859775393L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(2, 2400959708L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            r1(3, 3395469782L);
            h0 += SHACode.A;
            h2 += SHACode.B;
            h3 += SHACode.C;
            h4 += SHACode.D;
            h5 += SHACode.E;
        } while (nread > 56);
        SHACode.W = null;
        SHACode.BW = null;
        s = null;
        SHACode.A = 0L;
        SHACode.B = 0L;
        SHACode.C = 0L;
        SHACode.D = 0L;
        SHACode.E = 0L;
        SHACode.temp = 0L;
        h5 = 0L;
        h4 = 0L;
        h3 = 0L;
        h2 = 0L;
        SHACode.p0 = 0;
        SHACode.p1 = 0;
        SHACode.p2 = 0;
        SHACode.p3 = 0;
        SHACode.p4 = 0;
        final int lowBit = (int)(h0 & 0xFFFFL);
        final int highBit = (int)(h0 >> 16 & 0xFFFFFL);
        h0 = 0L;
        String shaHigh = new String(Integer.toHexString(highBit));
        int leng = shaHigh.length();
        if (leng > 4) {
            shaHigh = shaHigh.substring(leng - 4);
        }
        else if (leng < 4) {
            for (int j = 0; j < 4 - leng; ++j) {
                shaHigh = "0" + shaHigh;
            }
        }
        String shaLow = new String(Integer.toHexString(lowBit));
        leng = shaLow.length();
        if (leng > 4) {
            shaLow = shaLow.substring(leng - 4);
        }
        else if (leng < 4) {
            for (int k = 0; k < 4 - leng; ++k) {
                shaLow = "0" + shaLow;
            }
        }
        return Long.parseLong(String.valueOf(shaHigh) + shaLow, 16);
    }
}
