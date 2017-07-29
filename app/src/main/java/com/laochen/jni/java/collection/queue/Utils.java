package com.laochen.jni.java.collection.queue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Date:2017/7/11 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class Utils {
    private static final String Util_LOG = "Utils";

    /**
     * 检查是否存在SD卡
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 创建目录
     *
     * @param context
     * @param dirName 文件夹名称
     * @return
     */
    public static File createFileDir(Context context, String dirName) {
        File dir = null;
        // 如SD卡已存在，则存储；反之存在data目录下
        if (hasSdcard()) {
            // SD卡路径
            dir = new File(Environment.getExternalStorageDirectory(), dirName);
        } else {
            dir = new File(context.getCacheDir().getPath(), dirName);
        }
        if (!dir.exists()) {
            boolean isCreate = dir.mkdirs();
            Log.i(Util_LOG, dir + " has created. " + isCreate);
        }
        return dir;
    }

    /**
     * 删除文件（若为目录，则递归删除子目录和文件）
     *
     * @param file
     * @param delThisPath true代表删除参数指定file，false代表保留参数指定file
     */
    public static void delFile(File file, boolean delThisPath) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                int num = subFiles.length;
                // 删除子目录和文件
                for (int i = 0; i < num; i++) {
                    delFile(subFiles[i], true);
                }
            }
        }
        if (delThisPath) {
            file.delete();
        }
    }

    /**
     * 获取文件大小，单位为byte（若为目录，则包括所有子目录和文件）
     *
     * @param file
     * @return
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] subFiles = file.listFiles();
                if (subFiles != null) {
                    int num = subFiles.length;
                    for (int i = 0; i < num; i++) {
                        size += getFileSize(subFiles[i]);
                    }
                }
            } else {
                size += file.length();
            }
        }
        return size;
    }

    /**
     * 保存Bitmap到指定目录
     *
     * @param dir      目录
     * @param fileName 文件名
     * @param bitmap
     * @throws IOException
     */
    public static void saveBitmap(String dir, String fileName, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        File file = new File(dir, fileName);
        FileOutputStream fos = null;
        try {
            if (file.createNewFile()) {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Bitmap getBitmapFromFile(String dir, String fileName) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        File file = new File(dir, fileName);
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        }
        return null;
    }

    /**
     * 判断某目录下文件是否存在
     *
     * @param dir      目录
     * @param fileName 文件名
     * @return
     */
    public static boolean isFileExists(String dir, String fileName) {
        return new File(dir, fileName).exists();
    }


}
