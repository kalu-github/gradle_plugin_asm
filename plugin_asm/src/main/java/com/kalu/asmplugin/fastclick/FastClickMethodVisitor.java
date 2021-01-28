package com.kalu.asmplugin.fastclick;

import com.kalu.asmplugin.base.BaseClassVisitor;
import com.kalu.asmplugin.base.BaseMethodVisitor;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * description: 继承自LocalVariablesSorter 有序遍历素有方法
 * created by kalu on 2021-01-19
 * <p>
 * https://blog.csdn.net/qq_33589510/article/details/105273233
 * <p>
 * <p>
 * visitAnnotationDefault?
 * ( visitAnnotation | visitParameterAnnotation | visitAttribute )\*
 * ( visitCode
 * ( visitTryCatchBlock | visitLabel | visitFrame | visitXxx Insn | visitLocalVariable | visitLineNumber ) \*
 * visitMaxs )?
 * visitEnd
 */
public class FastClickMethodVisitor extends BaseMethodVisitor {

    private long mTime = 2000;

    public FastClickMethodVisitor(BaseClassVisitor baseClassVisitor, MethodVisitor methodVisitor, int access, String descriptor, String methodName, String className) {
        super(baseClassVisitor, methodVisitor, access, descriptor, methodName, className);
        System.out.println("************************************************************");
        System.out.println("FastClickMethodVisitor[construct] => access = " + access + ", className = " + className + ", methodName = " + methodName + ", descriptor = " + descriptor + ", methodVisitor = " + methodVisitor);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        System.out.println("------------------------------------------------------------");
        System.out.println("FastClickMethodVisitor[visitAnnotation] => descriptor = " + descriptor + ", visible = " + visible);

        if (!"Lcom/kalu/asmplugin/annotation/FastClick;".equals(descriptor))
            return super.visitAnnotation(descriptor, visible);

        return new AnnotationVisitor(org.objectweb.asm.Opcodes.ASM4, super.visitAnnotation(descriptor, visible)) {
            @Override
            public void visit(String name, Object value) {
                super.visit(name, value);
                if ("time".equals(name) && value instanceof Long) {
                    mTime = (long) value;
                }
            }
        };
    }

    Label label7;
    Label label3;

