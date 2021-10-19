//Author: Timothy van der Graaff
package models;

import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Load_Game {
    
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public static Connection connection;
    
    //global variables
    private static String game_id;
    private static String player_session;
    
    //mutators
    protected static void set_game_id(String this_game_id) {
        
        game_id = this_game_id;
    }
    
    protected static void set_player_session(String this_player_session) {
        
        player_session = this_player_session;
    }
    
    //accessors
    private static String get_game_id() {
        
        return game_id;
    }
    
    private static String get_player_session() {
        
        return player_session;
    }
    
    
    
    
    private static void create_new_tic_tac_toe_games_table() {
        
        try {
            
            PreparedStatement create_statement = connection.prepareStatement(
                    
                    "CREATE TABLE company_tic_tac_toe_games (row_id INT NOT NULL, " +
                            "date_received TEXT NOT NULL, time_received TEXT NOT NULL, " +
                            "PRIMARY KEY (row_id)) ENGINE = MYISAM;");
            
            create_statement.execute();
        } catch (SQLException e) {

            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_games' " +
                    "table was not created because it already exists.  " +
                    "This is not necessarily an error.");
        }
    }
    
    private static void create_new_tic_tac_toe_players_table() {
        
        try {
            
            PreparedStatement create_statement = connection.prepareStatement(
                    
                    "CREATE TABLE company_tic_tac_toe_players (row_id INT NOT NULL, " +
                            "player_full_name TEXT NOT NULL, player_session TEXT NOT NULL, " +
                            "player_chosen_game_piece TEXT NOT NULL, game_id TEXT NOT NULL, " +
                            "date_received TEXT NOT NULL, time_received TEXT NOT NULL, " +
                            "PRIMARY KEY (row_id)) ENGINE = MYISAM;");
            
            create_statement.execute();
        } catch (SQLException e) {

            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_players' " +
                    "table was not created because it already exists.  " +
                    "This is not necessarily an error.");
        }
    }
    
    private static void create_new_game_spaces_table() {
        
        try {
            
            PreparedStatement create_statement = connection.prepareStatement(
                    
                    "CREATE TABLE company_game_spaces (row_id INT NOT NULL, game_id TEXT NOT NULL, " +
                            "player_full_name TEXT NOT NULL, player_chosen_game_piece TEXT NOT NULL, " +
                            "player_chosen_game_space TEXT NOT NULL, player_session TEXT NOT NULL, " +
                            "date_received TEXT NOT NULL, time_received TEXT NOT NULL, " +
                            "PRIMARY KEY (row_id)) ENGINE = MYISAM;");
            
            create_statement.execute();
        } catch (SQLException e) {

            LOGGER.log(Level.INFO, "The 'company_game_spaces' " +
                    "table was not created because it already exists.  " +
                    "This is not necessarily an error.");
        }
    }
    
    protected static ArrayList<String> search_current_game() {
        
        ArrayList<String> output = new ArrayList<>();
        
        int games_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT game_id FROM company_tic_tac_toe_players " +
                    "WHERE player_session = ? ORDER BY row_id ASC LIMIT 1");
            
            select_statement.setString(1, get_player_session());
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                output.add(select_results.getString(1));
                
                games_count++;
            }
            
            if (games_count == 0) {
                
                output.add("no occupied games");
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_players' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_players_table();
            
            output.add("fail");
        }
        
        return output;
    }
    
    protected static ArrayList<String> search_available_games() {
        
        ArrayList<String> output = new ArrayList<>();
        
        int games_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT row_id FROM company_tic_tac_toe_games " +
                    "ORDER BY row_id ASC LIMIT 5");
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                output.add(String.valueOf(select_results.getInt(1)));
                
                games_count++;
            }
            
            if (games_count == 0) {
                
                output.add("no available games");
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_games' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_games_table();
            
            output.add("fail");
        }
        
        return output;
    }    
    
    protected static boolean is_game_accessible() {
        
        boolean output;
        
        int matching_players_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT row_id FROM company_tic_tac_toe_players " +
                    "WHERE player_session = ? AND game_id = ? ORDER BY row_id ASC");
            
            select_statement.setString(1, get_player_session());
            select_statement.setString(2, get_game_id());
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                matching_players_count++;
            }
            
            switch (matching_players_count) {
                
                case 0:
                    
                    output = false;
                    
                    break;
                
                default:
                    
                    output = true;
                    
                    break;
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_players' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_players_table();
            
            output = false;
        }
        
        return output;
    }
    
    protected static ArrayList<ArrayList<String>> search_occupied_game_spaces() {
        
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        
        ArrayList<String> each_game_id = new ArrayList<>();
        ArrayList<String> each_player_chosen_game_piece = new ArrayList<>();
        ArrayList<String> each_player_chosen_game_space = new ArrayList<>();
        ArrayList<String> each_player_session = new ArrayList<>();
        
        int occupied_game_spaces_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT game_id, player_chosen_game_piece, " +
                    "player_chosen_game_space, player_session FROM company_game_spaces " +
                    "WHERE game_id = ? ORDER BY row_id ASC");
            
            select_statement.setString(1, get_game_id());
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                each_game_id.add(select_results.getString(1));
                each_player_chosen_game_piece.add(select_results.getString(2));
                each_player_chosen_game_space.add(select_results.getString(3));
                each_player_session.add(select_results.getString(4));
                
                occupied_game_spaces_count++;
            }
            
            if (occupied_game_spaces_count == 0) {
                
                each_game_id.add("no game spaces occupied");
                each_player_chosen_game_piece.add("no game spaces occupied");
                each_player_chosen_game_space.add("no game spaces occupied");
                each_player_session.add("no game spaces occupied");
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_game_spaces' " +
                    "table is corrupt or does not exist");
            
            create_new_game_spaces_table();
            
            each_game_id.add("fail");
            each_player_chosen_game_piece.add("fail");
            each_player_chosen_game_space.add("fail");
            each_player_session.add("fail");
        }
        
        output.add(each_game_id);
        output.add(each_player_chosen_game_piece);
        output.add(each_player_chosen_game_space);
        output.add(each_player_session);
        
        return output;
    }
}
