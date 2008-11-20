import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;

class Main extends JFrame implements ActionListener{
	
	private ProcessGenerator processGenerator;
	private Consumer consumer;
	private Supplier supplier;
	private Thread consumerThread;
	private Thread updateDataFields;
	// Generator Panel
	private JPanel generatorPanel = new JPanel();
	private JLabel numOfProcessesLabel = new JLabel("# Of Processes: ");
	private JLabel fileNameLabel = new JLabel("File Name: ");
	
	private JLabel cpuBurstsAverageLabel = new JLabel("# CPU Bursts Average: ");
	private JLabel cpuBurstTimeRangeLabel = new JLabel("CPU Burst Time Range: ");
	private JLabel ioBurstsAverageLabel = new JLabel("# IO Bursts Average: ");
	private JLabel ioBurstTimeRangeLabel = new JLabel("IO Burst Time Range: ");
	private JLabel dashLabel = new JLabel(" - ");
	
	private SpinnerNumberModel numOfProcessesSpinnerModel = new SpinnerNumberModel(200, 1, 500, 1); 
	private JSpinner numOfProcessesField = new JSpinner(numOfProcessesSpinnerModel);
	private JTextField fileNameField = new JTextField("processes.txt");
	
	
	private SpinnerNumberModel cpuBurstsAverageSpinnerModel = new SpinnerNumberModel(75, 51, 100, 1); 
	private JSpinner cpuBurstsAverageField = new JSpinner(cpuBurstsAverageSpinnerModel);
	private SpinnerNumberModel ioBurstsAverageSpinnerModel = new SpinnerNumberModel(25, 0, 49, 1); 
	private JSpinner ioBurstsAverageField = new JSpinner(ioBurstsAverageSpinnerModel);
	
	
	private SpinnerNumberModel cpuBurstTimeStartSpinnerModel = new SpinnerNumberModel(5, 1, 49, 1); 
	private JSpinner cpuBurstTimeStartField = new JSpinner(cpuBurstTimeStartSpinnerModel);
	private SpinnerNumberModel ioBurstTimeStartSpinnerModel = new SpinnerNumberModel(500, 1, 999, 1); 
	private JSpinner ioBurstTimeStartField = new JSpinner(ioBurstTimeStartSpinnerModel);

	private SpinnerNumberModel cpuBurstTimeEndSpinnerModel = new SpinnerNumberModel(50, 50, 500, 1); 
	private JSpinner cpuBurstTimeEndField = new JSpinner(cpuBurstTimeEndSpinnerModel);
	private SpinnerNumberModel ioBurstTimeEndSpinnerModel = new SpinnerNumberModel(1000, 1000, 10000, 1); 
	private JSpinner ioBurstTimeEndField = new JSpinner(ioBurstTimeEndSpinnerModel);
	
	
	
	
	private JButton generateBtn = new JButton("Generate");
	private JButton resetDefaultBtn = new JButton("Reset Default");
	// Generator Panel ---- END
	
	
	
	
	// Simulator Preferences Panel
	private JPanel simulatorPreferencesPanel = new JPanel();
	private JLabel openFileNameLabel = new JLabel("Processes File: ");
	private JLabel queue0QuantumLabel = new JLabel("Queue 0 Quantum: ");
	private JLabel queue1QuantumLabel = new JLabel("Queue 1 Quantum: ");
	private JLabel contextSwitchTimeLabel = new JLabel("Context Switch Time: ");
	private JLabel multiprogrammingDegreeLabel = new JLabel("Multiprogramming Degree: ");
	
	private JTextField openFileNameField = new JTextField("processes.txt");
	
	private JComboBox queue0QuantumField = new JComboBox(new Integer[] {8,15,20});
	private JComboBox queue1QuantumField = new JComboBox(new Integer[] {16,25,30});
	
	private SpinnerNumberModel contextSwitchTimeSpinnerModel = new SpinnerNumberModel(2, 1, 20, 1); 
	private JSpinner contextSwitchTimeField = new JSpinner(contextSwitchTimeSpinnerModel);
	
	private SpinnerNumberModel multiprogrammingDegreeSpinnerModel = new SpinnerNumberModel(20, 1, 500, 1); 
	private JSpinner multiprogrammingDegreeField = new JSpinner(multiprogrammingDegreeSpinnerModel);
	
	
	
	private JButton simulateBtn = new JButton("Simulate");
	private JButton resetPreferencesBtn = new JButton("Reset Preferences");
	// Simulator Preferences Panel --- END
	
	
	// Simulation Result Panel
	private JPanel simulationResultPanel = new JPanel();
	private JLabel cpuUtilizationLabel = new JLabel("CPU Utilization");
	private JLabel throughputLabel = new JLabel("Throughput (# of Processes Per 1000 Time Unit)");
	private JLabel averageTurnAroundTimeLabel = new JLabel("Average Turn Around time");
	private JLabel averageWaitingTimeLabel = new JLabel("Average Waiting Time");
	
