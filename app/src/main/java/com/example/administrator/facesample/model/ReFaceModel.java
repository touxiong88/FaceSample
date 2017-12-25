package com.example.administrator.facesample.model;

import com.example.administrator.facesample.model.FaceServer;

/**
 * Created by liujie on 2017/7/4.
 */

public interface ReFaceModel {
    //开启注册
    public void  registFace(String imgdata, String ID, FaceServer.FaceRegistCallback callback);
    //人脸识别
    public void faceRecognition(String base64String, FaceServer.FaceRecogCallback callback);
    /**
     * 删除人脸信息
     */
    public void deleteFaceData(String member_id, FaceServer.FaceDataDeleteCallback callback);
}
