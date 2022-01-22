//Author: Timothy van der Graaff
package controllers;

import java.sql.Connection;
import java.sql.SQLException;
import views.Show_Loaded_Game;

public class Control_Save_Chat_Messages extends models.Save_Chat_Messages {

    //global variables
    public static Connection use_connection;
    public static String game_id;
    public static String player_full_name;
    public static String player_message;
    public static String player_session;
    public static String date_received;
    public static String time_received;
    public static String add_chat_message;
    public static String delete_chat_messages;
    
    public static String control_add_chat_message() {
        
        String output = "";
        
        if (add_chat_message.equals("Add message")) {
            
            connection = use_connection;
            
            try {
                
                set_game_id(Integer.valueOf(game_id));
                set_player_session(player_session);
                set_player_full_name(player_full_name);
                set_player_message(player_message);
                set_date_received(date_received);
                set_time_received(time_received);
                
                if (add_chat_message().equals("success")) {
                    
                    output = "success";
                } else {
                    
                    output = "fail";
                }
            } catch (Exception e) {
                
                output = "";
            }
        }
        
        return output;
    }
    
    public static String control_search_instant_chat_messages() {
        
        String output;
        
        connection = use_connection;
        
        Show_Loaded_Game.instant_chat_messages = search_instant_chat_messages();
        
        output = Show_Loaded_Game.show_all_chat_messages();
        
        return output;
    }
    
    public static String control_search_all_games() {
        
        String output;
        
        connection = use_connection;
        
        Show_Loaded_Game.all_games = search_all_games();
        
        output = Show_Loaded_Game.show_all_games();
        
        return output;
    }
    
    public static String control_delete_all_chat_messages() {
        
        String output = "";
        
        if (delete_chat_messages.equals("Delete messages")) {
            
            connection = use_connection;
            
            if (delete_all_chat_messages().equals("success")) {
                
                if (delete_all_games().equals("success")) {
                    
                    output = "success";
                } else {
                    
                    output = "fail";
                }
            } else {
                
                output = "fail";
            }
            
            try {
                
                use_connection.close();
            } catch (SQLException e) {
            }
        }
        
        return output;
    }
}