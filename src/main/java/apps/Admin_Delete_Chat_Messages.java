//Author: Timothy van der Graaff
package apps;

import configuration.Config;
import controllers.Control_Save_Chat_Messages;

import java.io.IOException;

import java.sql.Connection;
import java.util.ArrayList;

import utilities.Find_and_replace;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://tdscloud-dev-ed--c.visualforce.com", maxAge = 3600)
@RestController
@EnableAutoConfiguration
@RequestMapping("/admin-delete-chat-messages")
public class Admin_Delete_Chat_Messages {
    
    @RequestMapping(method = RequestMethod.POST)
    String home(
			@RequestParam(value = "tic_tac_toe_game", defaultValue = "") String tic_tac_toe_game,
            @RequestParam(value = "delete_chat_messages", defaultValue = "") String delete_chat_messages
    ) {
		
        Connection use_open_connection;
        
        try {
			
            use_open_connection = Config.openConnection();
            
            Control_Save_Chat_Messages.use_connection = use_open_connection;
			
            String game_id_exception = "";
            
            ArrayList<String> find = new ArrayList<>();
            ArrayList<String> replace = new ArrayList<>();
            
            find.add("(");
            find.add(")");
            find.add(", ");
            
            replace.add("");
            replace.add("");
            replace.add(",");
			
            try {
                
                String[] each_tic_tac_toe_game = Find_and_replace.find_and_replace(find, replace, tic_tac_toe_game).split(",");
                
                Control_Save_Chat_Messages.tic_tac_toe_game_id = each_tic_tac_toe_game;
            } catch (NullPointerException e) {
                
                game_id_exception = "yes";
            }
			
            Control_Save_Chat_Messages.delete_chat_messages = delete_chat_messages;
            
            if (!(game_id_exception.equals("yes")) && delete_chat_messages.equals("Delete messages")) {
                
                return Control_Save_Chat_Messages.control_delete_all_chat_messages();
            } else {
                
                return "";
            }
        } catch (IOException e) {
            
            return "";
        }
    }
	
    public static void main(String[] args) throws Exception, IOException {
		
        SpringApplication.run(Admin_Delete_Chat_Messages.class, args);
    }
}
