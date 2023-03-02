/*
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * 
 */

// Write your code here


package com.example.player.service;
import com.example.player.model.Player;
import com.example.player.repository.PlayerRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import java.util.*;
import com.example.player.model.PlayerRowMapper;
// Don't modify the below code

@service
public class PlayerH2Service implements PlayerRepository {
@Autowired
private JdbcTemplate db;
    private static HashMap<Integer, Player> team = new HashMap<>();
    int uniquePlayerId=12;
    public PlayerH2Service() {
        team.put(1, new Player(1, "Alexander", 5, "All-rounder"));
        team.put(2, new Player(2, "Benjamin", 3, "All-rounder"));
        team.put(3, new Player(3, "Michael", 18, "Batsman"));
        team.put(4, new Player(4, "William", 45, "Batsman"));
        team.put(5, new Player(5, "Joshua", 19, "Batsman"));
        team.put(6, new Player(6, "Daniel", 10, "Bowler"));
        team.put(7, new Player(7, "Matthew", 34, "Bowler"));
        team.put(8, new Player(8, "Samuel", 17, "Batsman"));
        team.put(9, new Player(9, "John", 1, "Bowler"));
        team.put(10, new Player(10, "Earnest", 2, "All-rounder"));
        team.put(11, new Player(11, "Bob", 25, "Batsman"));
    }

    // Don't modify the above code

    // Write your code here
    @Override
    public void deletePlayer(int playerId){
         db.update("delete from player where playerId=?",playerId);
        
    }

    @Override
    public Player updatePlayer(int playerId, Player player) {
        Player existingPlayer=team.get(playerId);
        if(existingPlayer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (player.getplayerName() != null) {
            db.update("update player set playerName=? where playerId=?",player.getplayerName(),playerId);
        }
        
        if (String.valueOf(player.getjerseyNumber()) != null) {
             db.update("update player set jerseyNumber=? where playerId=?",player.getjerseyNumber(),playerId);
        }
        if (player.getrole() != null) {
            db.update("update player set role=? where playerId=?",player.getrole(),playerId);
        }
        
        
    return getPlayerById(playerId);
    }
    }
     @Override
    public Player addPlayer(Player player) {
        db.update("insert into player(playerName,jerseyNumber,role) values (?,?,?)",player.getplayerName(),player.getjerseyNumber(),player.getrole());
        Player savedPlayer=db.queryForObject("select * from player where playerName=? and jerseyNumber=? and role=? ", 
                            new PlayerRowMapper(),player.getplayerName(),player.getjerseyNumber(),player.getrole());
        return savedPlayer;
    }

    @Override
    public ArrayList<Player> getPlayers() {
        List<Player> playerList = db.query("select * from Player",new PlayerRowMapper());
        
        ArrayList<Player> players = new ArrayList<>(playerList);
        return players;
    }
    @Override
    public Player getPlayerById(int playerId){
        try{
        Player player=db.queryForObject("select * from player where playerId= ? ",new PlayerRowMapper(),playerId);
        return player;
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
