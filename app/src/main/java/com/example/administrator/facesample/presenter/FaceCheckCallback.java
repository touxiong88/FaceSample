package com.example.administrator.facesample.presenter;

import android.graphics.Bitmap;
import android.media.FaceDetector;

/**
 * Created by liujie on 2017/7/4.
 * 人脸识别回调
 */

public interface FaceCheckCallback {
    /**
     * 回调成功后的结果
     */
    public void onResult(int faceNume, FaceDetector.Face[] FaceData, Bitmap bitmap);
    /**
     * 出现异常的结果
     */
    public void onError(String errormsg);
}
