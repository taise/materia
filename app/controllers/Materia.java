package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import java.util.List;

import models.Product;
import play.db.ebean.Model.Finder;

public class Materia extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
  
}
