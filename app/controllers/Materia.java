package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

//TODO: separate to arrival controller
//import views.html.product.*;
//import views.html.arrival.*;
import views.html.*;

import java.util.List;
import java.util.Date;

import models.Product;
import play.db.ebean.Model.Finder;
import com.avaje.ebean.Query;

public class Materia extends Controller {
  
  /**
   * Defines a form wrapping the Product class.
   */
  final static Form<Product> productForm = form(Product.class);


    public static Result index() {
      List<Product> products = productsAll();
        return ok(
            views.html.product.index.render(products)
        );
    }
  
    public static Result newArrival() {
      return ok(
          views.html.arrival.form.render(productForm)
      );
    }

    public static Result arrival() {
      Form<Product> productForm = form(Product.class).bindFromRequest();
      if(productForm.hasErrors()) {
        return badRequest(views.html.arrival.form.render(productForm));
      } else {
        Product product =  productForm.get();

        Product updatedProduct = findBy(product.name, product.location);

        updatedProduct.quantity += product.quantity;
        updatedProduct.update();
      }

      List<Product> products = productsAll();
      return ok(views.html.product.index.render(products));
    }

    protected static List<Product> productsAll() {
      Finder<Long, Product> finder = new Finder<Long, Product>(Long.class, Product.class);
      List<Product> products = finder.all();
      return products;
    }

    protected static Product findBy(String name, String location) {
      Finder<Long, Product> finder = new Finder<Long, Product>(Long.class, Product.class);
      Query<Product> query = finder.where("name = '" + name + "' AND location = '" + location + "'");
      return query.findUnique();
    }
}
