package com.kalu.asmplugin.permission;

import com.kalu.asmplugin.base.BaseAnnotationVisitor;
import com.kalu.asmplugin.util.PluginLogUtil;

import org.objectweb.asm.AnnotationVisitor;

import java.util.HashMap;

/**
 * description: 运行时权限, 解析注解信息
 * created by kalu on 2021-01-26
 */
public class PermissionVerificationArrayAnnotationVisitor extends BaseAnnotationVisitor {

    private String key;
    private HashMap<String, Object> map = null;

    public PermissionVerificationArrayAnnotationVisitor(AnnotationVisitor annotationVisitor, HashMap<String, Object> map, String key) {
        super(annotationVisitor);
        this.map = map;
        this.key = key;
        PluginLogUtil.log("***********************************************************");
        PluginLogUtil.log("PermissionVerificationArrayAnnotationVisitor[construct] =>");
    }

    @Override
    public void visit(String name, Object value) {
        PluginLogUtil.log("------------------------------------------------------------");
        PluginLogUtil.log("PermissionVerificationArrayAnnotationVisitor[visit] => name = " + name + ", value = " + value);
        if (null != this.map && null != value && null != key) {
            if (null == map.get(key)) {
                Object[] news = new Object[1];
                news[0] = value;
                PluginLogUtil.log("PermissionVerificationArrayAnnotationVisitor[visit] => news = " + news.toString());
                map.put(key, news);
            } else {
                Object[] olds = (Object[]) map.remove(key);
                int length = olds.length;
                Object[] news = new Object[length + 1];
                for (int i = 0; i < length; i++) {
                    news[i] = olds[i];
                }
                news[length] = value;
                PluginLogUtil.log("PermissionVerificationArrayAnnotationVisitor[visit] => news = " + news.toString());
                map.put(key, news);
            }
        }
        super.visit(name, value);
    }
}