    @Override
    protected void onMethodEnter() {
        System.out.println("------------------------------------------------------------");
        System.out.println("FastClickMethodVisitor[onMethodEnter] =>");
        super.onMethodEnter();

        // 统计方法耗时
        addTimeComsumingStart();

        if (!containsDescriptor("Lcom/kalu/asmplugin/annotation/FastClick;"))
            return;

        // 变更
        setChange();

        mv.visitCode();
        Label label0 = new Label();
        Label label1 = new Label();
        Label label2 = new Label();
        mv.visitTryCatchBlock(label0, label1, label2, "java/lang/Exception");
        mv.visitLdcInsn("asmclick");
        mv.visitLdcInsn("*************************************");
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);
        mv.visitLdcInsn("asmclick");
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("view = ");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);
        mv.visitInsn(ACONST_NULL);
        mv.visitVarInsn(ALOAD, 1);
        label3 = new Label();
        mv.visitJumpInsn(IF_ACMPEQ, label3);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "getContext", "()Landroid/content/Context;", false);
        mv.visitVarInsn(ASTORE, 2);
        mv.visitLdcInsn("asmclick");
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("context = ");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);
        mv.visitInsn(ACONST_NULL);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitJumpInsn(IF_ACMPEQ, label3);
        mv.visitInsn(ICONST_M1);
        mv.visitVarInsn(ISTORE, 3);
        mv.visitLabel(label0);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitMethodInsn(INVOKEVIRTUAL, "android/content/Context", "getResources", "()Landroid/content/res/Resources;", false);
        mv.visitLdcInsn("id_plugin_annotation_fastclick");
        mv.visitLdcInsn("id");
        mv.visitVarInsn(ALOAD, 2);
        mv.visitMethodInsn(INVOKEVIRTUAL, "android/content/Context", "getPackageName", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "android/content/res/Resources", "getIdentifier", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitVarInsn(ISTORE, 3);
        mv.visitLabel(label1);
        Label label4 = new Label();
        mv.visitJumpInsn(GOTO, label4);
        mv.visitLabel(label2);
        mv.visitFrame(Opcodes.F_FULL, 4, new Object[]{"com/kalu/plugin/test/TestClick", "android/view/View", "android/content/Context", Opcodes.INTEGER}, 1, new Object[]{"java/lang/Exception"});
        mv.visitVarInsn(ASTORE, 4);
        mv.visitLabel(label4);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitLdcInsn("asmclick");
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("identifier = ");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(ILOAD, 3);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);
        mv.visitVarInsn(ILOAD, 3);
        mv.visitInsn(ICONST_M1);
        mv.visitJumpInsn(IF_ICMPEQ, label3);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitVarInsn(ILOAD, 3);
        mv.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "getTag", "(I)Ljava/lang/Object;", false);
        mv.visitVarInsn(ASTORE, 6);
        mv.visitInsn(ACONST_NULL);
        mv.visitVarInsn(ALOAD, 6);
        Label label5 = new Label();
        mv.visitJumpInsn(IF_ACMPEQ, label5);
        mv.visitVarInsn(ALOAD, 6);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "parseLong", "(Ljava/lang/String;)J", false);
        mv.visitVarInsn(LSTORE, 4);
        Label label6 = new Label();
        mv.visitJumpInsn(GOTO, label6);
        mv.visitLabel(label5);
        mv.visitFrame(Opcodes.F_APPEND, 3, new Object[]{Opcodes.TOP, Opcodes.TOP, "java/lang/Object"}, 0, null);
        mv.visitInsn(LCONST_0);
        mv.visitVarInsn(LSTORE, 4);
        mv.visitLabel(label6);
        mv.visitFrame(Opcodes.F_FULL, 6, new Object[]{"com/kalu/plugin/test/TestClick", "android/view/View", "android/content/Context", Opcodes.INTEGER, Opcodes.LONG, "java/lang/Object"}, 0, new Object[]{});
        mv.visitLdcInsn("asmclick");
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("lastTime = ");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(LLOAD, 4);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitVarInsn(LSTORE, 7);
        mv.visitLdcInsn("asmclick");
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("timeMillis = ");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(LLOAD, 7);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);
        mv.visitVarInsn(LLOAD, 7);
        mv.visitVarInsn(LLOAD, 4);
        mv.visitInsn(LSUB);
        mv.visitVarInsn(LSTORE, 9);
        mv.visitLdcInsn("asmclick");
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("timeDistance = ");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(LLOAD, 9);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);
        mv.visitInsn(LCONST_0);
        mv.visitVarInsn(LLOAD, 9);
        mv.visitInsn(LCMP);
        label7 = new Label();
        mv.visitJumpInsn(IFGE, label7);
        mv.visitVarInsn(LLOAD, 9);
        mv.visitLdcInsn(new Long(mTime));
        mv.visitInsn(LCMP);
        mv.visitJumpInsn(IFLT, label7);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitVarInsn(ILOAD, 3);
        mv.visitVarInsn(LLOAD, 7);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "setTag", "(ILjava/lang/Object;)V", false);
        mv.visitLdcInsn("asmclick");
        mv.visitLdcInsn("\u901a\u8fc7");
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);
    }

    @Override
    protected void onMethodExit(int opcode) {

        if (containsDescriptor("Lcom/kalu/asmplugin/annotation/FastClick;")) {

            // 变更
            setChange();

            mv.visitJumpInsn(GOTO, label3);
            mv.visitLabel(label7);
            mv.visitFrame(Opcodes.F_APPEND, 2, new Object[]{Opcodes.LONG, Opcodes.LONG}, 0, null);
            mv.visitLdcInsn("asmclick");
            mv.visitLdcInsn("\u62e6\u622a");
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
            mv.visitLabel(label3);
            mv.visitFrame(Opcodes.F_FULL, 2, new Object[]{"com/kalu/plugin/test/TestClick", "android/view/View"}, 0, new Object[]{});
            mv.visitLdcInsn("asmclick");
            mv.visitLdcInsn("*************************************");
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);

            // 统计方法耗时
            addTimeComsumingClose();

            mv.visitInsn(RETURN);
            mv.visitMaxs(4, 11);
            mv.visitEnd();
        } else {

            // 统计方法耗时
            addTimeComsumingClose();
        }

        super.onMethodExit(opcode);
    }
}
