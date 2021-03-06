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
            
            Show_Loaded_Game.player_session_status = "session found";
            Show_Loaded_Game.available_games = search_current_game;
        } else {
            
            Show_Loaded_Game.player_session_status = "no session found";
            Show_Loaded_Game.available_games = search_available_games();
            Show_Loaded_Game.all_players = search_all_players();
        }
        
        output = Show_Loaded_Game.show_available_games();
        
        return output;
    }
    
    public static String control_search_other_player() {
        
        String output;
        
        connection = use_connection;
        
        set_game_id(game_id);
        
        if (both_players_selected()) {
            
            output = "both players selected";
        } else {
            
            Show_Loaded_Game.other_player = search_other_player();
            
            output = Show_Loaded_Game.show_other_player();
        }
        
        return output;
    }
    
    public static String control_search_occupied_game_spaces() {
        
        String output;
        
        connection = use_connection;
        
        set_game_id(game_id);
        set_player_session(player_session);
        
        if (is_game_accessible()) {
            
            Show_Loaded_Game.game_player_information = search_game_player_information();
            Show_Loaded_Game.game_players = search_game_players();
            Show_Loaded_Game.game_players_whose_turn = search_game_players_whose_turn();
            Show_Loaded_Game.occupied_game_spaces = search_occupied_game_spaces();
            Show_Loaded_Game.instant_chat_messages = search_instant_chat_messages();
            
            output = Show_Loaded_Game.show_loaded_game();
        } else {
            
            output = Show_Loaded_Game.show_loaded_game_error_message();
        }
        
        return output;
    }
}