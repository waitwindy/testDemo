package com.gbicc.arangoDb;


import java.util.*;

import com.arangodb.*;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.BaseEdgeDocument;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.model.AqlQueryOptions;
import com.arangodb.util.MapBuilder;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocypack.exception.VPackException;
import org.junit.Test;

public class arangoDbtest {
    public static void main(String[] args) {
        ArangoDB arangoDB = new ArangoDB.Builder().host("192.168.172.100",8529).user("root").password("123456").build();
//        create db
        String dbName = "gbicc";
        try {
            arangoDB.createDatabase(dbName);
            System.out.println("Database created: " + dbName);
        } catch (ArangoDBException e) {
            System.err.println("Failed to create database: " + dbName + "; " + e.getMessage());
        }

//      创建集合
        String collectionName = "firstCollection";
        try {
            CollectionEntity myArangoCollection = arangoDB.db(dbName).createCollection(collectionName);
            System.out.println("Collection created: " + myArangoCollection.getName());
        } catch (ArangoDBException e) {
            System.err.println("Failed to create collection: " + collectionName + "; " + e.getMessage());
        }

//      创建文档
        BaseDocument myObject = new BaseDocument();
        myObject.setKey("myKey");
        myObject.addAttribute("a", "Foo");
        myObject.addAttribute("b", 42);
        try {
            arangoDB.db(dbName).collection(collectionName).insertDocument(myObject);
            System.out.println("Document created");
        } catch (ArangoDBException e) {
            System.err.println("Failed to create document. " + e.getMessage());
        }

//      read document
        try {
            BaseDocument myDocument = arangoDB.db(dbName).collection(collectionName).getDocument("myKey",
                    BaseDocument.class);
            System.out.println("Key: " + myDocument.getKey());
            System.out.println("Attribute a: " + myDocument.getAttribute("a"));
            System.out.println("Attribute b: " + myDocument.getAttribute("b"));
        } catch (ArangoDBException e) {
            System.err.println("Failed to get document: myKey; " + e.getMessage());
        }

//      更新文档
        myObject.addAttribute("c", "Bar");
        try {
            arangoDB.db(dbName).collection(collectionName).updateDocument("myKey", myObject);
        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
//      再次阅读文档
        try {
            BaseDocument myUpdatedDocument = arangoDB.db(dbName).collection(collectionName).getDocument("myKey",
                    BaseDocument.class);
            System.out.println("Key: " + myUpdatedDocument.getKey());
            System.out.println("Attribute a: " + myUpdatedDocument.getAttribute("a"));
            System.out.println("Attribute b: " + myUpdatedDocument.getAttribute("b"));
            System.out.println("Attribute c: " + myUpdatedDocument.getAttribute("c"));
        } catch (ArangoDBException e) {
            System.err.println("Failed to get document: myKey; " + e.getMessage());
        }

//      删除文档
        try {
            arangoDB.db(dbName).collection(collectionName).deleteDocument("myKey");
        } catch (ArangoDBException e) {
            System.err.println("Failed to delete document. " + e.getMessage());
        }
    }

    @Test
    public void insertGraph(){
        ArangoDB arangoDB = new ArangoDB.Builder().host("192.168.172.100", 8529).user("root").password("123456").build();
//        数据库
        ArangoDatabase db = arangoDB.db("AQLTest");
//        集合
        ArangoCollection coll = db.collection("EWriter");

        BaseDocument document = new BaseDocument();
        document.addAttribute("name","滚开");
        Set<String> books = new HashSet<>();
        books.add("巫师世界");
        books.add("神秘之旅");
        books.add("永恒剑主");
        books.add("剑道真解");
        books.add("极道天魔");
        document.addAttribute("books",books);
//      集合中插入document
        coll.insertDocument(document);

        ArangoCollection collbooks  = db.collection("Ebooks");

        BaseDocument document1 = new BaseDocument();
        document1.addAttribute("name","巫师世界");
        document1.addAttribute("writer","滚开");
        collbooks.insertDocument(document1);

        BaseDocument document2 = new BaseDocument();
        document2.addAttribute("name","神秘之旅");
        document2.addAttribute("writer","滚开");
        collbooks.insertDocument(document2);

        BaseDocument document3 = new BaseDocument();
        document3.addAttribute("name","永恒剑主");
        document3.addAttribute("writer","滚开");
        collbooks.insertDocument(document3);

        BaseDocument document4 = new BaseDocument();
        document4.addAttribute("name","剑道真解");
        document4.addAttribute("writer","滚开");
        collbooks.insertDocument(document4);

        BaseDocument document5 = new BaseDocument();
        document5.addAttribute("name","极道天魔");
        document5.addAttribute("writer","滚开");
        collbooks.insertDocument(document5);

    }

    @Test
    public void  getGraphPicture(){
        long time = System.currentTimeMillis()/1000;
        ArangoDB arangoDB = new ArangoDB.Builder().host("127.0.0.1", 8529).user("root").password("123456").build();
        //数据库
        ArangoDatabase db = arangoDB.db("AQLTest");
        //集合
        ArangoCollection coll =db.collection("QidianBooks");

        String queryCmmd = "for doc in @@collection return  doc";
        String queryCmmd2 = "for doc in @@collection return  doc";

        AqlQueryOptions options = new AqlQueryOptions();
        options.ttl(1000000);//持续时间
        Map map =new HashMap();
        map.put("@collection","Ebooks");
        Map map2 =new HashMap();
        map2.put("@collection","Ewriters");
        ArangoCursor<BaseEdgeDocument> cursor = db.query(queryCmmd, map, options, BaseEdgeDocument.class);
        int ii = 0;
        while (cursor.hasNext()) {
            ii++;
            BaseEdgeDocument object = cursor.next();
            String writes = object.getAttribute("writer").toString();
            String book = object.getAttribute("name").toString();
            String _to = object.getId();
            String _from = "";
            ArangoCursor<BaseEdgeDocument> cursorebooks = db.query(queryCmmd2, map2, options, BaseEdgeDocument.class);

            while (cursorebooks.hasNext()) {
                //Document
                BaseEdgeDocument object2 = cursorebooks.next();
                //BaseEdgeDocument object3 = new BaseEdgeDocument();object3.se
                if(object2.getAttribute("name").equals(writes)) {
                    _from = object2.getId();
                    BaseEdgeDocument baseDocument = new BaseEdgeDocument();
                    baseDocument.setFrom(_from);
                    baseDocument.setTo(_to);
                    baseDocument.addAttribute("relation","write");
                    baseDocument.addAttribute("status","active");
                    baseDocument.addAttribute("updataAt",time);
                    System.out.println(_to);
                    coll.insertDocument(baseDocument);
                    break;
                }
            }

        }

    }
}
