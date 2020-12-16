package io.kimmking.rpcfx.demo.provider.impl;

import io.kimmking.rpcfx.demo.api.dto.User;
import io.kimmking.rpcfx.demo.api.rpc.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        return new User(id, "KK" + System.currentTimeMillis());
    }
}
