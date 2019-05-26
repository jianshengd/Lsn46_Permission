package com.jianshengd.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PermissionActivity extends AppCompatActivity {
    private static final String PARAM_PERMISSION = "PARAM_PERMISSION";
    private static final String PARAM_REQUEST_PERMISSION = "PARAM_REQUEST_PERMISSION";


    private static IPermission permissionListen;

    public static void requestPermission(Context context,String[]permissions,int requestCode,IPermission iPermission){
        permissionListen = iPermission;
        Intent intent = new Intent(context,PermissionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(PARAM_PERMISSION,permissions);
        intent.putExtra(PARAM_REQUEST_PERMISSION,requestCode);

        context.startActivity(intent);
        if (context instanceof Activity){
            //屏蔽进入动画
            ((Activity)context).overridePendingTransition(0,0);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        String[] permissions = getIntent().getStringArrayExtra(PARAM_PERMISSION);
        int requestCode = getIntent().getIntExtra(PARAM_REQUEST_PERMISSION,-1);

        if (permissions == null || permissionListen == null){
            finish();
            return;
        }

        //权限是否已被授予
        if (PermissionUtils.hasPermission(this,permissions)){
            permissionListen.ganted();
            finish();
            return;
        }
        //权限申请
        ActivityCompat.requestPermissions(this,permissions,requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //权限请求成功
        if (PermissionUtils.verifyPermission(this,grantResults)){
            permissionListen.ganted();
            finish();
            return;
        }
        //用户点击不再提示,提示UI，跳转到设置
        if (!PermissionUtils.shouldShowRequestPermissionRationale(this,permissions)){
            permissionListen.denied();
            finish();
            return;
        }
        permissionListen.canceled();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        permissionListen = null;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}
