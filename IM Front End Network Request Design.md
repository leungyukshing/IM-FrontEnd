# IM FrontEnd Network Request Design Doc

**Date: ** 2019.10.13

## Goal

This document clarifies the network request in FrontEnd

## DataType

We use PB to transport.

## Network Connection

Retrofit + okhttp

## Message Push

+ when a user send a new message, we need to post it to the server.
+ and we should receive any push messages from the server.

Generally, we use two connection manager: retrofit and websocket:

+ Retrofit is responsible for most request from front-end to back-end
+ Websocket acts as a listener to receive pushed messsages from back-end. Also link to notification services.

## Reference

1. [Android use pb](https://www.jianshu.com/p/acbc7df5decd?utm_source=oschina-app)
2. [Retrofit + okHttp + Rxjava](https://juejin.im/entry/58a8faf38d6d81005836195d)
3. [use example](https://github.com/SYSUcarey/FTEReader-Android/blob/master/code/Group13/FinalProject/app/src/main/java/fte/finalproject/service/BookService.java)
4. [Retrofit + Rxjava + pb](https://blog.csdn.net/qq137722697/article/details/81630666)
5. [Okhttp Interceptor](https://blog.csdn.net/RockyHua/article/details/80079621)
6. [Android websocket example1](https://www.jianshu.com/p/9302f8552a7d)
7. [Android websocket example2](https://www.jianshu.com/p/7b919910c892)
8. [Android Protobuf conversion](https://blog.csdn.net/duanbokan/article/details/51029240)

