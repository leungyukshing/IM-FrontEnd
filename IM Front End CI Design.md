# IM Front End CI Design

This document states the CI for IM Front End

## Goal

Our development process can be described simply in the following steps:

1. commit code
2. pack a test version apk
3. upload test apk to some platform
4. inform testing crews

With CI, we simplify the above process to:

1. commit code
2. use `git tag` to pack a test version, describe the feature in tag.
3. Travis test our code and generate an apk, uploading to Github and other platform.

As a result, developer only need to focus on writing codes.

## Reference

1. [Android CI Travis example1](https://juejin.im/post/5b2efc34e51d45588346668e)
2. [Android CI Travis example2](https://xucanhui.com/2018/12/15/android-travis-ci/)