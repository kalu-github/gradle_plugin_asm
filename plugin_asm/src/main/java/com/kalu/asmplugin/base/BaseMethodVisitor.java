package com.kalu.asmplugin.base;

import com.kalu.asmplugin.impl.ImplMethodVisitor;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

import java.util.ArrayList;

/**
 * description: 处理方法耗时
 * created by kalu on 2021-01-26
 */
public class BaseMethodVisitor extends AdviceAdapter implements ImplMethodVisitor {

    private String className;
    private String methodName;

    private BaseClassVisitor baseClassVisitor;
    private ArrayList<String> list = new ArrayList<>();

    public BaseMethodVisitor(BaseClassVisitor baseClassVisitor, MethodVisitor methodVisitor, int access, String descriptor, String methodName, String className) {
        super(ASM4, methodVisitor, access, methodName, descriptor);
        this.baseClassVisitor = baseClassVisitor;
        this.methodName = methodName;
        this.className = className;
    }

    /**
     * @param descriptor 最先执行 判断是否存在注解 如果存在 就进行插桩
     * @param visible    是否运行时可见
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        System.out.println("------------------------------------------------------------");
        System.out.println("BaseMethodVisitor[visitAnnotation] => methodName = " + getMethodName() + ", descriptor = " + descriptor);

        if (!this.list.contains(descriptor)) {
            this.list.add(descriptor);
        }
        return super.visitAnnotation(descriptor, visible);
    }

    protected void addTimeComsumingStart() {

        if (this.list.contains("Lcom/kalu/asmplugin/annotation/TimeConsuming;")) {

            // 变更
            setChange();

//            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
//            int newLocal = newLocal(Type.LONG_TYPE);
//            mv.visitVarInsn(LSTORE, newLocal);

            // long startTime = System.currentTimeMillis();
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(LSTORE, 1002);

            // Log.d("asmtime", "methodName" + "[+" + "className" + "] => startTime" + startTime);
            mv.visitLdcInsn("asmtime");
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn(methodName + "[" + className + "] => startTime = ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(LLOAD, 1002);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
        }
    }

    protected void addTimeComsumingClose() {

        if (this.list.contains("Lcom/kalu/asmplugin/annotation/TimeConsuming;")) {

            // 变更
            setChange();

            // long closeTime = System.nanoTime();
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(LSTORE, 1004);

            // Log.d("asmtime", "methodName" + "[+" + "className" + "] => closeTime" + closeTime);
            mv.visitLdcInsn("asmtime");
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn(methodName + "[" + className + "] => closeTime = ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(LLOAD, 1004);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);

            // long costTime = closeTime - startTime;
            mv.visitVarInsn(LLOAD, 1004);
            mv.visitVarInsn(LLOAD, 1002);
            mv.visitInsn(LSUB);
            mv.visitVarInsn(LSTORE, 1006);

            // Log.d("asmtime", "methodName" + "[+" + "className" + "] => costTime" + costTime+"ms);
            mv.visitLdcInsn("asmtime");
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn(methodName + "[" + className + "] => costTime = ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(LLOAD, 1006);
            mv.visitInsn(L2F);
            mv.visitLdcInsn(new Float("1000.0"));
            mv.visitInsn(FDIV);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(F)Ljava/lang/StringBuilder;", false);
            mv.visitLdcInsn("ms");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setChange() {

        if (null == this.baseClassVisitor)
            return;

        this.baseClassVisitor.setChange(true);
    }

    @Override
    public boolean containsDescriptor(String descriptor) {
        return this.list.contains(descriptor);
    }
}
