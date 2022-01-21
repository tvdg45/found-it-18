//Author: Timothy van der Graaff
package apps;

import configuration.Config;
import controllers.Control_Save_Chat_Messages;

import java.io.IOException;

import java.sql.Connection;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://tdscloud-dev-ed--c.visualforce.com", maxAge = 3600)
@RestController
@EnableAutoConfiguration
@RequestMapping("/admin-extract-chat-messages")
public class Admin_Extract_Chat_Messages {
    
    @RequestMapping(method = RequestMethod.POST)
    String home() {
        
        Connection use_open_connection;
        
        try {
            
            use_open_connection = Config.openConnection();
            
            Control_Save_Chat_Messages.use_connection = use_open_connection;
            
            return "{\"chat_messages\": " +
                    Control_Save_Chat_Messages.control_search_instant_chat_messages() + "," +
                    " \"games\": " +
                    Control_Save_Chat_Messages.control_search_all_games() + "}";
        } catch (IOException e) {
            
            return "";
        }
    }
	
    public static void main(String[] args) throws Exception, IOException {
		
        SpringApplication.run(Admin_Extract_Chat_Messages.class, args);
    }
}
