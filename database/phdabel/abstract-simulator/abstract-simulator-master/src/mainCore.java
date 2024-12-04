import ufrgs.maslab.abstractsimulator.core.BlackBox;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.values.FireBuildingTask;
import ufrgs.maslab.abstractsimulator.variables.FireFighter;
import ufrgs.maslab.abstractsimulator.variables.FireStation;

public class mainCore {
	
	/*
	public DataSet getTaskDataSet()
	{
		DataSet training = new DataSet(9,1);
		//
		//  monta um conjunto de treino
		//  com tarefas aleatorias
		// 
        BufferedReader br = WriteFile.getInstance().readFile("inputset");
        String line = null;
        try {
			while((line = br.readLine()) != null)
			{
				String[] cols = line.split(";");
				double[] arr = new double[cols.length];
				for(int k = 0; k < cols.length; k++)
				{
					arr[k] = Double.valueOf(cols[k]);
				}
				DataSetRow d = new DataSetRow(arr, new double[]{0});
				
				training.addRow(d);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return training;
        //System.out.println(training.size());
		//training.normalize(new TemplateNormalizer(this.templateFireTask));
	}*/

}
