# <a id="table">目录</a>
[一、Android端Plugin生成](#project)

[1.1 项目结构](#1.1)

[1.2 编译](#1.2)

[二、Plugin导入及设置](#plugin)

[2.1 导入](#2.1)

[2.2 设置](#2.2)

[三、运行结果](#result)

# <a id="project">一、 Android端Plugin生成</a>
Demo:[GitHub](https://github.com/Unity-Technologies/NativeRenderingPlugin)



```
git clone https://github.com/Unity-Technologies/NativeRenderingPlugin.git
```

[☝](#table)

## <a id="1.1">1.1 项目结构</a>
![项目结构](https://github.com/Azhao1993/Notes/blob/master/assets/UnityNote/1593862311(1).png)

* `PluginSource` 用于生成C++ plugin的工程
 	* `projects`: 用于生成plugin的IDE工程文件
 	    * `Android` 生成Android平台的plugin
 	    * `VisualStudio2015` : Visual Studio 2015 解决方案生成 Windows 平台plugin
 	    * `UWPVisualStudio2015` : Visual Studio 2015 解决方案生成(UWP - Win10) plugin
	    * `Xcode`: Apple 平台 Xcode 工程文件生成 MacOS  plugin
	    * `GNUMake`:  Linux 平台
 	* `source`: 用于生成plugin的源代码
 	    * `RenderingPlugin.cpp` 入口文件
 	    * `RenderAPI*.*` 文件包含针对不同API的Rendering实现.

* `UnityProject` Unity 项目 (2018.3.9).
	* `scene` 一个使用Plugin的简单场景.

[☝](#table)


## <a id="1.2">1.2 编译</a>

### 1.2.1 修改Application.mk
将Application.mk中2、3行
```
APP_PLATFORM := android-9
APP_STL := gnustl_static
```
修改为

```
APP_PLATFORM := android-16
APP_STL := c++_static
```

[☝](#table)

### 1.2.2 编译
在目录
```
\PluginSource\projects\Android\jni 
```
打开命令行窗口执行

```
ndk-build
```

[☝](#table)

### 1.2.3 结果
arm64所需的.so在

```
 \PluginSource\projects\Android\libs\arm64-v8a\libRenderingPlugin.so
```



# <a id="plugin">二、Plugin导入及设置</a>
## <a id="2.1">2.1 导入</a>
在Unity工程中双击sence打开场景demo

![Assets](https://github.com/Azhao1993/Notes/blob/master/assets/UnityNote/Unity_20200704200623.png)

`Plugins`目录下包含各种平台的Plugin,但不包括Android平台

将上步骤生成的`libRenderingPlugin.so`复制到`Plugins\Android\`

    注：.so文件可以放在任意目录下

[☝](#table)

## <a id="2.2">2.2 设置</a>
选中`libRenderingPlugin.so`在`Inspector`面板中设置并Apply

![Inspector](https://github.com/Azhao1993/Notes/blob/master/assets/UnityNote/Unity_20200704201749.png)

[☝](#table)
# <a id='result'>三、运行结果</a>

在Build时,Unity会自动打包至apk中
![result](https://github.com/Azhao1993/Notes/blob/master/assets/UnityNote/res.gif)
[☝](#table)
