package com.jianshengd.permission;

/**
 * 权限申请接口
 *
 * @author jianshengd
 * @date 2019/2/26 19:57
 */
public interface IPermission {
    /**
     * 已经授权
     */
    void ganted();
    /**
     * 取消授权
     */
    void canceled();
    /**
     * 已拒绝，并且点了不再提示
     */
    void denied();
}