	private JTextField cpuUtilizationField = new JTextField();
	private JTextField throughputField = new JTextField();
	private JTextField averageTurnAroundTimeField = new JTextField();
	private JTextField averageWaitingTimeField = new JTextField();
	
	private JTextArea resultTextArea = new JTextArea();
	// Simulation Result Panel	--- END
	
	
	public Main(){
		super("Multilevel Feedback Queue Scheduling!");
		setLayout(new BorderLayout(5,5));//new GridLayout(2,1,5,5));
		generatorPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Generator"),new EmptyBorder(5,10,5,10)));
		simulatorPreferencesPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Simulator Preferences"),new EmptyBorder(5,10,5,10)));
		simulationResultPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Simulation Result"),new EmptyBorder(5,10,5,10)));
		
		
		generatorPanel.setLayout(new GridLayout(7,2,3,3));
		generatorPanel.add(numOfProcessesLabel);
		generatorPanel.add(numOfProcessesField);
		generatorPanel.add(fileNameLabel);
		generatorPanel.add(fileNameField);
		
		generatorPanel.add(cpuBurstsAverageLabel);
		generatorPanel.add(cpuBurstsAverageField);
		
		JPanel subPanel11 = new JPanel();
		subPanel11.setLayout(new GridLayout(1,1,1,1));
		JPanel subPanel12 = new JPanel();
		subPanel12.setLayout(new GridLayout(1,3,1,1));
		
		subPanel11.add(cpuBurstTimeRangeLabel);
		subPanel12.add(cpuBurstTimeStartField);
		subPanel12.add(new JLabel(" - "));
		subPanel12.add(cpuBurstTimeEndField);
		
		
		generatorPanel.add(subPanel11);
		generatorPanel.add(subPanel12);
		
		
		
		generatorPanel.add(ioBurstsAverageLabel);
		generatorPanel.add(ioBurstsAverageField);
		
		JPanel subPanel21 = new JPanel();
		subPanel21.setLayout(new GridLayout(1,1,1,1));
		JPanel subPanel22 = new JPanel();
		subPanel22.setLayout(new GridLayout(1,3,1,1));
		
		subPanel21.add(ioBurstTimeRangeLabel);
		subPanel22.add(ioBurstTimeStartField);
		subPanel22.add(dashLabel);
		subPanel22.add(ioBurstTimeEndField);
		
		
		generatorPanel.add(subPanel21);
		generatorPanel.add(subPanel22);
		
		

		
		generatorPanel.add(resetDefaultBtn);
		generatorPanel.add(generateBtn);
		
		
		
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout(1,2,5,5));
		
		
		
		simulatorPreferencesPanel.setLayout(new GridLayout(6,2,3,3));
		simulatorPreferencesPanel.add(openFileNameLabel);
		simulatorPreferencesPanel.add(openFileNameField);
		simulatorPreferencesPanel.add(queue0QuantumLabel);
		simulatorPreferencesPanel.add(queue0QuantumField);
		simulatorPreferencesPanel.add(queue1QuantumLabel);
		simulatorPreferencesPanel.add(queue1QuantumField);
		simulatorPreferencesPanel.add(contextSwitchTimeLabel);
		simulatorPreferencesPanel.add(contextSwitchTimeField);
		simulatorPreferencesPanel.add(multiprogrammingDegreeLabel);
		simulatorPreferencesPanel.add(multiprogrammingDegreeField);
		simulatorPreferencesPanel.add(resetPreferencesBtn);
		simulatorPreferencesPanel.add(simulateBtn);
		
		
		
		
		upperPanel.add(generatorPanel);
		upperPanel.add(simulatorPreferencesPanel);
		
		
		
