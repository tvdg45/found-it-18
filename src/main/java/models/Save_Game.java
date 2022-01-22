//Author: Timothy van der Graaff
package models;

import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Save_Game {
    
    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public static Connection connection;
    
    //global variables
    private static int game_id;
    private static int player_id;
    private static String player_full_name;
    private static String player_chosen_game_piece;
    private static String player_has_turn;
    private static String player_chosen_game_space;
    private static String player_session;
    private static String date_received;
    private static String time_received;
    
    //mutators
    protected static void set_game_id(int this_game_id) {
        
        game_id = this_game_id;
    }
    
    protected static void set_player_id(int this_player_id) {
        
        player_id = this_player_id;
    }
    
    protected static void set_player_full_name(String this_player_full_name) {
        
        player_full_name = this_player_full_name;
    }
    
    protected static void set_player_chosen_game_piece(String this_player_chosen_game_piece) {
        
        player_chosen_game_piece = this_player_chosen_game_piece;
    }
    
    protected static void set_player_has_turn(String this_player_has_turn) {
        
        player_has_turn = this_player_has_turn;
    }
    
    protected static void set_player_chosen_game_space(String this_player_chosen_game_space) {
        
        player_chosen_game_space = this_player_chosen_game_space;
    }
    
    protected static void set_player_session(String this_player_session) {
        
        player_session = this_player_session;
    }
    
    protected static void set_date_received(String this_date_received) {
        
        date_received = this_date_received;
    }
    
    protected static void set_time_received(String this_time_received) {
        
        time_received = this_time_received;
    }
    
    //accessors
    private static int get_game_id() {
        
        return game_id;
    }
    
    private static int get_player_id() {
        
        return player_id;
    }
    
    private static String get_player_full_name() {
        
        return player_full_name;
    }
    
    protected static String get_player_chosen_game_piece() {
        
        return player_chosen_game_piece;
    }
    
    protected static String get_player_has_turn() {
        
        return player_has_turn;
    }
    
    protected static String get_player_chosen_game_space() {
        
        return player_chosen_game_space;
    }
    
    private static String get_player_session() {
        
        return player_session;
    }
    
    private static String get_date_received() {
        
        return date_received;
    }
    
    protected static String get_time_received() {
        
        return time_received;
    }
    
    
    

    
    protected static int generate_tic_tac_toe_game_id() {
        
        int output;
        
        try {
            
            PreparedStatement prepared_statement = connection.prepareStatement("SELECT row_id FROM " +
                    "company_tic_tac_toe_games ORDER BY row_id DESC LIMIT 1");
            
            ResultSet select_results = prepared_statement.executeQuery();
            
            select_results.last();
            
            if (select_results.getRow() > 0) {
               
                output = select_results.getInt(1);
            } else {
                
                output = 0;
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "{0}", e);
            
            output = 0;
        }
        
        return output + 1;
    }
    
    protected static int generate_tic_tac_toe_players_id() {
        
        int output;
        
        try {
            
            PreparedStatement prepared_statement = connection.prepareStatement("SELECT row_id FROM " +
                    "company_tic_tac_toe_players ORDER BY row_id DESC LIMIT 1");
            
            ResultSet select_results = prepared_statement.executeQuery();
            
            select_results.last();
            
            if (select_results.getRow() > 0) {
               
                output = select_results.getInt(1);
            } else {
                
                output = 0;
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "{0}", e);
            
            output = 0;
        }
        
        return output + 1;
    }
    
    protected static int generate_tic_tac_toe_player_turns_id() {
        
        int output;
        
        try {
            
            PreparedStatement prepared_statement = connection.prepareStatement("SELECT row_id FROM " +
                    "company_tic_tac_toe_player_turns ORDER BY row_id DESC LIMIT 1");
            
            ResultSet select_results = prepared_statement.executeQuery();
            
            select_results.last();
            
            if (select_results.getRow() > 0) {
               
                output = select_results.getInt(1);
            } else {
                
                output = 0;
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "{0}", e);
            
            output = 0;
        }
        
        return output + 1;
    }
    
    private static int generate_game_space_id() {
        
        int output;
        
        try {
            
            PreparedStatement prepared_statement = connection.prepareStatement("SELECT row_id FROM " +
                    "company_tic_tac_toe_game_spaces ORDER BY row_id DESC LIMIT 1");
            
            ResultSet select_results = prepared_statement.executeQuery();
            
            select_results.last();
            
            if (select_results.getRow() > 0) {
               
                output = select_results.getInt(1);
            } else {
                
                output = 0;
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "{0}", e);
            
            output = 0;
        }
        
        return output + 1;
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
    
    //Start a new game.
    protected static String add_game() {
        
        String output;
        
        try {
            
            PreparedStatement insert_statement = connection.prepareStatement("INSERT INTO " +
                    "company_tic_tac_toe_games (row_id, date_received, time_received) " +
                    "VALUES(?, ?, ?)");
            
            insert_statement.setInt(1, get_game_id());
            insert_statement.setString(2, get_date_received());
            insert_statement.setString(3, get_time_received());
            
            insert_statement.addBatch();
            
            insert_statement.executeBatch();
            
            output = "success";
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_games' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_games_table();
            
            output = "fail";
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
    
    //There are a maximum of two players allowed.
    protected static String add_player() {
        
        String output;
        
        try {
            
            PreparedStatement insert_statement = connection.prepareStatement("INSERT INTO " +
                    "company_tic_tac_toe_players (row_id, player_full_name, player_session, player_chosen_game_piece, game_id, date_received, time_received) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)");
            
            insert_statement.setInt(1, generate_tic_tac_toe_players_id());
            insert_statement.setString(2, get_player_full_name());
            insert_statement.setString(3, get_player_session());
            insert_statement.setString(4, get_player_chosen_game_piece());
            insert_statement.setString(5, String.valueOf(get_game_id()));
            insert_statement.setString(6, get_date_received());
            insert_statement.setString(7, get_time_received());
            
            insert_statement.addBatch();
            
            insert_statement.executeBatch();
            
            output = "success";
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_players' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_players_table();
            
            output = "fail";
        }
        
        return output;
    }
    
    protected static String add_player_turn() {
        
        String output;
        
        try {
            
            PreparedStatement insert_statement = connection.prepareStatement("INSERT INTO " +
                    "company_tic_tac_toe_player_turns (row_id, player_id, game_id, player_has_turn, date_received, time_received) " +
                    "VALUES(?, ?, ?, ?, ?, ?)");
            
            insert_statement.setInt(1, generate_tic_tac_toe_player_turns_id());
            insert_statement.setString(2, String.valueOf(get_player_id()));
            insert_statement.setString(3, String.valueOf(get_game_id()));
            insert_statement.setString(4, get_player_has_turn());
            insert_statement.setString(5, get_date_received());
            insert_statement.setString(6, get_time_received());
            
            insert_statement.addBatch();
            
            insert_statement.executeBatch();
            
            output = "success";
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_player_turns' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_player_turns_table();
            
            output = "fail";
        }
        
        return output;
    }
    
    protected static int search_player_id() {
        
        int output = 0;
        
        int players_selected_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT row_id FROM company_tic_tac_toe_players " +
                    "WHERE game_id = ? AND player_chosen_game_piece = ? " +
                    "ORDER BY row_id ASC LIMIT 1");
            
            select_statement.setString(1, String.valueOf(get_game_id()));
            select_statement.setString(2, get_player_chosen_game_piece());
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                output = select_results.getInt(1);
                
                players_selected_count++;
            }
            
            if (players_selected_count == 0) {
                
                output = 0;
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_players' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_players_table();
            
            output = 0;
        }
        
        return output;
    }
    
    //Get game information on both players.
    protected static ArrayList<ArrayList<String>> search_game_players_whose_turn() {
        
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        
        ArrayList<String> use_player_id = new ArrayList<>();
        ArrayList<String> use_player_has_turn = new ArrayList<>();
        
        int players_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT player_id, player_has_turn " +
                    "FROM company_tic_tac_toe_player_turns WHERE game_id = ? ORDER BY row_id DESC LIMIT 2");
            
            select_statement.setString(1, String.valueOf(get_game_id()));
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                use_player_id.add(select_results.getString(1));
                use_player_has_turn.add(select_results.getString(2));
                
                players_count++;
            }
            
            if (players_count != 2) {
                
                use_player_id.clear();
                use_player_has_turn.clear();
                
                use_player_id.add("no players");
                use_player_has_turn.add("no players");
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_player_turns' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_player_turns_table();
            
            use_player_id.add("fail");
            use_player_has_turn.add("fail");
        }
        
        output.add(use_player_id);
        output.add(use_player_has_turn);
        
        return output;
    }
    
    protected static String change_player_has_turn_status() {
        
        String output;
        
        try {
            
            PreparedStatement update_statement = connection.prepareStatement("UPDATE " +
                    "company_tic_tac_toe_player_turns SET player_has_turn = ? WHERE player_id = ?");
            
            update_statement.setString(1, get_player_has_turn());
            update_statement.setString(2, String.valueOf(get_player_id()));
            
            update_statement.addBatch();
            
            update_statement.executeBatch();
            
            output = "success";
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_player_turns' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_player_turns_table();
            
            output = "fail";
        }
        
        return output;
    }
    
    protected static ArrayList<ArrayList<String>> search_game_players() {
        
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        
        ArrayList<String> each_player_full_name = new ArrayList<>();
        ArrayList<String> each_player_chosen_game_piece = new ArrayList<>();
        
        int players_selected_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT player_full_name, player_chosen_game_piece " +
                    "FROM company_tic_tac_toe_players WHERE game_id = ? AND player_session = ? " +
                    "ORDER BY row_id ASC LIMIT 1");
            
            select_statement.setString(1, String.valueOf(get_game_id()));
            select_statement.setString(2, get_player_session());
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                each_player_full_name.add(select_results.getString(1));
                each_player_chosen_game_piece.add(select_results.getString(2));
                
                players_selected_count++;
            }
            
            if (players_selected_count == 0) {
                
                each_player_full_name.add("no player found");
                each_player_chosen_game_piece.add("no player found");
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_players' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_players_table();
            
            each_player_full_name.add("fail");
            each_player_chosen_game_piece.add("fail");
        }
        
        output.add(each_player_full_name);
        output.add(each_player_chosen_game_piece);
        
        return output;
    }
    
    //Any player's move will be saved when the game is still active.
    protected static String add_game_piece() {
        
        String output;
        
        try {
            
            PreparedStatement insert_statement = connection.prepareStatement("INSERT INTO " +
                    "company_tic_tac_toe_game_spaces (row_id, game_id, player_full_name, player_chosen_game_piece, player_chosen_game_space, " +
                    "player_session, date_received, time_received) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            
            insert_statement.setInt(1, generate_game_space_id());
            insert_statement.setString(2, String.valueOf(get_game_id()));
            insert_statement.setString(3, get_player_full_name());
            insert_statement.setString(4, get_player_chosen_game_piece());
            insert_statement.setString(5, get_player_chosen_game_space());
            insert_statement.setString(6, get_player_session());
            insert_statement.setString(7, get_date_received());
            insert_statement.setString(8, get_time_received());
            
            insert_statement.addBatch();
            
            insert_statement.executeBatch();
            
            output = "success";
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_game_spaces' " +
                    "table is corrupt or does not exist");
            
            create_new_game_spaces_table();
            
            output = "fail";
        }
        
        return output;
    }
}