# FastDownloader
基于Okhttp, 结合Builder，加入多种设计模式，实现的android平台多线程下载利器！

# 功能 #

- 多线程多任务下载功能
- 基本的断点续传
- 下载数据持久，程序退出仍可以保留
- 支持视频，小说，文件，应用，文本多模式下载
- 支持通知栏下载进度同步更新
- 支持静默和非静默下载
- 支持多种参数定制，文件名，存储路径定制功能
- 智能进行优先级下载，wifi自动恢复失败任务
- 提供进度回调，可实现自定义的操作

# 效果

![效果](https://github.com/Tamicer/FastDownloader/blob/master/GIF.gif)

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
默认优先级为中等，回调UI下载自动处理，默认的保存路径为：你的apk包名+ /tamic/downloads/  下

**simple 2：**

     
     
     
    new Download.Builder()
          .url("this is url")//下载url
          .priority(Priority.HIGH) 
          .savepath("保存路径")
          .isImplicit(false)//是否显示UI
          .channel(3000)//渠道可选
          .client(DLClientFactory.createClient(Type.NORMAL, this))//下载器
          .setCallback(new DLCallback())//下载回调
          .build(this)
          .start();
     

这种配置 可定制下载的文件名，存储路径，优先级，以及渠道，是否显示通知栏更新UI等，并且回调可以自我实现，譬如下载完成后显示安装对话框等

#高级API

**重新开始下载**



     new Download.Builder()
                .url(url)
                .build(this)
                .reStart();
                
                
                
**下载Tag**



```
       new Download.Builder()
                  .tag("key")
                  .url(url)
                  .build(this)
                  .Start();
                
 ```  

             
 
  
  如果不设置Tag, 默认为Url+当前系统时间。 有了这个Tag你可以做暂停需取消等操作

**自定义回调**  

对某个下载进行操作可以单独加回调处理，使用`setCallback`如果忽略不设置，系统则默认不处理。



      new Download.Builder()
         .setCallback(new IDLCallback() {
                    @Override
                    public void onStart(String key, long fileLength, long downloaded, String savePath, String filenNme) {
                        
                    }

                    @Override
                    public void onSuccess(String key, long fileLength, long downloaded, String savePath, String filenNme, long aSpeed, String aAppiconName) {

                    }

                    @Override
                    public void onAppSuccess(String key, long fileLength, long downloaded, String savePath, String filenNme, long aSpeed, String aAppiconName, int downloadType, int appType) {

                    }

                    @Override
                    public void onFail(String key, long downloaded, String savePath, String filenNme, String aErrinfo) {

                    }

                    @Override
                    public void onCancel(String key, long fileLength, long downloaded, String savePath, String filenNme) {

                    }

                    @Override
                    public void onPause(String key, long fileLength, long downloaded, String savePath, String filenNme) {

                    }

                    @Override
                    public void onDownloading(String key, long fileLength, long downloadLength, long speed, String fileName, int downloadType) {

                    }

                    @Override
                    public void onRefresh(List<DownLoadInfo> infos) {

                    }
                })
