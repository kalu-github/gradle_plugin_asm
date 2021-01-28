package com.kalu.asmplugin.timeconsuming;

import com.kalu.asmplugin.base.BaseClassVisitor;
import com.kalu.asmplugin.base.BaseMethodVisitor;

import org.objectweb.asm.MethodVisitor;

/**
 * description: 继承自LocalVariablesSorter 有序遍历素有方法
 * created by kalu on 2021-01-19
 * <p>
 * https://blog.csdn.net/qq_33589510/article/details/105273233
 */
public class TimeComsumingMethodVisitor extends BaseMethodVisitor {

    public TimeComsumingMethodVisitor(BaseClassVisitor baseClassVisitor, MethodVisitor methodVisitor, int access, String descriptor, String methodName, String className) {
        super(baseClassVisitor, methodVisitor, access, descriptor, methodName, className);
    }

    /**
     * 统计方法耗时
     */
    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        System.out.println("------------------------------------------------------------");
        System.out.println("BaseMethodVisitor[onMethodEnter] =>");

        addTimeComsumingStart();
    }

    /**
     * 统计方法耗时
     *
     * @param opcode
     */
    @Override
    protected void onMethodExit(int opcode) {

        addTimeComsumingClose();

        super.onMethodExit(opcode);
    }
}
