package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import java.util.List;
import java.util.Date;

import models.Product;
import play.db.ebean.Model.Finder;

public class Materia extends Controller {
  
    public static Result index() {
      Product product = new Product();
      product.name = "えんぴつ";
      product.price = new Long(100);
      product.quantity = new Long(250);
      product.location = "東京";
      product.updateDate = new Date();
      product.save();

      Finder<Long, Product> finder = new Finder<Long, Product>(Long.class, Product.class);
      List<Product> products = finder.all();
      StringBuilder msg = new StringBuilder();
      msg.append("Products:\n");
      for (Product p : products) {
                  msg.append(p.toString()).append("\n");
      }
        return ok(msg.toString());
    }
  
}
