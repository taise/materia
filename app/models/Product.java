package models;

import java.util.Date;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.PreUpdate;

import com.avaje.ebean.validation.NotNull;
import com.avaje.ebean.*;


@Entity
public class Product extends Model {
  @Id
  public Long id;

  @NotNull
  public String name;

  @NotNull
  public Long price;

  @NotNull
  public Long quantity;

  @NotNull
  public String location;

  @Column(name = "updateDate")
  public Date updateDate;


  @Override
  public void update() {
    updateDate();
    super.update();
  }

  @PreUpdate
  void updateDate() {
    this.updateDate = new Date();
  }

  /**
   * Generic query helper for find Product
   */
  public static Model.Finder<Long, Product> find = new Finder<Long, Product>(Long.class, Product.class);

  public static Product  findBy(String name, String location) {
      Finder<Long, Product> finder = new Finder<Long, Product>(Long.class, Product.class);
      Query<Product> query = finder.where("name = '" + name + "' AND location = '" + location + "'");
      return query.findUnique();
  }
}
