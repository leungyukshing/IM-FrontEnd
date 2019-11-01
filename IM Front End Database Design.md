# IM Front End Database Design

This document shows the blueprint of front-end database design of IM.

## Intro

In IM's database structure, there are two main databases. One is for front-end, which is an embedded database, used to store local informations. The other is for back-end, used to store all user information. In this document, only front-end database is discussed.

---

## Techs

I try to use [Android SQLite](https://www.tutorialspoint.com/android/android_sqlite_database.htm) to implement. Store the database in the default path([SQLite Database Store path](https://blog.csdn.net/brucezcg/article/details/10208979))

---

## Content 

In this part, I present what content should be stored locally and how to store this content. I try to give the schema of tables in details.

In local database, I mainly store the chatting contents. Because chatting data is too large to store in remote database. And it's unnecessary to sync chatting data to the remote database frequently. We can allow users to store their chatting record manually by clicking a button.(This feature is optional).

There is one table: **chat_record**

### chat_record

| Name        | Type     | Key         | Note                                         |
| ----------- | -------- | ----------- | -------------------------------------------- |
| id          | int      | primary key | chat record id(auto increment)               |
| chat_id     | int      | foreign key | to indicate this record belong to which chat |
| sender_id   | int      | foreign key | to indicate who send this record             |
| sender_name | varchar  |             | to indicate who send this record             |
| content     | varchar  |             | message content                              |
| time        | datetime |             | record created time                          |
| encryptkey  | varchar  |             | key used to encrypt info                     |

---

## Reference

1. [Android SQLite Usage](https://blog.csdn.net/midnight_time/article/details/80834198)
2. [Encapsulate SQLiteDatabase](https://blog.csdn.net/jian11058/article/details/89239010)
3. [Android SQLite Multi-select Query](https://blog.csdn.net/luoshen87/article/details/74784161)

