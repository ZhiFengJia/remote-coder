package com.jzf.compiler;

/**
 * @author jiazhifeng
 * @date 2021/11/24 12:44
 */
public class DynamicCompilerTest {

    public static void main(String[] args) throws Exception {
        String name = "DynamicClass";
        String sourceCode = "public class DynamicClass {\n" +
                "public static void main(String[] args) {\n" +
                "        System.out.println(\"hello jzf\");\n" +
                "        System.out.println(\"hello jzf2\");\n" +
                "        String name = \"testStr2\";\n" +
                "        System.out.println(name);\n" +
                "    }" +
                "}\n";
        String result = JavaFileExecuter.execute(name, sourceCode);
        System.out.println(result);
    }
}