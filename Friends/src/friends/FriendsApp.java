package friends;

import java.io.*;
import java.util.*;

// Testing client for Friends
public class FriendsApp {

    public static void main (String[] args) {

	if ( args.length < 1 ) {
	    System.out.println("Expecting graph text file as input");
	    return;
	}

	String filename = args[0];
	try {
	    Graph g = new Graph(new Scanner(new File(filename)));

	    // Update p1 and p2 to refer to people on Graph g
	    // sam and sergei are from sample graph
	    String p1 = "sam";
	    String p2 = "michele";
	    ArrayList<String> shortestChain = Friends.shortestChain(g, p1, p2);

	    // Testing Friends.shortestChain
/*	    System.out.println("Shortest chain from " + p1 + " to " + p2);
		if (shortestChain == null)
			System.out.println("null");
	    else
		{
			for ( String s : shortestChain ) {
			System.out.println(s);
	    }
		}
		*/
	    // ADD test for Friends.cliques() here
		String school = "ucla";
		ArrayList<ArrayList<String>> cliques = Friends.cliques(g, school);
		for (int i = 0; i < cliques.size(); i++)
		{
			System.out.println(cliques.get(i));
		}
	    
	    
	    // ADD test for Friends.connectors() here
		ArrayList<String> connectors = Friends.connectors(g);
		if (connectors == null || connectors.size() == 0)
		{
			System.out.println("No connectors found");
		} else
		{
			System.out.print("(");
			for (String c : connectors)
			{
				System.out.println(c + ", ");
			}
			System.out.println(")");
		}

	} catch (FileNotFoundException e) {

	    System.out.println(filename + " not found");
	}
    }
}
