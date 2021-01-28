package com.kalu.asmplugin.util;

import org.gradle.internal.impldep.org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static groovyjarjarasm.asm.Opcodes.ACC_PUBLIC;
import static groovyjarjarasm.asm.Opcodes.ALOAD;
import static groovyjarjarasm.asm.Opcodes.ILOAD;
import static groovyjarjarasm.asm.Opcodes.INVOKESPECIAL;
import static groovyjarjarasm.asm.Opcodes.ISTORE;
import static groovyjarjarasm.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.ARRAYLENGTH;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.IALOAD;
import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.IF_ICMPGE;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.POP;

/**
 * description: 运行时权限
 * created by kalu on 2021-01-26
 */
public class PluginPermissionVerificationUtil {

    public static void createOnRequestPermissionsResult(@NotNull ClassVisitor classWriter, @NotNull HashMap<Integer, String> mMethodMap, @NotNull String className, @NotNull String superName) {

        if (null == mMethodMap || mMethodMap.size() == 0)
            return;

        System.out.println("createRequestPermissionsResultMethodMultiple methodMap.size=" + mMethodMap.size());
        List<Integer> mMethodList = new ArrayList<>();
        for (Map.Entry<Integer, String> i : mMethodMap.entrySet()) {
            mMethodList.add(i.getKey());
        }
        mMethodList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                if (integer > t1) {
                    return 1;
                } else if (integer < t1) {
                    return -1;
                }
                return 0;
            }
        });
        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "onRequestPermissionsResult", "(I[Ljava/lang/String;[I)V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);

        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ILOAD, 1);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitVarInsn(ALOAD, 3);
        mv.visitMethodInsn(INVOKESPECIAL, superName, "onRequestPermissionsResult", "(I[Ljava/lang/String;[I)V", false);

        Label l1 = new Label();
        mv.visitLabel(l1);

        createResult(mv);

        createCallback(mv, mMethodMap, mMethodList, className, superName);

        mv.visitInsn(RETURN);
        Label l9 = new Label();
        mv.visitLabel(l9);
        mv.visitLocalVariable("this", "L" + className + ";", null, l0, l9, 0);
        mv.visitLocalVariable("requestCode", "I", null, l0, l9, 1);
        mv.visitLocalVariable("permissions", "[Ljava/lang/String;", null, l0, l9, 2);
        mv.visitLocalVariable("grantResults", "[I", null, l0, l9, 3);
        mv.visitMaxs(4, 4);
        mv.visitEnd();
    }

    private static void createResult(MethodVisitor methodVisitor) {

        // boolean
        methodVisitor.visitInsn(ICONST_1);

        // boolean var = false
        methodVisitor.visitVarInsn(ISTORE, 4);
        methodVisitor.visitInsn(ICONST_0);

        methodVisitor.visitVarInsn(ISTORE, 5);
        Label label0 = new Label();
        methodVisitor.visitLabel(label0);
        methodVisitor.visitFrame(Opcodes.F_APPEND, 2, new Object[]{Opcodes.INTEGER, Opcodes.INTEGER}, 0, null);
        methodVisitor.visitVarInsn(ILOAD, 5);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitInsn(ARRAYLENGTH);
        Label label1 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPGE, label1);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ILOAD, 5);
        methodVisitor.visitInsn(IALOAD);
        Label label2 = new Label();
        methodVisitor.visitJumpInsn(IFEQ, label2);
        methodVisitor.visitInsn(ICONST_0);
        methodVisitor.visitVarInsn(ISTORE, 4);
        methodVisitor.visitJumpInsn(GOTO, label1);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        methodVisitor.visitIincInsn(5, 1);
        methodVisitor.visitJumpInsn(GOTO, label0);
        methodVisitor.visitLabel(label1);
        methodVisitor.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);

        // log
//        methodVisitor.visitLdcInsn("kalu");
//        methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
//        methodVisitor.visitInsn(DUP);
//        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
//        methodVisitor.visitVarInsn(ILOAD, 4);
//        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Z)Ljava/lang/StringBuilder;", false);
//        methodVisitor.visitLdcInsn("");
//        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
//        methodVisitor.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
//        methodVisitor.visitInsn(POP);
    }

    private static void createCallback(@NotNull MethodVisitor mv, @NotNull HashMap<Integer, String> mMethodMap, @NotNull List<Integer> mMethodList, @NotNull String className, @NotNull String superName) {

        mv.visitVarInsn(ILOAD, 1);
        Label[] labels = new Label[mMethodMap.size()];

        for (int i = 0; i < labels.length; i++) {
            Label label = new Label();
            labels[i] = label;

        }

        Label l5 = new Label();

        mv.visitTableSwitchInsn(mMethodList.get(0), mMethodList.get(mMethodList.size() - 1), l5, labels);


        for (int i = 0; i < labels.length; i++) {

            mv.visitLabel(labels[i]);

            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 0);
            if (superName.contains("Fragment")) {
                mv.visitMethodInsn(INVOKEVIRTUAL, className, "getActivity", "()Landroidx/fragment/app/FragmentActivity;", false);
            }
//            mv.visitVarInsn(ILOAD, 4);
//            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);

            mv.visitInsn(ICONST_1);
            mv.visitVarInsn(ILOAD, 4);
//            mv.visitInsn(ICONST_1);

            mv.visitMethodInsn(INVOKEVIRTUAL, className, mMethodMap.get(mMethodList.get(i)), "(Landroid/app/Activity;ZZ)V", false);


            Label l7 = new Label();
            mv.visitLabel(l7);

            mv.visitJumpInsn(GOTO, l5);
        }


        mv.visitLabel(l5);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

    }
}
