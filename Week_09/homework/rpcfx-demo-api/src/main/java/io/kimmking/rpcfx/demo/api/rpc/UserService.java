package io.kimmking.rpcfx.demo.api.rpc;

import io.kimmking.rpcfx.demo.api.dto.User;

public interface UserService {

    User findById(int id);

}
