# IM Front End UI Design Doc

This doc mainly introduce the User Interaction design of IM

## Basic View

+ Welcome: Welcome Page, only show 1 time
+ MainPage
+ LoginOrRegister
+ GuidePage

---

## View

- Welcome
  - ViewPager: support slide to left or right.
- MainPage:
  -  self-define components: 
     -  TitleBar
     -  LayoutSlide
     -  PictureAndTextButton
  -  3 Tab
     -  Chats: RecyclerView, ChatsLayout(UserItem)
     -  Contacts: RecyclerView, ContactsLayout(UserItem)
     -  Moments: RecyclerView, MomentsLayout
- LoginOrRegister: TabHost
- GuidePage: MultiView, use select to indicate which page the user is watching.
- DressUp: choose avatar and background
  - choose_image.xml
- Profile: EditText for editing user information
- Setting: some options saved in local cache

---

## reference

1. [ViewPager使用](<https://www.jianshu.com/p/9faa1fc3f527>)
2. [自定义控件](https://blog.csdn.net/guolin_blog/article/details/17357967)
