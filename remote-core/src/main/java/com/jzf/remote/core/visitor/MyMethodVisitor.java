package com.jzf.remote.core.visitor;

import com.jzf.remote.core.util.Constants;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author jiazhifeng
 * @date 2021/12/1 9:45
 */
public class MyMethodVisitor extends MethodVisitor {
    private String className;

    public MyMethodVisitor(String className, MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
        this.className = className;
    }

    @Override
    public void visitFieldInsn(final int opcode, final String owner, final String name, final String descriptor) {
        if (opcode == Opcodes.GETSTATIC && owner.equals(Constants.SYSTEM_CLASS)) {
            mv.visitFieldInsn(Opcodes.GETSTATIC, className, "jzfSystem", "L" + Constants.SYSTEM_CLASS + ";");
            super.visitFieldInsn(Opcodes.GETFIELD, owner, name, descriptor);
        } else {
            super.visitFieldInsn(opcode, owner, name, descriptor);
        }
    }

    @Override
    public void visitInsn(int opcode) {
        if (opcode == Opcodes.RETURN) {
            mv.visitFieldInsn(Opcodes.GETSTATIC, className, "jzfSystem", "L" + Constants.SYSTEM_CLASS + ";");
            super.visitInsn(Opcodes.ARETURN);
        } else {
            super.visitInsn(opcode);
        }
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack + 1, maxLocals + 1);
    }
}