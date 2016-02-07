/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

/**
 *
 * @author Romuald
 */
public class ScoreBoard {
   
    private Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
    private Scoreboard sbBuffer = Bukkit.getScoreboardManager().getNewScoreboard();
    private Objective objective = sb.registerNewObjective("players", "dummy");
    private Objective objectiveBuffer = sbBuffer.registerNewObjective("playerBuffer", "dummy");
  
    public HashMap<Player, Integer> scoreMap = new HashMap<>();
    
    public void setBoard(Player p, List<Player> playerList, String displayName){
      objective.setDisplaySlot(DisplaySlot.SIDEBAR);
      objective.setDisplayName(displayName);
      p.setScoreboard(sb);
      for(Player player : playerList){
        player.setScoreboard(sb);
      }
    }
  
    public void setScore(Player p, int score){
      Score s = objective.getScore(p.getName());
      Score s2 = objectiveBuffer.getScore(p.getName());
      int oldScore = scoreMap.containsKey(p) ? scoreMap.get(p) : 0;
      scoreMap.put(p, oldScore + score);

      s2.setScore(scoreMap.get(p));
      removeLowestScore(objectiveBuffer, 1);
      swap(objective, objectiveBuffer);

      s.setScore(scoreMap.get(p));
      removeLowestScore(objective, 1);
      swap(objectiveBuffer, objective);
    }
  
    public void swap(Objective ob1, Objective ob2){
      ob2.setDisplayName(ob1.getDisplayName());
      ob2.setDisplaySlot(ob1.getDisplaySlot());
    }
  
    public void removeLowestScore(Objective objective, int maxScoreboardEntries){
        if(objective.getScoreboard().getEntries().size() <= maxScoreboardEntries)
          return;

        int min = 0;
        Set<String> player = objective.getScoreboard().getEntries();
        List<Integer> scoreValue = new ArrayList<>();
        List<String> scorePlayer = new ArrayList<>();
        for(String p : player){
          scorePlayer.add(objective.getScore(p).getEntry());
          scoreValue.add(objective.getScore(p).getScore());
        }
        try{
          min = scoreValue.indexOf(Collections.min(scoreValue));
        } catch (NoSuchElementException nsee){
          System.out.println("No such element!");
        }

        objective.getScoreboard().resetScores(scorePlayer.get(min));
    }
}
