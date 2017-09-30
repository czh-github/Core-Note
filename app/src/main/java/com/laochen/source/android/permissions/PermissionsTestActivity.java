package com.laochen.source.android.permissions;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsGranted;
import com.joker.annotation.PermissionsRationale;
import com.joker.api.Permissions4M;
import com.laochen.jni.R;

public class PermissionsTestActivity extends AppCompatActivity {
    private Button button;
    private static final int REQUEST_CODE_RECORD_AUDIO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions_test);
        button = (Button) findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permissions4M.get(PermissionsTestActivity.this)
                        .requestForce(true)
                        .requestPageType(Permissions4M.PageType.MANAGER_PAGE)
                        .requestPermission(Manifest.permission.RECORD_AUDIO)
                        .requestCode(REQUEST_CODE_RECORD_AUDIO)
                        .request();

            }
        });
    }

//    授权成功时回调，注解中需要传入参数，分为两种情况：单参数和多参数
    @PermissionsGranted(REQUEST_CODE_RECORD_AUDIO)
    public void granted() {
        Toast.makeText(this, "录音授权成功", Toast.LENGTH_SHORT).show();
    }

//    授权失败时回调，注解中需要传入参数，分为两种情况：单参数和多参数
    @PermissionsDenied(REQUEST_CODE_RECORD_AUDIO)
    public void denied() {
        Toast.makeText(this, "录音授权失败", Toast.LENGTH_SHORT).show();
    }

//    二次授权时回调，用于解释为何需要此权限，注解中需要传入参数，分为两种情况：单参数和多参数
    @PermissionsRationale(REQUEST_CODE_RECORD_AUDIO)
    public void rational() {
        Toast.makeText(this, "请开启录音权限", Toast.LENGTH_SHORT).show();
    }

//    注：系统弹出权限申请 dialog 与 toast 提示是异步操作，所以如果存在希望自行弹出一个 dialog 后
//   （或其他同步需求）再弹出系统对话框，那么请使用 @PermissionsCustomRationale
//    @PermissionsCustomRationale(REQUEST_CODE_RECORD_AUDIO)
//    public void cusRational() {
//        new AlertDialog.Builder(this)
//                .setMessage("请开启录音权限")
//                .setPositiveButton("确定", new Di)
//    }

}
