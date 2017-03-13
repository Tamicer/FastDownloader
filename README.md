# FastDownloader
基于Okhttp, 结合Retrofit，加入多种设计模式，实现的android平台多线程下载利器！

# 功能 #

- 多线程多任务下载功能
- 基本的断点续传
- 下载数据持久，程序退出仍可以保留
- 支持视频，小说，文件，应用，文本多模式下载
- 支持通知栏同步更新
- 支持静默和非静默下载
- 支持多种参数定制，文件名，存储路径定制功能
- 智能进行优先级下载，wifi自动恢复失败任务


# 实践 #

## 依赖 ##

gradle依赖远程maven包：


    compile 'com.tamic.fastdownloader:Android-FastDownloader:1.0.0'

## 初始化 ##

Application中初始化下载

    @Override
    public void onCreate() {
       super.onCreate();
       DownloadInit.initDownload(getApplicationContext());
  
    }

## 调用 ##

**simple 1**：

    new Download.Builder()
              .url(url)
              .build(this)
              .start();

只要设置下载Url即可，文件名下载库会自动抓取，
默认优先级为中等，回调UI下载自动处理，默认的保存路径为：你的app包名+ /tammic/downloads/   下

**simple 2：**

    new Download.Builder()
         .url("http://downloads2.txt99.com/d/file/p/txt/2016/%E5%BD%B1%E5%B8%9D%E7%9A%84%E8%80%81%E5%A9%86.txt")//下载url
         .priority(Priority.HIGH)//优先级
         .savepath("可以保存指定目录")//保存路径
         .isImplicit(false)//是否提示UI
         .channel(3000)//渠道，可选
         .client(DLClientFactory.createClient(Type.NORMAL, this))//下载器
         .setCallback(new DLCallback())//下载回调
         .build(this)
         .start();

这种配置 可定制下载的文件名，存储路径，优先级，以及渠道，是否显示通知栏更新UI等，并且回调可以自我实现，譬如下载完成后显示安装对框等
