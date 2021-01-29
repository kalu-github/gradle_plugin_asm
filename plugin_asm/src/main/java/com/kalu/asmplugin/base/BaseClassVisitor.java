package com.kalu.asmplugin.base;

import com.kalu.asmplugin.impl.ImplClassVisitor;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;

import static org.objectweb.asm.Opcodes.ACC_INTERFACE;

/**
 * description:
 * created by kalu on 2021-01-21
 * <p>
 * https://www.oschina.net/question/4130051_2317555
 */
public class BaseClassVisitor extends ClassVisitor implements ImplClassVisitor {

    private String className;
    private String superName;
    private boolean isInterface;
    private boolean isChange;

//    boolean containsPermissionVerification = false;

    public BaseClassVisitor(ClassVisitor classVisitor) {
        super(org.objectweb.asm.Opcodes.ASM4, classVisitor);
    }

    /**
     * 访问类头部信息
     *
     * @param version    类版本
     * @param access     类访问标识符public等
     * @param name       类名称
     * @param signature  类签名（非泛型为NUll）
     * @param superName  类的父类
     * @param interfaces 类实现的接口
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
        this.superName = superName;
        this.isInterface = (access & ACC_INTERFACE) != 0;
    }

    /**
     * 访问类的源文件.
     *
     * @param source 源文件名称
     * @param debug  附加的验证信息，可以为空
     */
    @Override
    public void visitSource(String source, String debug) {
        super.visitSource(source, debug);
    }

    /**
     * 访问类的注解
     *
     * @param descriptor 注解类的类描述
     * @param visible    runtime时期注解是否可以被访问
     * @return 返回一个注解值访问器
     */
    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return super.visitAnnotation(descriptor, visible);
    }

    /**
     * 访问标注在类型上的注解
     *
     * @param typeRef
     * @param typePath
     * @param descriptor
     * @param visible
     * @return
     */
    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
    }

    /**
     * 访问一个类的属性
     *
     * @param attribute 类的属性
     */
    @Override
    public void visitAttribute(Attribute attribute) {
        super.visitAttribute(attribute);
    }

    /**
     * 访问内部类信息
     *
     * @param name
     * @param outerName
     * @param innerName
     * @param access
     */
    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        super.visitInnerClass(name, outerName, innerName, access);
    }

    /**
     * 访问class中字段的信息，返回一个FieldVisitor用于获取字段中更加详细的信息。
     * <p>
     * name：字段个的名称
     * <p>
     * descriptor：字段的描述
     * <p>
     * value：该字段的初始值，文档上面说：
     * <p>
     * 该参数，其可以是零，如果字段不具有初始值，必须是一个Integer，一Float，一Long，一个Double或一个String（对于int，float，long 或String分别字段）。此参数仅用于静态字段。对于非静态字段，它的值被忽略，非静态字段必须通过构造函数或方法中的字节码指令进行初始化（但是不管我怎么试，结果都是null）。
     */
    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        return super.visitField(access, name, descriptor, signature, value);
    }

    /**
     * 访问class中方法的信息，返回一个MethodVisitor用于获取方法中更加详细的信息。
     * <p>
     * name：方法的名称
     * <p>
     * descriptor：方法的描述
     * <p>
     * signature：方法的签名
     * <p>
     * exceptions：方法的异常名称
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setInterface(boolean anInterface) {
        isInterface = anInterface;
    }

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        isChange = change;
    }

    @Override
    public boolean isInject() {
        return isChange();
    }
}
