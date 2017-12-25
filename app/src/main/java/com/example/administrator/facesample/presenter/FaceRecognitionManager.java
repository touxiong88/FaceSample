package com.example.administrator.facesample.presenter;

import android.graphics.Bitmap;

import com.example.administrator.facesample.model.FaceServer;
import com.example.administrator.facesample.model.ReFaceModel;
import com.example.administrator.facesample.view.FaceRecognitionView;


/**
 * Created by liujie on 2017/7/6.
 */

public class FaceRecognitionManager {
    ReFaceModel reFaceModel;
    FaceRecognitionView mFaceRecognitionView;
    public FaceRecognitionManager(FaceRecognitionView faceRecognitionView){
        this.mFaceRecognitionView = faceRecognitionView;
        reFaceModel = new FaceServer();
    }
    public void recognitFace(){
        Bitmap bitmap = mFaceRecognitionView.getFaceImage();
        /*reFaceModel.faceRecognition(base64String, new FaceServer.FaceRecogCallback() {
            @Override
            public void onSuccess(FaceResponseBean faceResponseBean ) {
                if (faceResponseBean != null){
                    if (faceResponseBean.getCode() == 200){
                        String Face_id = faceResponseBean.getMember_id();
                        if(Face_id == null){
                            mFaceRecognitionView.onRecongitError(Constant.SERVER_ERROR_INFORMATION_ID_IS_EMPTY);
                        }
                        Long face_Id = Long.parseLong(Face_id);
                        PersonData personData = PersonDataDbUtil.queryData(face_Id);
                        //mFaceRecognitionView.onRecongitSuccess(personData);
                    }else {
                        if(faceResponseBean.getMsg().contains("not to find in the database")){
                            mFaceRecognitionView.onRegister();
                        }else{
                            mFaceRecognitionView.onRecongitError("no face");
                        }
                    }
                }
            }

            @Override
            public void onFailure(String errormessage) {
                mFaceRecognitionView.onRecongitError(errormessage);
            }
        });*/
    }
}
