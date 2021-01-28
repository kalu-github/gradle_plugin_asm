package com.kalu.asmplugin.base;

import com.kalu.asmplugin.impl.ImplAnnotationVisitor;

import org.objectweb.asm.AnnotationVisitor;

/**
 * description: AnnotationVisitor访问顺序如下:
 * <p>
 * (visit | visitEnum| visitAnnotation | code visitArray )* visitEnd.
 * （）*可以访问多次，而visitEnd只能访问一次;
 * <p>
 * visit:访问注解的基本值；
 * visitEnum:访问注解的枚举类型值;
 * visitAnnotation:访问嵌套注解类型，也就是一个注解可能被其他注解所注释;
 * visitArray: 访问注解的数组值;
 * visitEnd:访问结束通知；
 * <p>
 * created by kalu on 2021-01-26
 */
public class BaseAnnotationVisitor extends AnnotationVisitor implements ImplAnnotationVisitor {

    public BaseAnnotationVisitor(AnnotationVisitor annotationVisitor) {
        super(org.objectweb.asm.Opcodes.ASM4, annotationVisitor);
    }
}
