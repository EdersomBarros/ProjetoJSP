package util;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ReportUtil implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio, HashMap<String, Object> params, ServletContext servletContext) throws Exception {
		
		/*Cria a lista de Dados que vem do nosso SQL da consulta feita*/
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDados);
		
		String caminhaJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
		
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhaJasper, params, dataSource);
		
		return JasperExportManager.exportReportToPdf(impressoraJasper);
		
		
	}

	public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio, ServletContext servletContext) throws Exception {
		
		/*Cria a lista de Dados que vem do nosso SQL da consulta feita*/
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDados);
		
		String caminhaJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
		
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhaJasper, new HashMap(), dataSource);
		
		return JasperExportManager.exportReportToPdf(impressoraJasper);
		
		
	}
	
	

}
