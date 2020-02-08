# IM Front End CI Design

This document states the CI for IM Front End

## Goal

Our development process can be described simply in the following steps:

1. commit code
2. pack a test version apk
3. upload test apk to some platform(https://www.pgyer.com/app/distribution)
4. inform testing crews

With CI, we simplify the above process to:

1. commit code
2. use `git tag` to pack a test version, describe the feature in tag.
3. Travis test our code and generate an apk, uploading to Github and other platform.

As a result, developer only need to focus on writing codes.

## Reference

1. [Android CI Travis example1](https://juejin.im/post/5b2efc34e51d45588346668e)
2. [Android CI Travis example2](https://xucanhui.com/2018/12/15/android-travis-ci/)
3. [Github Action auto-build Android1](http://blog.yoqi.me/lyq/16666.html)
4. [Github Action auto-build Android2](https://blog.csdn.net/xx326664162/article/details/103921480)
5. [Android Unit Test](https://www.jianshu.com/p/472c4c35efdb)