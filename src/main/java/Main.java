import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        Object[] sources = new Object[10];
        
        sources[0] = Directory_Hider.class;
		sources[1] = apps.Add_Game.class;
		sources[2] = apps.Add_Game_Piece.class;
		sources[3] = apps.Join_Game.class;
        sources[4] = apps.Load_Game.class;
		sources[5] = apps.Search_Available_Games.class;
		sources[6] = apps.Search_Other_Player.class;
		sources[7] = apps.Add_Chat_Message.class;
		sources[8] = apps.Admin_Extract_Chat_Messages.class;
		sources[9] = apps.Admin_Delete_Chat_Messages.class;
		
        SpringApplication.run(sources, args);
    }
}
