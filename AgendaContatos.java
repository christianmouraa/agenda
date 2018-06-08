/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

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
public class AgendaContatos {
    
    private static final String[] CAMPOS = {"nome", "telefone", "email", "aniversário"};
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        String path = System.getProperty("user.home") + "/Desktop/" + "listaContatos.txt";
        File file = new File(path);
        
        if (!file.exists()) {
            file.createNewFile();
        }
        
        int opcao = 10;
        
        while (opcao != 0) {            
            
            String tempOpcao = JOptionPane.showInputDialog(
                    
                    "Tecle 1 para listar seus contatos\n"
                    + "Tecle 2 para adicionar um novo contato\n"
                    + "Tecle 3 para alterar um contato\n"
                    + "Tecle 4 para excluir um contato\n"
                    + "Tecle 0 para fechar a aplicação"
            );
            
            if (tempOpcao.matches("\\d")) {
                opcao = Integer.parseInt(tempOpcao);
            }
            
            switch(opcao){
                
                case 0: System.out.println("Encerrando a aplicação ...");break;
                case 1: listar(file);break;
                case 2: adicionar(file);break;
                case 3: alterar(file);break;
                case 4: excluir(file);break;
                default: System.out.println("Opção inválida");
                
            }
        }
    }
    
    private static void listar(File file){
        
        try {
            
            ArrayList<String> tempFile = geraTempFile(file);
            
            for (String contato : tempFile) {
                
                String[] arrContatos = contato.split(",");
                
                for (int i = 0; i < arrContatos.length; i++) {
                    
                    if (i < (arrContatos.length-1)) {
                        System.out.println(CAMPOS[i] + ": " + arrContatos[i]);
                    }else{
                        System.out.println(arrContatos[i]);
                        System.out.println("==================================");
                    }
                }
            }
            
        } catch (IOException e) {
                System.out.println(e);
        } 
        
    }
    
    private static void adicionar(File file){
        
        try {

            String[] valores = new String[4];
            
            for (int i = 0; i < CAMPOS.length; i++) {
                
                valores[i] = (JOptionPane.showInputDialog("Entre com o " + CAMPOS[i]));
            }
            
            ArrayList<String> tempFile = geraTempFile(file);
            
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
    
    private static void alterar(File file){
        
        String valorAntigo;
        String valorNovo;
        
        while (true) { 
            
            String temp = JOptionPane.showInputDialog(
              
                            "Digite o campo do contato que deseja alterar.\n"
                            + "Tecle 1 para nome\n"
                            + "Tecle 2 para telefone\n"
                            + "Tecle 3 para enail\n"
                            + "Tecle 4 para aniversário"
            );
            
            
            int i = 10;
            
            if (temp.matches("\\d")) {
                 i = (Integer.parseInt(temp) -1);
            }
            
            if (i >= 0 && i <= 3) {
                
                valorAntigo = JOptionPane.showInputDialog("Entre com " + CAMPOS[i] + " atual do contato a ser alterado");
                
                try {
                    
                    Scanner scan = new Scanner(file);
                    
                    String linha;
                    
                    ArrayList<String> tempFile = geraTempFile(file);
                    
                    for (String contatoAtual : tempFile) {
                        
                        if (contatoAtual.contains(valorAntigo)) {
                            
                            linha = contatoAtual;
                            valorNovo = JOptionPane.showInputDialog("Entre com o novo " + CAMPOS[i]);
                            
                            linha = linha.replace(valorAntigo, valorNovo);
                            tempFile.remove(contatoAtual);
                            tempFile.add(linha);
                        
                        }else if (!scan.hasNextLine()) {
                            
                            System.out.println("O " + CAMPOS[i] + " \"" + valorAntigo + "\" não existe na lista.");
                        }
                    }
                    
                    escreveArquivo(tempFile, file);
                    
                } catch (IOException ex) {
                    
                    System.out.println("Ocorreu um erro com o aarquivo\n" + ex);
                }
                
                break;
            
            }else System.out.println("Opção inválida");
        }
        
    }
    
    private static void excluir(File file){
    
        try {
            
            String numero = JOptionPane.showInputDialog("Entre com o numero do contato que deseja excluir");
            
            ArrayList<String> tempFile = geraTempFile(file);
            
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
    
    private static ArrayList geraTempFile(File file) throws FileNotFoundException{
        
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
