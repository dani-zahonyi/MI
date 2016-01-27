package miproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
//public class Main {
//
//	public static void main(String[] args) {
//		
//		HashMap<String,String> myspace_ids=		new HashMap<String,String>();
//			
//			 try {
//				 DBConnect dbconn =new DBConnect();
//				 myspace_ids=	dbconn.getRandIDs();
////				 dbconn.getData();
//				 dbconn.closeConnection();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			System.out.println(myspace_ids);
//			 
//
//			new  MySpaceSearch(myspace_ids);
//	}
//
//}
//
//package hu.ppke.itk.mi;
//

/**
 * Created by dobei_000 on 2015.03.13..
 */

public class Main {
	public static String readFromKeyboard() throws IOException {
		String str = "";

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		str=br.readLine();

		return str;
	}

	public static void main(String[] args) {


		try {
			DBConnect DBConn = new DBConnect();
			String artist;
			artist =readFromKeyboard();
			System.out.println(artist);
			ArrayList<ArrayList<Boolean>> matrix = DBConn.likedArtists();
//			System.out.println(DBConn.getAritstByIndex(8));
			ArrayList<Integer> valami =AI.bestOnes(DBConn.getAritstIndex(artist), matrix, 5);
			for(int i:valami)
			{
				System.out.println(DBConn.getAritstByIndex(i));
			}
				
			System.out.println(AI.bestOnes(DBConn.getAritstIndex(artist), matrix, 5));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<Boolean> zene1 = new ArrayList<Boolean>();
		ArrayList<Boolean> zene2 = new ArrayList<Boolean>();

		zene1.add(true);
		zene1.add(true);
		zene1.add(true);
		zene1.add(true);
		zene1.add(true);
		zene1.add(false);

		zene2.add(false);
		zene2.add(false);
		zene2.add(false);
		zene2.add(false);
		zene2.add(false);
		zene2.add(true);

//		System.out.println(AI.SlopeOne(zene1, zene2));

		// // http://static.echonest.com/playlist/moms/
		// // beparsoljuk a zenei listát
		// MapParser <MusicalGenre> musicParser = new MapParser("map.txt");
		//
		// // zenei műfajok listája
		// ArrayList<MusicalGenre> categories = musicParser.getCategories();
		//
		// // új hálót csinálunk ezekkel a műfajokkal
		// Net net = new Net(categories);
		//
		// for(GraphNode gn: categories)
		// {
		// System.out.println(gn.getName());
		// }
		//
		//
		// // megcsináljuk a klaszterhalmazokat
		// ArrayList<Bug> clasters = new ArrayList<Bug>(net.createClusters());
		// // a klaszterhalmazok valamelyikében szereplő node-ok listája
		// (mindegyik csak egyszer max)
		// ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();
		// // a klaszterhalmazokban többször előforduló node-ok
		// ArrayList<GraphNode> doubleNodes = new ArrayList<GraphNode>();
		//
		//
		// HashMap<GraphNode, ArrayList<GraphNode>> classicClasters =
		// net.classicCluster();
		//
		// // System.out.println("Cluster source size: " +
		// classicClasters.keySet().size());
		// int num = 0;
		// // for(GraphNode gn: classicClasters.keySet()){
		// // System.out.println(gn.getName());
		// // System.out.println(classicClasters.get(gn).size());
		// // // System.out.println("---------------------------");
		// // //System.out.println(classicClasters.get(gn).get(0).getName());
		// // num += classicClasters.get(gn).size();
		// // }
		//
		// System.out.println(num);
		// System.out.println("-------------------------------");
		// HashMap<GraphNode, ArrayList<GraphNode>> heurClasters =
		// net.heuristicCluster();
		// num = 0;
		// for(GraphNode gn: heurClasters.keySet()){
		// // System.out.println(gn.getName());
		// // System.out.println(heurClasters.get(gn).size());
		// // System.out.println("---------------------------");
		// //System.out.println(classicClasters.get(gn).get(0).getName());
		// num += heurClasters.get(gn).size();
		//
		// }
		// System.out.println(num);
		//
	}
}
