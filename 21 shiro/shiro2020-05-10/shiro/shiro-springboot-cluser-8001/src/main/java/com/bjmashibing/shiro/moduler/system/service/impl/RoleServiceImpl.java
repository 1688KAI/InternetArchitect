package com.bjmashibing.shiro.moduler.system.service.impl;

import com.bjmashibing.shiro.moduler.system.entity.Role;
import com.bjmashibing.shiro.moduler.system.service.RoleService;
import com.bjmashibing.shiro.moduler.system.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author 孙志强
 * @since 2020-04-13
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
