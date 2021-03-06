package com.github.srang.skiot.trails;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/**
 * Created by samuelrang on 6/11/2017.
 */
public class DataVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {

    JsonObject config = Vertx.currentContext().config();

    String uri = config.getString("mongo_uri");
    if (uri == null) {
      uri = "mongodb://client:password@mongodb/skiot";
    }
    String db = config.getString("mongo_db");
    if (db == null) {
      db = "skiot";
    }

    JsonObject mongoconfig = new JsonObject()
        .put("connection_string", uri)
        .put("db_name", db);

    MongoClient mongoClient = MongoClient.createShared(vertx, mongoconfig);

    JsonObject product1 = new JsonObject().put("itemId", "12345").put("name", "Cooler").put("price", "100.0");

    mongoClient.save("products", product1, id -> {
      System.out.println("Inserted id: " + id.result());

      mongoClient.find("products", new JsonObject().put("itemId", "12345"), res -> {
        System.out.println("Name is " + res.result().get(0).getString("name"));

        mongoClient.remove("products", new JsonObject().put("itemId", "12345"), rs -> {
          if (rs.succeeded()) {
            System.out.println("Product removed ");
          }
        });

      });

    });

  }
}
