package de.coins2015.oscar1.twittercrawler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    public Controller() {
    }

    public static void main(String[] args) {

	String host = args[0];
	String port = args[1];
	String userName = args[2];
	String password = args[3];
	String database = args[4];
	String searchTxt = args[5];

	MongoDBHandler mdbh = new MongoDBHandler(host, port, userName,
		database, password);

	TwitterCrawler tc = new TwitterCrawler(mdbh);

	List<String> searchItems = new ArrayList<String>();
	try {
	    for (String searchItem : Files.readAllLines(Paths.get(searchTxt),
		    Charset.defaultCharset())) {
		if (!searchItem.startsWith("-") && !searchItem.isEmpty()) {
		    searchItems.add(searchItem);
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}

	tc.retrieve(searchItems);

    }

    private static List<List<String>> getItemLists(String searchTxt) {
	List<String> searchItems = new ArrayList<String>();
	try {
	    searchItems = Files.readAllLines(Paths.get(searchTxt),
		    Charset.defaultCharset());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	int generalStartIndex = 0;
	int generalEndIndex = 0;
	int pictureStartIndex = 0;
	int pictureEndIndex = 0;
	int directorStartIndex = 0;
	int directorEndIndex = 0;
	int actorStartIndex = 0;
	int actorEndIndex = 0;
	int actressStartIndex = 0;
	int actressEndIndex = 0;
	int sActorStartIndex = 0;
	int sActorEndIndex = 0;
	int sActressStartIndex = 0;
	int sActressEndIndex = 0;
	for (String searchItem : searchItems) {
	    switch (searchItem) {
	    case "-General-":
		generalStartIndex = searchItems.indexOf(searchItem) + 1;
		break;
	    case "-/General-":
		generalEndIndex = searchItems.indexOf(searchItem) - 1;
		break;
	    case "-Best Picture-":
		pictureStartIndex = searchItems.indexOf(searchItem) + 1;
		break;
	    case "-/Best Picture-":
		pictureEndIndex = searchItems.indexOf(searchItem) - 1;
		break;
	    case "-Best Director-":
		directorStartIndex = searchItems.indexOf(searchItem) + 1;
		break;
	    case "-/Best Director-":
		directorEndIndex = searchItems.indexOf(searchItem) - 1;
		break;
	    case "-Best Actor-":
		actorStartIndex = searchItems.indexOf(searchItem) + 1;
		break;
	    case "-/Best Actor-":
		actorEndIndex = searchItems.indexOf(searchItem) - 1;
		break;
	    case "-Best Actress-":
		actressStartIndex = searchItems.indexOf(searchItem) + 1;
		break;
	    case "-/Best Actress-":
		actressEndIndex = searchItems.indexOf(searchItem) - 1;
		break;
	    case "-Best Supporting Actor-":
		sActorStartIndex = searchItems.indexOf(searchItem) + 1;
		break;
	    case "-/Best Supporting Actor-":
		sActorEndIndex = searchItems.indexOf(searchItem) - 1;
		break;
	    case "-Best Supporting Actress-":
		sActressStartIndex = searchItems.indexOf(searchItem) + 1;
		break;
	    case "-/Best Supporting Actress-":
		sActressEndIndex = searchItems.indexOf(searchItem) - 1;
		break;

	    default:
		break;
	    }
	}

	List<String> generalItems = searchItems.subList(generalStartIndex,
		generalEndIndex);
	List<String> bestPictureItems = searchItems.subList(pictureStartIndex,
		pictureEndIndex);
	List<String> bestDirectorItems = searchItems.subList(
		directorStartIndex, directorEndIndex);
	List<String> bestActorItems = searchItems.subList(actorStartIndex,
		actorEndIndex);
	List<String> bestActressItems = searchItems.subList(actressStartIndex,
		actressEndIndex);
	List<String> bestSupportingActorItems = searchItems.subList(
		sActorStartIndex, sActorEndIndex);
	List<String> bestSupportingActressItems = searchItems.subList(
		sActressStartIndex, sActressEndIndex);
	List<List<String>> itemLists = new ArrayList<List<String>>();
	itemLists.add(generalItems);
	itemLists.add(bestPictureItems);
	itemLists.add(bestDirectorItems);
	itemLists.add(bestActorItems);
	itemLists.add(bestActressItems);
	itemLists.add(bestSupportingActorItems);
	itemLists.add(bestSupportingActressItems);

	return itemLists;

    }
}
