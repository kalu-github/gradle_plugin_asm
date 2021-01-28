package com.kalu.asmplugin.util;

import org.gradle.internal.impldep.org.jetbrains.annotations.NotNull;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;


public class PluginAsmUtil {

    public static String FIELD_LONG = "J";

    /**
     * 生成属性
     *
     * @param classVisitor
     * @param descriptor
     * @param fieldName
     * @param fieldValue
     */
    public static final void createFieldLong(@NotNull ClassVisitor classVisitor, @NotNull String descriptor, @NotNull String fieldName, @NotNull int fieldValue) {

        FieldVisitor fieldVisitor = classVisitor.visitField(org.objectweb.asm.Opcodes.ACC_PRIVATE, fieldName, descriptor, null, fieldValue);
        AnnotationVisitor annotationVisitor = fieldVisitor.visitAnnotation("Landroidx/annotation/Keep;", false);
        annotationVisitor.visitEnd();
        fieldVisitor.visitEnd();
    }
}
