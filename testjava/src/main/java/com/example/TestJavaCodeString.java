package com.example;

import java.util.Random;
import java.util.logging.Logger;

/**
 * Package com.example.
 * Created by yaoh on 2016/11/11.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class TestJavaCodeString {


    public static void main(String args[]){

        System.out.print(toJavaCodeString("100"));
    }


     private static String toJavaCodeString(String string) {

         Random r = new Random(System.currentTimeMillis());

         byte[] b = string.getBytes();
         int c = b.length;
         StringBuffer sb = new StringBuffer();

         sb.append("new Object(){");
         sb.append("int t;");
         sb.append("public String toString() {");
         sb.append("byte[] buf = new byte[");
         sb.append(c);
         sb.append("];");

        for (int i = 0; i < c; ++i) {
            int t = r.nextInt();
            int f = r.nextInt(24) + 1;

            t = (t & ~(0xff << f)) | (b[i] << f);

            sb.append("t = ");
            sb.append(t);
            sb.append(";");
            sb.append("buf[");
            sb.append(i);
            sb.append("] = (byte) (t >>> ");
            sb.append(f);
            sb.append(");");
        }

        sb.append("return new String(buf);");
        sb.append("}}.toString()");

        return sb.toString();
    }
}
