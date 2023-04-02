
--create table users (
--username varchar(50) not null primary key,
--password varchar(500) not null,
--authority enum('ADMIN', 'USER') not null
--);

--/*1.テーブル定義を複製する*/
--CREATE TABLE inventories_bk LIKE inventories;
--
--/*2.複製したテーブル定義を確認する*/
--show full columns from inventories_bk;
--
--/*3.既存テーブルのデータを新規テーブルに挿入する*/
--INSERT INTO inventories_bk SELECT * FROM inventories;
--
--/*4.新規テーブルの中身を確認する*/
--select * from inventories_bk;

--テーブル自体の削除
--drop table inventories;

--inventoriesのバックアップテーブル
--SELECT * INTO inventories_bk FROM inventories;

create table inventories (
id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
inventoryname varchar(50) NOT NULL,
username varchar(50) NOT NULL,
stock int(99) NOT NULL,
remarks VARCHAR(256) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at datetime DEFAULT CURRENT_TIMESTAMP
);