package com.java.lib.number;

/**
 * Create by limin on 2021/5/19.
 **/
public class ShortTool {

    /**
     * 将short数值转换为占两个个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。
     *
     * @param value
     * @return byte[]
     */
    public static byte[] shortToLittleEndianBytes(short value){
        byte[] src = new byte[2];
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将short数值转换为占两个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。
     *
     * @param value
     * @return byte[]
     */
    public static byte[] shortToBigEndianBytes(short value){
        byte[] src = new byte[2];
        src[0] = (byte) ((value>>8)&0xFF);
        src[1] = (byte) (value & 0xFF);
        return src;
    }
}
