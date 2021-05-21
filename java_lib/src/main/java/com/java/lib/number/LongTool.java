package com.java.lib.number;

/**
 * Create by limin on 2021/5/19.
 **/
public class LongTool {

    /**
     * 将long数值转换为占八个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。
     *
     * @param value
     * @return byte[]
     */
    public static byte[] longToLittleEndianBytes(long value){
        byte[] src = new byte[8];
        src[7] =  (byte) ((value>>56) & 0xFF);
        src[6] =  (byte) ((value>>48) & 0xFF);
        src[5] =  (byte) ((value>>40) & 0xFF);
        src[4] =  (byte) ((value>>32) & 0xFF);
        src[3] =  (byte) ((value>>24) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将long数值转换为占八个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。
     *
     * @param value
     * @return byte[]
     */
    public static byte[] longToBigEndianBytes(long value){
        byte[] src = new byte[8];
        src[0] = (byte) ((value>>56) & 0xFF);
        src[1] = (byte) ((value>>48) & 0xFF);
        src[2] = (byte) ((value>>40) & 0xFF);
        src[3] = (byte) ((value>>32) & 0xFF);
        src[4] = (byte) ((value>>24) & 0xFF);
        src[5] = (byte) ((value>>16)& 0xFF);
        src[6] = (byte) ((value>>8)&0xFF);
        src[7] = (byte) (value & 0xFF);
        return src;
    }
}
