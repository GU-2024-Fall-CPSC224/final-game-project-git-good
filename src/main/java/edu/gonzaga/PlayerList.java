package edu.gonzaga;

import java.util.ArrayList;


public class PlayerList extends Player{
    private final ArrayList<Player> players;

    public PlayerList(){
        players = new ArrayList<>();

    }

    public void addPlayer(String playerName){
        players.add(new Player(playerName));
    }

    /**
     * Gets the name of the player
     */
    public String getPlayerName(Integer index){
        return players.get(index).name;
    }

    /**
     * gets the number of players in the list
     */
    public Integer getNumPlayers(){
        return players.size();
    }


}
