# redis 存入 session共享

## 思路
1. 登陆成功之后将用户信息存入redis中 唯一标识 并写入客户端的cookie或header里面
1. 每次客户端请求都带cookie或header标识用户身份
1. 服务器端拦截器通过获取客户端的cookie或header的信息对比redis的信息进而查找出用户信息数据