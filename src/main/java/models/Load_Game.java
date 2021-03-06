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
    
    private static void create_new_tic_tac_toe_player_turns_table() {
        
        try {
            
            PreparedStatement create_statement = connection.prepareStatement(
                    
                    "CREATE TABLE company_tic_tac_toe_player_turns (row_id INT NOT NULL, " +
                            "player_id TEXT NOT NULL, game_id TEXT NOT NULL, player_has_turn TEXT NOT NULL, " +
                            "date_received TEXT NOT NULL, time_received TEXT NOT NULL, " +
                            "PRIMARY KEY (row_id)) ENGINE = MYISAM;");
            
            create_statement.execute();
        } catch (SQLException e) {

            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_player_turns' " +
                    "table was not created because it already exists.  " +
                    "This is not necessarily an error.");
        }
    }
    
    private static void create_new_game_spaces_table() {
        
        try {
            
            PreparedStatement create_statement = connection.prepareStatement(
                    
                    "CREATE TABLE company_tic_tac_toe_game_spaces (row_id INT NOT NULL, game_id TEXT NOT NULL, " +
                            "player_full_name TEXT NOT NULL, player_chosen_game_piece TEXT NOT NULL, " +
                            "player_chosen_game_space TEXT NOT NULL, player_session TEXT NOT NULL, " +
                            "date_received TEXT NOT NULL, time_received TEXT NOT NULL, " +
                            "PRIMARY KEY (row_id)) ENGINE = MYISAM;");
            
            create_statement.execute();
        } catch (SQLException e) {

            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_game_spaces' " +
                    "table was not created because it already exists.  " +
                    "This is not necessarily an error.");
        }
    }
    
    private static void create_new_tic_tac_toe_chat_messages_table() {
        
        try {
            
            PreparedStatement create_statement = connection.prepareStatement(
                    
                    "CREATE TABLE company_tic_tac_toe_chat_messages (row_id INT NOT NULL, " +
                            "player_session TEXT NOT NULL, player_full_name TEXT NOT NULL, " +
                            "player_message TEXT NOT NULL, game_id TEXT NOT NULL, " +
                            "date_received TEXT NOT NULL, time_received TEXT NOT NULL, " +
                            "PRIMARY KEY (row_id)) ENGINE = MYISAM;");
            
            create_statement.execute();
        } catch (SQLException e) {

            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_chat_messages' " +
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
                    "ORDER BY row_id DESC");
            
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
    
    protected static ArrayList<String> search_all_players() {
        
        ArrayList<String> output = new ArrayList<>();
        
        int players_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT game_id FROM company_tic_tac_toe_players " +
                    "ORDER BY row_id ASC");
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                output.add(select_results.getString(1));
                
                players_count++;
            }
            
            if (players_count == 0) {
                
                output.add("0");
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_players' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_players_table();
            
            output.add("0");
        }
        
        return output;
    }
    
    //Check if both players have joined a game.  The maximum is two.
    protected static boolean both_players_selected() {
        
        boolean output;
        
        int players_selected_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT row_id FROM company_tic_tac_toe_players " +
                    "WHERE game_id = ? ORDER BY row_id ASC");
            
            select_statement.setString(1, String.valueOf(get_game_id()));
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                players_selected_count++;
            }
            
            if (players_selected_count >= 2) {
                
                output = true;
            } else {
                
                output = false;
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_players' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_players_table();
            
            output = true;
        }
        
        return output;
    }
    
    //When joining a game a potential player should be able to see whom he/she would
    //be playing against.
    protected static ArrayList<ArrayList<String>> search_other_player() {
        
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        
        ArrayList<String> player_full_name = new ArrayList<>();
        ArrayList<String> player_chosen_game_piece = new ArrayList<>();
        
        int other_player_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT player_full_name, player_chosen_game_piece " +
                    "FROM company_tic_tac_toe_players WHERE game_id = ? ORDER BY row_id DESC LIMIT 1");
            
            select_statement.setString(1, get_game_id());
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                player_full_name.add(select_results.getString(1));
                player_chosen_game_piece.add(select_results.getString(2));
                
                other_player_count++;
            }
            
            if (other_player_count == 0) {
                
                player_full_name.add("no other player");
                player_chosen_game_piece.add("no other player");
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_players' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_players_table();
            
            player_full_name.add("fail");
            player_chosen_game_piece.add("fail");
        }
        
        output.add(player_full_name);
        output.add(player_chosen_game_piece);
        
        return output;
    }
    
    //Get game information on player active session.
    protected static ArrayList<ArrayList<String>> search_game_player_information() {
        
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        
        ArrayList<String> player_id = new ArrayList<>();
        ArrayList<String> player_full_name = new ArrayList<>();
        ArrayList<String> player_chosen_game_piece = new ArrayList<>();
        
        int players_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT row_id, player_full_name, player_chosen_game_piece " +
                    "FROM company_tic_tac_toe_players WHERE game_id = ? AND player_session = ? ORDER BY row_id DESC LIMIT 1");
            
            select_statement.setString(1, get_game_id());
            select_statement.setString(2, get_player_session());
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                player_id.add(select_results.getString(1));
                player_full_name.add(select_results.getString(2));
                player_chosen_game_piece.add(select_results.getString(3));
                
                players_count++;
            }
            
            if (players_count != 1) {
                
                player_id.add("no players");
                player_full_name.add("no players");
                player_chosen_game_piece.add("no players");
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_players' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_players_table();
            
            player_id.add("fail");
            player_full_name.add("fail");
            player_chosen_game_piece.add("fail");
        }
        
        output.add(player_id);
        output.add(player_full_name);
        output.add(player_chosen_game_piece);
        
        return output;
    }
    
    //Get game information on both players.
    protected static ArrayList<ArrayList<String>> search_game_players() {
        
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        
        ArrayList<String> player_id = new ArrayList<>();
        ArrayList<String> player_full_name = new ArrayList<>();
        ArrayList<String> player_chosen_game_piece = new ArrayList<>();
        
        int players_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT row_id, player_full_name, player_chosen_game_piece " +
                    "FROM company_tic_tac_toe_players WHERE game_id = ? ORDER BY row_id DESC LIMIT 2");
            
            select_statement.setString(1, get_game_id());
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                player_id.add(select_results.getString(1));
                player_full_name.add(select_results.getString(2));
                player_chosen_game_piece.add(select_results.getString(3));
                
                players_count++;
            }
            
            if (players_count != 2) {
                
                player_id.add("no players");
                player_full_name.add("no players");
                player_chosen_game_piece.add("no players");
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_players' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_players_table();
            
            player_id.add("fail");
            player_full_name.add("fail");
            player_chosen_game_piece.add("fail");
        }
        
        output.add(player_id);
        output.add(player_full_name);
        output.add(player_chosen_game_piece);
        
        return output;
    }
    
    //Get game information on both players.
    protected static ArrayList<ArrayList<String>> search_game_players_whose_turn() {
        
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        
        ArrayList<String> player_id = new ArrayList<>();
        ArrayList<String> player_has_turn = new ArrayList<>();
        
        int players_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT player_id, player_has_turn " +
                    "FROM company_tic_tac_toe_player_turns WHERE game_id = ? ORDER BY row_id DESC LIMIT 2");
            
            select_statement.setString(1, get_game_id());
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                player_id.add(select_results.getString(1));
                player_has_turn.add(select_results.getString(2));
                
                players_count++;
            }
            
            if (players_count != 2) {
                
                player_id.clear();
                player_has_turn.clear();
                
                player_id.add("no players");
                player_has_turn.add("no players");
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_player_turns' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_player_turns_table();
            
            player_id.add("fail");
            player_has_turn.add("fail");
        }
        
        output.add(player_id);
        output.add(player_has_turn);
        
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
                    "player_chosen_game_space, player_session FROM company_tic_tac_toe_game_spaces " +
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
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_game_spaces' " +
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
    
    //Search all chat messages relating to your game.
    protected static ArrayList<ArrayList<String>> search_instant_chat_messages() {
        
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        
        ArrayList<String> use_game_id = new ArrayList<>();
        ArrayList<String> player_full_name = new ArrayList<>();
        ArrayList<String> player_message = new ArrayList<>();
        ArrayList<String> date_received = new ArrayList<>();
        ArrayList<String> time_received = new ArrayList<>();
        
        int chat_message_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT game_id, player_full_name, player_message, " +
                    "date_received, time_received FROM company_tic_tac_toe_chat_messages " + 
                    "WHERE game_id = ? ORDER BY row_id ASC");
            
            select_statement.setString(1, get_game_id());
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                use_game_id.add(select_results.getString(1));
                player_full_name.add(select_results.getString(2));
                player_message.add(select_results.getString(3));
                date_received.add(select_results.getString(4));
                time_received.add(select_results.getString(5));
                
                chat_message_count++;
            }
            
            if (chat_message_count == 0) {
                
                use_game_id.add("no message");
                player_full_name.add("no message");
                player_message.add("no message");
                date_received.add("no message");
                time_received.add("no message");
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_chat_messages' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_chat_messages_table();
            
            use_game_id.add("fail");
            player_full_name.add("fail");
            player_message.add("fail");
            date_received.add("fail");
            time_received.add("fail");
        }
        
        output.add(use_game_id);
        output.add(player_full_name);
        output.add(player_message);
        output.add(date_received);
        output.add(time_received);
                
        return output;
    }
}
