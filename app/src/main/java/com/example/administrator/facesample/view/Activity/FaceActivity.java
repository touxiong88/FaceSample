package com.example.administrator.facesample.view.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.administrator.MainApplication;
import com.example.administrator.facesample.R;
import com.example.administrator.facesample.presenter.FaceRecognitionManager;
import com.example.administrator.facesample.presenter.MyCameraManager;
import com.example.administrator.facesample.view.CameraView;
import com.example.administrator.facesample.view.FaceRecognitionView;


public class FaceActivity extends Activity implements CameraView, FaceRecognitionView {
    MyCameraManager myCameraManager;
    SurfaceView aftSurfaceView, befSurfaceView;
    SurfaceHolder aftmSurfaceHolder, befSurfaceHolder;
    FaceRecognitionManager faceRecognitionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);
        initView();
        initData();
    }

    public void initView(){
        aftSurfaceView = (SurfaceView) findViewById(R.id.aftsurfaceview);
        befSurfaceView = (SurfaceView) findViewById(R.id.befsurfaceview);
       // zlView.setRect(200,300,400,500);
       // zlView.postInvalidate();
        //
    }
    public void initData(){
        myCameraManager = new MyCameraManager(this);
        faceRecognitionManager = new FaceRecognitionManager(this);
        aftmSurfaceHolder = aftSurfaceView.getHolder();
        //使的改sufaceview处于上面，并且透明
        befSurfaceView.setZOrderOnTop(true);
        befSurfaceView.setZOrderMediaOverlay(true);
        befSurfaceHolder = befSurfaceView.getHolder();
        // 让它背景透明，以便显示下面的内容
        befSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        aftmSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        aftmSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
               // Logger.v("surfaceCreated");
                myCameraManager.open(surfaceHolder);
                myCameraManager.setbefmSurfaceHolder(befSurfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
               // Logger.v("surfaceChanged");
                myCameraManager.takePreview();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                myCameraManager.close();
               // Logger.v("surfaceDestroyed");
            }
        });
    }
    //用Toast显示处理成功结果
    @Override
    public void onToastSuccess(final String success) {
        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showSuccess(success);
            }
        });*/
    }

    @Override
    //用Toast显示处理失败的结果
    public void onToastError(final String error) {
        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showWarning(error);
            }
        });*/
    }

    @Override
    public void onDetectFace(Bitmap bitmap) {
        myCameraManager.setFaceCheckFlag(false);
        MainApplication.mRotaBitmap = bitmap;
        faceRecognitionManager.recognitFace();
    }


    /**
     * 人脸识别后回调注册
     * 显示注册
     */
    @Override
    public void onRegister() {
       /* runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.setDialog(FaceActivity.this, "是否进行人脸注册", "我还不认识您，是否开始人脸注册？", "开始注册", "继续识别", new DialogUtil.ChooseResult() {
                    @Override
                    public void clickOK() {
                        Logger.v("click0k");
                        myCameraManager.close();
                        Intent intent = new Intent(FaceActivity.this, FaceRegisterActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void clickQX() {
                        Logger.v("clickQX");
                        ToastUtil.showWarning("已经取消!");
                        myCameraManager.setFaceCheckFlag(true);
                    }
                });
            }
        });*/
    }//

    @Override
    public void onRecongitError(final String msg) {
       /* runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showWarning(msg);
            }
        });
        myCameraManager.setFaceCheckFlag(true);*/
    }

    @Override
    public Bitmap getFaceImage() {
        return MainApplication.mRotaBitmap;
    }

    @Override
    protected void onStop() {
        super.onStop();
        myCameraManager.close();
    }
}
