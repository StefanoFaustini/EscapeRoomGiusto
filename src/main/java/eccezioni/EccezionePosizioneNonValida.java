/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eccezioni;

/**
 *
 * @author work
 */
public class EccezionePosizioneNonValida {
    
     private int ripiano;
    private int posizione;

    public EccezionePosizioneNonValida(int ripiano,int posizione)
    {
        this.ripiano=ripiano;
        this.posizione=posizione;
    }
    
    
    public int getRipiano() {
        return ripiano;
    }

    public int getPosizione() {
        return posizione;
    }
    
    public String toString()
    {
        String s=" ";
        
        s+="La posizione ripiano: "+getRipiano()+"posizione: "+getPosizione()+" non è valida.";
        return s;
    }
    
}
