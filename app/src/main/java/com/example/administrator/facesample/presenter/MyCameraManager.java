package com.example.administrator.facesample.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.FaceDetector;
import android.util.Log;
import android.view.SurfaceHolder;


import com.example.administrator.common.Config;
import com.example.administrator.facesample.utils.CamParaUtil;
import com.example.administrator.facesample.view.CameraView;

import java.io.ByteArrayOutputStream;



/**
 * Created by liujie on 2017/7/4.
 */

public class MyCameraManager implements FaceCheckCallback {
    public static final String TAG = "MyCameraManager";
    private Camera mCamera;
    private Camera.Parameters parameters;
    private CameraView mCameraView;
    private boolean isPreviewing = false;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceHolder befmSurfaceHolder;

    private int errornum = 0;
    private volatile int threadNum = 0;
    private boolean faceCheckFlag = true;
    public  MyCameraManager(CameraView cmView){
        mCameraView = cmView;
    }
    public void open(SurfaceHolder surface){
        setFaceCheckFlag(true);
        //获取摄像头管理
        mCamera = Camera.open(Config.mCameraID);// 开启摄像头
        mSurfaceHolder = surface;
        try{
            mCamera.setPreviewDisplay(mSurfaceHolder);//set the surface to be used for live preview
        } catch (Exception ex){
            if(null != mCamera)
            {
                mCamera.release();
                mCamera = null;
            }
            mCameraView.onToastError("摄像头开启出错");
            //Logger.v("打开相机失败");
        }
    }
    public void setbefmSurfaceHolder(SurfaceHolder surfaceHolder){
            befmSurfaceHolder = surfaceHolder;
    }
    /**
     * 开始预览
     */
    public void takePreview() {
       // Logger.v("takePreview");
        if(mCamera != null){
            if(isPreviewing){
                mCamera.stopPreview();
                return;
            }
            parameters = mCamera.getParameters();
            parameters.setFlashMode("off"); // 无闪光灯
            parameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
            parameters.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            parameters.setPreviewFormat(ImageFormat.NV21);
            //mCamera.setDisplayOrientation(90);

            CamParaUtil.getInstance().printSupportPictureSize(parameters);
            CamParaUtil.getInstance().printSupportPreviewSize(parameters);
            Camera.Size pictureSize = CamParaUtil.getInstance().getPropPictureSize(
                    parameters.getSupportedPictureSizes(), Config.previewRate, 800);
            parameters.setPictureSize(pictureSize.width, pictureSize.height);
            Camera.Size previewSize = CamParaUtil.getInstance().getPropPreviewSize(
                    parameters.getSupportedPreviewSizes(), Config.previewRate
                    , 800);



            parameters.setPictureSize(pictureSize.width, pictureSize.height);
            parameters.setPreviewSize(previewSize.width, previewSize.height);
            mCamera.setParameters(parameters);
            //Logger.v("setPreviewCallback前" + pictureSize.width + "//" + pictureSize.height + " oo "+ previewSize.width + "//" + previewSize.height);
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {
                   Log.v(TAG,"setPreviewCallback");
                    Camera.Size size = camera.getParameters().getPreviewSize();
                    YuvImage image = new YuvImage(bytes, ImageFormat.NV21, size.width, size.height, null);
                    if(bytes != null){
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        image.compressToJpeg(new Rect(0, 0, size.width, size.height), 50, out);
                        byte[] datas = out.toByteArray();
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        Bitmap mBitmap = BitmapFactory.decodeByteArray(datas, 0, datas.length, options);
                        long l = System.nanoTime();
                        //FileUtil.saveBitmap("cccc/"+l+"ee.png", mBitmap);
                        Matrix matrix = new Matrix();
                        //matrix.postRotate((float)90);
                        matrix.postScale(0.4f, 0.3125f); //照片的大小使 1280*960 屏幕的大小使 1024*600 这里需要注意换算比例
                       // Logger.v("MyCameraManager faceCheckFlag");
                        //synchronized (this) {
                            //Logger.v("MyCameraManager synchronized");
                            //if(faceCheckFlag){
                                //setFaceCheckFlag(false);
                                //Logger.v("MyCameraManager synchronized" + errornum + mBitmap.getWidth() + "dd " + mBitmap.getHeight());
                                Bitmap bitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, false);
                                FaceCheckManager faceCheckManager = new FaceCheckManager.Builder().setCmView(mCameraView).setImgData(bitmap).create();
                                faceCheckManager.findFace(MyCameraManager.this, bitmap);
                        Log.v(TAG,"setPreviewCallback MyCameraManager");
                            //}
                       // }
                        mBitmap.recycle();
                    }
                }
            });
           // Logger.v("setPreviewCallback后");
            mCamera.startPreview();
            isPreviewing = true;
        }
    }

    Paint paint = new Paint();
    {
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.5f);//设置线宽
        paint.setAlpha(100);
    };
    public void close(){
        if(null != mCamera){
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            isPreviewing = false;
            mCamera.release();
            mCamera = null;
        }
    }

  /*  public void setCameraDisplayOrientation(int paramInt, Camera paramCamera){
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(paramInt, info);
        int rotation = ((WindowManager)getSystemService("window")).getDefaultDisplay().getRotation();  //获得显示器件角度
        int degrees = 0;
        Log.i(TAG,"getRotation's rotation is " + String.valueOf(rotation));
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        orientionOfCamera = info.orientation;      //获得摄像头的安装旋转角度
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        paramCamera.setDisplayOrientation(result);  //注意前后置的处理，前置是映象画面，该段是SDK文档的标准DEMO
    }*/
    @Override
    public void onResult(int faceNum, final FaceDetector.Face[] faceData, final Bitmap bitmap) {
            //Logger.v("MyCameraManager 发现了几个脸" +faceNum);
            Log.v(TAG,"MyCameraManager 发现了几个脸" +faceNum);
            if(faceNum <= 0){
                errornum ++;
                if(errornum>2){
                    clear();
                    errornum = 0;
                }
                setFaceCheckFlag(true);
                return;
            }
            if(befmSurfaceHolder != null ){//&& threadNum < 6)
                //锁定整个SurfaceView
                   /* new Thread(){
                        @Override
                        public void run() {
                            super.run();*/
                           // threadNum ++;
                            Canvas mCanvas = befmSurfaceHolder.lockCanvas();
                            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                            for(FaceDetector.Face face : faceData){
                                if (face == null) {
                                    break;
                                }
                                errornum = 0;
                                //mCanvas.drawBitmap(MainApplication.mRotaBitmap, 0, 0 ,paint);
                                PointF pointF = new PointF();
                                face.getMidPoint(pointF);
                                float eyesDistance = face.eyesDistance();

                                //Logger.v("onResult"+ pointF.x + "_" + pointF.y);
                                mCanvas.drawRect(new Rect((int)(pointF.x - eyesDistance)*2,
                                                (int)(pointF.y- eyesDistance)*2,
                                                (int)(pointF.x + eyesDistance)*2,
                                                (int)(pointF.y + eyesDistance)*2),paint);//绘制矩形
                            }
                            befmSurfaceHolder.unlockCanvasAndPost(mCanvas);
                           // threadNum --;
                            //mCameraView.onDetectFace(bitmap);
                        }

                    //}.start();

        //}
    }
    public void setFaceCheckFlag(boolean faceCheckFlag){
        synchronized (this){
            this.faceCheckFlag = faceCheckFlag;
           // Logger.v("MyCameraManager" + faceCheckFlag);
        }
    }
    private void clear() {
        Canvas mCanvas2 = befmSurfaceHolder.lockCanvas();
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mCanvas2.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        befmSurfaceHolder.unlockCanvasAndPost(mCanvas2);
    }
    @Override
    public void onError(String errormsg) {
        //Logger.e(errormsg);
        mCameraView.onToastError(errormsg);
    }
}
