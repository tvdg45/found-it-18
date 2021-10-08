//Author: Timothy van der Graaff
package controllers;

import java.util.ArrayList;

import java.sql.Connection;
import views.Show_Loaded_Game;

public class Control_Load_Game extends models.Load_Game {
    
    //global variables
    public static Connection use_connection;
    public static String game_id;
    public static String player_session;
    public static ArrayList<String> search_current_game;
    
    public static String control_search_available_games() {
        
        String output;
        
        connection = use_connection;
        
        set_player_session(player_session);
        
        search_current_game = search_current_game();
        
        if (!(search_current_game.get(0).equals("no occupied games")
                || search_current_game.get(0).equals("fail"))) {
            
            Show_Loaded_Game.available_games = search_current_game;
        } else {
            
            Show_Loaded_Game.available_games = search_available_games();
        }
        
        output = Show_Loaded_Game.show_available_games();
        
        return output;
    }
    
    public static String control_search_occupied_game_spaces() {
        
        String output;
        
        connection = use_connection;
        
        set_game_id(game_id);
        set_player_session(player_session);
        
        if (is_game_accessible()) {
            
            Show_Loaded_Game.occupied_game_spaces = search_occupied_game_spaces();
            
            output = Show_Loaded_Game.show_loaded_game();
        } else {
            
            output = Show_Loaded_Game.show_loaded_game_error_message();
        }
        
        return output;
    }
}
