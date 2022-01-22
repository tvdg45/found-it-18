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
@RequestMapping("/admin-delete-chat-messages")
public class Admin_Delete_Chat_Messages {
    
    @RequestMapping(method = RequestMethod.POST)
    String home(
            @RequestParam(value = "delete_chat_messages", defaultValue = "") String delete_chat_messages
    ) {
		
        Connection use_open_connection;
        
        try {
            use_open_connection = Config.openConnection();
            
            Control_Save_Chat_Messages.use_connection = use_open_connection;
            Control_Save_Chat_Messages.delete_chat_messages = delete_chat_messages;
            
            if (delete_chat_messages.equals("Delete messages")) {
                
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
