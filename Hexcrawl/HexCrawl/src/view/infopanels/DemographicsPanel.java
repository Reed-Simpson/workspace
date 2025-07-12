package view.infopanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import data.population.PopulationModel;
import data.population.NPCSpecies;
import view.InfoPanel;

@SuppressWarnings("serial")
public class DemographicsPanel extends JPanel {
	private InfoPanel info;
	private JLabel demoLabel;
	private JTextArea demographics;
	private JLabel toleranceLabel;
	
	public DemographicsPanel(InfoPanel info) {
		this.info = info;
		this.setLayout(new BorderLayout());
		

		demoLabel = new JLabel("Demographics: ");
		JPanel dummy3 = new JPanel();
		dummy3.setLayout(new BoxLayout(dummy3, BoxLayout.X_AXIS));
		dummy3.setAlignmentX(RIGHT_ALIGNMENT);
		dummy3.add(demoLabel);
		dummy3.add(Box.createHorizontalGlue());
		toleranceLabel = new JLabel("Tolerance: ");
		dummy3.add(toleranceLabel);
		dummy3.add(Box.createHorizontalStrut(100));
		this.add(dummy3,BorderLayout.NORTH);

		demographics = new JTextArea();
		demographics.setEditable(false);
		demographics.setLineWrap(true);
		demographics.setWrapStyleWord(true);
		demographics.setHighlighter(null);

		JScrollPane p = new JScrollPane(demographics);
		p.setMinimumSize(new Dimension(9999,52));
		p.setMaximumSize(new Dimension(9999,52));
		p.setPreferredSize(new Dimension(9999,52));
		this.add(p,BorderLayout.CENTER);
	}
	
	public void doPaint() {
		PopulationModel population = info.getPanel().getController().getPopulation();
		Point pos;
		if(info.getPanel().isShowDistance()) pos = info.getPanel().getMouseoverGridPoint();
		else pos = info.getPanel().getSelectedGridPoint();

		float pop = population.getUniversalPopulation(pos);
		int popScale = population.getPopScale(pos) ;
		LinkedHashMap<NPCSpecies,Integer> demographics = population.getTransformedDemographics(pos);

		int transformedUniversalPopulation = population.getTransformedUniversalPopulation(pos);
		this.setDemoLabel("Demographics: "+getDemoLabelText(pop, popScale,transformedUniversalPopulation));
		Iterator<NPCSpecies> iterator = demographics.keySet().iterator();
		if(iterator.hasNext()) {
			float isolationFactor = Math.min(population.getIsolationFactor(pos, iterator.next(), NPCSpecies.HUMAN),1.0f);
			this.toleranceLabel.setText("Tolerance: "+PopulationModel.populationPercentStringFormat.format(isolationFactor));
		}

		setText(this.getDemoString(pos,demographics));
	}

	public void setText(String string) {
		this.demographics.setText(string);
	}

	public void setDemoLabel(String string) {
		this.demoLabel.setText(string);
	}

	private String getDemoString(Point pos,LinkedHashMap<NPCSpecies,Integer> demographics) {
		PopulationModel population = info.getPanel().getController().getPopulation();
		int pop = population.getTransformedUniversalPopulation(pos);
		String demoString = "";
		for(NPCSpecies s:demographics.keySet()) {
			if(demographics.get(s)!=null&&demographics.get(s)>0) {
				demoString+=s.name()+" "+population.demoTransformString(demographics.get(s),pop)+", ";
			}
		}
		if(demoString.length()>2) {
			demoString = demoString.substring(0, demoString.length()-2);
		}else {
			demoString = "none";
		}
		return demoString;
	}

	private String getDemoLabelText(float pop, int popScale, int transformedUniversalPopulation) {
		if(transformedUniversalPopulation>0) {
			return info.getPanel().getController().getPopulation().demoTransformString(pop,pop, popScale);
		}else {
			return "None";
		}
	}

}
