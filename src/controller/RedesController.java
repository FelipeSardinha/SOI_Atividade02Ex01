package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;	

public class RedesController {

	public RedesController() {
		super();
	}
	
	public void commandIp(String os) {
		String linha = "";
		if (os.contains("Windows")) {
			System.out.println("Adaptador(es) Ethernet e IPv4 identificados: ");
			try {
				Process p = Runtime.getRuntime().exec("ipconfig");
				InputStream fluxo = p.getInputStream();
				InputStreamReader leitor = new InputStreamReader(fluxo);
				BufferedReader buffer = new BufferedReader(leitor);
				linha = buffer.readLine();
				String adapter= "";
				while (linha != null) {
					if (linha.contains("Ethernet")) {
						adapter = linha;
					}
					if (linha.contains("IPv4") && adapter != "") {
						System.out.println(adapter + "\n" + linha + "\n");
						adapter = "";
					}
					linha = buffer.readLine();
				}
				fluxo.close();
				leitor.close();
				buffer.close();
			} catch (IOException e) {
				String msgErro = e.getMessage();
				if (msgErro.contains("error=740")) {
					StringBuffer buffer = new StringBuffer();
					buffer.append("cmd /c ");
					buffer.append("ipconfig");
					try {
						Process p = Runtime.getRuntime().exec(buffer.toString());
						InputStream fluxo2 = p.getInputStream();
						InputStreamReader leitor2 = new InputStreamReader(fluxo2);
						BufferedReader buffer2 = new BufferedReader(leitor2);
						linha = buffer2.readLine();
						while (buffer2.readLine()!= null) {
							System.out.println(linha);
							linha = buffer2.readLine();
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					e.printStackTrace();
				}
			}
		}
		else {
			if (os.contains("Linux")) {
				System.out.println("Adaptador(es) Ethernet e IPv4 identificados: ");
				try {
					Process p = Runtime.getRuntime().exec("ifconfig");
					InputStream fluxo = p.getInputStream();
					InputStreamReader leitor = new InputStreamReader(fluxo);
					BufferedReader buffer = new BufferedReader(leitor);
					linha = buffer.readLine();
					String adapter = "";
					while (linha != null) {
						if (linha.contains("flags=")) {
							adapter = linha;
							String adapter1 [] = adapter.split(" ");
							adapter = adapter1[0];						
					}
					if (linha.contains("inet ") && adapter != "") {
						System.out.println(adapter + "\n" + linha.trim() + "\n");
						adapter = "";
					}
					linha = buffer.readLine();
				}
				buffer.close();
				fluxo.close();
				leitor.close();
			} catch (IOException e) {
				e.printStackTrace();
				}
			}
			else {
				System.out.println("Sistema incompativel");
			}
		}
	}
	
	public void commandPing(String os) {
		String linha = "";
		if (os.contains("Windows")) {
			System.out.println("Comando PING com 10 iterações em execução" + "\n");
			try {
				Process p = Runtime.getRuntime().exec("ping -n 10 www.google.com.br");
				InputStream fluxo = p.getInputStream();
				InputStreamReader leitor = new InputStreamReader(fluxo);
				BufferedReader buffer = new BufferedReader(leitor);
				linha = buffer.readLine();
				while (linha != null) {
					if (linha.contains("Mdia")) {
						String ms = (linha.substring(linha.lastIndexOf(" ")));
						System.out.println("Tempo médio de ping:" + ms );
					}
					linha = buffer.readLine();
					if (linha != null) {
						linha = Normalizer.normalize(linha, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
					}
				}
				buffer.close();
				leitor.close();
				fluxo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			if(os.contains("Linux")) {
				try {
					Process p = Runtime.getRuntime().exec("ping -c 10 www.google.com.br");
					InputStream fluxo = p.getInputStream();
					InputStreamReader leitor = new InputStreamReader(fluxo);
					BufferedReader buffer = new BufferedReader(leitor);
					linha = buffer.readLine();
					while (linha != null) {
						if (linha.contains("avg")) {
							String ms [] = linha.split("/");
							System.out.println("Tempo médio de ping:" + ms );
						}
						linha = buffer.readLine();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Sistema incompativel");
			}
		}
	}
}