//Author: Timothy van der Graaff
package views;

import java.util.ArrayList;
import utilities.Find_and_replace;

public class Show_Loaded_Game {
    
    //global variables
    public static String player_session_status;
    public static ArrayList<String> available_games = new ArrayList<>();
    public static ArrayList<String> all_players = new ArrayList<>();
    public static ArrayList<ArrayList<String>> other_player = new ArrayList<>();
    public static ArrayList<ArrayList<String>> game_player_information = new ArrayList<>();
    public static ArrayList<ArrayList<String>> game_players = new ArrayList<>();
    public static ArrayList<ArrayList<String>> game_players_whose_turn = new ArrayList<>();
    public static ArrayList<ArrayList<String>> occupied_game_spaces = new ArrayList<>();
    public static ArrayList<ArrayList<String>> instant_chat_messages = new ArrayList<>();
    public static ArrayList<ArrayList<String>> all_games = new ArrayList<>();
    
    public static String show_available_games() {
        
        String output = "";
        
        ArrayList<String> find = new ArrayList<>();
        ArrayList<String> replace = new ArrayList<>();
        
        int players_per_game = 0;
        int available_games_count = 0;
        
        find.add("<script");
        find.add("<style");
        find.add("\"");
        find.add("'");
        find.add("<br />");
        find.add("<br>");
        find.add("<div>");
        find.add("</div>");
        
        replace.add("&lt;script");
        replace.add("&lt;style");
        replace.add("&quot;");
        replace.add("&apos;");
        replace.add(" ");
        replace.add("");
        replace.add("");
        replace.add("");
        
        output += "{\"session_status\": \"" + player_session_status + "\", ";
        output += "\"available_games\": [";
        
        for (int i = 0; i < available_games.size(); i++) {
            
            for (int j = 0; j < all_players.size(); j++) {
                
                if (available_games.get(i).equals(all_players.get(j))) {
                    
                    players_per_game++;
                }
            }
            
            if (player_session_status.equals("session found")) {
                
                output += "{\"row_id\": \"" +
                        Find_and_replace.find_and_replace(find, replace, String.valueOf(available_games.get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                        "\"}, ";
                
                available_games_count++;               
            } else {
                
                if (players_per_game == 1) {
                    
                    output += "{\"row_id\": \"" +
                            Find_and_replace.find_and_replace(find, replace, String.valueOf(available_games.get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                            "\"}, ";
                    
                    available_games_count++;
                }
            }
            
            players_per_game = 0;
        }
        
        if (available_games_count == 0) {
            
            output += "{\"row_id\": \"no available games\"}, ";
        }
        
        output += "{}]}";
        
        output = output.replace(", {}", ""); 
        
        return output;
    }
    
    public static String show_other_player() {
        
        String output = "";
        
        ArrayList<String> find = new ArrayList<>();
        ArrayList<String> replace = new ArrayList<>();
        
        find.add("<script");
        find.add("<style");
        find.add("\"");
        find.add("'");
        find.add("<br />");
        find.add("<br>");
        find.add("<div>");
        find.add("</div>");
        
        replace.add("&lt;script");
        replace.add("&lt;style");
        replace.add("&quot;");
        replace.add("&apos;");
        replace.add(" ");
        replace.add("");
        replace.add("");
        replace.add("");
        
        output += "[";
        
        for (int i = 0; i < other_player.get(0).size(); i++) {
            
            output += "{\"player_full_name\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(other_player.get(0).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"player_chosen_game_piece\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(other_player.get(1).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\"}, ";
        }
        
        output += "{}]";
        
        output = output.replace(", {}", ""); 
        
        return output;
    }
    
    public static String show_loaded_game() {
        
        String output = "";
        
        ArrayList<String> find = new ArrayList<>();
        ArrayList<String> replace = new ArrayList<>();
        
        int game_player_information_count = 0;
        int players_per_game = 0;
        int game_player_turns = 0;
        int game_spaces_taken = 0;
        int instant_chat_message_count = 0;
        
        find.add("<script");
        find.add("<style");
        find.add("\"");
        find.add("'");
        find.add("<br />");
        find.add("<br>");
        find.add("<div>");
        find.add("</div>");
        
        replace.add("&lt;script");
        replace.add("&lt;style");
        replace.add("&quot;");
        replace.add("&apos;");
        replace.add(" ");
        replace.add("");
        replace.add("");
        replace.add("");
        
        output += "{\"game_player_information\": [";
        
        for (int i = 0; i < game_player_information.get(0).size(); i++) {
            
            output += "{\"player_id\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(game_player_information.get(0).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"player_full_name\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(game_player_information.get(1).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"player_chosen_game_piece\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(game_player_information.get(2).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\"}, ";
            
            game_player_information_count++;
        }
        
        if (game_player_information_count != 1) {
            
            output += "{\"player_id\": \"no players\", \"player_full_name\": \"no players\", " +
                    "\"player_chosen_game_piece\": \"no players\"}, ";
        }
        
        output += "{}], ";
        output += "\"game_players\": [";
        
        for (int i = 0; i < game_players.get(0).size(); i++) {
            
            output += "{\"player_id\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(game_players.get(0).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"player_full_name\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(game_players.get(1).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"player_chosen_game_piece\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(game_players.get(2).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\"}, ";
            
            players_per_game++;
        }
        
        if (players_per_game != 2) {
            
            output += "{\"player_id\": \"no players\", \"player_full_name\": \"no players\", " +
                    "\"player_chosen_game_piece\": \"no players\"}, ";
        }
        
        output += "{}], ";
        output += "\"game_player_turns\": [";
        
        for (int i = 0; i < game_players_whose_turn.get(0).size(); i++) {
            
            output += "{\"player_id\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(game_players_whose_turn.get(0).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"player_has_turn\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(game_players_whose_turn.get(1).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\"}, ";
            
            game_player_turns++;
        }
        
        if (game_player_turns != 2) {
            
            output += "{\"player_id\": \"no players\", \"player_has_turn\": \"no players\"}, ";
        }
       
        output += "{}], ";
        output += "\"game_board\": [";
        
        for (int i = 0; i < occupied_game_spaces.get(0).size(); i++) {
            
            output += "{\"game_id\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(occupied_game_spaces.get(0).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"player_chosen_game_piece\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(occupied_game_spaces.get(1).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"player_chosen_game_space\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(occupied_game_spaces.get(2).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"player_session\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(occupied_game_spaces.get(3).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\"}, ";
            
            game_spaces_taken++;
        }
        
        if (game_spaces_taken == 0) {
            
            output += "{\"game_id\": \"no game spaces occupied\", " +
                    "\"player_chosen_game_piece\": \"no game spaces occupied\", " +
                    "\"player_chosen_game_space\": \"no game spaces occupied\", " +
                    "\"player_session\": \"no game spaces occupied\"}, ";
        }
        
        output += "{}], ";
        output += "\"chat_messages\": [";
        
        for (int i = 0; i < instant_chat_messages.get(0).size(); i++) {
            
            output += "{\"game_id\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(instant_chat_messages.get(0).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"player_full_name\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(instant_chat_messages.get(1).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"player_message\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(instant_chat_messages.get(2).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"date_received\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(instant_chat_messages.get(3).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"time_received\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(instant_chat_messages.get(4).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\"}, ";
            
            instant_chat_message_count++;
        }
        
        if (instant_chat_message_count == 0) {
            
            output += "{\"game_id\": \"no message\", " +
                    "\"player_full_name\": \"no message\", " +
                    "\"player_message\": \"no message\", " +
                    "\"date_received\": \"no message\", " +
                    "\"time_received\": \"no message\"}, ";
        }
        
        output += "{}]}";
        
        output = output.replace(", {}", ""); 
        
        return output;
    }
    
    public static String show_loaded_game_error_message() {
        
        String output = "";
        
        output += "{\"game_player_information\": [";
        output += "{\"player_id\": \"no players\", \"player_full_name\": \"no players\", " +
                "\"player_chosen_game_piece\": \"no players\"}], ";
        output += "\"game_players\": [";
        output += "{\"player_id\": \"no players\", \"player_full_name\": \"no players\", " +
                "\"player_chosen_game_piece\": \"no players\"}], ";
        output += "\"game_player_turns\": [";
        output += "{\"player_id\": \"no players\", \"player_has_turn\": \"no players\"}], ";
        output += "\"game_board\": [";
        output += "{\"game_id\": \"no game spaces occupied\", " +
                "\"player_chosen_game_piece\": \"no game spaces occupied\", " +
                "\"player_chosen_game_space\": \"no game spaces occupied\", " +
                "\"player_session\": \"no game spaces occupied\"}], ";
        output += "\"chat_messages\": [";
        output += "{\"player_full_name\": \"no message\", " +
                "\"player_message\": \"no message\", " +
                "\"date_received\": \"no message\", " +
                "\"time_received\": \"no message\"}]}";
        
        return output;
    }
    
    public static String show_all_chat_messages() {
        
        String output = "";
        int instant_chat_message_count = 0;
        
        ArrayList<String> find = new ArrayList<>();
        ArrayList<String> replace = new ArrayList<>();
        
        find.add("<script");
        find.add("<style");
        find.add("\"");
        find.add("'");
        find.add("<br />");
        find.add("<br>");
        find.add("<div>");
        find.add("</div>");
        
        replace.add("&lt;script");
        replace.add("&lt;style");
        replace.add("&quot;");
        replace.add("&apos;");
        replace.add(" ");
        replace.add("");
        replace.add("");
        replace.add("");
        
        output += "[";
        
        for (int i = 0; i < instant_chat_messages.get(0).size(); i++) {
            
            output += "{\"game_id\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(instant_chat_messages.get(0).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"player_full_name\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(instant_chat_messages.get(1).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"player_message\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(instant_chat_messages.get(2).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"date_received\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(instant_chat_messages.get(3).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"time_received\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(instant_chat_messages.get(4).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\"}, ";
            
            instant_chat_message_count++;
        }
        
        if (instant_chat_message_count == 0) {
            
            output += "{\"game_id\": \"no message\", " +
                    "\"player_full_name\": \"no message\", " +
                    "\"player_message\": \"no message\", " +
                    "\"date_received\": \"no message\", " +
                    "\"time_received\": \"no message\"}, ";
        }
        
        output += "{}]";
        
        output = output.replace(", {}", ""); 
        
        return output;
    }
    
    public static String show_all_games() {
        
        String output = "";
        int game_count = 0;
        
        ArrayList<String> find = new ArrayList<>();
        ArrayList<String> replace = new ArrayList<>();
        
        find.add("<script");
        find.add("<style");
        find.add("\"");
        find.add("'");
        find.add("<br />");
        find.add("<br>");
        find.add("<div>");
        find.add("</div>");
        
        replace.add("&lt;script");
        replace.add("&lt;style");
        replace.add("&quot;");
        replace.add("&apos;");
        replace.add(" ");
        replace.add("");
        replace.add("");
        replace.add("");
        
        output += "[";
        
        for (int i = 0; i < all_games.get(0).size(); i++) {
            
            output += "{\"game_id\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(all_games.get(0).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"date_received\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(all_games.get(1).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\", \"time_received\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(all_games.get(2).get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\"}, ";
            
            game_count++;
        }
        
        if (game_count == 0) {
            
            output += "{\"game_id\": \"0\", " +
                    "\"date_received\": \"no game\", " +
                    "\"time_received\": \"no game\"}, ";
        }
        
        output += "{}]";
        
        output = output.replace(", {}", "");
        
        return output;
    }
}
