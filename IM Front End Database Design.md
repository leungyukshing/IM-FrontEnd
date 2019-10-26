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

| Name    | Type    | Key         | Note                                         |
| ------- | ------- | ----------- | -------------------------------------------- |
| id      | int     | primary key | chat record id(auto increment)               |
| chat_id | int     | foreign key | to indicate this record belong to which chat |
| user_id | int     | foreign key | to indicate who send this record             |
| content | varchar |             | message content                              |
| time    | varchar |             | record created time                          |

