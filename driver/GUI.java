/*  $$$$$:  Comments by Liang
 *
 *  $$$$$$: Codes modified and/or added by Liang
 */


package driver;

import ga.GAChartOutput;
import ga.GATracker;
import ga.GeneticCode;
import ga.GeneticCodeException;
import ga.PhenotypeMaster;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

/**
 * Simulation configuration dialog
 * @author time itself
 *
 */
public class GUI extends JPanel implements ActionListener {

	final String TickScheduler = "cobweb.TickScheduler";
	TextField Width;
	TextField Height;
	Checkbox keepOldAgents;
	Checkbox spawnNewAgents;
	Checkbox keepOldArray;
	Checkbox dropNewFood;
	Checkbox ColorCodedAgents;
	Checkbox newColorizer;
	Checkbox keepOldWaste;
	Checkbox keepOldPackets;
	Checkbox wrap;
	Checkbox flexibility;
	Checkbox PrisDilemma;
	Checkbox FoodWeb;
	TextField numColor;
	TextField colorSelectSize;
	TextField reColorTimeStep;
	TextField colorizerMode;
	TextField RandomSeed;
	TextField randomStones;
	TextField maxFoodChance;
	TextField memory_size;

	// //////////////////////////////////////////////////////////////////////////////////////////////

	public static final int GA_AGENT_1_ROW = 0;
	public static final int GA_AGENT_2_ROW = 1;
	public static final int GA_AGENT_3_ROW = 2;
	public static final int GA_AGENT_4_ROW = 3;
	public static final int GA_LINKED_PHENOTYPE_ROW = 4;
	public static final int GA_GENE_1_COL = 1;
	public static final int GA_GENE_2_COL = 2;
	public static final int GA_GENE_3_COL = 3;
	public static final int GA_NUM_AGENT_TYPES = 4;
	public static final int GA_NUM_GENES = 3;

	/** The list of mutable phenotypes shown on Genetic Algorithm tab. */
	JList mutable_phenotypes;

	/** The TextFields and Buttons of the Genetic Algorithm tab. */
	JButton link_gene_1 = new JButton("Link to Gene 1.");

	JButton link_gene_2 = new JButton("Link to Gene 2.");

	JButton link_gene_3 = new JButton("Link to Gene 3.");

	public static String[] meiosis_mode_list = {"Colour Averaging",
	"Random Recombination", "Gene Swapping"};
	public static JComboBox meiosis_mode = new JComboBox(meiosis_mode_list);

	/** Default genetic bits. All genes are 00011110. */
	public static String[][] default_genetics = {
			{ "Agent Type 1", "00011110", "00011110", "00011110" },
			{ "Agent Type 2", "00011110", "00011110", "00011110" },
			{ "Agent Type 3", "00011110", "00011110", "00011110" },
			{ "Agent Type 4", "00011110", "00011110", "00011110" },
			{ "Linked Phenotype", "None", "None", "None" } };

	public static String[] genetic_table_col_names = { "", "Gene 1", "Gene 2",
			"Gene 3" };

	/** The TextFields that store the genetic bits of the agents. */
	public static JTable genetic_table = new JTable(default_genetics,
			genetic_table_col_names);

	/** Controls whether or not the distribution of gene status of an agent type is tracked and outputted. */
	public static JCheckBox track_gene_status_distribution;

	/** Controls whether or not the distribution of gene value of an agent type is tracked and outputted. */
	public static JCheckBox track_gene_value_distribution;

	/** The number of chart updates per time step. */
	public static JTextField chart_update_frequency;

	public void actionPerformed(java.awt.event.ActionEvent e) {
		try {
			if (e.getSource().equals(link_gene_1)) {
				String linked_to_gene_1 = mutable_phenotypes.getSelectedValue()
						.toString();
				if (!linked_to_gene_1.equals("[No Phenotype]")) {
					PhenotypeMaster.setLinkedAttributes(linked_to_gene_1,
							PhenotypeMaster.RED_PHENOTYPE);
					genetic_table.setValueAt(linked_to_gene_1,
							GA_LINKED_PHENOTYPE_ROW, GA_GENE_1_COL);
				} else if (!PhenotypeMaster.linked_phenotypes[PhenotypeMaster.RED_PHENOTYPE]
						.equals("")) {
					PhenotypeMaster
							.removeLinkedAttributes(PhenotypeMaster.RED_PHENOTYPE);
					genetic_table.setValueAt("None", GA_LINKED_PHENOTYPE_ROW,
							GA_GENE_1_COL);
				}
			} else if (e.getSource().equals(link_gene_2)) {
				String linked_to_gene_2 = mutable_phenotypes.getSelectedValue()
						.toString();
				if (!linked_to_gene_2.equals("[No Phenotype]")) {
					PhenotypeMaster.setLinkedAttributes(linked_to_gene_2,
							PhenotypeMaster.GREEN_PHENOTYPE);
					genetic_table.setValueAt(linked_to_gene_2,
							GA_LINKED_PHENOTYPE_ROW, GA_GENE_2_COL);
				} else if (!PhenotypeMaster.linked_phenotypes[PhenotypeMaster.GREEN_PHENOTYPE]
						.equals("")) {
					PhenotypeMaster
							.removeLinkedAttributes(PhenotypeMaster.GREEN_PHENOTYPE);
					genetic_table.setValueAt("None", GA_LINKED_PHENOTYPE_ROW,
							GA_GENE_2_COL);
				}
			} else if (e.getSource().equals(link_gene_3)) {
				String linked_to_gene_3 = mutable_phenotypes.getSelectedValue()
						.toString();
				if (!linked_to_gene_3.equals("[No Phenotype]")) {
					PhenotypeMaster.setLinkedAttributes(linked_to_gene_3,
							PhenotypeMaster.BLUE_PHENOTYPE);
					genetic_table.setValueAt(linked_to_gene_3,
							GA_LINKED_PHENOTYPE_ROW, GA_GENE_3_COL);
				} else if (!PhenotypeMaster.linked_phenotypes[PhenotypeMaster.BLUE_PHENOTYPE]
						.equals("")) {
					PhenotypeMaster
							.removeLinkedAttributes(PhenotypeMaster.BLUE_PHENOTYPE);
					genetic_table.setValueAt("None", GA_LINKED_PHENOTYPE_ROW,
							GA_GENE_3_COL);
				}
			} else if (e.getSource().equals(meiosis_mode)) {

				// Read which mode of meiosis is selected and
				// add save it.
				GeneticCode.meiosis_mode
				= (String) ((JComboBox) e.getSource()).getSelectedItem();
			} else if (e.getSource().equals(track_gene_status_distribution)) {
				boolean state = GATracker.negateTrackGeneStatusDistribution();
				track_gene_status_distribution.setSelected(state);
			} else if (e.getSource().equals(track_gene_value_distribution)) {
				boolean state = GATracker.negateTrackGeneValueDistribution();
				track_gene_value_distribution.setSelected(state);
			} else if (e.getSource().equals(chart_update_frequency)) {
				try {
					int freq = Integer.parseInt(chart_update_frequency.getText());

					if (freq <= 0) {
						chart_update_frequency.setText("Input must be > 0.");
					} else {
						GAChartOutput.update_frequency = freq;
					}
				} catch (NumberFormatException f) {
					chart_update_frequency.setText("Input must be integer.");
				}
			}
		} catch (GeneticCodeException f) {
			// Handle Exception.
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////

	JTable table1;

	JTable table2;

	JTable table3;

	JTable tablePD;

	JTabbedPane tabbedPane;

	static Button close;

	static Button save;

	public Object[][] inputArray;

	public Object[][] data1;

	public Object[][] data2;

	public Object[][] data3;

	public Object[][] PDdata = { { null, null }, { null, null },
			{ null, null }, { null, null } };

	int row;

	int row2;

	int row3;

	int rowpd;

	static JFrame frame;

	Parser p;

	private final CobwebApplication CA;

	private static String datafile;

	int types;

	public GUI() {
		super();
		CA = null;
	}
	// GUI Special Constructor
	public GUI(CobwebApplication ca, String filename) {
		super();

		CA = ca;
		datafile = filename;
		tabbedPane = new JTabbedPane();

		/* Environment panel - composed of 4 panels */

		JComponent panel1 = new JPanel(new GridLayout(3, 2));

		/* Environment Settings */
		JPanel panel11 = new JPanel();
		panel11.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.blue), "Grid Settings"));
		JPanel labelPane = new JPanel(new GridLayout(3, 1));

		labelPane.add(new Label("Width"));
		labelPane.add(new Label("Height"));
		labelPane.add(new Label("Wrap"));
		JPanel fieldPane = new JPanel(new GridLayout(3, 1));

		Width = new TextField(3);
		Height = new TextField(3);
		wrap = new Checkbox("");
		fieldPane.add(Width, "North");
		fieldPane.add(Height, "North");
		fieldPane.add(wrap, "North");

		panel11.add(labelPane, BorderLayout.WEST);
		panel11.add(fieldPane, BorderLayout.EAST);
		panel1.add(panel11, "WEST");

