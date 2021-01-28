package com.kalu.asmplugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.kalu.asmplugin.simple.SimpleClassVisitor
import com.kalu.asmplugin.util.PluginFileUtil
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

class AsmTransform extends Transform {

    @Override
    String getName() {
        System.out.println("AsmTransform[getName] =>")
        return "AsmTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {

        // 遍历输入
        for (TransformInput input in inputs) {

            if (null == input)
                continue

            // 遍历JarInput 因为我们这里只对自己的方法插桩 所以不对JarInput做处理, 虽然不做处理 但是还是要记得重新拷贝回去 不然会有问题
            System.out.println("************************************************************")
            System.out.println("AsmTransform[transform] => parseJarInputs")
            Collection<JarInput> jarInputs = input.getJarInputs()
            parseJarInputs(outputProvider, jarInputs)

            // 遍历DirectoryInput
            System.out.println("************************************************************")
            System.out.println("AsmTransform[transform] => parseDirectoryInputs")
            Collection<DirectoryInput> directoryInputs = input.getDirectoryInputs()
            parseDirectoryInputs(outputProvider, directoryInputs)
        }
    }

    void parseJarInputs(TransformOutputProvider outputProvider, Collection<JarInput> jarInputs) {
        System.out.println("------------------------------------------------------------")
        System.out.println("AsmTransform[parseJarInputs] => jarInputs = " + jarInputs)

        if (null == jarInputs || jarInputs.size() == 0)
            return

        System.out.println("AsmTransform[parseJarInputs] => size = " + jarInputs.size())

        //jar（第三方库，module）
        for (JarInput jarInput : jarInputs) {

            if (null == jarInput)
                continue

            System.out.println("AsmTransform[parseJarInputs] => src = " + jarInput.name)

            if (jarInput.scopes.contains(QualifiedContent.Scope.SUB_PROJECTS)) {
                //module library
                //从module中获取注解信息
                //                    readClassWithJar(jarInput)
            }

            // 虽然不做处理 但是还是要记得重新拷贝回去 不然会有问题
            PluginFileUtil.copyJarInput(outputProvider, jarInput)
        }
    }

    void parseDirectoryInputs(TransformOutputProvider outputProvider, Collection<DirectoryInput> directoryInputs) {
        System.out.println("------------------------------------------------------------")
        System.out.println("AsmTransform[parseDirectoryInputs] => directoryInputs = " + directoryInputs)

        if (null == directoryInputs || directoryInputs.size() == 0)
            return

        System.out.println("AsmTransform[parseDirectoryInputs] => size = " + directoryInputs.size())
        for (DirectoryInput directoryInput in directoryInputs) {

            if (null == directoryInput)
                continue

            // 1.处理需要插桩的文件
            parseDirectoryInput(directoryInput)

            System.out.println("AsmTransform[parseDirectoryInputs] => srcFile = " + directoryInput.file.absolutePath)

            // 2.Copy修改之后的文件
            File destFile = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
            System.out.println("AsmTransform[parseDirectoryInputs] => destFile = " + destFile.absolutePath)

            PluginFileUtil.copyDirectory(directoryInput, destFile)
        }
    }

    /**
     * 解析DirectoryInput
     * @param dir
     */
    void parseDirectoryInput(DirectoryInput directoryInput) {
        System.out.println("------------------------------------------------------------")
        System.out.println("AsmTransform[parseDirectoryInput] => directoryInput = " + directoryInput)
        if (null == directoryInput)
            return

        File file = directoryInput.file
        System.out.println("AsmTransform[parseDirectoryInput] => file = " + file)

        if (null == file)
            return

        String absolutePath = file.absolutePath
        System.out.println("AsmTransform[parseDirectoryInput] => absolutePath = " + absolutePath)

        if (null == absolutePath || absolutePath.length() == 0)
            return

        file.eachFileRecurse { File self ->

            // 过滤路径
            if (self.isDirectory())
                return

            String filePath = self.absolutePath
            System.out.println("AsmTransform[parseDirectoryInput] => filePath = " + filePath)

            //过滤非class文件
            if (null == filePath || filePath.length() == 0 || !filePath.endsWith(".class"))
                return

            // 获取类名
            String className = filePath.substring(absolutePath.length() + 1, filePath.length() - 6).replaceAll("/", ".").replaceAll("\\\\", ".")
            System.out.println("AsmTransform[parseDirectoryInput] => className = " + className)

            // 过滤系统文件
            if (null == className || className.length() == 0 || className.matches('^android\\..*') || className.matches('^androidx\\..*') || className.matches('.*\\.R$') || className.matches('.*\\.R\\$.*$') || className.matches('.*\\.BuildConfig$'))
                return

            // 注入代码
            injectClass(filePath, className)
        }
    }

    /**
     *
     * @param filePath
     * @param className
     */
    void injectClass(String filePath, String className) {
        System.out.println("------------------------------------------------------------")
        System.out.println("AsmTransform[injectClass] => filePath = " + filePath + ", className = " + className)

        File file = new File(filePath)
        FileInputStream fileInputStream = new FileInputStream(file)

        //1.声明ClassReader
        ClassReader reader = new ClassReader(fileInputStream)

        //2.声明ClassWriter
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)

        //3.声明ClassVisitor
        SimpleClassVisitor adapter = new SimpleClassVisitor(writer)

        //4.调用accept方法 传入classVisitor
        reader.accept(adapter, ClassReader.EXPAND_FRAMES)

        boolean isInject = adapter.isInject()
        System.out.println("AsmTransform[injectClass] => isInject = " + isInject)

        if (!isInject)
            return
        System.out.println("AsmTransform[injectClass] => 注入代码")

        byte[] bytes = writer.toByteArray()
        FileOutputStream fos = new FileOutputStream(new File(filePath))
        fos.write(bytes)
    }
}