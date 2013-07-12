package models;

import java.util.Date;
import java.sql.Timestamp;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
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
  public String quantity;

  @NotNull
  public String location;

  @Version
  public Timestamp update;

  @CreatedTimestamp
  public Date createDate;
}
