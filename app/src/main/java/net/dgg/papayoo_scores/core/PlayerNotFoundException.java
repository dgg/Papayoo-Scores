package net.dgg.papayoo_scores.core;

public class PlayerNotFoundException extends IllegalArgumentException{
    private PlayerNotFoundException(String detailedMessage){
        super(detailedMessage);
    }

    public static PlayerNotFoundException forName(String name){
        String detailedMessage = String.format("No player named '{0}'.", name);
        return new PlayerNotFoundException(name);
    }
}
