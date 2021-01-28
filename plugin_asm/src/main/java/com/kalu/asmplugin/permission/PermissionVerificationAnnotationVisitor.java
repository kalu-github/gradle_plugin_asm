package com.kalu.asmplugin.permission;

import com.kalu.asmplugin.base.BaseAnnotationVisitor;
import com.kalu.asmplugin.util.PluginLogUtil;

import org.objectweb.asm.AnnotationVisitor;

import java.util.HashMap;

/**
 * description: 运行时权限, 解析注解信息
 * created by kalu on 2021-01-26
 */
public class PermissionVerificationAnnotationVisitor extends BaseAnnotationVisitor {

    private HashMap<String, Object> map = null;

    public PermissionVerificationAnnotationVisitor(AnnotationVisitor annotationVisitor, HashMap<String, Object> map) {
        super(annotationVisitor);
        this.map = map;
        PluginLogUtil.log("***********************************************************");
        PluginLogUtil.log("PermissionVerificationAnnotationVisitor[construct] =>");
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
        PluginLogUtil.log("------------------------------------------------------------");
        PluginLogUtil.log("PermissionVerificationAnnotationVisitor[visitArray] => name = " + name);
        AnnotationVisitor annotationVisitor = super.visitArray(name);
        PermissionVerificationArrayAnnotationVisitor permissionVerificationArrayAnnotationVisitor = new PermissionVerificationArrayAnnotationVisitor(annotationVisitor, this.map, name);
        return permissionVerificationArrayAnnotationVisitor;
    }

    @Override
    public void visit(String name, Object value) {
        PluginLogUtil.log("------------------------------------------------------------");
        PluginLogUtil.log("PermissionVerificationAnnotationVisitor[visit] => name = " + name + ", value = " + value);
        if (null != this.map) {
            this.map.put(name, value);
        }
        super.visit(name, value);
    }
}
