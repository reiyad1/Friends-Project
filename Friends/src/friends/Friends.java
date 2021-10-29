package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) 
	{
		/** COMPLETE THIS METHOD **/
		ArrayList<String> str = new ArrayList<String>();
		ArrayList<String> result = new ArrayList<String>();
		if (p1 == p2)
		{
			result.add(p1);
			return result;
		}

		int a = g.members.length;
		boolean[] marked = new boolean[a];
		int[] edgeTo = new int[a];
		int[] distTo = new int[a];

		for (int i = 0; i < a; i++)
		{
			marked[i] = false;
			distTo[i] = Integer.MAX_VALUE;
			edgeTo[i] = -1;
		}
		

		//Friend t = g.members[0].first;
		Queue<String> queue = new Queue<String>();
		marked[g.map.get(p1)] = true;
		distTo[g.map.get(p1)] = 0;
		queue.enqueue(p1);

		//System.out.println(g.map.get(p2));

		while(!queue.isEmpty())
		{
			String v = queue.dequeue();
			for (Friend w = g.members[g.map.get(v)].first; w != null; w = w.next)
			{
				//System.out.println(w.fnum);	//test
				if (!marked[w.fnum])
				{
					marked[w.fnum] = true;
					queue.enqueue(g.members[w.fnum].name);
					//System.out.println(g.members[w.fnum].name);	//test
					edgeTo[w.fnum] = g.map.get(v);
					distTo[w.fnum] = distTo[g.map.get(v)] + 1;

					if (w.fnum == g.map.get(p2) || edgeTo[w.fnum] == g.map.get(p2))
					{
						int crawl = g.map.get(p2);
						str.add(g.members[crawl].name);
						while (edgeTo[crawl] != -1)
						{
							str.add(g.members[edgeTo[crawl]].name);
							crawl = edgeTo[crawl];
						}
						for (int i = str.size()-1; i >= 0; i--)
						{
							result.add(str.get(i));
						}
						
						return result;
					}
				}
			}
		}
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return null;
	}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null or empty array list if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) 
	{
		/** COMPLETE THIS METHOD **/
		//boolean[] marked;
		//int[] edgeTo;

		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		int a = g.members.length;
		boolean[] marked = new boolean[a];
		//int[] edgeTo = new int[a];
		//boolean[] visited = new boolean[a];

		for (int i = 0; i < a; i++)
		{
			Person p = g.members[i];
			if (!p.student || marked[i])
				continue;
			ArrayList<String> newList = new ArrayList<String>();
			dfs(g, i, school, marked, newList);
			//marked[i] = false;
			//edgeTo[i] = -1;

			if (newList.size() > 0 && newList != null)
			{
				result.add(newList);	//add to big list
			}
		}


		//System.out.println("startIndex: " + startIndex);
		

/*		
		marked[school] = true;
		for (Friend w = g.members[g.map.get(school)].first; w != null; w = w.next)	//fix school
		{
			if (!marked[w])
			{
				if (w.school == school)
				{
					edgeTo[w] = school;
					cliques(g,w);
				}

			}
		}*/

		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return result;
		
	}
	private static void dfs(Graph g, int startIndex, String school, boolean[] marked, ArrayList<String> miniClique)
	{
		Person person = g.members[startIndex];

		if (!marked[startIndex] && person.school.equals(school) && person.student)
			miniClique.add(person.name);

		marked[g.map.get(person.name)] = true;

		Friend check = g.members[startIndex].first;
		while (check != null)	
		{
			int index = check.fnum;
			Person newFriend = g.members[index];
			//System.out.println("w: " + w.fnum);
			if (!marked[index])
			{
				//System.out.println("marked " + marked[w.fnum]);	//test
				if (newFriend.student && newFriend.school.equals(school))
				{
					dfs(g, index, school, marked, miniClique);
				}

			}
			check = check.next;	//goes to next friend

		}
		//return null;
	}


	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null or empty array list if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) 
	{
		/** COMPLETE THIS METHOD **/

		ArrayList<String> connectors = new ArrayList<String>(); //gonna return this

		int a = g.members.length;
		boolean[] marked = new boolean[a];
		int[] dfsnum = new int[a];
		int[] back = new int[a];
		boolean[] friend = new boolean[a];	//helps to see if friend is visited and not just rando person

		for(int i = 0; i < a; i++) 
		{
			if(!marked[i])
				dfs2(g, i, i, marked, dfsnum, back, connectors, friend);
			else
				continue;
		}

		return connectors;

		//return null

	}

	private static void dfs2(Graph g, int j, int i, boolean[] marked, int[] dfsnum, int back[], ArrayList<String> connectors, boolean[] friend)
	{
		marked[j] = true;
		dfsnum[j] = dfsnum[i] + 1;
		back[j] = dfsnum[j];

		//traverse
		for(Friend e = g.members[j].first; e != null; e = e.next) 
		{
			int w = e.fnum;	//index of person

			if(!marked[w])		//obvi checks if person was visited this proj is so stupid i hate it
			{
				dfs2(g, e.fnum, j, marked, dfsnum, back, connectors, friend);	//recursion4life

				if(dfsnum[j] <= back[w]) 
				{
					if (connectors.contains(g.members[j].name) == false)
					{
						if (j != i || friend[j])
							connectors.add(g.members[j].name);
					}
					//System.out.println(w);
				}

				if(dfsnum[j] > back[w]) 
				{
					back[j] = Math.min(back[j], back[w]);	//can i even use math.min idk
				}

				else 
				{
					friend[j] = true;
				}
			}

			else 	//if the friend has already been visited- marked is true
			{
				back[j] = Math.min(back[j], dfsnum[w]);
			}
			
		}
		
	}


}
