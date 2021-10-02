//Author: Timothy van der Graaff
package apps;

import configuration.Config;
import controllers.Control_Load_Game;

import java.io.IOException;

import java.sql.Connection;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@EnableAutoConfiguration
@RequestMapping("/search-available-games")
public class Search_Available_Games {
    
    @RequestMapping(method = RequestMethod.POST)
    String home() {
        
        Connection use_open_connection;
      
        String return_string = "";
        
        try {
            
            use_open_connection = Config.openConnection();
            
            Control_Load_Game.use_connection = use_open_connection;
          
            return_string += Control_Load_Game.control_search_available_games();
			
            try {
                
                use_open_connection.close();
            } catch (Exception e) {
            }
        } catch (IOException e) {
            
            return_string += "";
        }
		
		return return_string;
    }
	
    public static void main(String[] args) throws Exception, IOException {
		
        SpringApplication.run(Search_Available_Games.class, args);
    }
}
