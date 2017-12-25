package com.example.administrator.facesample.view;

import android.graphics.Bitmap;

/**
 * Created by liujie on 2017/7/6.
 */

public interface FaceRecognitionView {
    /**
     * 传送Bitmap数据
     * @return
     */
    public Bitmap getFaceImage();

    //检测到人脸是否注册
    public void onRegister();
    //注册失败
    public void onRecongitError(String msg);
    //失败
    public void onToastError(String error);
}
