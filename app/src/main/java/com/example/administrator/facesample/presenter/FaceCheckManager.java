package com.example.administrator.facesample.presenter;

import android.graphics.Bitmap;
import android.media.FaceDetector;

import com.example.administrator.common.Config;
import com.example.administrator.facesample.view.CameraView;


/**
 * Created by liujie on 2017/7/4.
 */

public class FaceCheckManager {
    private FaceDetector mFaceDetector;
    private FaceDetector.Face[] mFace = new FaceDetector.Face[Config.FACE_MAX_NUM];
    private FaceCheckManager(Bitmap bitmap){
        mFaceDetector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), Config.FACE_MAX_NUM);
    }
    public static class Builder{
        private CameraView mCmView;
        private Bitmap mBitmap;
        public Builder setImgData(Bitmap image){
            this.mBitmap = image;
            return this;
        }
        public Builder setCmView(CameraView mCmView){
            this.mCmView = mCmView;
            return this;
        }
        public FaceCheckManager create(){
            return new FaceCheckManager(mBitmap);
        }

    }
    public void findFace(final FaceCheckCallback faceCallback,Bitmap bitmap){
        if(bitmap == null){
            faceCallback.onError("mBitmap == null");
            return;
        }
        Bitmap mBitmap = bitmap.copy(Bitmap.Config.RGB_565, true);
        final int faceResult = mFaceDetector.findFaces(mBitmap, mFace);
        faceCallback.onResult(faceResult, mFace, mBitmap);
        bitmap.recycle();
    }
}
