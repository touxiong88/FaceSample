package com.example.administrator.facesample.view;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/7/4.
 */

public interface CameraView {
    //预览成功
    public void onToastSuccess(String success);
    //显示异常
    public void onToastError(String error);
    //检测到人脸 进行识别判定
    public void onDetectFace(Bitmap bitmap);
}
