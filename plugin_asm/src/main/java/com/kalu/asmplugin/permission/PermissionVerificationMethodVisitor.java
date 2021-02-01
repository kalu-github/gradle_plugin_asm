package com.kalu.asmplugin.permission;

import com.kalu.asmplugin.base.BaseAnnotationVisitor;
import com.kalu.asmplugin.base.BaseClassVisitor;
import com.kalu.asmplugin.base.BaseMethodVisitor;
import com.kalu.asmplugin.util.PluginLogUtil;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description: 运行时权限
 * created by kalu on 2021-01-26
 * <p>
 * https://blog.csdn.net/qq_33589510/article/details/105273233
 */
public class PermissionVerificationMethodVisitor extends BaseMethodVisitor {

    private int requestCode = -1;
    private String requestCall = null;
    private String[] requestPermissions = null;

    public PermissionVerificationMethodVisitor(BaseClassVisitor baseClassVisitor, MethodVisitor methodVisitor, int access, String descriptor, String methodName, String className) {
        super(baseClassVisitor, methodVisitor, access, descriptor, methodName, className);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        System.out.println("------------------------------------------------------------");
        System.out.println("PermissionVerificationMethodVisitor[visitAnnotation] => descriptor = " + descriptor + ", visible = " + visible);

        if (!"Lcom/kalu/asmplugin/annotation/PermissionVerification;".equals(descriptor))
            return super.visitAnnotation(descriptor, visible);

        return new BaseAnnotationVisitor(super.visitAnnotation(descriptor, visible)) {

            @Override
            public void visit(String name, Object value) {
                super.visit(name, value);

                PluginLogUtil.log("PermissionVerificationMethodVisitor[BaseAnnotationVisitor-visit] => name = " + name + ", value = " + value);

                // requestCode
                if ("requestCode".equals(name) && null != value && value instanceof Integer) {
                    requestCode = (int) value;
                }
                // requestCall
                else if ("requestCall".equals(name) && null != value && value instanceof org.objectweb.asm.Type) {
                    requestCall = value.toString();
                }
            }

            @Override
            public AnnotationVisitor visitArray(String name) {

                return new BaseAnnotationVisitor(super.visitArray(name)) {

                    @Override
                    public void visit(String name, Object value) {
                        super.visit(name, value);

                        PluginLogUtil.log("PermissionVerificationMethodVisitor[BaseAnnotationVisitor-visitArray-visit] => name = " + name + ", value = " + value);

                        // requestPermissions
                        if (null == name && value instanceof String) {

                            if (null == requestPermissions) {
                                requestPermissions = new String[]{(String) value};
                            } else {
                                List<String> olds = Arrays.asList(requestPermissions);
                                ArrayList<String> news = new ArrayList<>(olds.size() + 1);
                                news.addAll(olds);
                                news.add((String) value);
                                requestPermissions = news.toArray(new String[0]);
                            }
                        }
                    }
                };
            }
        };
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();

        PluginLogUtil.log("PermissionVerificationMethodVisitor[onMethodEnter] => requestCode = " + requestCode);
        PluginLogUtil.log("PermissionVerificationMethodVisitor[onMethodEnter] => requestCall = " + requestCall);
        PluginLogUtil.log("PermissionVerificationMethodVisitor[onMethodEnter] => requestPermissions = " + Arrays.toString(requestPermissions));

        if (requestCode != -1 && null != requestPermissions && requestPermissions.length > 0 && null != requestCall && requestCall.length() != 0 && containsDescriptor("Lcom/kalu/asmplugin/annotation/PermissionVerification;")) {

            // 变更
            setChange();

            mv.visitCode();
            mv.visitInsn(ICONST_2);
            mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);
            mv.visitLdcInsn("android.permission.CAMERA");
            mv.visitInsn(AASTORE);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_1);
            mv.visitLdcInsn("android.permission.WRITE_EXTERNAL_STORAGE");
            mv.visitInsn(AASTORE);
            mv.visitVarInsn(ASTORE, 7);
            mv.visitInsn(ICONST_1);
            mv.visitVarInsn(ISTORE, 8);
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ISTORE, 9);
            Label label0 = new Label();
            mv.visitLabel(label0);
            mv.visitFrame(Opcodes.F_APPEND, 3, new Object[]{"[Ljava/lang/String;", Opcodes.INTEGER, Opcodes.INTEGER}, 0, null);
            mv.visitVarInsn(ILOAD, 9);
            mv.visitVarInsn(ALOAD, 7);
            mv.visitInsn(ARRAYLENGTH);
            Label label1 = new Label();
            mv.visitJumpInsn(IF_ICMPGE, label1);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ALOAD, 7);
            mv.visitVarInsn(ILOAD, 9);
            mv.visitInsn(AALOAD);
            mv.visitMethodInsn(INVOKESTATIC, "androidx/core/content/ContextCompat", "checkSelfPermission", "(Landroid/content/Context;Ljava/lang/String;)I", false);
            mv.visitVarInsn(ISTORE, 10);
            mv.visitLdcInsn("asmpermission");
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("checkSelfPermission => permission");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(ALOAD, 7);
            mv.visitVarInsn(ILOAD, 9);
            mv.visitInsn(AALOAD);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitLdcInsn(", status = ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(ILOAD, 10);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
            mv.visitVarInsn(ILOAD, 10);
            Label label2 = new Label();
            mv.visitJumpInsn(IFEQ, label2);
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ISTORE, 8);
            mv.visitJumpInsn(GOTO, label1);
            mv.visitLabel(label2);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitIincInsn(9, 1);
            mv.visitJumpInsn(GOTO, label0);
            mv.visitLabel(label1);
            mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
            mv.visitLdcInsn("asmpermission");
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("checkSelfPermission => isCall = ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Z)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
            mv.visitLdcInsn("asmpermission");
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("checkSelfPermission => isAgree = ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(ILOAD, 8);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Z)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
            mv.visitLdcInsn("asmpermission");
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("checkSelfPermission => isPass = ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Z)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
            mv.visitVarInsn(ILOAD, 8);
            mv.visitVarInsn(ISTORE, 3);
            mv.visitLdcInsn("asmpermission");
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("checkSelfPermission => isPass = ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Z)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
            mv.visitVarInsn(ILOAD, 2);
            Label label3 = new Label();
            mv.visitJumpInsn(IFNE, label3);
            mv.visitVarInsn(ILOAD, 8);
            mv.visitJumpInsn(IFNE, label3);

            mv.visitVarInsn(ALOAD, 1);
//            // fragment
//            if (requestCall.contains("Fragment")) {
//                mv.visitMethodInsn(INVOKEVIRTUAL, requestCall, "getActivity", "()Landroidx/fragment/app/FragmentActivity;", false);
//            }

            mv.visitVarInsn(ALOAD, 7);
            mv.visitIntInsn(SIPUSH, requestCode);
            // fragment
            if (requestCall.contains("Fragment")) {
                mv.visitMethodInsn(INVOKEVIRTUAL, requestCall, "requestPermissions", "([Ljava/lang/String;I)V", false);
            }
            // activity
            else {
                mv.visitMethodInsn(INVOKESTATIC, "androidx/core/app/ActivityCompat", "requestPermissions", "(Landroid/app/Activity;[Ljava/lang/String;I)V", false);
            }
            mv.visitInsn(RETURN);
            mv.visitLabel(label3);
        }
    }

    @Override
    protected void onMethodExit(int opcode) {


        super.onMethodExit(opcode);
    }

    //    private void create(boolean isFragment, int requestCode, String[] permissions) {
//
//        Label l0 = new Label();
//        mv.visitLabel(l0);
//
//        mv.visitVarInsn(ALOAD, 2);
//        Label l1 = new Label();
//        mv.visitJumpInsn(IFNE, l1);
//
//        mv.visitVarInsn(ALOAD, 1);
//        mv.visitLdcInsn(permissions[0]);
//        mv.visitMethodInsn(INVOKESTATIC, "androidx/core/content/ContextCompat", "checkSelfPermission", "(Landroid/content/Context;Ljava/lang/String;)I", false);
//        if (permissions.length > 1) {
//            Label l2 = new Label();
//            mv.visitJumpInsn(IFNE, l2);
//            for (int i = 1; i < permissions.length; i++) {
//                mv.visitVarInsn(ALOAD, 1);
//                mv.visitLdcInsn(permissions[i]);
//                Label l = new Label();
//                mv.visitLabel(l);
//                mv.visitMethodInsn(INVOKESTATIC, "androidx/core/content/ContextCompat", "checkSelfPermission", "(Landroid/content/Context;Ljava/lang/String;)I", false);
//                if (i == permissions.length - 1) {
//                    mv.visitJumpInsn(IFEQ, l1);
//                    mv.visitLabel(l2);
//                    mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//                } else {
//                    mv.visitJumpInsn(IFNE, l2);
//                }
//            }
//        } else {
//            mv.visitJumpInsn(IFEQ, l1);
//            Label l2 = new Label();
//            mv.visitLabel(l2);
//        }
//
//
//        mv.visitVarInsn(ALOAD, isFragment ? 0 : 1);
//        switch (permissions.length) {
//            case 1:
//                mv.visitInsn(ICONST_1);
//                break;
//            case 2:
//                mv.visitInsn(ICONST_2);
//                break;
//            case 3:
//                mv.visitInsn(ICONST_3);
//                break;
//            case 4:
//                mv.visitInsn(ICONST_4);
//                break;
//
//        }
//        mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
//        for (int i = 0; i < permissions.length; i++) {
//            mv.visitInsn(DUP);
//            switch (i) {
//                case 0:
//                    mv.visitInsn(ICONST_0);
//                    break;
//                case 1:
//                    mv.visitInsn(ICONST_1);
//                    break;
//                case 2:
//                    mv.visitInsn(ICONST_2);
//                    break;
//                case 3:
//                    mv.visitInsn(ICONST_3);
//                    break;
//                case 4:
//                    mv.visitInsn(ICONST_4);
//                    break;
//
//            }
//
//            mv.visitLdcInsn(permissions[i]);
//            mv.visitInsn(AASTORE);
//        }
//
//        PluginLogUtil.log("PermissionVerificationMethodVisitor[visitCode] => requestCode = " + requestCode);
//        mv.visitIntInsn(SIPUSH, requestCode);
//
//        if (isFragment) {
//            mv.visitMethodInsn(INVOKEVIRTUAL, callName, "requestPermissions", "([Ljava/lang/String;I)V", false);
//        } else {
//            mv.visitMethodInsn(INVOKESTATIC, "androidx/core/app/ActivityCompat", "requestPermissions", "(Landroid/app/Activity;[Ljava/lang/String;I)V", false);
//        }
//        Label l4 = new Label();
//        mv.visitLabel(l4);
//
//        mv.visitInsn(RETURN);
//        mv.visitLabel(l1);
//
//    //    mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
////            mv.visitInsn(RETURN);
////            Label l5 = new Label();
////            mv.visitLabel(l5);
//    }
}
