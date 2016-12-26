package com.bwie.xyn.demo.utils;

import android.content.Context;
import android.graphics.Bitmap;


import com.bwie.xyn.demo.R;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

public class ImageLoaderUtils {
    /**
     * 初始化ImageLoaderConfiguration 这个可以只做简单的初始化,此方法建议在
     * Application中进行初始化
     *
     * @param context
     */
    public static void initConfiguration(Context context) {

        Builder configuration = new Builder(context);
//--------------------------------------------------------------------
//    本段代码，如果是测试使用时，可以不添加，不影响ImageLoader的正常使用
      configuration.memoryCacheExtraOptions(480, 800)
      // default = device screen dimensions
      // 缓存到磁盘中的图片宽高
              .diskCacheExtraOptions(480, 800, null)
              // .taskExecutor(null)
              // .taskExecutorForCachedImages()
              .threadPoolSize(3)
              // default 线程优先级
              .threadPriority(Thread.NORM_PRIORITY - 2)
              // default
              .tasksProcessingOrder(QueueProcessingType.FIFO)
              // // default设置在内存中缓存图像的多种尺寸
              // 加载同一URL图片时,imageView从小变大时,从内存缓存中加载
              .denyCacheImageMultipleSizesInMemory()
              // 超过设定的缓存大小时,内存缓存的清除机制
              .memoryCache(new LruMemoryCache(30 * 1024 * 1024))
              // 内存的一个大小
              .memoryCacheSize(30 * 1024 * 1024).memoryCacheSizePercentage(13)
              // default 将图片信息缓存到该路径下
              // default 磁盘缓存的大小
              .diskCacheSize(100 * 1024 * 1024)
              // 磁盘缓存文件的个数
              .diskCacheFileCount(100)
              // 磁盘缓存的文件名的命名方式//一般使用默认值 (获取文件名称的hashcode然后转换成字符串)或MD5 new
              // Md5FileNameGenerator()源文件的名称同过md5加密后保存
              .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
              // 设置默认的图片加载
              // 使用默认的图片解析器
              .imageDecoder(new BaseImageDecoder(true)) // default
              .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
              .writeDebugLogs();

//---------------------------------------------------------------------
        ImageLoader.getInstance().init(configuration.build());
    }

    /**
     * 初始化DisplayImageOptions
     *
     * @param
     * @return
     */
    public static DisplayImageOptions initOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
                .showImageOnLoading(R.mipmap.ic_launcher)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc(true)


//--------------------------------------------------------------------
//如果您只想简单使用ImageLoader这块也可以不用配置
                // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .considerExifParams(true)
                // 设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片的解码类型//
                .bitmapConfig(Bitmap.Config.RGB_565)
                // 设置图片的解码配置
                // .decodingOptions(options)
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的下载前的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                // 设置图片在下载前是否重置，复位
                .resetViewBeforeLoading(true)
                // 是否设置为圆角，弧度为多少
           //     .displayer(new CircleBitmapDisplayer())
                // 是否图片加载好后渐入的动画时间
//                .displayer(new FadeInBitmapDisplayer(100))
                // 构建完成
//-------------------------------------------------------------------

                .build();
        return options;
    }
    public static DisplayImageOptions initCrileOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
                .showImageOnLoading(R.mipmap.ic_launcher)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc(true)


//--------------------------------------------------------------------
//如果您只想简单使用ImageLoader这块也可以不用配置
                // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .considerExifParams(true)
                // 设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片的解码类型//
                .bitmapConfig(Bitmap.Config.RGB_565)
                // 设置图片的解码配置
                // .decodingOptions(options)
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的下载前的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                // 设置图片在下载前是否重置，复位
                .resetViewBeforeLoading(true)
                // 是否设置为圆角，弧度为多少
                .displayer(new CircleBitmapDisplayer())
                // 是否图片加载好后渐入的动画时间
//                .displayer(new FadeInBitmapDisplayer(100))
                // 构建完成
//-------------------------------------------------------------------

                .build();
        return options;
    }
}