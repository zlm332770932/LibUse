package com.java.lib.number;

/**
 * Create by limin on 2021/4/20.
 **/
public class ByteTool {

    /**
     * byte to int 强制转换，int 有正负
     * @param bt    byte
     * @return  int
     */
    public static int byteToInt(byte bt){
        return (int)bt;
    }

    /**
     * byte to unsigned int
     *
     * 与强转的区别：
     *      强转之后表示数值不变（不论正数、0、负数），如-120强转后还是-120，
     *      但是这种方式强转之后，正数和0也不变，但是负数会变成：
     *          输入值+2<sup>8</sup>，如 -120 转换后变为136，即-120+256
     *
     * @param bt byte
     * @return unsigned int
     */
    public static int byteToUnsignedInt(byte bt){
        return Byte.toUnsignedInt(bt);
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序
     *
     * @param bytes byte[]
     * @param offset 从数组的第offset位开始
     * @return int
     */
    public static int littleEndianBytesToInt(byte[] bytes, int offset){
        int value;
        value = (int) ((bytes[offset] & 0xFF)
                | ((bytes[offset+1] & 0xFF)<<8)
                | ((bytes[offset+2] & 0xFF)<<16)
                | ((bytes[offset+3] & 0xFF)<<24));
        return value;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序
     *
     * @param bytes byte[]
     * @param offset  从数组的第offset位开始
     * @return int
     */
    public static int bigEndianBytesToInt(byte[] bytes, int offset){
        int value;
        value = (int) ( ((bytes[offset] & 0xFF)<<24)
                |((bytes[offset+1] & 0xFF)<<16)
                |((bytes[offset+2] & 0xFF)<<8)
                |(bytes[offset+3] & 0xFF));
        return value;
    }

    /**
     * byte to short 强制转换，short 有正负
     * @param bt    byte
     * @return  short
     */
    public static short byteToShort(byte bt){
        return (short)bt;
    }

    /**
     * byte to unsigned short
     * @param bt    byte
     * @return  short
     */
    public static short byteToUnsignedShort(byte bt){
        return (short)(bt & 0x00FF);
    }

    /**
     * byte数组中取short数值，本方法适用于(低位在前，高位在后)的顺序
     *
     * @param bytes byte[]
     * @param offset    从数组的第offset位开始
     * @return short
     */
    public static short littleEndianBytesToShort(byte[] bytes, int offset){
        short value;
        value = (short) ((bytes[offset] & 0xFF)
                | ((bytes[offset+1] & 0xFF)<<8));
        return value;
    }

    /**
     * byte数组中取short数值，本方法适用于(低位在后，高位在前)的顺序
     *
     * @param bytes byte[]
     * @param offset  从数组的第offset位开始
     * @return int
     */
    public static short bigEndianBytesToShort(byte[] bytes, int offset){
        short value;
        value = (short) ( ((bytes[offset] & 0xFF)<<8)
                |((bytes[offset+1] & 0xFF)));
        return value;
    }


}
