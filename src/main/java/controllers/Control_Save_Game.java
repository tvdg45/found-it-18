//Author: Timothy van der Graaff
package controllers;

import java.util.ArrayList;

import java.sql.Connection;

public class Control_Save_Game extends models.Save_Game {
    
    //global variables
    public static Connection use_connection;
    public static String game_id;
    public static String player_full_name;
    public static String player_chosen_game_piece;
    public static String player_chosen_game_space;
    public static String player_session;
    public static String date_received;
    public static String time_received;
    public static String add_game;
    public static String join_game;
    public static String add_game_piece;
    public static ArrayList<ArrayList<String>> search_game_players;
    
    public static String control_add_game() {
        
        String output = "";
        
        if (add_game.equals("New game")) {
            
            connection = use_connection;
            
            set_game_id(generate_tic_tac_toe_game_id());
            set_date_received(date_received);
            set_time_received(time_received);
            
            if (add_game().equals("success")) {
                
                set_player_full_name(player_full_name);
                set_player_session(player_session);
                
                if (add_player().equals("success")) {
                    
                    output = "success";
                } else {
                    
                    output = "fail";
                }
            } else {
                
                output = "fail";
            }
        }
        
        return output;
    }
    
    public static String control_join_game() {
        
        String output = "";
        
        if (join_game.equals("Join game")) {
            
            connection = use_connection;
            
            try {
                
                set_game_id(Integer.valueOf(game_id));
                
                if (both_players_selected()) {
                    
                    output = "both players selected";
                } else {
                    
                    set_player_full_name(player_full_name);
                    set_player_session(player_session);
                    set_date_received(date_received);
                    set_time_received(time_received);
                    
                    if (add_player().equals("success")) {
                        
                        output = "success";
                    } else {
                        
                        output = "fail";
                    }
                }
            } catch (Exception e) {
                
                output = "";
            }
        }
        
        return output;
    }
    
    public static String control_add_game_piece() {
        
        String output = "";
        
        if (add_game_piece.equals("Add game piece")) {
            
            connection = use_connection;
            
            try {
                
                set_game_id(Integer.valueOf(game_id));
                set_player_session(player_session);
                
                search_game_players = search_game_players();
                
                if (!(search_game_players.get(0).get(0).equals("no player found")
                        || search_game_players.get(0).get(0).equals("fail"))) {
                    
                    set_player_full_name(search_game_players.get(0).get(0));
                    set_player_chosen_game_piece(search_game_players.get(0).get(1));
                    set_player_chosen_game_space(player_chosen_game_space);
                    set_date_received(date_received);
                    set_time_received(time_received);
                    
                    if (add_game_piece().equals("success")) {
                        
                        output = "success";
                    } else {
                        
                        output = "fail";
                    }
                } else {
                    
                    output = "fail";
                }
            } catch (Exception e) {
                
                output = "fail";
            }
        }
        
        return output;
    }
}