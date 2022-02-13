//Author: Timothy van der Graaff
package models;

import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Save_Chat_Messages {
    
    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public static Connection connection;
    
    //global variables
    private static int game_id;
    private static String player_full_name;
    private static String player_message;
    private static String player_session;
    private static String date_received;
    private static String time_received;
    
    //mutators
    protected static void set_game_id(int this_game_id) {
        
        game_id = this_game_id;
    }
    
    protected static void set_player_full_name(String this_player_full_name) {
        
        player_full_name = this_player_full_name;
    }
    
    protected static void set_player_message(String this_player_message) {
        
        player_message = this_player_message;
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
    
    private static String get_player_full_name() {
        
        return player_full_name;
    }
    
    private static String get_player_message() {
        
        return player_message;
    }
    
    private static String get_player_session() {
        
        return player_session;
    }
    
    private static String get_date_received() {
        
        return date_received;
    }
    
    private static String get_time_received() {
        
        return time_received;
    }
    
    
    

    
    private static int generate_tic_tac_toe_chat_message_id() {
        
        int output;
        
        try {
            
            PreparedStatement prepared_statement = connection.prepareStatement("SELECT row_id FROM " +
                    "company_tic_tac_toe_chat_messages ORDER BY row_id DESC LIMIT 1");
            
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
    
    protected static String add_chat_message() {
        
        String output;
        
        try {
            
            PreparedStatement insert_statement = connection.prepareStatement("INSERT INTO " +
                    "company_tic_tac_toe_chat_messages (row_id, player_session, player_full_name, " +
                    "player_message, game_id, date_received, time_received) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)");
            
            insert_statement.setInt(1, generate_tic_tac_toe_chat_message_id());
            insert_statement.setString(2, get_player_session());
            insert_statement.setString(3, get_player_full_name());
            insert_statement.setString(4, get_player_message());
            insert_statement.setString(5, String.valueOf(get_game_id()));
            insert_statement.setString(6, get_date_received());
            insert_statement.setString(7, get_time_received());
            
            insert_statement.addBatch();
            
            insert_statement.executeBatch();
            
            output = "success";
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_chat_messages' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_chat_messages_table();
            
            output = "fail";
        }
        
        return  output;
    }
    
    //Search all chat messages relating to your game.
    protected static ArrayList<ArrayList<String>> player_search_instant_chat_messages() {
        
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        
        ArrayList<String> use_game_id = new ArrayList<>();
        ArrayList<String> use_player_full_name = new ArrayList<>();
        ArrayList<String> use_player_message = new ArrayList<>();
        ArrayList<String> use_date_received = new ArrayList<>();
        ArrayList<String> use_time_received = new ArrayList<>();
        
        int chat_message_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT game_id, player_full_name, player_message, " +
                    "date_received, time_received FROM company_tic_tac_toe_chat_messages " + 
                    "WHERE game_id = ? ORDER BY row_id DESC");
            
            select_statement.setString(1, String.valueOf(get_game_id()));
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                use_game_id.add(select_results.getString(1));
                use_player_full_name.add(select_results.getString(2));
                use_player_message.add(select_results.getString(3));
                use_date_received.add(select_results.getString(4));
                use_time_received.add(select_results.getString(5));
                
                chat_message_count++;
            }
            
            if (chat_message_count == 0) {
                
                use_game_id.add("no message");
                use_player_full_name.add("no message");
                use_player_message.add("no message");
                use_date_received.add("no message");
                use_time_received.add("no message");
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_chat_messages' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_chat_messages_table();
            
            use_game_id.add("fail");
            use_player_full_name.add("fail");
            use_player_message.add("fail");
            use_date_received.add("fail");
            use_time_received.add("fail");
        }
        
        output.add(use_game_id);
        output.add(use_player_full_name);
        output.add(use_player_message);
        output.add(use_date_received);
        output.add(use_time_received);
                
        return output;
    }
    
    //A third party server searches all chat messages.
    protected static ArrayList<ArrayList<String>> search_instant_chat_messages() {
        
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        
        ArrayList<String> use_game_id = new ArrayList<>();
        ArrayList<String> use_player_full_name = new ArrayList<>();
        ArrayList<String> use_player_message = new ArrayList<>();
        ArrayList<String> use_date_received = new ArrayList<>();
        ArrayList<String> use_time_received = new ArrayList<>();
        
        int chat_message_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT game_id, player_full_name, player_message, " +
                    "date_received, time_received FROM company_tic_tac_toe_chat_messages ORDER BY row_id DESC");
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                use_game_id.add(select_results.getString(1));
                use_player_full_name.add(select_results.getString(2));
                use_player_message.add(select_results.getString(3));
                use_date_received.add(select_results.getString(4));
                use_time_received.add(select_results.getString(5));
                
                chat_message_count++;
            }
            
            if (chat_message_count == 0) {
                
                use_game_id.add("no message");
                use_player_full_name.add("no message");
                use_player_message.add("no message");
                use_date_received.add("no message");
                use_time_received.add("no message");
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_chat_messages' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_chat_messages_table();
            
            use_game_id.add("fail");
            use_player_full_name.add("fail");
            use_player_message.add("fail");
            use_date_received.add("fail");
            use_time_received.add("fail");
        }
        
        output.add(use_game_id);
        output.add(use_player_full_name);
        output.add(use_player_message);
        output.add(use_date_received);
        output.add(use_time_received);
                
        return output;
    }
    
    //A third party server searches all games.
    protected static ArrayList<String> search_all_games() {
        
        ArrayList<String> output = new ArrayList<>();
        
        int game_count = 0;
        
        PreparedStatement select_statement;
        ResultSet select_results;
        
        try {
            
            select_statement = connection.prepareStatement("SELECT row_id " +
                    "FROM company_tic_tac_toe_games ORDER BY row_id DESC");
            
            select_results = select_statement.executeQuery();
            
            while (select_results.next()) {
                
                output.add(String.valueOf(select_results.getInt(1)));
                
                game_count++;
            }
            
            if (game_count == 0) {
                
                output.add("no game");
            }
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_games' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_games_table();
            
            output.add("fail");
        }
        
        return output;
    }
    
    //A third party server deletes all chat messages.
    protected static String delete_all_chat_messages() {
        
        String output;
        
        PreparedStatement delete_statement;

        try {
            
            delete_statement = connection.prepareStatement("DELETE " +
                    "FROM company_tic_tac_toe_chat_messages");
            
            delete_statement.addBatch();
            
            delete_statement.executeBatch();
            
            output = "success";
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_chat_messages' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_chat_messages_table();
            
            output = "fail";
        }
        
        return output;
    }
    
    //A third party server deletes all games.
    protected static String delete_all_games() {
        
        String output;
        
        PreparedStatement delete_statement;

        try {
            
            delete_statement = connection.prepareStatement("DELETE " +
                    "FROM company_tic_tac_toe_games");
            
            delete_statement.addBatch();
            
            delete_statement.executeBatch();
            
            output = "success";
        } catch (SQLException e) {
            
            LOGGER.log(Level.INFO, "The 'company_tic_tac_toe_games' " +
                    "table is corrupt or does not exist");
            
            create_new_tic_tac_toe_games_table();
            
            output = "fail";
        }
        
        return output;
    }
}