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
      List<Product> products = productsAll();
        return ok(views.html.product.index.render(products));
    }
  
    public static Result newArrival() {
      return ok(views.html.product.arrival.render());
    }

    public static Result arrival() {
      List<Product> products = productsAll();
      return ok(views.html.product.index.render(products));
    }

    protected static List<Product> productsAll() {
      Finder<Long, Product> finder = new Finder<Long, Product>(Long.class, Product.class);
      List<Product> products = finder.all();
      return products;
    }
}
