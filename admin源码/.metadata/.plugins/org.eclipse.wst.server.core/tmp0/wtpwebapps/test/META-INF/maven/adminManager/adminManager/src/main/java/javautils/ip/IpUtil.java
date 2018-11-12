package javautils.ip;

import java.io.IOException;
import java.nio.ByteOrder;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;
import java.io.File;
import java.nio.ByteBuffer;

public class IpUtil
{
    public static boolean enableFileWatch;
    private static int offset;
    private static int[] index;
    private static ByteBuffer dataBuffer;
    private static ByteBuffer indexBuffer;
    private static Long lastModifyTime;
    private static File ipFile;
    private static ReentrantLock lock;
    
    static {
        IpUtil.enableFileWatch = false;
        IpUtil.index = new int[256];
        IpUtil.lastModifyTime = 0L;
        IpUtil.lock = new ReentrantLock();
    }
    
    public static void main(final String[] args) {
        load("D:\\17monipdb.dat");
        System.out.println(Arrays.toString(find("180.191.100.73")));
    }
    
    public static void load(final String filename) {
        IpUtil.ipFile = new File(filename);
        load();
        if (IpUtil.enableFileWatch) {
            watch();
        }
    }
    
    public static String[] find(final String ip) {
        final int ip_prefix_value = new Integer(ip.substring(0, ip.indexOf(".")));
        final long ip2long_value = ip2long(ip);
        int start = IpUtil.index[ip_prefix_value];
        final int max_comp_len = IpUtil.offset - 1028;
        long index_offset = -1L;
        int index_length = -1;
        final byte b = 0;
        for (start = start * 8 + 1024; start < max_comp_len; start += 8) {
            if (int2long(IpUtil.indexBuffer.getInt(start)) >= ip2long_value) {
                index_offset = bytesToLong(b, IpUtil.indexBuffer.get(start + 6), IpUtil.indexBuffer.get(start + 5), IpUtil.indexBuffer.get(start + 4));
                index_length = (0xFF & IpUtil.indexBuffer.get(start + 7));
                break;
            }
        }
        IpUtil.lock.lock();
        byte[] areaBytes;
        try {
            IpUtil.dataBuffer.position(IpUtil.offset + (int)index_offset - 1024);
            areaBytes = new byte[index_length];
            IpUtil.dataBuffer.get(areaBytes, 0, index_length);
        }
        finally {
            IpUtil.lock.unlock();
        }
        IpUtil.lock.unlock();
        return new String(areaBytes).split("\t");
    }
    
    private static void watch() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                final long time = IpUtil.ipFile.lastModified();
                if (time > IpUtil.lastModifyTime) {
                    IpUtil.access$2(time);
                    load();
                }
            }
        }, 1000L, 5000L, TimeUnit.MILLISECONDS);
    }
    
    private static void load() {
        IpUtil.lastModifyTime = IpUtil.ipFile.lastModified();
        FileInputStream fin = null;
        IpUtil.lock.lock();
        try {
            IpUtil.dataBuffer = ByteBuffer.allocate((int)IpUtil.ipFile.length());
            fin = new FileInputStream(IpUtil.ipFile);
            final byte[] chunk = new byte[4096];
            while (fin.available() > 0) {
                final int readBytesLength = fin.read(chunk);
                IpUtil.dataBuffer.put(chunk, 0, readBytesLength);
            }
            IpUtil.dataBuffer.position(0);
            final int indexLength = IpUtil.dataBuffer.getInt();
            final byte[] indexBytes = new byte[indexLength];
            IpUtil.dataBuffer.get(indexBytes, 0, indexLength - 4);
            (IpUtil.indexBuffer = ByteBuffer.wrap(indexBytes)).order(ByteOrder.LITTLE_ENDIAN);
            IpUtil.offset = indexLength;
            int loop = 0;
            while (loop++ < 256) {
                IpUtil.index[loop - 1] = IpUtil.indexBuffer.getInt();
            }
            IpUtil.indexBuffer.order(ByteOrder.BIG_ENDIAN);
        }
        catch (IOException ex) {
            return;
        }
        finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            }
            catch (IOException ex2) {}
            IpUtil.lock.unlock();
        }
        try {
            if (fin != null) {
                fin.close();
            }
        }
        catch (IOException ex3) {}
        IpUtil.lock.unlock();
    }
    
    private static long bytesToLong(final byte a, final byte b, final byte c, final byte d) {
        return int2long((a & 0xFF) << 24 | (b & 0xFF) << 16 | (c & 0xFF) << 8 | (d & 0xFF));
    }
    
    private static int str2Ip(final String ip) {
        final String[] ss = ip.split("\\.");
        final int a = Integer.parseInt(ss[0]);
        final int b = Integer.parseInt(ss[1]);
        final int c = Integer.parseInt(ss[2]);
        final int d = Integer.parseInt(ss[3]);
        return a << 24 | b << 16 | c << 8 | d;
    }
    
    private static long ip2long(final String ip) {
        return int2long(str2Ip(ip));
    }
    
    private static long int2long(final int i) {
        long l = (long)i & 0x7FFFFFFFL;
        if (i < 0) {
            l |= 0x80000000L;
        }
        return l;
    }
    
    static /* synthetic */ void access$2(final Long lastModifyTime) {
        IpUtil.lastModifyTime = lastModifyTime;
    }
}
