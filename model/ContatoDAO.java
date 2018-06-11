/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda.model;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author chris
 */
public class ContatoDAO {
    
    private static final String[] CAMPOS = {"nome", "telefone", "email", "aniversário"};
    private final File file = new File("C:/Users/Chris/Desktop/listaContatos.txt");
    
    public ContatoDAO(){}
    /**
     * @return the CAMPOS
     */
    public static String[] getCAMPOS() {
        return CAMPOS;
    }
    
    public boolean adicionar(Contato contato, boolean ignorarDuplicado){
        
        try {
            
            ArrayList<String> tempFile = getContatos();
            
            boolean canWrite = true;
            
            String[] contatoArray = contato.toArray();
            
            if (!ignorarDuplicado) {
                
                for (String contatoAtual : tempFile) {

                    if (contatoAtual.contains(contatoArray[1])){

                        canWrite = false;
                    }
                }
            }
            
            if (canWrite) {
                
                String linha = contato.serialize();
                
                tempFile.add(linha);
                escreveArquivo(tempFile);
                return true;
            }
            
        } catch (IOException e) {
            
            System.out.println(e);
        }
        
        return false;
    }
    
    public void alterar(String valorAntigo, String valorNovo){
        
        try {

            String linha;

            ArrayList<String> tempFile = this.getContatos();
            
            int i = 0;
            
            for (String contatoAtual : tempFile) {

                if (contatoAtual.contains(valorAntigo)) {

                    linha = contatoAtual;
                    linha = linha.replace(valorAntigo, valorNovo);
                    tempFile.set(i, linha);
                }
                i++;
            }
            
            escreveArquivo(tempFile);

        } catch (IOException ex) {

            System.out.println("Ocorreu um erro com o aarquivo\n" + ex);
        }
        
    }
    
    public void excluir(File file){
    
        try {
            
            String numero = JOptionPane.showInputDialog("Entre com o numero do contato que deseja excluir");
            
            ArrayList<String> tempFile = this.getContatos();
            
            int index = 121212;
            
            for (int i = 0; i < tempFile.size(); i++) {
                
                if (tempFile.get(i).contains(numero)) {
                    index = i;
                }
            }
            
            if (index != 121212) {
                
                tempFile.remove(index);
                
            }else System.out.println("Contato não encontrado para o número " + numero);
            
            escreveArquivo(tempFile);
            
        } catch (IOException ex) {
            System.out.println("Ocorreu um erro com o aquixo\n" + ex);;
        }
    }
    
    public ArrayList getContatos() throws FileNotFoundException{
        
        ArrayList<String> tempFile = new ArrayList<>();
        Scanner scan = new Scanner(file);
        
        String linha;
        while(scan.hasNextLine()){
            
            linha = scan.nextLine();
            tempFile.add(linha);
        }
        
        return tempFile;
    }
    
    private void escreveArquivo(ArrayList<String> tempFile) throws IOException{
    
        BufferedWriter bw = new BufferedWriter(new FileWriter(this.file));
        
        for (String linha : tempFile) {
            
            if (!linha.equals("")) {
                
                bw.write(linha);
                bw.newLine();
            }
        }
        
        bw.close();
    }
}
