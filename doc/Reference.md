## 技术问题 QaA
* ### 设计
  * 本地模型或者接口上如何判断错误图片 如非云的图片识别问题 有没有可能解决 
* ### 代码
  * 好难 不会啊 :<  10/12/2024 学学kotlin  点名批评 twilight 还在偷懒 我看这人什么时候能发现
  * 屏幕旋转问题的解决 application 对象 以及 jetback
  * 快捷方式 创建 metadata 同时可能涉及调用接口的token问题
  * 历史记录 可能使用 SQlite
  * 图片的 读取 不知道是读取sd卡 还是内存 sd卡 使用bitmap 相关工具 
  * contentprovider 实现读取相册图片 貌似是 title="༼ つ ◕_◕ ༽つ Sorry I'm late. Take up anew

## Overview
![](./image/1.jpg)


## 矢量图下载网站 svg
[](https://www.freepik.com/search?format=search&freeSvg=free&iconType=standard&last_filter=query&last_value=physics&query=physics&type=icon)


### 10/30
```
任务与技术细节
基础 ：
  1. image卡片设计————要求              
    （1） 自定义细节 
          a. 分类tag 默认自定义导入 识别结果 可以自定义  或者按照自己的类别分类
          b. location 用户自己填入吧 不获取地理位置 时间，这个可以填入 也可以修改
          c.  用户自定义 文字 默认存在一些默认文字 （到时候 我来准备(SpikeIHG)）
          
    （2）显示逻辑
          a. 点击可以放大 放大的另一个格式
          b. 所有的图片都是一个card一样 形成一个类似图库的上下滑动 gallery
          c. 可以随时删除 del
    (3) 拓展功能
          a. 也许可以自定图册 如果识别图片很多的话 atlas
          b. 顺序也学可以自定义滑动
    (4) 初始显示
          a. 可以我来提供初始的图片信息就是8大类云朵的图片实例吧

```

*   ref: [添加删除逻辑](https://github.com/wasabeef/recyclerview-animators)
*   ref: [滑动布局](https://github.com/yqritc/RecyclerView-MultipleViewTypesAdapter)
*   ref: [布局](https://github.com/magiepooh/RecyclerItemDecoration)
*   ref : [这里有一个奇怪的布局](https://github.com/flavienlaurent/discrollview)
*   ref : [一个替代的tabbar](https://github.com/nirukk52/SnapTabLayout)
*   
*   其他资料 可以搜搜更多的 recycleview
### 11/4
```
  2. wiki设计
    （1）. 布局
          目前打算使用那个球显示 点击显示细节
    （2）. 逻辑
          打算可以支持自定义的一套分类逻辑
    （3）. 加入一些诗歌关于云的 
    （4）.  国际云图实时的api 有可能获得吗
```
* ref [一个fragment](https://github.com/magic-goop/tag-sphere)
### 10/30
```
  3. 创建按钮 button
  (1). 创建 然后分为拍照和导入
  (2). 具体就搜 button 控件看看 

```
*  ref : 权限申请 动态权限申请 视频有讲
*  ref :[基本的button](https://github.com/futuresimple/android-floating-action-button)
*  ref :[可以参考的button](https://github.com/oguzbilgener/CircularFloatingActionMenu)
### 11/4
```
  4. 快捷方式 ———— 视频里有 工作量一般 可写
```
### 11/10
```
  5 ，导航切换方式
    两种 就像图片里的 一种是最初计划 另外一种 是 as原生的navigation
```
* ref : [menu 效果](https://github.com/Yalantis/Side-Menu.Android)
```
  6. 其他效果 effect
  (1) blur 查看图片 blur
  (2) refresh效果
  (3) 一些可爱的小动画 maybe
  (4) 初始启动的页面，想要使用最近看的一部电影里面的一个截图的风格 
  7. 内置搜索功能 这个有点茫然 看时间和工作量把
  8. 引导页 与 启动不一样 可以考虑实现 就是一个 viewpager 并且视频有讲
```
* ref :[选择照片的一个framework](https://github.com/ChiliLabs/ChiliPhotoPicker)
```
 Badge wall 徽章墙 一些小东西 比如说集齐所有大种类的云 比如发现某些稀有云朵 属于一个小拓展
 想要加入自动定位的功能 获取经纬度 自动填上日期与经纬度
```
### 11/04
* 需要的文字素材 和 图片素材 

### fragment知识点总结
#### 静态注册 就是一个布局xml 可以快捷键创建
* 生命周期 健壮性问题
  * attach 关联到activity
  * oncreate  
```
Fragment 的生命周期与 Activity 相似，但它有自己特定的生命周期方法。理解这些方法对管理 Fragment 的行为至关重要。以下是 Fragment 生命周期的主要阶段及其对应的方法：

1. 创建阶段
onAttach(Context context): 当 Fragment 与 Activity 关联时调用。在这里，你可以访问宿主 Activity 的上下文。

onCreate(Bundle savedInstanceState): 在 Fragment 被创建时调用。你可以在这里进行初始化操作，比如设置保存的状态。

2. 视图创建阶段
onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState): 创建 Fragment 的用户界面视图。你在这里返回布局视图，通常使用 inflater 来加载 XML 布局。

onViewCreated(View view, Bundle savedInstanceState): 在视图创建后调用，此时可以对视图进行初始化（例如，设置适配器、点击监听等）。

3. 可见性阶段
onStart(): 当 Fragment 可见时调用。此时可以启动动画或加载数据。

onResume(): 当 Fragment 与用户交互时调用。在这里可以执行任何需要在 Fragment 活动期间保持的操作（例如，监听器或传感器）。

4. 暂停和停止阶段
onPause(): 当 Fragment 不再与用户交互时调用。此时可以保存数据或停止动画。

onStop(): 当 Fragment 不再可见时调用。在这里可以释放资源或停止不必要的操作。

5. 销毁阶段
onDestroyView(): 在视图被销毁时调用。此时可以清理与视图相关的资源（例如，解除视图绑定）。

onDestroy(): 在 Fragment 被销毁时调用。此时可以清理任何其他资源。

onDetach(): 当 Fragment 与其宿主 Activity 解除关联时调用。此时可以进行最后的清理。

生命周期图示
scss
Copy code
   onAttach()
      ↓
   onCreate()
      ↓
   onCreateView()
      ↓
   onViewCreated()
      ↓
   onStart()
      ↓
   onResume()
当用户离开 Fragment 时：

scss
Copy code
   onPause()
      ↓
   onStop()
      ↓
   onDestroyView()
      ↓
   onDestroy()
      ↓
   onDetach()
注意事项
Fragment 的生命周期与其宿主 Activity 的生命周期相关联。当 Activity 被销毁时，Fragment 也会被销毁。
理解生命周期方法有助于正确管理资源和状态，避免内存泄漏和其他问题。
如果你有任何具体的问题或需要更深入的解释，请告诉我！
```

* 动态注册
 * activity的一个地方可能会用
  ```java
  public class MainActivity extends ActionBarActivity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.container,new MainFragment()).commit();
        }
 
    }



  ```

  ## fragment replace 会销毁这些东西   

## recycler view 的详细东西 其实挺简单的 [Recyclerview](https://blog.csdn.net/huweiliyi/article/details/105779329)

## scrollview 需要添加一个下拉的这个效果吧
## 可以添加一个卡片导出为本地图片的功能

```
布局 —— recyclerview弹出 
查看fragment
数据库 Sqlite  entity  申请位置权限  得知当前所在位置经纬度

调相册 照片 该权限 清单文件 动态申请权限   intent的用法 

```


模型部署问题
[模型部署](https://github.com/tensorflow/examples/tree/master/lite)

* 实现一个分享链接的功能 如果可以的话
