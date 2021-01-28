
![image](https://github.com/153437803/plugin_asm_app/blob/master/image.gif )


#### 统计耗时方法 - @TimeConsuming

插桩前

![image](https://github.com/153437803/plugin_asm_app/blob/master/image20210128122556.png )

插桩后

![image](https://github.com/153437803/plugin_asm_app/blob/master/image20210128122649.png )



#### ASM插桩监控
```
1. 检测方法耗时 - 已完成
2. 拦截快速点击 - 已完成
3. 运行时动态权限 - 未完成
```

#### 思路

```
找好注入点, 可以通过ASM插件来生成字节码
```

#### 插件

[ASM插件生成字节码 - ASM Bytecode Outline](https://plugins.jetbrains.com/plugin/5918-asm-bytecode-outline)

#### 参考

[ASM4使用指南](https://raw.githubusercontent.com/153437803/plugin_asm_app/master/ASM4%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97.pdf)

[深入探索编译插桩技术（四、ASM 探秘）](https://juejin.im/post/5e8d87c4f265da47ad218e6b)

[使用javassist和ASM修改class，并实现方法耗时检测插件](https://juejin.im/post/5dea581fe51d45581d170b7c)