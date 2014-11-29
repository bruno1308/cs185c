package edu.sjsu.cs185c.util;

import org.bukkit.ChatColor;

public enum ErrorHandler {
	PERMISSION_DENIED("You don't have the permission to use this command."),
	INVALID_TARGET("Invalid target to destroy."),
	INVALID_LOCATION("Invalid location to visit."),
	INVALID_COMMAND("Invalid command."),
	INEXISTENT_COMMAND("Command doesn't exist."),
	INEXISTENT_PLAYER("Player doesn't exist."),
	INEXISTENT_PROFESSION("This profession doesn't exist."),
	INVALID_NUMBER("Amount provided is not a number."),
	NOT_ENOUGH_MONEY("You don't have enough money to transfer."),
	WRONG_COMMAND_USAGE("Wrong command usage."),
	WRONG_NUMBER_OF_PARMS("Wrong number of parameters.");


	 private String message;
	 public String toString()
	    {
	        return ChatColor.RED+message;
	    }
	 ErrorHandler(String message)
	    {
	        this.message = message;
	    }
}
