package com.jzf.compiler.visitor;

import com.jzf.compiler.util.Constants;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author jiazhifeng
 * @date 2021/12/1 9:45
 */
public class MyClassVisitor extends ClassVisitor {
    private String className;

    public MyClassVisitor(String className, ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
        this.className = className;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);

        FieldVisitor fieldVisitor = cv.visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL,
                "jzfSystem", "L" + Constants.SYSTEM_CLASS + ";", null, null);
        fieldVisitor.visitEnd();

        MethodVisitor methodVisitor = cv.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);

        methodVisitor.visitCode();
        methodVisitor.visitTypeInsn(Opcodes.NEW, Constants.SYSTEM_CLASS);
        methodVisitor.visitInsn(Opcodes.DUP);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, Constants.SYSTEM_CLASS, "<init>", "()V", false);
        methodVisitor.visitFieldInsn(Opcodes.PUTSTATIC, className, "jzfSystem", "L" + Constants.SYSTEM_CLASS + ";");
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitMaxs(2, 2);
        methodVisitor.visitEnd();
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (name.equals("main")) {
            desc = "([Ljava/lang/String;)L" + Constants.SYSTEM_CLASS + ";";
        }
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("<init>") || name.equals("<clinit>")) {
            return methodVisitor;
        }
        return new MyMethodVisitor(className, methodVisitor);
    }
}