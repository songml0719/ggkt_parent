package com.atguigu.ggkt.user.service;


import com.atguigu.ggkt.model.user.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author songml
 * @since 20230401
 */
public interface UserInfoService extends IService<UserInfo> {

    UserInfo getUserInfoOpenid(String openId);
}
