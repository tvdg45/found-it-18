//Author: Timothy van der Graaff
package views;

import java.util.ArrayList;
import utilities.Find_and_replace;

public class Show_Loaded_Game {

    //global variables
    public static String player_session_status;
    public static ArrayList<String> available_games = new ArrayList<>();
    public static ArrayList<ArrayList<String>> occupied_game_spaces = new ArrayList<>();
    
    public static String show_available_games() {
        
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
        
        output += "{\"session_status\": \"[{" + player_session_status + "}]\", ";
        output += "\"available_games\": [";
        
        for (int i = 0; i < available_games.size(); i++) {
            
            output += "{\"row_id\": \"" +
                    Find_and_replace.find_and_replace(find, replace, String.valueOf(available_games.get(i)).replace("<", "&lt;").replace(">", "&gt;")) +
                    "\"}, ";
        }
        
        output += "{}]}";
        
        output = output.replace(", {}", ""); 
        
        return output;
    }
    
    public static String show_loaded_game() {
        
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
        }
        
        output += "{}]";
        
        output = output.replace(", {}", ""); 
        
        return output;
    }
    
    public static String show_loaded_game_error_message() {
        
        String output = "";
        
        output += "[";
        output += "{\"game_id\": \"no game spaces occupied\", \"player_chosen_game_piece\": \"no game spaces occupied\", \"player_chosen_game_space\": \"no game spaces occupied\", \"player_session\": \"no game spaces occupied\"}";
        output += "]";
        
        return output;
    }
}