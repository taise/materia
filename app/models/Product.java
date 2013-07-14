package models;

import java.util.Date;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.PreUpdate;

import com.avaje.ebean.validation.NotNull;


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

}
