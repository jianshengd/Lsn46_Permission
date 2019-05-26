package com.jianshengd.permission;

import android.Manifest;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jianshengd.permission.annotation.Permission;
import com.jianshengd.permission.annotation.PermissionCanceled;
import com.jianshengd.permission.annotation.PermissionDenied;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
    }
    @Permission(value = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},requestCode = 200)
    public void requestPermission(){
        Toast.makeText(this,"请求权限",Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(requestCode = 200)
    public void deny(){
        Toast.makeText(this,"拒绝",Toast.LENGTH_SHORT).show();
    }
    @PermissionCanceled(requestCode = 200)
    public void cancel(){
        Toast.makeText(this,"取消",Toast.LENGTH_SHORT).show();
    }
}
