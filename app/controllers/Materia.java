package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Materia extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
  
}
