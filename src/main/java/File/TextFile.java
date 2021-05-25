/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author work
 */
public class TextFile {
    
     private char mode;
    private BufferedReader reader;
    private BufferedWriter writer;
    
    public TextFile(String nomeFile, char mode) throws FileNotFoundException, IOException
    {
        this.mode='R';
        if(mode=='w' || mode=='W')
            this.mode='W';
        
        if(this.mode=='R')
        reader=new BufferedReader(new FileReader(nomeFile));
 
        else
            writer=new BufferedWriter(new FileWriter(nomeFile));
        
    }
    public TextFile (String nomeFile, char mode, boolean append) throws FileNotFoundException, IOException
    {
        this.mode='R';
        if(mode=='w' || mode=='W')
            this.mode='W';
        
        if(this.mode=='R')
        reader=new BufferedReader(new FileReader(nomeFile));
 
        else
            writer=new BufferedWriter(new FileWriter(nomeFile, append));
        
    }
    
    public void toFile(String line) throws FileExeption, IOException
    {
        if (mode=='R')
            throw new FileExeption("impossibile scrivere sul file. File aperto in lettura!");
        
        writer.write(line);
        writer.newLine();
        
    }
    
    public String fromFile() throws FileExeption, IOException
    {
        String rigaLetta;
         if (mode=='W')
            throw new FileExeption("impossibile leggere dal file. File aperto in scrittura!");
         
         rigaLetta=reader.readLine();
         if(rigaLetta==null)
             throw new FileExeption("Fine del file!");
         
         return rigaLetta;
        
    }
    
    public void close() throws IOException
    {
        if(mode=='W')
            writer.close();
        else
            reader.close();
    }
    
    
    
}
