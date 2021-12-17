package com.jzf.remote.core.util;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import org.apache.commons.codec.binary.CharSequenceUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

/**
 * @author jiazhifeng
 * @date 2021/12/17 15:35
 */
public class HexUtils {

    public static String bytesToBeautiful(byte[] bytes) {
        String hexStr = Hex.encodeHexString(bytes);
        int lineIndex = 0;
        boolean isLastLine = false;
        StringBuilder lineStr = new StringBuilder();
        lineStr.append("    \\    | 00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15\r\n");
        lineStr.append("     \\   | -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --\r\n");

        //替换不可见字符
        byte[] temp = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            if (b <= 0x20 || b == 0x7f){
                temp[i] = 0;
            }else{
                temp[i] = b;
            }
        }

        for (int startIndex = 0; startIndex < hexStr.length(); startIndex += 32) {
            int endIndex = startIndex + 32;
            if (endIndex > hexStr.length()) {
                endIndex = hexStr.length();
                isLastLine = true;
            }

            lineStr.append(String.format("%08x", lineIndex++)).append(" | ");
            int startPaddingMark = lineStr.length();
            for (int startOffset = 0; startOffset < (endIndex - startIndex); startOffset += 2) {
                int endOffset = startOffset + 2;
                if (endOffset > (endIndex - startIndex)) {
                    endOffset = (endIndex - startIndex);
                }
                lineStr.append(hexStr, startIndex + startOffset, startIndex + endOffset);
                lineStr.append(" ");
            }
            if (isLastLine) {
                int padding = 48 - (lineStr.length() - startPaddingMark);
                lineStr.append(StringUtils.rightPad(" ", padding));
            }
            lineStr.append("| ");
            lineStr.append(new String(temp, startIndex / 2, (endIndex - startIndex) / 2, StandardCharsets.UTF_8));
            lineStr.append("\r\n");
        }
        return lineStr.toString();
    }
}
