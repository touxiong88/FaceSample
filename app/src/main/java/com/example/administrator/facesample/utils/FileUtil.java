package com.example.administrator.facesample.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by liujie on 2017/7/5.
 */

public class FileUtil {
    private FileUtil(){}

    /** 保存方法 */
    public static void saveBitmap(String picName, Bitmap bm) {

        String path = Environment.getExternalStorageDirectory().getPath();
        File f = new File(path+"/temp/", picName);
        if (f.exists()) {
            f.delete();
        }
        f.getParentFile().mkdirs();
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
