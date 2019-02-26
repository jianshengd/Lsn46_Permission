package com.jianshengd.permission.core;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.jianshengd.permission.IPermission;
import com.jianshengd.permission.PermissionActivity;
import com.jianshengd.permission.PermissionUtils;
import com.jianshengd.permission.annotation.Permission;
import com.jianshengd.permission.annotation.PermissionCanceled;
import com.jianshengd.permission.annotation.PermissionDenied;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class PermissionAspect {

    private static final String TAG = "PermissionAspect";

    @Pointcut("execution(@com.jianshengd.permission.annotation.Permission * *(..)) && @annotation(permission)")
    public void requestPermission(Permission permission) {

    }

    @Around("requestPermission(permission)")
    public void aroundJointPoint(final ProceedingJoinPoint joinPoint, Permission permission) throws Throwable{

        //初始化context
        Context context = null;

        final Object object = joinPoint.getThis();
        if (joinPoint.getThis() instanceof Context) {
            context = (Context) object;
        } else if (joinPoint.getThis() instanceof Fragment) {
            context = ((Fragment) object).getActivity();
        }

        if (context == null || permission == null) {
            Log.d(TAG, "aroundJonitPoint error ");
            return;
        }

        final Context finalContext = context;
        PermissionActivity.requestPermission(context, permission.value(), permission.requestCode(), new IPermission() {
            @Override
            public void ganted() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            /**
             * 取消授权
             */
            @Override
            public void canceled() {
                PermissionUtils.invokAnnotation(object, PermissionCanceled.class);

            }
            @Override
            public void denied() {
                PermissionUtils.invokAnnotation(object, PermissionDenied.class);
                PermissionUtils.goToMenu(finalContext);
            }
        });

    }



}
