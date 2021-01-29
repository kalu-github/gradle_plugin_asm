package com.kalu.asmplugin.util;

import org.gradle.internal.impldep.org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.util.HashMap;
import java.util.Map;

import static groovyjarjarasm.asm.Opcodes.ACC_PUBLIC;
import static groovyjarjarasm.asm.Opcodes.ALOAD;
import static groovyjarjarasm.asm.Opcodes.ILOAD;
import static groovyjarjarasm.asm.Opcodes.INVOKESPECIAL;
import static groovyjarjarasm.asm.Opcodes.ISTORE;
import static org.objectweb.asm.Opcodes.ACONST_NULL;
import static org.objectweb.asm.Opcodes.ARRAYLENGTH;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.IALOAD;
import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.IFNE;
import static org.objectweb.asm.Opcodes.IF_ACMPEQ;
import static org.objectweb.asm.Opcodes.IF_ICMPGE;
import static org.objectweb.asm.Opcodes.IF_ICMPNE;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.SIPUSH;

/**
 * description: 运行时权限
 * created by kalu on 2021-01-26
 */
public class PluginPermissionVerificationUtil {

    public static void createOnRequestPermissionsResult(ClassVisitor classWriter, @NotNull HashMap<Integer, String> mMethodMap, @NotNull String className, @NotNull String superName) {

        if (null == mMethodMap || mMethodMap.size() == 0)
            return;

        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "onRequestPermissionsResult", "(I[Ljava/lang/String;[I)V", null, null);
        methodVisitor.visitCode();

        Label start = new Label();
        methodVisitor.visitLabel(start);

        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, superName, "onRequestPermissionsResult", "(I[Ljava/lang/String;[I)V", false);

        createPass(methodVisitor);

        createCallback(methodVisitor, mMethodMap, className, superName);

        methodVisitor.visitInsn(RETURN);

        Label end = new Label();
        methodVisitor.visitLabel(end);

        methodVisitor.visitLocalVariable("this", "L" + className + ";", null, start, end, 0);
        methodVisitor.visitLocalVariable("requestCode", "I", null, start, end, 1);
        methodVisitor.visitLocalVariable("permissions", "[Ljava/lang/String;", null, start, end, 2);
        methodVisitor.visitLocalVariable("grantResults", "[I", null, start, end, 3);

//        methodVisitor.visitMaxs(4, 4);
        methodVisitor.visitEnd();
    }

    private static void createPass(MethodVisitor methodVisitor) {

        methodVisitor.visitInsn(ICONST_1);
        methodVisitor.visitVarInsn(ISTORE, 4);
        methodVisitor.visitInsn(ACONST_NULL);
        methodVisitor.visitVarInsn(ALOAD, 2);
        Label label0 = new Label();
        methodVisitor.visitJumpInsn(IF_ACMPEQ, label0);
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitInsn(ARRAYLENGTH);
        methodVisitor.visitJumpInsn(IFEQ, label0);
        methodVisitor.visitInsn(ACONST_NULL);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitJumpInsn(IF_ACMPEQ, label0);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitInsn(ARRAYLENGTH);
        Label label1 = new Label();
        methodVisitor.visitJumpInsn(IFNE, label1);
        methodVisitor.visitLabel(label0);
        methodVisitor.visitInsn(ICONST_0);
        methodVisitor.visitVarInsn(ISTORE, 4);
        Label label2 = new Label();
        methodVisitor.visitJumpInsn(GOTO, label2);
        methodVisitor.visitLabel(label1);
        methodVisitor.visitInsn(ICONST_0);
        methodVisitor.visitVarInsn(ISTORE, 5);
        Label label3 = new Label();
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(ILOAD, 5);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitInsn(ARRAYLENGTH);
        methodVisitor.visitJumpInsn(IF_ICMPGE, label2);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ILOAD, 5);
        methodVisitor.visitInsn(IALOAD);
        Label label4 = new Label();
        methodVisitor.visitJumpInsn(IFEQ, label4);
        methodVisitor.visitInsn(ICONST_0);
        methodVisitor.visitVarInsn(ISTORE, 4);
        methodVisitor.visitJumpInsn(GOTO, label2);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitIincInsn(5, 1);
        methodVisitor.visitJumpInsn(GOTO, label3);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLdcInsn("asmpermission");
        methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        methodVisitor.visitLdcInsn("onRequestPermissionsResult => pass = ");
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        methodVisitor.visitVarInsn(ILOAD, 4);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Z)Ljava/lang/StringBuilder;", false);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        methodVisitor.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        methodVisitor.visitInsn(POP);

    }

    private static void createCallback(@NotNull MethodVisitor methodVisitor, @NotNull HashMap<Integer, String> map, @NotNull String className, @NotNull String superName) {

        for (Map.Entry<Integer, String> entry : map.entrySet()) {

            methodVisitor.visitVarInsn(ILOAD, 1);
            methodVisitor.visitIntInsn(SIPUSH, new Integer(entry.getKey()));

            Label label5 = new Label();
            methodVisitor.visitJumpInsn(IF_ICMPNE, label5);

            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitInsn(ICONST_1);
            methodVisitor.visitVarInsn(ILOAD, 4);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, className, entry.getValue(), "(Landroid/app/Activity;ZZ)V", false);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitLabel(label5);
        }
    }
}
