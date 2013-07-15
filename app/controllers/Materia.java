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
        return ok(index.render(products));
    }
  
    public static Result newProduct() {
      return ok(createForm.render(productForm));
    }

    public static Result create() {
      Form<Product> productForm = form(Product.class).bindFromRequest();
      checkCreateForm(productForm);
      if(productForm.hasErrors()) {
        return badRequest(createForm.render(productForm));
      }

      Product product = productForm.get();
      if(Product.findBy(product.name, product.location) != null) {
        productForm.reject("name", "既に登録されています。");
        return badRequest(createForm.render(productForm));
      };
      product.save();
      return GO_HOME;
    }

    /**
     * Arrival Oparation
     */
    public static Result newArrival() {
      return ok(arrivalForm.render(productForm));
    }

    public static Result arrival() {
      Form<Product> productForm = form(Product.class).bindFromRequest();
      // Validation of input data
      checkStockOperationForm(productForm);
      checkQuantityLimit(productForm);
      if(productForm.hasErrors()) {
        return badRequest(arrivalForm.render(productForm));
      }

      Product product =  productForm.get();
      Product updatedProduct = Product.findBy(product.name, product.location);
      // Exist check for update data
      if(updatedProduct == null) {
        productForm.reject("name", "商品が登録されていません。");
        return badRequest(arrivalForm.render(productForm));
      }

      updatedProduct.quantity += product.quantity;
      updatedProduct.update();
      return GO_HOME;
    }

    /**
     * Shipping Oparation
     */
    public static Result newShipping() {
      return ok(shippingForm.render(productForm));
    }

    public static Result shipping() {
      Form<Product> productForm = form(Product.class).bindFromRequest();
      checkStockOperationForm(productForm);
      if(productForm.hasErrors()) {
        return badRequest(shippingForm.render(productForm));
      }

      Product product =  productForm.get();
      Product updatedProduct = Product.findBy(product.name, product.location);
      // Exist check for update data
      if(updatedProduct == null) {
        productForm.reject("name", "商品が登録されていません。");
        return badRequest(shippingForm.render(productForm));
      }

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
    protected static void checkStockOperationForm(Form<Product> form) {
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

    protected static void checkCreateForm(Form<Product> form) {
      if(form.field("name").valueOr("").isEmpty()) {
        form.reject("name", "商品が選択されていません。");
      }
      if(form.field("location").valueOr("").isEmpty()) {
        form.reject("location", "保管場所が選択されていません。");
      }
      if(form.field("price").valueOr("").isEmpty()) {
        form.reject("price", "値段が入力されていません。");
      }
    }

    /**
     * Check the quantity is between min to max.(bang method)
     */
    protected static void checkQuantityLimit(Form<Product> form) {
      try {
        long quantity = form.get().quantity;
        if(quantity < 0 || quantity > 100) {
          form.reject("quantity", "出荷数が在庫数を超えています。");
        }
      } catch(NullPointerException e) {
        // this null checked by checkStockOperationForm
      }
    }
}
