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
      checkForm(productForm);
      checkQuantityLimit(productForm);
      if(productForm.hasErrors()) {
        return badRequest(arrivalForm.render(productForm));
      }
      Product product =  productForm.get();
      Product updatedProduct = Product.findBy(product.name, product.location);

      updatedProduct.quantity += product.quantity;
      updatedProduct.update();
      return GO_HOME;
    }

    public static Result newShipping() {
      return ok(
          shippingForm.render(productForm)
      );
    }

    public static Result shipping() {
      Form<Product> productForm = form(Product.class).bindFromRequest();
      checkForm(productForm);
      if(productForm.hasErrors()) {
        return badRequest(shippingForm.render(productForm));
      }
      Product product =  productForm.get();
      Product updatedProduct = Product.findBy(product.name, product.location);
      updatedProduct.quantity -= product.quantity;

      if(updatedProduct.quantity < 0) {
        productForm.reject("quantity", "出荷数が在庫数を超えています。");
        return badRequest(shippingForm.render(productForm));
      }
      updatedProduct.update();
      return GO_HOME;
    }

    /**
     * Check form values are not blanc or empty(.bang method)
     */
    protected static void checkForm(Form<Product> form) {
      if(form.field("name").valueOr("").isEmpty()) {
        form.reject("name", "商品が選択されていません。");
      }
      if(form.field("location").valueOr("").isEmpty()) {
        form.reject("location", "保管場所が選択されていません。");
      }
      if(form.field("quantity").valueOr("").isEmpty()) {
        form.reject("quantity", "数量が入力されていません。");
      }
    }

    /**
     * Check the quantity is between min to max.(bang method)
     */
    protected static void checkQuantityLimit(Form<Product> form) {
      long quantity = form.get().quantity;
      if(quantity < 0 || quantity > 100) {
        form.reject("quantity", "出荷数が在庫数を超えています。");
      }
    }
}
