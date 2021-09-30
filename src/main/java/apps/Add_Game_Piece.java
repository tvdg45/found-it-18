//Author: Timothy van der Graaff
package apps;

import configuration.Config;
import controllers.Control_Save_Game;

import java.time.LocalDate;
import java.time.LocalTime;

import java.io.IOException;

import java.sql.Connection;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@EnableAutoConfiguration
@RequestMapping("/add-game-piece")
public class Add_Game_Piece {
    
    @RequestMapping(method = RequestMethod.POST)
    String home(
            @RequestParam(value = "game_id", defaultValue = "") String game_id,
            @RequestParam(value = "player_session", defaultValue = "") String player_session,
            @RequestParam(value = "player_chosen_game_space", defaultValue = "") String player_chosen_game_space,
            @RequestParam(value = "add_game_piece", defaultValue = "") String add_game_piece
    ) {
        
        Connection use_open_connection;
        
        DateTimeFormatter time_format = DateTimeFormatter.ofPattern("hh:mm a 'EST'");
          
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now(ZoneId.of("America/New_York"));
        
        try {
            
            use_open_connection = Config.openConnection();
            
            Control_Save_Game.use_connection = use_open_connection;
            Control_Save_Game.game_id = game_id;
            Control_Save_Game.player_session = player_session;
            Control_Save_Game.player_chosen_game_space = player_chosen_game_space;
            Control_Save_Game.date_received = String.valueOf(localDate);
            Control_Save_Game.time_received = String.valueOf(time_format.format(localTime));
            Control_Save_Game.add_game_piece = add_game_piece;
            
            if (add_game_piece.equals("Add game piece")) {
                
                return Control_Save_Game.control_add_game_piece();
            } else {
                
                return "";
            }
        } catch (IOException e) {
            
            return "";
        }
    }
	
    public static void main(String[] args) throws Exception, IOException {
		
        SpringApplication.run(Add_Game_Piece.class, args);
    }
}
