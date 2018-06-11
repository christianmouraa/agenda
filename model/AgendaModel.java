/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author chris
 */
public class AgendaModel {
    
    private static final String[] CAMPOS = {"nome", "telefone", "email", "aniversário"};
    private final File file = new File("C:/Users/Chris/Desktop/listaContatos.txt");
    
    public AgendaModel(){}
    /**
     * @return the CAMPOS
     */
    public static String[] getCAMPOS() {
        return CAMPOS;
    }
    
    public ArrayList getContatos() throws FileNotFoundException{
        
        return geraTempFile();
    }
    
    public void adicionar(File file){
        
        try {

            String[] valores = new String[4];
            
            for (int i = 0; i < getCAMPOS().length; i++) {
                
                valores[i] = (JOptionPane.showInputDialog("Entre com o " + getCAMPOS()[i]));
            }
            
            ArrayList<String> tempFile = geraTempFile();
            
            boolean canWrite = true;
            
            for (String contatoAtual : tempFile) {
                
                if (contatoAtual.contains(valores[0])){
                    
                    String temp = JOptionPane.showInputDialog(
                            "Este nome já está associado a um contato existente.\n" +
                            "Deseja inclir um novo contato com nome duplicado?\n" +
                            "Digite 1 para sim ou 0 para cancelar a operação" +
                            " ou 2 para alerar o nome digitado"
                    );
                    
                    
                    int confirma = 10;
                    
                    if (temp.matches("\\d")) {
                        
                        confirma = Integer.parseInt(temp);
                    }
                    
                    boolean opcaoConfirmada = false;
                    
                    while(!opcaoConfirmada){
                        
                        switch(confirma){
                            
                            case 0: 
                                canWrite = false;
                                opcaoConfirmada = true;
                                break;
                            
                            case 1: 
                                canWrite = true;
                                opcaoConfirmada = true;
                                break;
                            
                            case 2: 
                                valores[0] = JOptionPane.showInputDialog("Entre com o novo nome");
                                opcaoConfirmada = true;
                                break;
                            
                            default:
                                confirma = Integer.parseInt(JOptionPane.showInputDialog("Esta opção é inválida,"
                                    + " entre apenas com 0, 1 ou 2!"));
                        }
                    }
                    
                }
            }
            
            if (canWrite) {
                
                String linha = "";
                
                for (int i = 0; i < valores.length; i++) {
                    
                    if (i < 3) {
                        linha = linha.concat(valores[i] + ",");
                    }else linha = linha.concat(valores[i]);
                }
                
                tempFile.add(linha);
                escreveArquivo(tempFile, file);
            }
            
        } catch (IOException e) {
            
            System.out.println(e);
        }
    }
    
    public void alterar(String valorAntigo, String valorNovo){
        
        try {

            String linha;

            ArrayList<String> tempFile = this.geraTempFile();
            
            int i = 0;
            
            for (String contatoAtual : tempFile) {

                if (contatoAtual.contains(valorAntigo)) {

                    linha = contatoAtual;
                    linha = linha.replace(valorAntigo, valorNovo);
                    tempFile.set(i, linha);
                }
                i++;
            }
            
            escreveArquivo(tempFile, file);

        } catch (IOException ex) {

            System.out.println("Ocorreu um erro com o aarquivo\n" + ex);
        }
        
    }
    
    public void excluir(File file){
    
        try {
            
            String numero = JOptionPane.showInputDialog("Entre com o numero do contato que deseja excluir");
            
            ArrayList<String> tempFile = this.geraTempFile();
            
            int index = 121212;
            
            for (int i = 0; i < tempFile.size(); i++) {
                
                if (tempFile.get(i).contains(numero)) {
                    index = i;
                }
            }
            
            if (index != 121212) {
                
                tempFile.remove(index);
                
            }else System.out.println("Contato não encontrado para o número " + numero);
            
            escreveArquivo(tempFile, file);
            
        } catch (IOException ex) {
            System.out.println("Ocorreu um erro com o aquixo\n" + ex);;
        }
    }
    
    public ArrayList geraTempFile() throws FileNotFoundException{
        
        ArrayList<String> tempFile = new ArrayList<>();
        Scanner scan = new Scanner(file);
        
        String linha;
        while(scan.hasNextLine()){
            
            linha = scan.nextLine();
            tempFile.add(linha);
        }
        
        return tempFile;
    }
    
    private static void escreveArquivo(ArrayList<String> tempFile, File file) throws IOException{
    
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        
        for (String linha : tempFile) {
            
            if (!linha.equals("")) {
                
                bw.write(linha);
                bw.newLine();
            }
        }
        
        bw.close();
    }
}
