package com.kalu.asmplugin.permission;

import com.kalu.asmplugin.base.BaseClassVisitor;
import com.kalu.asmplugin.base.BaseMethodVisitor;
import com.kalu.asmplugin.util.PluginLogUtil;

import org.gradle.internal.impldep.org.jetbrains.annotations.NotNull;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;

/**
 * description: 运行时权限
 * created by kalu on 2021-01-26
 * <p>
 * https://blog.csdn.net/qq_33589510/article/details/105273233
 */
public class PermissionVerificationMethodVisitor extends BaseMethodVisitor {

    private boolean isVerification = false;

    private HashMap<Integer, String> mMethodMap;
    private HashMap<String, Object> map = new HashMap<>();

    public PermissionVerificationMethodVisitor(BaseClassVisitor baseClassVisitor, MethodVisitor methodVisitor, int access, String descriptor, String methodName, String className, @NotNull HashMap<Integer, String> mMethodMap) {
        super(baseClassVisitor, methodVisitor, access, descriptor, methodName, className);
        this.mMethodMap = mMethodMap;
        System.out.println("************************************************************");
        System.out.println("PermissionVerificationMethodVisitor[construct] => access = " + access + ", className = " + className + ", methodName = " + methodName + ", descriptor = " + descriptor + ", methodVisitor = " + methodVisitor);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        PluginLogUtil.log("------------------------------------------------------------");
        PluginLogUtil.log("PermissionVerificationMethodVisitor[visitAnnotation] => descriptor = " + descriptor + ", visible = " + visible);

        // 快速点击
        if ("Lcom/kalu/asmplugin/annotation/PermissionVerification;".equals(descriptor)) {
            PermissionVerificationAnnotationVisitor permissionVerificationAnnotationVisitor = new PermissionVerificationAnnotationVisitor(super.visitAnnotation(descriptor, visible), this.map);
            this.isVerification = true;
            return permissionVerificationAnnotationVisitor;
        }
        // 默认
        else {
            this.isVerification = false;
            return super.visitAnnotation(descriptor, visible);
        }
    }

//    @Override
//    protected void onMethodEnter() {
//        PluginLogUtil.log("------------------------------------------------------------");
//        PluginLogUtil.log("PermissionVerificationMethodVisitor[onMethodEnter] =>");
//        super.onMethodEnter();
//
//        String descriptor = getDescriptor();
//        PluginLogUtil.log("PermissionVerificationMethodVisitor[onMethodEnter] => descriptor = " + descriptor);
//
//        // 快速点击
//        if (!"Lcom/kalu/asmplugin/annotation/FastClick;".equals(descriptor))
//            return;
//
//        mv.visitVarInsn(ALOAD, 1);
//        mv.visitMethodInsn(INVOKESTATIC, "lib/kalu/fast/FastUtil", "intercept", "(Landroid/view/View;)Z", false);
//        Label l1 = new Label();
//        mv.visitJumpInsn(IFNE, l1);
//        mv.visitInsn(RETURN);
//        mv.visitLabel(l1);
//        mv.visitFrame(F_SAME, 0, null, 0, null);
//    }

    /**
     * 遍历代码的开始 声明一个局部变量
     */
    @Override
    public void visitCode() {
        PluginLogUtil.log("------------------------------------------------------------");
        PluginLogUtil.log("PermissionVerificationMethodVisitor[visitCode] =>");
        super.visitCode();

        PluginLogUtil.log("PermissionVerificationMethodVisitor[visitCode] => isVerification = " + this.isVerification);
        if (!isVerification)
            return;

        PluginLogUtil.log("PermissionVerificationMethodVisitor[visitCode] => map = " + this.map);
        if (null == this.map || this.map.size() == 0)
            return;

        Object object = this.map.getOrDefault("requestPermissions", null);
        PluginLogUtil.log("PermissionVerificationMethodVisitor[visitCode] => object = " + object);
        if (null == object || !(object instanceof Object[]))
            return;

        Object[] permissions = (Object[]) object;
        int requestCode = (int) this.map.getOrDefault("requestCode", -1);
        boolean isFragment = (boolean) this.map.getOrDefault("isFragment", false);
        PluginLogUtil.log("PermissionVerificationMethodVisitor[visitCode] => requestCode = " + requestCode);
        PluginLogUtil.log("PermissionVerificationMethodVisitor[visitCode] => requestPermissions = " + permissions.toString());
        PluginLogUtil.log("PermissionVerificationMethodVisitor[visitCode] => isFragment = " + isFragment);

        if (null == permissions || permissions.length == 0 || requestCode == -1)
            return;
        PluginLogUtil.log("PermissionVerificationMethodVisitor[visitCode] => length = " + permissions.length);

        // create
        create(isFragment, requestCode, permissions);
    }

    private void create(boolean isFragment, int requestCode, Object[] permissions) {

        // TODO: 2021-01-26
        mMethodMap.remove(requestCode);
        mMethodMap.put(requestCode, getMethodName());

        Label l0 = new Label();
        mv.visitLabel(l0);

        mv.visitVarInsn(ALOAD, 2);
        Label l1 = new Label();
        mv.visitJumpInsn(IFNE, l1);

        Label l4 = new Label();
        mv.visitLabel(l4);

        mv.visitInsn(RETURN);
        mv.visitLabel(l1);

        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//            mv.visitInsn(RETURN);
//            Label l5 = new Label();
//            mv.visitLabel(l5);
    }

//    private void create( boolean isFragment, int requestCode, Object[] permissions){
//
//        // TODO: 2021-01-26
//        mMethodMap.remove(requestCode);
//        mMethodMap.put(requestCode, method);
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
//        PluginLogUtil.log("PermissionVerificationMethodVisitor[visitCode] => requestCode = " + requestCode);
//        mv.visitIntInsn(SIPUSH, requestCode);
//
//        if (isFragment) {
//            mv.visitMethodInsn(INVOKEVIRTUAL, clazz, "requestPermissions", "([Ljava/lang/String;I)V", false);
//        } else {
//            mv.visitMethodInsn(INVOKESTATIC, "androidx/core/app/ActivityCompat", "requestPermissions", "(Landroid/app/Activity;[Ljava/lang/String;I)V", false);
//        }
//        Label l4 = new Label();
//        mv.visitLabel(l4);
//
//        mv.visitInsn(RETURN);
//        mv.visitLabel(l1);
//
//        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
////            mv.visitInsn(RETURN);
////            Label l5 = new Label();
////            mv.visitLabel(l5);
//    }
}
