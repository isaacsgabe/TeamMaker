# TeamMaker
TeamMaker is an algorithm that creates new and fair teams regardless of how many players or teams are necessary.
TeamMakers uses a Java GUI to take in the input of how many teams are needed, if there are positions or not, and retreives the excel spreadsheet of player names 
with there ranking. 
The first column in the spreadsheet must either be the positition,or the names, depending on if their are positions or not. 
The second column of the spreadhsheet is the talent level of the player or the name of the player, depending on if their are positions or not. 
The final column of the spreadsheet should exist only if there are position in column 1. In which case column 3 should be the talent levels of the players.  
The main data structure for TeamMaker is either a priority queue or a Hashtable depending on whether there are positions for the players or not.