		JPanel panel15 = new JPanel();
		panel15.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.blue), "Prisoner's Dilemma Options"));
		labelPane = new JPanel(new GridLayout(3, 1));
		labelPane.add(new Label("Prisoner's Game"));
		labelPane.add(new Label("Memory Size"));
		labelPane.add(new Label("Energy Based"));

		PrisDilemma = new Checkbox("");
		memory_size = new TextField(3);
		flexibility = new Checkbox("");
		fieldPane = new JPanel(new GridLayout(3, 1));

		fieldPane.add(PrisDilemma, "North");
		fieldPane.add(memory_size, "North");
		fieldPane.add(flexibility, "North");

		panel15.add(labelPane, BorderLayout.WEST);
		panel15.add(fieldPane, BorderLayout.EAST);
		panel1.add(panel15, "WEST");

		/* Colour Settings */
		JPanel panel12 = new JPanel();
		panel12.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.blue),
				"Environment Transition Settings"));
		labelPane = new JPanel(new GridLayout(6, 1));
		labelPane.add(new Label("Keep Old Agents"));
		labelPane.add(new Label("Spawn New Agents"));
		labelPane.add(new Label("Keep Old Array"));
		labelPane.add(new Label("New Colorizer"));
		labelPane.add(new Label("Keep Old Waste"));
		labelPane.add(new Label("Keep Old Packets"));

		fieldPane = new JPanel(new GridLayout(6, 1));
		keepOldAgents = new Checkbox("");
		spawnNewAgents = new Checkbox("");
		keepOldArray = new Checkbox("");
		ColorCodedAgents = new Checkbox("");
		newColorizer = new Checkbox("", true);
		keepOldWaste = new Checkbox("", true);
		keepOldPackets = new Checkbox("", true);

		fieldPane.add(keepOldAgents, "North");
		fieldPane.add(spawnNewAgents, "North");
		fieldPane.add(keepOldArray, "North");
		fieldPane.add(newColorizer, "North");
		fieldPane.add(keepOldWaste, "North");
		fieldPane.add(keepOldPackets, "North");

		panel12.add(labelPane, BorderLayout.WEST);
		panel12.add(fieldPane, BorderLayout.EAST);
		panel1.add(panel12);

		/* Options */
		JPanel panel13 = new JPanel();
		panel13.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.blue), "Colour Settings"));
		labelPane = new JPanel(new GridLayout(5, 1));

		labelPane.add(new Label("No. of Colors"));
		labelPane.add(new Label("Color Select Size"));
		labelPane.add(new Label("Recolor Time Step"));
		labelPane.add(new Label("Colorizer Mode"));
		labelPane.add(new Label("Color Coded Agents"));

		fieldPane = new JPanel(new GridLayout(5, 1));

		numColor = new TextField(3);
		colorSelectSize = new TextField(3);
		reColorTimeStep = new TextField(3);
		colorizerMode = new TextField(3);

		fieldPane.add(numColor, "North");
		fieldPane.add(colorSelectSize, "North");
		fieldPane.add(reColorTimeStep, "North");
		fieldPane.add(colorizerMode, "North");
		fieldPane.add(ColorCodedAgents, "North");

		panel13.add(labelPane, BorderLayout.WEST);
		panel13.add(fieldPane, BorderLayout.CENTER);
		panel1.add(panel13, "EAST");

		/* Random variables */
		JPanel panel14 = new JPanel();
		panel14.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.blue), "Random Variables"));
		labelPane = new JPanel(new GridLayout(2, 1));
		labelPane.add(new Label("Random Seed"));
		labelPane.add(new Label("Random Stones no."));
		fieldPane = new JPanel(new GridLayout(2, 1));
		RandomSeed = new TextField(3);
		randomStones = new TextField(3);

		fieldPane.add(RandomSeed, "North");
		fieldPane.add(randomStones, "North");

		panel14.add(labelPane, BorderLayout.WEST);
		panel14.add(fieldPane, BorderLayout.EAST);
		panel1.add(panel14, "EAST");

		JPanel panel16 = new JPanel();
		panel16.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.blue), "General Food Variables"));
		labelPane = new JPanel(new GridLayout(2, 1));
		labelPane.add(new Label("Drop New Food"));
		labelPane.add(new Label("Max Food Chance"));

		dropNewFood = new Checkbox("");
		maxFoodChance = new TextField(3);

		fieldPane = new JPanel(new GridLayout(2, 1));
		fieldPane.add(dropNewFood, "North");
		fieldPane.add(maxFoodChance, "North");

		panel16.add(labelPane, BorderLayout.WEST);
		panel16.add(fieldPane, BorderLayout.EAST);
		panel1.add(panel16, "EAST");

		String[] rownames = { "Initial Food Amount", "Food Rate",
				"Growth Rate", "Depletion Rate", "Depletion Steps",
				"Draught period", "Food Mode" };
		row = rownames.length;
		String[] rownames2 = { "Initial Num. of Agents", "Mutation Rate",
				"Initial Energy", "Favourite Food Energy", "Other Food Energy",
				"Breed Energy", "Pregnancy Period - 1 parent",
				"Step Energy Loss", "Step Rock Energy Loss",
				"Turn Right Energy Loss", "Turn Left Energy Loss",
				"Memory Bits", "Min. Communication Similarity",
				"Step Agent Energy Loss", "Communication Bits",
				"Pregnancy Period - 2 parents", "Min. Breed Similarity",
				"2 parents Breed Chance", "1 parent Breed Chance",
				"Aging Mode", "Aging Limit", "Aging Rate", "Waste Mode",
				"Step Waste Energy Loss", "Energy gain Limit",
				"Energy usage Limit", "Waste Half-life Rate",
				"Initial Waste Quantity", "PD Tit for Tat",
				"PD Cooperation Probability", "Broadcast Mode",
				"Broadcast range energy-based", "Broadcast fixed range",
				"Broadcast Minimum Energy", "Broadcast Energy Cost" };

		row2 = rownames2.length;
		String[] rownames3 = { "Agent Type 1", "Agent Type 2", "Agent Type 3",
				"Agent Type 4", "Food Type 1", "Food Type 2", "Food Type 3",
				"Food Type 4", };

		row3 = rownames3.length;

		String[] PDrownames = { "Temptation", "Reward", "Punishment",
				"Sucker's Payoff" };
		rowpd = PDrownames.length;

		/*
		 * check filename, if file name exists and has a correct format set the
		 * values from the file filename
		 */

		File f = new File(datafile);  // $$$$$  a new file named as datafile will be automatically created
									  //          after click "OK" button, line 590 write the file.  Jan 24
		// $$$$$$ delete temporary file "default_data_(reserved).temp", for "Default" button (and "Retrieve Default Data" menu).  Jan 31
		//if ( datafile.equals( (CobwebApplication.DEFAULT_DATA_FILE_NAME + TEMPORARY_FILE_SUFFIX) ) ) {
		//	f.deleteOnExit();
			//f.delete();
		//}
		if (f.exists()) {
			try {
				p = new Parser(datafile);
				loadfromParser(p);
			} catch (Throwable e) {
				setDefault();
			}
		} else {
			setDefault();
		}

		/* Resources panel */
		JComponent panel2 = new JPanel();
		table1 = new JTable(new MyTableModel(rownames, data1.length, data1));
		table1.setPreferredScrollableViewportSize(new Dimension(500, 300));
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel colModel = table1.getColumnModel();
		// Get the column at index pColumn, and set its preferred width.
		colModel.getColumn(0).setPreferredWidth(120);

		for (int i = 1; i < data1.length + 1; i++) {
			colModel.getColumn(i).setPreferredWidth(60);
		}

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane1 = new JScrollPane(table1);
		scrollPane1.setPreferredSize(new Dimension(420, 300));
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// Add the scroll pane to this panel.

		panel2.add(scrollPane1, BorderLayout.CENTER);

		/* Agents' panel */

		JComponent panel3 = new JPanel();
		// tabbedPane.addTab("Agents", panel3);

		table2 = new JTable(new MyTableModel(rownames2, data2.length, data2));

		table2.setPreferredScrollableViewportSize(new Dimension(800, 300));
		// table2.getModel().addTableModelListener(model);

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane2 = new JScrollPane(table2);
		scrollPane2.setPreferredSize(new Dimension(400, 300));
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		TableColumnModel colModel2 = table2.getColumnModel();
		// Get the column at index pColumn, and set its preferred width.
		colModel2.getColumn(0).setPreferredWidth(175);

		for (int i = 1; i < data2.length + 1; i++) {
			colModel2.getColumn(i).setPreferredWidth(50);
		}

		// Add the scroll pane to this panel.
		panel3.add(scrollPane2, BorderLayout.CENTER);

		JComponent panel5 = new JPanel();
		// tabbedPane.addTab("Agents", panel3);

		table3 = new JTable(new MyTableModel2(rownames3, data3.length, data3));

		table3.setPreferredScrollableViewportSize(new Dimension(800, 300));
		// table2.getModel().addTableModelListener(model);

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane3 = new JScrollPane(table3);
		scrollPane3.setPreferredSize(new Dimension(400, 300));
		table3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		panel5.add(scrollPane3, BorderLayout.CENTER);

		// Toolkit toolkit = Toolkit.getDefaultToolkit();
		// ImageIcon icon = new
		// ImageIcon((toolkit.createImage("images/sesame.GIF")).getScaledInstance(30,30,10)
		// );

		tabbedPane = new JTabbedPane();

		tabbedPane.addTab("Environment", panel1);
		tabbedPane.addTab("Resources", panel2);
		tabbedPane.addTab("Agents", panel3);
		tabbedPane.addTab("Food Web", panel5);

		/***********************************************************************
		 * /* Prisoners' Dilemma (PD) options and payoff matrix added as a
		 * separate tab
		 */
		JComponent panelPD = new JPanel();

		tablePD = new JTable(new PDTable(PDrownames, PDdata));
		tablePD.setPreferredScrollableViewportSize(new Dimension(800, 300));
		JScrollPane scrollPanePD = new JScrollPane(tablePD);
		// Create the scroll pane and add the table to it.
		scrollPanePD.setPreferredSize(new Dimension(400, 150));
		tablePD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		(tablePD.getColumnModel()).getColumn(0).setPreferredWidth(200);

		panelPD.add(scrollPanePD, BorderLayout.CENTER);
		tabbedPane.addTab("PD Options", panelPD);
		/** **************************************************************************************************** */
		/** ************************************************************************************* */
		// GA interface implementation in Panel 6.

		JComponent panelGA = new JPanel(new BorderLayout());
		DefaultListModel mutable_list_model = new DefaultListModel();
		for (String element : PhenotypeMaster.mutable) {
			mutable_list_model.addElement(element);
		}
		mutable_list_model.addElement("[No Phenotype]");
		mutable_phenotypes = new JList(mutable_list_model);
		mutable_phenotypes
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		mutable_phenotypes.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		mutable_phenotypes.setVisibleRowCount(-1);
		JScrollPane phenotypeScroller = new JScrollPane(mutable_phenotypes);
		phenotypeScroller.setPreferredSize(new Dimension(150, 260));

		// Set the default selected item as the previously selected item.
		meiosis_mode.setSelectedIndex(GeneticCode.meiosis_mode_index);

		JPanel gene_1 = new JPanel();
		gene_1.add(link_gene_1);
		gene_1.setPreferredSize(new Dimension(150, 100));

		JPanel gene_2 = new JPanel();
		gene_2.add(link_gene_2);
		gene_2.setPreferredSize(new Dimension(150, 100));

		JPanel gene_3 = new JPanel();
		gene_3.add(link_gene_3);
		gene_3.setPreferredSize(new Dimension(150, 100));

		JPanel gene_info_display = new JPanel();

		gene_info_display.add(gene_1, BorderLayout.WEST);
		gene_info_display.add(gene_2, BorderLayout.CENTER);
		gene_info_display.add(gene_3, BorderLayout.EAST);
		JPanel meiosis_mode_panel = new JPanel(new BorderLayout());
		meiosis_mode_panel.add(new JLabel("Mode of Meiosis"), BorderLayout.NORTH);
		meiosis_mode_panel.add(meiosis_mode, BorderLayout.CENTER);

		// Checkboxes and TextAreas
		track_gene_status_distribution = new JCheckBox("Track Gene Status Distribution", GATracker.getTrackGeneStatusDistribution());
		track_gene_value_distribution = new JCheckBox("Track Gene Value Distribution", GATracker.getTrackGeneValueDistribution());
		chart_update_frequency = new JTextField(GAChartOutput.update_frequency + "", 12);
		JPanel chart_update_frequency_panel = new JPanel();
		chart_update_frequency_panel.add(new JLabel("Time Steps per Chart Update"));
		chart_update_frequency_panel.add(chart_update_frequency);
		JPanel gene_check_boxes = new JPanel(new BorderLayout());
		gene_check_boxes.add(track_gene_status_distribution, BorderLayout.NORTH);
		gene_check_boxes.add(track_gene_value_distribution, BorderLayout.CENTER);
		gene_check_boxes.add(chart_update_frequency_panel, BorderLayout.SOUTH);

		// Combine Checkboxes and Dropdown menu
		JPanel ga_combined_panel = new JPanel(new BorderLayout());
		ga_combined_panel.add(meiosis_mode_panel, BorderLayout.EAST);
		ga_combined_panel.add(gene_check_boxes, BorderLayout.WEST);

		gene_info_display.add(ga_combined_panel, BorderLayout.SOUTH);

		JScrollPane genetic_table_scroller = new JScrollPane(genetic_table);
		genetic_table_scroller.setPreferredSize(new Dimension(150, 260));
		panelGA.add(genetic_table_scroller, BorderLayout.CENTER);
		panelGA.add(gene_info_display, BorderLayout.SOUTH);
		panelGA.add(phenotypeScroller, BorderLayout.NORTH);

		/** Listeners of JButtons, JComboBoxes, and JCheckBoxes */
		link_gene_1.addActionListener(this);
		link_gene_2.addActionListener(this);
		link_gene_3.addActionListener(this);
    	meiosis_mode.addActionListener(this);
    	track_gene_status_distribution.addActionListener(this);
    	track_gene_value_distribution.addActionListener(this);
    	chart_update_frequency.addActionListener(this);

		tabbedPane.addTab("Genetic Algorithm", panelGA);

		/** ************************************************************************************* */

		/**
		 * ***************************************************************************************************.
		 *  /* "OK" button
		 */
		close = new java.awt.Button("OK");
		close.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

			/*	$$$$$$  Invalidate the below codes and use the help
			 *  $$$$$$    method checkValidityOfGAInput instead.  Feb 1
				Pattern pattern = Pattern.compile("^[01]{8}$");
				Matcher matcher;

				boolean correct_input = true;

				// $$$$$$  added on Jan 23  $$$$$  not perfect since the dialog won't popup until hit "OK"
				updateTable(genetic_table);

				for (int i = GA_AGENT_1_ROW; i < GA_AGENT_1_ROW + GA_NUM_AGENT_TYPES; i++) {
					for (int j = GA_GENE_1_COL; j < GA_GENE_1_COL + GA_NUM_GENES; j++) {
						matcher = pattern.matcher((String) genetic_table.getValueAt(i,j));
						if (!matcher.find()) {
							correct_input = false;
							JOptionPane.showMessageDialog(GUI.this,
							"GA: All genes must be binary and 8-bit long");
							break;
						}
					}
					if (!correct_input) {
						break;
					}
				}
			*/
				// $$$$$$ check validity of genetic table input.  Feb 1
				boolean correct_GA_input;
				correct_GA_input = checkValidityOfGAInput();

				// Save the chart update frequency
				try {
					int freq = Integer.parseInt(chart_update_frequency.getText());
					if (freq <= 0) { // Non-positive integers
						GAChartOutput.update_frequency = 1;
					} else { // Valid input
						GAChartOutput.update_frequency = freq;
					}
				} catch (NumberFormatException e) { // Invalid input
					GAChartOutput.update_frequency = 1;
				}

				if ( (checkHeightLessThanWidth() != false) && (correct_GA_input != false) ) {

					//checkRandomSeedValidity(); // $$$$$$ added on Feb 18
					// $$$$$$ Change the above code as follows on Feb 25
					if (CA.randomSeedReminder == 0) {
						CA.randomSeedReminder = checkRandomSeedStatus();
					}
					/*
					 * this fragment of code is necessary to update the last cell of
					 * the table before saving it
					 */
					updateTable(table1);
					updateTable(table2);
					updateTable(table3);
					updateTable(tablePD); // $$$$$$ Jan 25

					/* write UI info to xml file */
					try {
						write(datafile);  // $$$$$ write attributes showed in the "Test Data" window into the file "datafile".   Jan 24
					} catch (java.io.IOException e) {
					}

					/* create a new parser for the xml file */
					try {
						p = new Parser(datafile);
					} catch (FileNotFoundException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					CA.openFile(p);
					if (!datafile.equals(CA.getCurrentFile())) {CA.setCurrentFile(datafile);}  // $$$$$$ added on Mar 14
					frame.setVisible(false);
					frame.dispose(); // $$$$$$ added on Feb 28
					// $$$$$$ Added on Mar 14
					if (CA.getUI() != null) {
						if(CA.isInvokedByModify() == false) {
							CA.getUI().reset();   // reset tick
							//CA.refresh(CA.getUI());
							//if (CA.tickField != null && !CA.tickField.getText().equals("")) {CA.tickField.setText("");}    // $$$$$$ Mar 17
						}
						CA.getUI().refresh(1);

						/*** $$$$$$ Cancel textWindow  Apr 22*/
						if (cobweb.globals.usingTextWindow == true) {
							// $$$$$$ Reset the output window, specially for Linux.  Mar 29
							if (CA.textArea.getText().endsWith(CobwebApplication.GREETINGS) == false) {
								CA.textArea.setText(CobwebApplication.GREETINGS);
							}
						}

					}
				}
			}
		});

		save = new java.awt.Button("Save");
		save.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// $$$$$$ check validity of genetic table input.  Feb 1

				// Save the chart update frequency
				try {
					int freq = Integer.parseInt(chart_update_frequency.getText());
					if (freq <= 0) { // Non-positive integers
						GAChartOutput.update_frequency = 1;
					} else { // Valid input
						GAChartOutput.update_frequency = freq;
					}
				} catch (NumberFormatException f) { // Invalid input
					GAChartOutput.update_frequency = 1;
				}

				boolean correct_GA_input;
				correct_GA_input = checkValidityOfGAInput();

				// $$$$$$ Implement "Save" only if GA input is correct
				if ( (checkHeightLessThanWidth() != false) && (correct_GA_input != false) ) { // modified on Feb 21

					//checkRandomSeedValidity();	// $$$$$$ added on Feb 22
					// $$$$$$ Change the above code as follows on Feb 25
					if (CA.randomSeedReminder == 0) {
						CA.randomSeedReminder = checkRandomSeedStatus();
					}

					updateTable(table1);
					updateTable(table2);
					updateTable(table3);
					updateTable(tablePD); // $$$$$$ Jan 25
					//updateTable(genetic_table);  // $$$$$$ Jan 25 $$$$$$ genetic_table already updated by checkValidityOfGAInput(). Feb 22
					openFileDialog();



				}
			}
		});

		JPanel buttons = new JPanel();
		buttons.setLayout(new BorderLayout());
		buttons.add(save, BorderLayout.WEST);
		buttons.add(new JLabel(" "));
		buttons.add(close, BorderLayout.EAST);

		// Add the tabbed pane to this panel.
		add(tabbedPane);
		add(buttons, BorderLayout.SOUTH);
	}

	// $$$$$$  Check the validity of genetic_table input, used by "OK" and "Save" buttons.  Feb 1
	private boolean checkValidityOfGAInput() {
		Pattern pattern = Pattern.compile("^[01]{8}$");
		Matcher matcher;

		boolean correct_input = true;

		// $$$$$$  added on Jan 23  $$$$$  not perfect since the dialog won't popup until hit "OK"
		updateTable(genetic_table);

		for (int i = GA_AGENT_1_ROW; i < GA_AGENT_1_ROW + GA_NUM_AGENT_TYPES; i++) {
			for (int j = GA_GENE_1_COL; j < GA_GENE_1_COL + GA_NUM_GENES; j++) {
				matcher = pattern.matcher((String) genetic_table.getValueAt(i,j));
				if (!matcher.find()) {
					correct_input = false;
					JOptionPane.showMessageDialog(GUI.this,
						"GA: All genes must be binary and 8-bit long");
					break;
				}
			}
			if (!correct_input) {
				break;
			}
		}

		return correct_input;
	}

	/**************** Rendered defunct by Andy, because the pop up is annoying. */
	// $$$$$$ To check whether RandomSeed == 0. If RandomSeed != 0, popup a message.  Apr 18 [Feb 18] Refer class ComplexEnvironment line 365-6 for the reason
	private int checkRandomSeedStatus() {
		/*
		if ( Integer.parseInt(RandomSeed.getText()) != 0) {   // $$$$$$ change from "==".  Apr 18
			//JOptionPane.showMessageDialog(GUI.frame,
			//	"CAUTION:  \"Random Seed\" is setting to zero.\n" +
			//	"\nFor retrievable experiments, please set \"Random Seed\" to non-zero.");
			// $$$$$$ Change the above block as follows and return an integer.  Feb 25
			Object[] options = {"Yes, please",
								"No, thanks"};
			int n = JOptionPane.showOptionDialog(frame,
													"Note:  You can set \"Random Seed\" to zero for non-repeatable experiments.\n" +
													"\nDo you want to be reminded next time?",
													"Random Seed Setting",
													JOptionPane.YES_NO_OPTION,
													JOptionPane.QUESTION_MESSAGE,
													null,     //do not use a custom Icon
													options,  //the titles of buttons
													options[0]); //default button title
			return n;
		} else {
			return 0;
		}*/
		return 0;
	}

	// $$$$$$ check if Width >= Height, for if Height > Width, an exception will occur and cobweb2 will malfunction.  Feb 20
	private boolean checkHeightLessThanWidth(){
		if ( Integer.parseInt(Width.getText()) < Integer.parseInt(Height.getText()) ) {
			JOptionPane.showMessageDialog(GUI.this,
					"Please set Width >= Height for Grid Settings, or Cobweb2 would malfunction.",
					"Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		} else {
			return true;
		}
	}


	public void updateTable(JTable table) {
		int row = table.getEditingRow();
		int col = table.getEditingColumn();
		if (table.isEditing()) {
			table.getCellEditor(row, col).stopCellEditing();
		}
	}

	// $$$$$$ This openFileDialog method is invoked by pressing the "Save" button
	public void openFileDialog() {
		java.awt.FileDialog theDialog = new java.awt.FileDialog(frame,
				"Choose a file to save state to", java.awt.FileDialog.SAVE);
		theDialog.setVisible(true);
		if (theDialog.getFile() != null) {
			// $$$$$$ Check if the saving filename is one of the names reserved by CobwebApplication.  Feb 8
			// $$$$$$$$$$$$$$$$$$$$$$$$$$ Block silenced by Andy due to the annoyingness of this feature. May 7, 2008
			//String savingFileName;
			//savingFileName = theDialog.getFile();

			// Block silenced, see above.

			/*if ( (savingFileName.contains(CobwebApplication.INITIAL_OR_NEW_INPUT_FILE_NAME) != false)
					  || (savingFileName.contains(CobwebApplication.CURRENT_DATA_FILE_NAME) != false) //$$$$$ added for "Modify Current Data"
					  || (savingFileName.contains(CobwebApplication.DEFAULT_DATA_FILE_NAME) != false)) {


				JOptionPane.showMessageDialog(GUI.this,
				"Save State: The filename\"" + savingFileName + "\" is reserved by Cobweb Application.\n" +
						"                       Please choose another file to save.",
						"Warning", JOptionPane.WARNING_MESSAGE); // $$$$$$ modified on Feb 22
				openFileDialog();
			} else { */  // $$$$$ If filename not reserved.  Feb 8
				try {
					// $$$$$$ The following block added to handle a readonly file.  Feb 22
					String savingFile = theDialog.getDirectory() + theDialog.getFile();
					File sf = new File(savingFile);
					if ( (sf.isHidden() != false) || ((sf.exists() != false) && (sf.canWrite() == false)) ) {
						JOptionPane.showMessageDialog(GUI.frame,   // $$$$$$ change from "this" to "GUI.frame".  Feb 22
								"Caution:  File \"" + savingFile + "\" is NOT allowed to be written to.",
								"Warning", JOptionPane.WARNING_MESSAGE);
					} else {
						// $$$$$ The following block used to be the original code.  Feb 22
						write(theDialog.getDirectory() + theDialog.getFile());
						p = new Parser(theDialog.getDirectory() + theDialog.getFile());
						CA.openFile(p);
						if (!datafile.equals(CA.getCurrentFile())) {CA.setCurrentFile(datafile);}  // $$$$$$ added on Mar 14
						frame.setVisible(false);
						frame.dispose(); // $$$$$$ Feb 28
						// $$$$$$ Added on Mar 14
						if (CA.getUI() != null) {
							if(CA.isInvokedByModify() == false) {
								CA.getUI().reset();    // reset tick
								//CA.refresh(CA.getUI());
								//if (CA.tickField != null && !CA.tickField.getText().equals("")) {CA.tickField.setText("");}    // $$$$$$ Mar 17
							}
							CA.getUI().refresh(1);

							/*** $$$$$$ Cancel textWindow  Apr 22*/
							if (cobweb.globals.usingTextWindow == true) {
								// $$$$$$ Reset the output window, specially for Linux.  Mar 29
								if (CA.textArea.getText().endsWith(CobwebApplication.GREETINGS) == false) {
									CA.textArea.setText(CobwebApplication.GREETINGS);
								}
							}

						}
					}
				} 	catch (java.io.IOException evt) {
					JOptionPane.showMessageDialog(CA,  // $$$$$$ added on Apr 22
							"Save failed: " + evt.getMessage(),
							"Warning", JOptionPane.WARNING_MESSAGE);
					/*** $$$$$$ Cancel textWindow  Apr 22*/
					if (cobweb.globals.usingTextWindow == true) {CA.textArea.append("Save failed:" + evt.getMessage());} // $$$$$$ Added to be consistent with
																												// CobwebApplication's saveFile method.  Feb 8
				}
			// }
		}
	}

	public Parser getParser() {
		return p;
	}

	class PDTable extends AbstractTableModel {
		private final Object[][] values;

		private final String[] rownames;

		public PDTable(String rownames[], Object data[][]) {
			this.rownames = rownames;
			values = data;
		}

		public int getRowCount() {
			return values.length;
		}

		public int getColumnCount() {
			return 2;
		}

		@Override
		public String getColumnName(int column) {
			if (column == 0) {
				return "";
			}
			return "value";
		}

		public String getRowName(int row) {
			return rownames[row];
		}

		public Object getValueAt(int row, int column) {
			if (column == 0) {
				return rownames[row];
			}
			return values[row][0];
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			if (col == 0) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			if ((isCellEditable(row, col))) {
				try {
					values[row][0] = new Integer((String) value);
				} catch (NumberFormatException e) {
					if (SwingUtilities.isEventDispatchThread()) {
						JOptionPane.showMessageDialog(GUI.this, "The \""
								+ getRowName(row)
								+ "\" row only accepts integer values.");
					} else {
						System.err
								.println("User attempted to enter non-integer"
										+ " value (" + value
										+ ") into an integer-only column.");
					}
				}
			}
		}
		// public Class getColumnClass(int c) { return values[0].getClass();}

		public static final long serialVersionUID = 0x38FAF24EC6162F2CL;
	}

	/* table class */
	class MyTableModel extends AbstractTableModel {

		private final Object[][] data;

		private final String[] rowNames;

		private int numTypes = 0;

		MyTableModel(String rownames[], int numcol, Object data[][]) {
			this.data = data;
			rowNames = rownames;
			numTypes = numcol;
		}

		/* return the number of columns */
		public int getColumnCount() {
			return numTypes + 1;
		}

		/* return the number of rows */
		public int getRowCount() {
			return rowNames.length;
		}

		/* return column name given the number of the column */
		@Override
		public String getColumnName(int col) {
			if (col == 0) {
				return "";
			} else {
				return "Type" + (col);
			}
		}

		/* return row name given the number of the row */
		public String getRowName(int row) {
			return rowNames[row];
		}

		public Object getValueAt(int row, int col) {
			if (col == 0) {
				return rowNames[row];
			}
			return data[col - 1][row];
		}

		/* add a column to this table */
		public void addColumn() {
			numTypes++;
			for (int i = 0; i < getRowCount(); i++) {
				data[numTypes][i] = "0";
			}
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		@Override
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			if (col == 0) {
				return false;
			} else {
				return true;
			}
		}

		/*
		 * set the value at (row,col)
		 */
		@Override
		public void setValueAt(Object value, int row, int col) {

			if ((isCellEditable(row, col))) {
				// check if this cell is supposed to contain and integer
				if (data[col - 1][row] instanceof Integer) {
					// If we don't do something like this, the column
					// switches to contain Strings.
					try {
						data[col - 1][row] = new Integer((String) value);

					} catch (NumberFormatException e) {
						if (SwingUtilities.isEventDispatchThread()) {
							JOptionPane.showMessageDialog(GUI.this, "The \""
									+ getRowName(row)
									+ "\" row only accepts integer values.");
						} else {
							System.err
									.println("User attempted to enter non-integer"
											+ " value ("
											+ value
											+ ") into an integer-only column.");
						}
					}
					// check if this cell is supposed to contain float or double
				} else if ((data[col - 1][row] instanceof Double)
						|| (data[col - 1][row] instanceof Float)) {
					try {
						data[col - 1][row] = new Float((String) value);

					} catch (NumberFormatException e) {
						if (SwingUtilities.isEventDispatchThread()) {
							JOptionPane
									.showMessageDialog(
											GUI.this,
											"The \""
													+ getRowName(row)
													+ "\" row only accepts float or double values.");
						} else {
							System.err
									.println("User attempted to enter non-float"
											+ " value ("
											+ value
											+ ") into an float-only column.");
						}
					}
				} else {
					data[col - 1][row] = value;
				}
				printDebugData();

			}
		}

		// print the data from the table each time it gets updated (used for
		// testing)

		private void printDebugData() {
			/*
			 * int numRows = getRowCount(); int numCols = getColumnCount();
			 *
			 * for (int i=0; i < numRows; i++) { System.out.print(" row " + i +
			 * ":"); for (int j=0; j < numCols-1; j++) { System.out.print(" " +
			 * data[j][i]); } System.out.println(); }
			 * System.out.println("--------------------------");
			 */
		}

		public static final long serialVersionUID = 0x38DC79AEAD8B2091L;
	}

	/* extends MyTableModel, implements the checkboxes in the food web class */
	class MyTableModel2 extends MyTableModel {
		@SuppressWarnings("unused")
		private Object[][] data;

		@SuppressWarnings("unused")
		private String[] rowNames;

		private final int numTypes = 0;

		MyTableModel2(String rownames[], int numcol, Object data[][]) {
			super(rownames, numcol, data);
		}

		@Override
		public Class<?> getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		public static final long serialVersionUID = 0x6E1D565A6F6714AFL;
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	public static void createAndShowGUI(CobwebApplication ca, String filename) {
		// Make sure we have nice window decorations.
		//JFrame.setDefaultLookAndFeelDecorated(true);

		// Create and set up the window.
		frame = new JFrame("Test Data");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.

		JComponent newContentPane = new GUI(ca, filename);
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.getContentPane().add(newContentPane/* new GUI(ca, datafile) */,
				BorderLayout.CENTER);
		// Display the window.

		frame.pack();
		frame.setVisible(true);
	}

	public void setDefault() {
		Width.setText("20");
		wrap.setState(false);
		Height.setText(Width.getText()); // $$$$$$ change to make Width == Height.  Feb 20
		memory_size.setText("10");
		flexibility.setState(false);
		PrisDilemma.setState(false);
		keepOldAgents.setState(false);
		spawnNewAgents.setState(true);
		keepOldArray.setState(false);
		dropNewFood.setState(true);
		ColorCodedAgents.setState(true);
		newColorizer.setState(true);
		keepOldWaste.setState(false); // $$$$$$ change from true to false for retrievable experiments.  Feb 20
		keepOldPackets.setState(true);
		numColor.setText("3");
		colorSelectSize.setText("3");
		reColorTimeStep.setText("300");
		colorizerMode.setText("0");
		RandomSeed.setText("0");
		randomStones.setText("10");
		maxFoodChance.setText("0.8");

		/* Resources */
		Object[][] temp1 = {
				{ new Integer(20), new Double(0.5), new Integer(5),
						new Double(0.9), new Integer(40), new Integer(0),
						new Integer(0) },
				{ new Integer(20), new Double(0.5), new Integer(5),
						new Double(0.9), new Integer(40), new Integer(0),
						new Integer(0) },
				{ new Integer(20), new Double(0.5), new Integer(5),
						new Double(0.9), new Integer(40), new Integer(0),
						new Integer(0) },
				{ new Integer(20), new Double(0.5), new Integer(5),
						new Double(0.9), new Integer(40), new Integer(0),
						new Integer(0) } };
		data1 = temp1;

		/* AGENTS INFO */
		Object[][] temp2 = { { new Integer(20), /* Initial num of agents */
		new Float(0.05), /* Mutation Rate */
		new Integer(30), /* Initial Energy */
		new Integer(30), /* Favourite Food Energy */
		new Integer(20), /* Other Food Energy */
		new Integer(100), /* Breed Energy */
		new Integer(0), /* Pregnancy Period - 1 parent */
		new Integer(1), /* Step ENergy Loss */
		new Integer(20), /* Step Rock Energy Loss */
		new Integer(1), /* TUrn Right Energy Loss */
		new Integer(1), /* Turn Left Energy Loss */
		new Integer(2), /* Memory Bits */
		new Integer(0), /* Min. Communication Similarity */
		new Integer(4), /* Step Agent Energy Loss */
		new Integer(2), /* Communication Bits */
		new Integer(1), /* Pregnancy Period - 2 parents */
		new Float(0.0), /* Min. Breed Similarity */
		new Float(1.0), /* 2-parents Breed Chance */
		new Float(0.0), /* 1 parent Breed Chance */
		new Boolean(false), /* Agent Aging */
		new Integer(100), /* Age LImit */
		new Float(1.0), /* Age Rate */
		new Boolean(true), /* Waste Production */
		new Integer(20), /* Step Waste Energy Loss */
		new Integer(110), /* Energy gain to trigger waste production */
		new Integer(110), /* Energy spend to trigger Waste production */
		new Float(0.20), /* Half-life rate for the waste */
		new Integer(110), /* Initial waste amount */
		new Boolean(true), /* PD Tit for Tat on/off */
		new Integer(50), /* PD cooperation bias */
		new Boolean(true), /* Broadcast Mode */
		new Boolean(true), /* Broadcast range energy-based */
		new Integer(20), /* Broadcast fixed range */
		new Integer(100), /* Broadcast Minimum Energy */
		new Integer(40) }, /* Broadcast Energy Cost */
		{ new Integer(20), /* Initial num of agents */
		new Float(0.05), /* Mutation Rate */
		new Integer(30), /* Initial Energy */
		new Integer(30), /* Favourite Food Energy */
		new Integer(20), /* Other Food Energy */
		new Integer(100), /* Breed Energy */
		new Integer(0), /* Pregnancy Period - 1 parent */
		new Integer(1), /* Step ENergy Loss */
		new Integer(20), /* Step Rock Energy Loss */
		new Integer(1), /* TUrn Right Energy Loss */
		new Integer(1), /* Turn Left Energy Loss */
		new Integer(2), /* Memory Bits */
		new Integer(0), /* Min. Communication Similarity */
		new Integer(4), /* Step Agent Energy Loss */
		new Integer(2), /* Communication Bits */
		new Integer(1), /* Pregnancy Period - 2 parents */
		new Float(0.0), /* Min. Breed Similarity */
		new Float(1.0), /* 2-parents Breed Chance */
		new Float(0.0), /* 1 parent Breed Chance */
		new Boolean(false), /* Agent Aging */
		new Integer(100), /* Age LImit */
		new Float(1.0), /* Age Rate */
		new Boolean(true), /* Waste Production */
		new Integer(20), /* Step Waste Energy Loss */
		new Integer(110), /* Energy gain to trigger waste production */
		new Integer(110), /* Energy spend to trigger Waste production */
		new Float(0.20), /* Half-life rate for the waste */
		new Integer(110), /* Initial waste amount */
		new Boolean(true), /* PD Tit for Tat on/off */
		new Integer(50), /* PD cooperation bias */
		new Boolean(true), /* Broadcast Mode */
		new Boolean(true), /* Broadcast range energy-based */
		new Integer(20), /* Broadcast fixed range */
		new Integer(100), /* Broadcast Minimum Energy */
		new Integer(40) }, /* Broadcast Energy Cost */
		{ new Integer(20), /* Initial num of agents */
		new Float(0.05), /* Mutation Rate */
		new Integer(30), /* Initial Energy */
		new Integer(30), /* Favourite Food Energy */
		new Integer(20), /* Other Food Energy */
		new Integer(100), /* Breed Energy */
		new Integer(0), /* Pregnancy Period - 1 parent */
		new Integer(1), /* Step ENergy Loss */
		new Integer(20), /* Step Rock Energy Loss */
		new Integer(1), /* TUrn Right Energy Loss */
		new Integer(1), /* Turn Left Energy Loss */
		new Integer(2), /* Memory Bits */
		new Integer(0), /* Min. Communication Similarity */
		new Integer(4), /* Step Agent Energy Loss */
		new Integer(2), /* Communication Bits */
		new Integer(1), /* Pregnancy Period - 2 parents */
		new Float(0.0), /* Min. Breed Similarity */
		new Float(1.0), /* 2-parents Breed Chance */
		new Float(0.0), /* 1 parent Breed Chance */
		new Boolean(false), /* Agent Aging */
		new Integer(100), /* Age LImit */
		new Float(1.0), /* Age Rate */
		new Boolean(true), /* Waste Production */
		new Integer(20), /* Step Waste Energy Loss */
		new Integer(110), /* Energy gain to trigger waste production */
		new Integer(110), /* Energy spend to trigger Waste production */
		new Float(0.20), /* Half-life rate for the waste */
		new Integer(110), /* Initial waste amount */
		new Boolean(true), /* PD Tit for Tat on/off */
		new Integer(50), /* PD cooperation bias */
		new Boolean(true), /* Broadcast Mode */
		new Boolean(true), /* Broadcast range energy-based */
		new Integer(20), /* Broadcast fixed range */
		new Integer(100), /* Broadcast Minimum Energy */
		new Integer(40) }, /* Broadcast Energy Cost */
		{ new Integer(20), /* Initial num of agents */
		new Float(0.05), /* Mutation Rate */
		new Integer(30), /* Initial Energy */
		new Integer(30), /* Favourite Food Energy */
		new Integer(20), /* Other Food Energy */
		new Integer(100), /* Breed Energy */
		new Integer(0), /* Pregnancy Period - 1 parent */
		new Integer(1), /* Step ENergy Loss */
		new Integer(20), /* Step Rock Energy Loss */
		new Integer(1), /* TUrn Right Energy Loss */
		new Integer(1), /* Turn Left Energy Loss */
		new Integer(2), /* Memory Bits */
		new Integer(0), /* Min. Communication Similarity */
		new Integer(4), /* Step Agent Energy Loss */
		new Integer(2), /* Communication Bits */
		new Integer(1), /* Pregnancy Period - 2 parents */
		new Float(0.0), /* Min. Breed Similarity */
		new Float(1.0), /* 2-parents Breed Chance */
		new Float(0.0), /* 1 parent Breed Chance */
		new Boolean(false), /* Agent Aging */
		new Integer(100), /* Age LImit */
		new Float(1.0), /* Age Rate */
		new Boolean(true), /* Waste Production */
		new Integer(20), /* Step Waste Energy Loss */
		new Integer(110), /* Energy gain to trigger waste production */
		new Integer(110), /* Energy spend to trigger Waste production */
		new Float(0.20), /* Half-life rate for the waste */
		new Integer(110), /* Initial waste amount */
		new Boolean(true), /* PD Tit for Tat on/off */
		new Integer(50), /* PD cooperation bias */
		new Boolean(true), /* Broadcast Mode */
		new Boolean(true), /* Broadcast range energy-based */
		new Integer(20), /* Broadcast fixed range */
		new Integer(100), /* Broadcast Minimum Energy */
		new Integer(40) } /* Broadcast Energy Cost */
		};
		data2 = temp2;

		/* FOOD WEB */
		Object[][] temp3 = {
				{ new Boolean(false), new Boolean(false), new Boolean(false),
						new Boolean(false), new Boolean(true),
						new Boolean(true), new Boolean(true), new Boolean(true) },
				{ new Boolean(false), new Boolean(false), new Boolean(false),
						new Boolean(false), new Boolean(true),
						new Boolean(true), new Boolean(true), new Boolean(true) },
				{ new Boolean(false), new Boolean(false), new Boolean(false),
						new Boolean(false), new Boolean(true),
						new Boolean(true), new Boolean(true), new Boolean(true) },
				{ new Boolean(false), new Boolean(false), new Boolean(false),
						new Boolean(false), new Boolean(true),
						new Boolean(true), new Boolean(true), new Boolean(true) } };
		data3 = temp3;

		Object[][] tempPD = { { new Integer(8), null },
				{ new Integer(6), null }, { new Integer(3), null },
				{ new Integer(2), null } };
		PDdata = tempPD;
	}

	private void loadfromParser(Parser p) {
		types = ((Integer) (Array.get(p.getfromHashTable("ComplexEnvironment"),
				0))).intValue();
		data1 = new Object[types][row];
		data2 = new Object[types][row2];
		data3 = new Object[types][row3];
		// PDdata = new Object[4][2];
		setTextField(Width, Array.get(p.getfromHashTable("width"), 0));
		setTextField(Height, Array.get(p.getfromHashTable("height"), 0));
		setCheckBoxState(wrap, Array.get(p.getfromHashTable("wrap"), 0));
		setCheckBoxState(PrisDilemma, Array.get(p
				.getfromHashTable("PrisDilemma"), 0));
		setTextField(memory_size, Array
				.get(p.getfromHashTable("memorysize"), 0));
		setCheckBoxState(flexibility, Array.get(p.getfromHashTable("foodBias"),
				0));
		setCheckBoxState(keepOldAgents, Array.get(p
				.getfromHashTable("keepoldagents"), 0));
		setCheckBoxState(keepOldArray, Array.get(p
				.getfromHashTable("keepoldarray"), 0));
		setCheckBoxState(spawnNewAgents, Array.get(p
				.getfromHashTable("spawnnewagents"), 0));
		setCheckBoxState(dropNewFood, Array.get(p
				.getfromHashTable("dropnewfood"), 0));
		setCheckBoxState(ColorCodedAgents, Array.get(p
				.getfromHashTable("colorcodedagents"), 0));
		setCheckBoxState(keepOldWaste, Array.get(p
				.getfromHashTable("keepoldwaste"), 0));
		setCheckBoxState(keepOldPackets, Array.get(p
				.getfromHashTable("keepoldpackets"), 0));

		setCheckBoxState(newColorizer, Array.get(p
				.getfromHashTable("newcolorizer"), 0));
		setTextField(numColor, Array.get(p.getfromHashTable("numcolor"), 0));
		setTextField(colorSelectSize, Array.get(p
				.getfromHashTable("colorselectsize"), 0));
		setTextField(reColorTimeStep, Array.get(p
				.getfromHashTable("recolortimestep"), 0));

		setTextField(colorizerMode, Array.get(p
				.getfromHashTable("colorizermode"), 0));
		setTextField(RandomSeed, Array.get(p.getfromHashTable("randomseed"), 0));
		setTextField(randomStones, Array.get(
				p.getfromHashTable("randomstones"), 0));
		setTextField(maxFoodChance, Array.get(p
				.getfromHashTable("maxfoodchance"), 0));

		setTableData(data1, Array.get(p.getfromHashTable("food"), 0), 1);
		setTableData(data1, Array.get(p.getfromHashTable("foodrate"), 0), 2);
		setTableData(data1, Array.get(p.getfromHashTable("foodgrow"), 0), 3);
		setTableData(data1, Array.get(p.getfromHashTable("fooddeplete"), 0), 4);
		setTableData(data1, Array
				.get(p.getfromHashTable("depletetimesteps"), 0), 5);
		setTableData(data1, Array.get(p.getfromHashTable("DraughtPeriod"), 0),
				6);
		setTableData(data1, Array.get(p.getfromHashTable("foodmode"), 0), 7);

		setTableData(data2, Array.get(p.getfromHashTable("agents"), 0), 1);
		setTableData(data2, Array.get(p.getfromHashTable("mutationrate"), 0), 2);
		setTableData(data2, Array.get(p.getfromHashTable("initenergy"), 0), 3);
		setTableData(data2, Array.get(p.getfromHashTable("foodenergy"), 0), 4);
		setTableData(data2,
				Array.get(p.getfromHashTable("otherfoodenergy"), 0), 5);
		setTableData(data2, Array.get(p.getfromHashTable("breedenergy"), 0), 6);
		setTableData(data2,
				Array.get(p.getfromHashTable("pregnancyperiod"), 0), 7);

		setTableData(data2, Array.get(p.getfromHashTable("stepenergy"), 0), 8);
		setTableData(data2, Array.get(p.getfromHashTable("steprockenergy"), 0),
				9);
		setTableData(data2,
				Array.get(p.getfromHashTable("turnrightenergy"), 0), 10);
		setTableData(data2, Array.get(p.getfromHashTable("turnleftenergy"), 0),
				11);
		setTableData(data2, Array.get(p.getfromHashTable("memorybits"), 0), 12);
		setTableData(data2, Array.get(p.getfromHashTable("commsimmin"), 0), 13);
		setTableData(data2,
				Array.get(p.getfromHashTable("stepagentenergy"), 0), 14);
		setTableData(data2, Array.get(p.getfromHashTable("communicationbits"),
				0), 15);
		setTableData(data2, Array.get(p
				.getfromHashTable("sexualpregnancyperiod"), 0), 16);
		setTableData(data2, Array.get(p.getfromHashTable("breedsimmin"), 0), 17);
		setTableData(data2, Array.get(p.getfromHashTable("sexualbreedchance"),
				0), 18);
		setTableData(data2, Array.get(p.getfromHashTable("asexualbreedchance"),
				0), 19);

		setTableData(data2, Array.get(p.getfromHashTable("agingMode"), 0), 20);
		setTableData(data2, Array.get(p.getfromHashTable("agingLimit"), 0), 21);
		setTableData(data2, Array.get(p.getfromHashTable("agingRate"), 0), 22);

		setTableData(data2, Array.get(p.getfromHashTable("wasteMode"), 0), 23);
		setTableData(data2, Array.get(p.getfromHashTable("wastePen"), 0), 24);
		setTableData(data2, Array.get(p.getfromHashTable("wasteGain"), 0), 25);
		setTableData(data2, Array.get(p.getfromHashTable("wasteLoss"), 0), 26);
		setTableData(data2, Array.get(p.getfromHashTable("wasteRate"), 0), 27);
		setTableData(data2, Array.get(p.getfromHashTable("wasteInit"), 0), 28);
		setTableData(data2, Array.get(p.getfromHashTable("pdTitForTat"), 0), 29);
		setTableData(data2, Array.get(p.getfromHashTable("pdCoopProb"), 0), 30);
		setTableData(data2, Array.get(p.getfromHashTable("broadcastMode"), 0),
				31);
		setTableData(data2, Array.get(p
				.getfromHashTable("broadcastEnergyBased"), 0), 32);
		setTableData(data2, Array.get(
				p.getfromHashTable("broadcastFixedRange"), 0), 33);
		setTableData(data2, Array.get(p.getfromHashTable("broadcastEnergyMin"),
				0), 34);
		setTableData(data2, Array.get(
				p.getfromHashTable("broadcastEnergyCost"), 0), 35);
		setTableHelper(data3);

		for (int i = 0; i < data3.length; i++) {
			int j;
			for (j = 0; j < data3.length; j++) {
				setTableData_agents2eat(data3, Array.get(p
						.getfromHashTable("agents2eat"), i), j, i);
			}
			for (int k = 0; k < data3.length; k++) {
				// setTableData2(data3,
				// Array.get(p.getfromHashTable("plants2eat"), i),k+j, i);
				setTableData_plants2eat(data3, Array.get(p
						.getfromHashTable("plants2eat"), i), k, i);
			}
		}
		// TODO take off second dimension from array if not used
		PDdata[0][0] = Array.get(p.getfromHashTable("temptation"), 0);
		PDdata[1][0] = Array.get(p.getfromHashTable("reward"), 0);
		PDdata[2][0] = Array.get(p.getfromHashTable("punishment"), 0);
		PDdata[3][0] = Array.get(p.getfromHashTable("sucker"), 0);
	}

	private void setTextField(TextField fieldName, Object value) {
		fieldName.setText(value.toString());
	}

	private void setCheckBoxState(Checkbox boxName, Object state) {
		boxName.setState(((Boolean) state).booleanValue());
	}

	private void setTableData(Object data[][], Object rowdata, int row) {
		for (int i = 0; i < data.length; i++) {
			data[i][row - 1] = Array.get(rowdata, i);
		}
	}

	/*
	 * Helper method: load a list of "agents to eat" from the hashtable into
	 * current data array
	 */
	private void setTableData_agents2eat(Object data[][], Object coldata,
			int j, int i) {
		int k = ((Integer) Array.get(coldata, j)).intValue();
		if (k > -1) {
			data[i][k] = new Boolean(true);

		}

	}

	/*
	 * Helper method: load a list of "plants to eat" from the hashtable into
	 * current data array
	 */
	private void setTableData_plants2eat(Object data[][], Object coldata,
			int j, int i) {

		int k = ((Integer) Array.get(coldata, j)).intValue();
		if (k > -1) {
			data[i][k + data3.length] = new Boolean(true);

		}

	}

	private void setTableHelper(Object data[][]) {

		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				data[i][j] = new Boolean(false);
			}
		}
	}

	/**
	 * Writes the information stored in this tree to an XML file, comforming to
	 * the rules of our spec.
	 *
	 * @param fileName
	 *            the name of the file to which to save the file
	 * @return true if the file was saved successfully, false otherwise
	 */
	public boolean write(String fileName) throws IOException {
		try {
			// open the file
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			if (out != null) {
				// write the initial project info
				out.write("<?xml version='1.0' encoding='utf-8'?>");
				out.write("\n\n");
				out.write("<inputData>" + "\n");

				out.write("\t" + "<scheduler>" + TickScheduler + "</scheduler>"
						+ "\n");
				// out.write("\t" + "<ComplexEnvironment>" + new
				// Integer(ComplexEnvironment.getText()) +
				// "</ComplexEnvironment>" + "\n");
				out.write("\t" + "<Width>" + Width.getText() + "</Width>"
						+ "\n");
				out.write("\t" + "<Height>" + Height.getText() + "</Height>"
						+ "\n");
				out.write("\t" + "<wrap>" + new Boolean(wrap.getState())
						+ "</wrap>" + "\n");
				out.write("\t" + "<PrisDilemma>"
						+ new Boolean(PrisDilemma.getState())
						+ "</PrisDilemma>" + "\n");
				out.write("\t" + "<randomStones>"
						+ new Integer(randomStones.getText())
						+ "</randomStones>" + "\n");
				out.write("\t" + "<maxFoodChance>"
						+ new Float(maxFoodChance.getText())
						+ "</maxFoodChance>" + "\n");
				out.write("\t" + "<keepOldAgents>"
						+ new Boolean(keepOldAgents.getState())
						+ "</keepOldAgents>" + "\n");
				out.write("\t" + "<spawnNewAgents>"
						+ new Boolean(spawnNewAgents.getState())
						+ "</spawnNewAgents>" + "\n");
				out.write("\t" + "<keepOldArray>"
						+ new Boolean(keepOldArray.getState())
						+ "</keepOldArray>" + "\n");
				out.write("\t" + "<dropNewFood>"
						+ new Boolean(dropNewFood.getState())
						+ "</dropNewFood>" + "\n");
				out.write("\t" + "<randomSeed>"
						+ new Integer(RandomSeed.getText()) + "</randomSeed>"
						+ "\n");
				out.write("\t" + "<newColorizer>"
						+ new Boolean(newColorizer.getState())
						+ "</newColorizer>" + "\n");
				out.write("\t" + "<keepOldWaste>"
						+ new Boolean(keepOldWaste.getState())
						+ "</keepOldWaste>" + "\n");
				out.write("\t" + "<keepOldPackets>"
						+ new Boolean(keepOldPackets.getState())
						+ "</keepOldPackets>" + "\n");
				out.write("\t" + "<numColor>" + new Integer(numColor.getText())
						+ "</numColor>" + "\n");
				out.write("\t" + "<colorSelectSize>"
						+ new Integer(colorSelectSize.getText())
						+ "</colorSelectSize>" + "\n");
				out.write("\t" + "<reColorTimeStep>"
						+ new Integer(reColorTimeStep.getText())
						+ "</reColorTimeStep>" + "\n");
				out.write("\t" + "<colorizerMode>"
						+ new Integer(colorizerMode.getText())
						+ "</colorizerMode>" + "\n");
				out.write("\t" + "<ColorCodedAgents>"
						+ new Boolean(ColorCodedAgents.getState())
						+ "</ColorCodedAgents>" + "\n");
				out.write("\t" + "<memorySize>"
						+ new Integer(memory_size.getText()) + "</memorySize>"
						+ "\n");
				out.write("\t" + "<food_bias>"
						+ new Boolean(flexibility.getState()) + "</food_bias>"
						+ "\n");

				writeHelperFood(out, table1.getColumnCount());
				writeHelperAgents(out, table2.getColumnCount());
				writeHelperPDOptions(out);
				writeHelperGA(out);
				out.write("</inputData>");

				out.close();
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * internal recursive helper
	 */
	private boolean writeHelperFood(BufferedWriter out, int foodtypes)
			throws IOException {
		for (int type = 1; type < foodtypes; type++) {
			try {
				// write the node info for the current node
				out.write("<food>" + "\n");
				out.write("\t" + "<Index>" + (type - 1) + "</Index>" + "\n");
				out.write("\t" + "<Food>" + table1.getValueAt(0, type)
						+ "</Food>" + "\n");
				out.write("\t" + "<FoodRate>" + table1.getValueAt(1, type)
						+ "</FoodRate>" + "\n");
				out.write("\t" + "<FoodGrow>" + table1.getValueAt(2, type)
						+ "</FoodGrow>" + "\n");
				out.write("\t" + "<FoodDeplete>" + table1.getValueAt(3, type)
						+ "</FoodDeplete>" + "\n");
				out.write("\t" + "<DepleteTimeSteps>"
						+ table1.getValueAt(4, type) + "</DepleteTimeSteps>"
						+ "\n");
				out.write("\t" + "<DraughtPeriod>" + table1.getValueAt(5, type)
						+ "</DraughtPeriod>" + "\n");
				out.write("\t" + "<FoodMode>" + table1.getValueAt(6, type)
						+ "</FoodMode>" + "\n");
				out.write("</food>" + "\n");
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}

	/* helper method for agents' parameters */
	private boolean writeHelperAgents(BufferedWriter out, int agentypes) {

		for (int type = 1; type < agentypes; type++) {
			try {
				// write the node info for the current node
				out.write("<agent>" + "\n");
				out.write("\t" + "<Index>" + (type - 1) + "</Index>" + "\n");
				out.write("\t" + "<Agents>" + table2.getValueAt(0, type)
						+ "</Agents>" + "\n");
				out.write("\t" + "<MutationRate>" + table2.getValueAt(1, type)
						+ "</MutationRate>" + "\n");
				out.write("\t" + "<InitEnergy>" + table2.getValueAt(2, type)
						+ "</InitEnergy>" + "\n");
				out.write("\t" + "<FoodEnergy>" + table2.getValueAt(3, type)
						+ "</FoodEnergy>" + "\n");
				out.write("\t" + "<OtherFoodEnergy>"
						+ table2.getValueAt(4, type) + "</OtherFoodEnergy>"
						+ "\n");
				out.write("\t" + "<BreedEnergy>" + table2.getValueAt(5, type)
						+ "</BreedEnergy>" + "\n");
				out.write("\t" + "<pregnancyPeriod>"
						+ table2.getValueAt(6, type) + "</pregnancyPeriod>"
						+ "\n");
				out.write("\t" + "<StepEnergy>" + table2.getValueAt(7, type)
						+ "</StepEnergy>" + "\n");
				out.write("\t" + "<StepRockEnergy>"
						+ table2.getValueAt(8, type) + "</StepRockEnergy>"
						+ "\n");
				out.write("\t" + "<TurnRightEnergy>"
						+ table2.getValueAt(9, type) + "</TurnRightEnergy>"
						+ "\n");
				out.write("\t" + "<TurnLeftEnergy>"
						+ table2.getValueAt(10, type) + "</TurnLeftEnergy>"
						+ "\n");
				out.write("\t" + "<MemoryBits>" + table2.getValueAt(11, type)
						+ "</MemoryBits>" + "\n");
				out.write("\t" + "<commSimMin>" + table2.getValueAt(12, type)
						+ "</commSimMin>" + "\n");
				out.write("\t" + "<StepAgentEnergy>"
						+ table2.getValueAt(13, type) + "</StepAgentEnergy>"
						+ "\n");
				out.write("\t" + "<communicationBits>"
						+ table2.getValueAt(14, type) + "</communicationBits>"
						+ "\n");
				out.write("\t" + "<sexualPregnancyPeriod>"
						+ table2.getValueAt(15, type)
						+ "</sexualPregnancyPeriod>" + "\n");
				out.write("\t" + "<breedSimMin>" + table2.getValueAt(16, type)
						+ "</breedSimMin>" + "\n");
				out.write("\t" + "<sexualBreedChance>"
						+ table2.getValueAt(17, type) + "</sexualBreedChance>"
						+ "\n");
				out.write("\t" + "<asexualBreedChance>"
						+ table2.getValueAt(18, type) + "</asexualBreedChance>"
						+ "\n");
				out.write("\t" + "<agingMode>" + table2.getValueAt(19, type)
						+ "</agingMode>" + "\n");
				out.write("\t" + "<agingLimit>" + table2.getValueAt(20, type)
						+ "</agingLimit>" + "\n");
				out.write("\t" + "<agingRate>" + table2.getValueAt(21, type)
						+ "</agingRate>" + "\n");
				out.write("\t" + "<wasteMode>" + table2.getValueAt(22, type)
						+ "</wasteMode>" + "\n");
				out.write("\t" + "<wastePen>" + table2.getValueAt(23, type)
						+ "</wastePen>" + "\n");
				out.write("\t" + "<wasteGain>" + table2.getValueAt(24, type)
						+ "</wasteGain>" + "\n");
				out.write("\t" + "<wasteLoss>" + table2.getValueAt(25, type)
						+ "</wasteLoss>" + "\n");
				out.write("\t" + "<wasteRate>" + table2.getValueAt(26, type)
						+ "</wasteRate>" + "\n");
				out.write("\t" + "<wasteInit>" + table2.getValueAt(27, type)
						+ "</wasteInit>" + "\n");
				out.write("\t" + "<pdTitForTat>" + table2.getValueAt(28, type)
						+ "</pdTitForTat>" + "\n");
				out.write("\t" + "<pdCoopProb>" + table2.getValueAt(29, type)
						+ "</pdCoopProb>" + "\n");
				out.write("\t" + "<broadcastMode>"
						+ table2.getValueAt(30, type) + "</broadcastMode>"
						+ "\n");
				out.write("\t" + "<broadcastEnergyBased>"
						+ table2.getValueAt(31, type)
						+ "</broadcastEnergyBased>" + "\n");
				out.write("\t" + "<broadcastFixedRange>"
						+ table2.getValueAt(32, type)
						+ "</broadcastFixedRange>" + "\n");
				out.write("\t" + "<broadcastEnergyMin>"
						+ table2.getValueAt(33, type) + "</broadcastEnergyMin>"
						+ "\n");
				out.write("\t" + "<broadcastEnergyCost>"
						+ table2.getValueAt(34, type)
						+ "</broadcastEnergyCost>" + "\n");
				writeHelperFoodWeb(out, type, table1.getColumnCount(), table2
						.getColumnCount());
				out.write("</agent>" + "\n");
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}

	private boolean writeHelperFoodWeb(BufferedWriter out, int type,
			int num_foodtypes, int num_agentypes) throws IOException {
		try {
			// write the node info for the current node
			out.write("<foodweb>" + "\n");
			for (int i = 1; i < num_agentypes; i++) {
				out.write("\t" + "<agent" + i + ">"
						+ table3.getValueAt(i - 1, type) + "</agent" + i + ">"
						+ "\n");
			}
			for (int i = 1; i < num_foodtypes; i++) {
				out.write("\t" + "<food" + i + ">"
						+ table3.getValueAt((num_agentypes + i) - 2, type)
						+ "</food" + i + ">" + "\n");
			}
			out.write("</foodweb>" + "\n");
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	private boolean writeHelperPDOptions(BufferedWriter out) throws IOException {
		try {
			out.write("<pd>");
			out.write("<temptation>");
			out.write("" + tablePD.getValueAt(0, 1));
			out.write("</temptation>\n");

			out.write("<reward>");
			out.write("" + tablePD.getValueAt(1, 1));
			out.write("</reward>\n");

			out.write("<punishment>");
			out.write("" + tablePD.getValueAt(2, 1));
			out.write("</punishment>");

			out.write("<sucker>");
			out.write("" + tablePD.getValueAt(3, 1));
			out.write("</sucker>\n");
			out.write("</pd>\n");
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/** The GA portion of the write() method that writes parameters to xml file. */
	private boolean writeHelperGA(BufferedWriter out) throws IOException {
		try {
			out.write("<ga>");
			out.write("<agent1gene1>");
			out.write(""
					+ genetic_table.getValueAt(GA_AGENT_1_ROW, GA_GENE_1_COL));
			out.write("</agent1gene1>\n");
			out.write("<agent1gene2>");
			out.write(""
					+ genetic_table.getValueAt(GA_AGENT_1_ROW, GA_GENE_2_COL));
			out.write("</agent1gene2>\n");
			out.write("<agent1gene3>");
			out.write(""
					+ genetic_table.getValueAt(GA_AGENT_1_ROW, GA_GENE_3_COL));
			out.write("</agent1gene3>\n");
			out.write("<agent2gene1>");
			out.write(""
					+ genetic_table.getValueAt(GA_AGENT_2_ROW, GA_GENE_1_COL));
			out.write("</agent2gene1>\n");
			out.write("<agent2gene2>");
			out.write(""
					+ genetic_table.getValueAt(GA_AGENT_2_ROW, GA_GENE_2_COL));
			out.write("</agent2gene2>\n");
			out.write("<agent2gene3>");
			out.write(""
					+ genetic_table.getValueAt(GA_AGENT_2_ROW, GA_GENE_3_COL));
			out.write("</agent2gene3>\n");
			out.write("<agent3gene1>");
			out.write(""
					+ genetic_table.getValueAt(GA_AGENT_3_ROW, GA_GENE_1_COL));
			out.write("</agent3gene1>\n");
			out.write("<agent3gene2>");
			out.write(""
					+ genetic_table.getValueAt(GA_AGENT_3_ROW, GA_GENE_2_COL));
			out.write("</agent3gene2>\n");
			out.write("<agent3gene3>");
			out.write(""
					+ genetic_table.getValueAt(GA_AGENT_3_ROW, GA_GENE_3_COL));
			out.write("</agent3gene3>\n");
			out.write("<agent4gene1>");
			out.write(""
					+ genetic_table.getValueAt(GA_AGENT_4_ROW, GA_GENE_1_COL));
			out.write("</agent4gene1>\n");
			out.write("<agent4gene2>");
			out.write(""
					+ genetic_table.getValueAt(GA_AGENT_4_ROW, GA_GENE_2_COL));
			out.write("</agent4gene2>\n");
			out.write("<agent4gene3>");
			out.write(""
					+ genetic_table.getValueAt(GA_AGENT_4_ROW, GA_GENE_3_COL));
			out.write("</agent4gene3>\n");
			out.write("<linkedphenotype1>");
			out.write(""
					+ genetic_table.getValueAt(GA_LINKED_PHENOTYPE_ROW,
							GA_GENE_1_COL));
			out.write("</linkedphenotype1>\n");
			out.write("<linkedphenotype2>");
			out.write(""
					+ genetic_table.getValueAt(GA_LINKED_PHENOTYPE_ROW,
							GA_GENE_2_COL));
			out.write("</linkedphenotype2>\n");
			out.write("<linkedphenotype3>");
			out.write(""
					+ genetic_table.getValueAt(GA_LINKED_PHENOTYPE_ROW,
							GA_GENE_3_COL));
			out.write("</linkedphenotype3>\n");
			out.write("<meiosismode>");
			out.write(""+ GeneticCode.meiosis_mode);
			out.write("</meiosismode>\n");
			out.write("<trackgenestatusdistribution>");
			out.write("" + GATracker.getTrackGeneStatusDistribution());
			out.write("</trackgenestatusdistribution>\n");
			out.write("<trackgenevaluedistribution>");
			out.write("" + GATracker.getTrackGeneValueDistribution());
			out.write("</trackgenevaluedistribution>\n");
			out.write("<chartupdatefrequency>");
			out.write("" + GAChartOutput.update_frequency);
			out.write("</chartupdatefrequency>");
			out.write("</ga>\n");
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public static final long serialVersionUID = 0xB9967684A8375BC0L;
}
