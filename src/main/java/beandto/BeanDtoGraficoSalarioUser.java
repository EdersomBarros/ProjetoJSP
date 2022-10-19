package beandto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BeanDtoGraficoSalarioUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	List<String> perfis = new ArrayList<String>();
	List<Double> salarios = new ArrayList<Double>();
	
	
	
	
	public List<String> getPerfis() {
		return perfis;
	}
	public void setPerfis(List<String> perfis) {
		this.perfis = perfis;
	}
	public List<Double> getSalarios() {
		return salarios;
	}
	public void setSalarios(List<Double> salarios) {
		this.salarios = salarios;
	}
	
	
	
	
	

}