		add(upperPanel,BorderLayout.NORTH);
		
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new GridLayout(1,2,3,3));
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(4,2,1,1));
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout(3,3));
		
		leftPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Data"),new EmptyBorder(5,10,5,10)));
		
		leftPanel.add(cpuUtilizationLabel);
		leftPanel.add(cpuUtilizationField);
		leftPanel.add(throughputLabel);
		leftPanel.add(throughputField);
		leftPanel.add(averageTurnAroundTimeLabel);
		leftPanel.add(averageTurnAroundTimeField);
		leftPanel.add(averageWaitingTimeLabel);
		leftPanel.add(averageWaitingTimeField);
		
		cpuUtilizationField.setEditable(false);
		throughputField.setEditable(false);
		averageTurnAroundTimeField.setEditable(false);
		averageWaitingTimeField.setEditable(false);
		resultTextArea.setEditable(false);
		
		
		
		
		rightPanel.setBorder(new CompoundBorder(BorderFactory.createTitledBorder("Monitor"),new EmptyBorder(5,10,5,10)));
		
		//rightPanel.add(new JLabel("Moniter"), BorderLayout.NORTH);
		rightPanel.add(new JScrollPane(resultTextArea));
		simulationResultPanel.setLayout(new GridLayout(1,2));
		simulationResultPanel.add(leftPanel);
		simulationResultPanel.add(rightPanel);
		add(simulationResultPanel);
		
		
		resetDefaultBtn.addActionListener(this);
		generateBtn.addActionListener(this);
		resetPreferencesBtn.addActionListener(this);
		
		// When Press Must Create a new Consumer and a New Scheduler
		simulateBtn.addActionListener(this);
		
		
		setSize(900,600);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		String command = e.getActionCommand();
		if(command.equals("Reset Default"))
			resetDefault();
		else if(command.equals("Reset Preferences"))
			resetPreferences();
		else if(command.equals("Generate"))
			generate();
		else if(command.equals("Simulate"))
			simulate();
	}
	
	private void resetDefault(){
		numOfProcessesField.setValue(new Integer(200));
		fileNameField.setText("processes.txt");
		cpuBurstsAverageField.setValue(new Integer(75));
		cpuBurstTimeStartField.setValue(new Integer(5));
		cpuBurstTimeEndField.setValue(new Integer(50));
		ioBurstsAverageField.setValue(new Integer(25));
		ioBurstTimeStartField.setValue(new Integer(500));
		ioBurstTimeEndField.setValue(new Integer(1000));
	}
	private void resetPreferences(){
		openFileNameField.setText("processes.txt");
		queue0QuantumField.setSelectedIndex(0);
		queue1QuantumField.setSelectedIndex(0);
		contextSwitchTimeField.setValue(new Integer(2));
		multiprogrammingDegreeField.setValue(new Integer(20));
	}
	private void generate(){
		String name = fileNameField.getText();
		int num = (Integer)numOfProcessesField.getValue();
		processGenerator = new ProcessGenerator(name);
		processGenerator.generateProcesses(num);
		JOptionPane.showMessageDialog(this, num + " Processes has been generated and been saved to '" + name + "' File", "Done!", JOptionPane.INFORMATION_MESSAGE);
	}
	private void simulate(){
		resultTextArea.setText("");
		String processesFile = openFileNameField.getText();
		String line;
		int lineCount = 0;
		try{
			BufferedReader br = new BufferedReader(new FileReader(processesFile));
			while ((line = br.readLine()) != null) {
			    lineCount++;
			}
			br.close();
			int quantum0 = (Integer)queue0QuantumField.getSelectedItem();
			int quantum1 = (Integer)queue1QuantumField.getSelectedItem();
			int context = (Integer)contextSwitchTimeField.getValue();
			int degree =  (Integer)multiprogrammingDegreeField.getValue();
			System.out.println("Line Counts: " + lineCount);
			
			consumer = new Consumer(quantum0, quantum1, context, degree, lineCount,resultTextArea);
			consumerThread = new Thread(consumer);
			supplier =  new Supplier(processesFile, consumer);

			supplier.start();
			consumerThread.start();
			
			updateDataFields = new Thread(new UpdateFields(supplier,consumerThread));
			updateDataFields.start();
			
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	
		
	}
	
	public class UpdateFields implements Runnable{
		Thread s,c;
		public UpdateFields(Thread s, Thread c){
			
		}
		public void run(){
			try{
				supplier.join();
				consumerThread.join();
				System.out.println("Finished");
				cpuUtilizationField.setText(""+consumer.getCpuUtilization()+"%");
				throughputField.setText(""+consumer.getThroughput());
				averageTurnAroundTimeField.setText(""+consumer.getTurnAroundTime());
				averageWaitingTimeField.setText(""+consumer.getWaitingTime());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
		}
	}
	public static void main(String argv[]) throws Exception {
		/* Parameters Could be changed from the Interface
		**  GENERATOR	
		**		- Number Of Process To Generate
		**		- File Name
		**		- Range Of CPU Burst...etc
		**	SCHEDULER
		** 		- Quantum for Both Q1 and Q2
		**		- Context Switch Time
		**	CONSUMER
		**		- Delay
		** 		- Maximum Number Of Process In System
		**/
		Main f = new Main();
		/*
		int numOfProcesses = 200;
		
		ProcessGenerator processG = new ProcessGenerator("processes.txt");
		processG.generateProcesses(numOfProcesses);
		String processesFile = processG.getFileName();
		
		// Two Threads
		Consumer consumer = new Consumer(numOfProcesses);
		Thread consumerThread = new Thread(consumer);
		Supplier supplier =  new Supplier(processesFile, consumer);
		
		supplier.start();
		consumerThread.start();
		*/
    }


}