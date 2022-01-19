//Author: Timothy van der Graaff
package apps;

import configuration.Config;
import controllers.Control_Save_Chat_Messages;

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
@RequestMapping("/add-chat-message")
public class Add_Chat_Message {
    
    @RequestMapping(method = RequestMethod.POST)
    String home(
            @RequestParam(value = "game_id", defaultValue = "") String game_id,
            @RequestParam(value = "player_full_name", defaultValue = "") String player_full_name,
            @RequestParam(value = "player_message", defaultValue = "") String player_message,
            @RequestParam(value = "player_session", defaultValue = "") String player_session,
            @RequestParam(value = "add_chat_message", defaultValue = "") String add_chat_message
    ) {
        
        Connection use_open_connection;
        
        DateTimeFormatter time_format = DateTimeFormatter.ofPattern("hh:mm a 'EST'");
          
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now(ZoneId.of("America/New_York"));
        
        try {
            
            use_open_connection = Config.openConnection();
            
            Control_Save_Chat_Messages.use_connection = use_open_connection;
            Control_Save_Chat_Messages.game_id = game_id;
            Control_Save_Chat_Messages.player_full_name = player_full_name;
            Control_Save_Chat_Messages.player_message = player_message;
            Control_Save_Chat_Messages.player_session = player_session;
            Control_Save_Chat_Messages.date_received = String.valueOf(localDate);
            Control_Save_Chat_Messages.time_received = String.valueOf(time_format.format(localTime));
            Control_Save_Chat_Messages.add_chat_message = add_chat_message;
            
            if (add_chat_message.equals("Add message")) {
                
                return Control_Save_Chat_Messages.control_add_chat_message();
            } else {
                
                return "";
            }
        } catch (IOException e) {
            
            return "";
        }
    }
	
    public static void main(String[] args) throws Exception, IOException {
		
        SpringApplication.run(Add_Chat_Message.class, args);
    }
}
