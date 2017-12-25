package com.example.administrator.facesample.model;


import com.example.administrator.facesample.bean.FaceResponseBean;

/**
 * Created by liujie on 2017/7/6.
 */

public class FaceServer implements ReFaceModel {
    @Override
    public void registFace(final String imgdata, final String ID, final FaceRegistCallback callback) {
       /* new Thread(){
            @Override
            public void run() {
                super.run();
                FaceHttpnetUtil.httpPostJson(false, imgdata, ID, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callback.onFailure(Constant.NETWORK_ACCESS_FAILURE);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        if (response.isSuccessful()) {
                            String responseBodystr = response.body().string();
                            Gson gson = new Gson();
                            FaceResponseBean faceResponseBean = gson.fromJson(responseBodystr, FaceResponseBean.class);
                            Log.d("TIEJIANG", "onResponse: " + responseBodystr);
                            callback.onSuccess(faceResponseBean);
                        }else{
                            callback.onFailure("注册失败");
                        }
                    }
                });
            }
        }.start();*/
    }

    @Override
    public void faceRecognition(final String base64String, final FaceRecogCallback callback) {
       /* new Thread(){
            @Override
            public void run() {
                super.run();
                FaceHttpnetUtil.httpPostJson(true, base64String, "0", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callback.onFailure(Constant.NETWORK_ACCESS_FAILURE);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String onResponsebody = response.body().string();
                            Logger.w("onResponse: " + onResponsebody);
                            Gson gson = new Gson();
                            FaceResponseBean faceResponseBean = gson.fromJson(onResponsebody, FaceResponseBean.class);
                            callback.onSuccess(faceResponseBean);
                        }else{
                            callback.onFailure("识别失败");
                        }
                    }
                });
            }
        }.start();*/
    }

    @Override
    public void deleteFaceData(final String member_id, final FaceDataDeleteCallback callback) {
        /*new Thread(){
            @Override
            public void run() {
                super.run();
                FaceHttpnetUtil.httpPostJson_Delete(member_id, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callback.onFailure(Constant.NETWORK_ACCESS_FAILURE);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String onResponsebody = response.body().string();
                            Logger.v("onResponse: " + onResponsebody);
                            Gson gson = new Gson();
                            FaceResponseBean faceResponseBean = gson.fromJson(onResponsebody, FaceResponseBean.class);
                            callback.onSuccess(faceResponseBean);
                        }else{
                            callback.onFailure("识别失败");
                        }
                    }
                });
            }
        }.start();*/
    }

    public interface FaceRecogCallback{
            public void onSuccess(FaceResponseBean faceResponseBean);
            public void onFailure(String errormessage);

    }
    public interface  FaceRegistCallback{
        public void onSuccess(FaceResponseBean faceResponseBean);
        public void onFailure(String errormessage);
    }
    public interface  FaceDataDeleteCallback{
        public void onSuccess(FaceResponseBean faceResponseBean);
        public void onFailure(String errormessage);
    }
}
