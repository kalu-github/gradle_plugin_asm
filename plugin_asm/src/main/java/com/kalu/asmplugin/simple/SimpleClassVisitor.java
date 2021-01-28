package com.kalu.asmplugin.simple;

import com.kalu.asmplugin.base.BaseClassVisitor;
import com.kalu.asmplugin.base.BaseMethodVisitor;
import com.kalu.asmplugin.fastclick.FastClickMethodVisitor;
import com.kalu.asmplugin.impl.ImplClassVisitor;
import com.kalu.asmplugin.timeconsuming.TimeComsumingMethodVisitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class SimpleClassVisitor extends BaseClassVisitor implements ImplClassVisitor {

    public SimpleClassVisitor(ClassVisitor classVisitor) {
        super(classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//        System.out.println("------------------------------------------------------------");
//        System.out.println("SimpleClassVisitor[visitMethod] => access = " + access + ", name = " + name + ", descriptor = " + descriptor + ", signature = " + signature + ", exceptions = " + exceptions);

        // 排除
        if (null == name || name.length() == 0 || "<init>".equals(name)) {
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }
        // 快速点击
        else if (name.contains("onClick") && "(Landroid/view/View;)V".equals(descriptor)) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
            return new FastClickMethodVisitor(this, methodVisitor, access, descriptor, name, getClassName());
        }
        // 耗时检测
        else {
            MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
            return new TimeComsumingMethodVisitor(this, methodVisitor, access, descriptor, name, getClassName());
        }
    }
}
