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
@RequestMapping("/load-game")
public class Load_Game {
    
    @RequestMapping(method = RequestMethod.POST)
    String home(
            @RequestParam(value = "game_id", defaultValue = "") String game_id,
            @RequestParam(value = "player_session", defaultValue = "") String player_session,
            @RequestParam(value = "load_game", defaultValue = "") String load_game
    ) {
        
        Connection use_open_connection;
        
        try {
            
            use_open_connection = Config.openConnection();
            
            Control_Load_Game.use_connection = use_open_connection;
            Control_Load_Game.game_id = game_id;
            Control_Load_Game.player_session = player_session;
			
			return Control_Load_Game.control_search_occupied_game_spaces();
        } catch (IOException e) {
            
            return "";
        }
    }
	
    public static void main(String[] args) throws Exception, IOException {
		
        SpringApplication.run(Load_Game.class, args);
    }
}
