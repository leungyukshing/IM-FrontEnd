## IM Project--Front End

This is the FE of IM.

### Basic View

+ Welcome: Welcome Page, only show 1 time
+ MainPage
+ LoginOrRegister
+ GuidePage

## Server



---

## Design

### View

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

## Logic

- Welcome
- MainPage
- LoginOrRegister
- GuidePage

---
## Docs
 + [FrontEnd Database Design](https://github.com/leungyukshing/IM-FrontEnd/blob/master/IM%20Front%20End%20Database%20Design.md)
 + [FrontEnd Network Request Design](https://github.com/leungyukshing/IM-FrontEnd/blob/master/IM%20Front%20End%20Network%20Request%20Design.md)
 + [FrontEnd CI Design](https://github.com/leungyukshing/IM-FrontEnd/blob/master/IM%20Front%20End%20CI%20Design.md)


## TimeLine

+ 2019.8.23: Init
+ 2019.9.2: Finish Basic Views, testing on phone successfully.
+ 2019.10.13: Discuss the network connection part

---

## reference

1. [ViewPager使用](<https://www.jianshu.com/p/9faa1fc3f527>)
2. [自定义控件](https://blog.csdn.net/guolin_blog/article/details/17357967)
