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


public class Materia extends Controller {
  
  /**
   * Defines a form wrapping the Product class.
   */
  final static Form<Product> productForm = form(Product.class);


    public static Result index() {
      List<Product> products = Product.find.all();
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

        Product updatedProduct = Product.findBy(product.name, product.location);

        updatedProduct.quantity += product.quantity;
        updatedProduct.update();
      }

      List<Product> products = Product.find.all();
      return ok(views.html.product.index.render(products));
    }

    // if you want define common method, write protected static method
}
