package miproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class MySpaceSearch {

	protected JMenuBar menubar;
	protected JMenu file;
	protected JMenuItem exit, newGame;

	private JFrame window;
	private JPanel cpanel = new JPanel();
	private JPanel bpanel = new JPanel();
	private JPanel toppanel = new JPanel();
	private JLabel topLabel = new JLabel();
	private JFrame menu;
	private JTextField username = new JTextField();
	private JLabel leftLabel = new JLabel();
	private JButton okButton = new JButton(), noButton = new JButton();
	private HashMap<String, String> ids;
	private Stack<String> artists= new Stack<String>();

	public MySpaceSearch(HashMap<String, String> ids) {
		this.ids = ids;
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				init();
			}
		});
	}

	private void init() {

		menubar = new JMenuBar();
		file = new JMenu("File");
		exit = new JMenuItem("Exit");

		window = new JFrame("RND MUSIC liking");
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = window.getContentPane();

		window.setJMenuBar(menubar);
		menubar.add(file);
		file.add(exit);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.exit(0);
			}
		});

		cpanel.setLayout(new BorderLayout());
		bpanel.setLayout(new BorderLayout());
		bpanel.add(okButton, "North");
		bpanel.add(noButton, "South");

		okButton.setText("OK");
		noButton.setText("NO");

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Search s1 = new Search();
				new Thread(s1).start();
			}
		});
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Search s1 = new Search();
				new Thread(s1).start();
			System.out.println(artists.pop());
			}
		});

		topLabel.setBackground(new Color(0, 128, 255));
		topLabel.setText("Artist:");
		topLabel.setForeground(new Color(255, 255, 255));
		toppanel.setBackground(new Color(0, 128, 255));
		leftLabel.setForeground(new Color(90, 60, 90));
		leftLabel.setPreferredSize(new Dimension(300, 300));
		cpanel.add(leftLabel, "Center");
		cpanel.add(bpanel, "South");
		cpanel.add(new JScrollPane(leftLabel), "West");
		cpanel.add(username, "North");
		toppanel.add(topLabel);
		container.setLayout(new BorderLayout(10, 10));
		container.add(toppanel, "North");
		container.add(cpanel, "Center");
		window.pack();
		window.setVisible(true);
	}

	public class Search implements Runnable {

		private String artistID, imagelink,
				mySpaceSearch = "https://myspace.com/";
		private String artist = "";

		public String getArtist() {
			return artist;
		}

		public void run() {
			try {
				if(ids.isEmpty()){
					System.out.println("Menti az adatokat");
					new DBConnect().insertRecordIntoDbUserTable(username.getText(), artists);
					
					System.exit(0);
					}
					
				List<String> valuesList = new ArrayList<String>(ids.keySet());
				
				artistID = ids.remove(valuesList.get(valuesList.size() - 1));
				artist = valuesList.get(valuesList.size() - 1);
				topLabel.setText("Artist: " + artist);
				artists.push(artist);
				URL urlicon;
				urlicon = new URL(numOfResults(artistID));
				BufferedImage img = ImageIO.read(urlicon);
				ImageIcon icon = new ImageIcon(img);
				leftLabel.setIcon(icon);
				window.pack();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public String numOfResults(String artistID) {
			try {
				URL myUrl = new URL(mySpaceSearch + artistID);
				URLConnection gConn = myUrl.openConnection();

				BufferedReader in = new BufferedReader(new InputStreamReader(
						gConn.getInputStream(), "UTF-8"));

				String inputLine;
				StringBuilder a = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					if (inputLine.matches(".*300x300.jpg.*"))
						a.append(inputLine);

				}
				in.close();
				String fullText = a.toString();
				System.out.println(fullText);

				imagelink = fullText.replaceFirst(".*img src=\"", "")
						.replaceFirst("\" alt=\"\" />.*", "");

				return imagelink;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "Ha ez megy vissza, akkor gond van";
		}
	}
}