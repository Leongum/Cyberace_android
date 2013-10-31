package com.G5432.Cyberace.ShareSNS;

import cn.sharesdk.framework.TitleLayout;
import cn.sharesdk.framework.authorize.AuthorizeAdapter;
import cn.sharesdk.tencent.qzone.QZone;


/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-30
 * Time: 下午4:45
 * To change this template use File | Settings | File Templates.
 */
public class MySNSAdapter extends AuthorizeAdapter {

    public void onCreate() {
        // 隐藏标题栏右部的Share SDK Logo
        hideShareSDKLogo();

        String platName = getPlatformName();
        if (QZone.NAME.equals(platName)) {
            TitleLayout llTitle = getTitleLayout();
            llTitle.getTvTitle().setText("QQ登录");
        }
    }
}
