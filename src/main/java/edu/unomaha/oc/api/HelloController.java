package edu.unomaha.oc.api;

import org.springframework.web.bind.annotation.RestController;

import edu.unomaha.oc.domain.User;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@RestController
public class HelloController {

	// This method returns a user as serialized JSON along with an HTTP status of OK
    @RequestMapping(value="/", method=RequestMethod.GET)
    public ResponseEntity<User> index() {
        User myUser = new User(123, "Henry");

        return new ResponseEntity<User>(myUser, HttpStatus.OK);
    }

    // This method takes a post with the variable "value" populated and returns it back as a string
    @RequestMapping(value="/", method=RequestMethod.POST)
    public ResponseEntity<Object> testPost(HttpServletRequest request, @RequestParam(value="value") String value) {
    	// Instead of just printing here, we'd build an object and save it to the database
    	System.out.println("Value is: " + value);
    	
    	return new ResponseEntity<Object>(value, HttpStatus.OK);
    }
    
}