package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.product.*;

import java.util.List;
import java.util.Date;

import models.Product;


public class Materia extends Controller {
  
  /**
   * Defines a form wrapping the Product class.
   */
  final static Form<Product> productForm = form(Product.class);

  /**
   * Directly redirect Materia home.
   */
  public static Result GO_HOME = redirect(
      routes.Materia.index()
      );

    public static Result index() {
      List<Product> products = Product.find.all();
        return ok(
            index.render(products)
        );
    }
  
    public static Result newArrival() {
      return ok(
          arrivalForm.render(productForm)
      );
    }

    public static Result arrival() {
      Form<Product> productForm = form(Product.class).bindFromRequest();
      if(productForm.hasErrors()) {
        return badRequest(arrivalForm.render(productForm));
      } else {
        Product product =  productForm.get();
        Product updatedProduct = Product.findBy(product.name, product.location);

        updatedProduct.quantity += product.quantity;
        updatedProduct.update();
        return GO_HOME;
      }
    }

    // if you want define common method, write protected static method
}
