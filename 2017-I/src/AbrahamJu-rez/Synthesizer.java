/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Abraham
 */
public class Synthesizer implements ControlInterfaces{
    
    public String brand;
    public String synthesisType;
    public String electronicOscillator;
    public String lfo;
    public String vcf;
    
     public Synthesizer(String brandSynthesizer, String synthesis, String Oscillator, String lfo, String vcf) {
        brand = brandSynthesizer;
        synthesisType = synthesis;
        electronicOscillator = Oscillator;
        lfo = lfo;
        vcf = vcf ;
    
     }
    
    @Override
    public void musicalKeyboard(){}
    @Override
    public void musicSequencers(){}
    @Override
    public void instrumentControllers(){}
    @Override
    public void fingerboards(){}
    @Override
    public void guitarSynthesizers(){}
    @Override
    public void windControllers(){}
    @Override
     public void electronicDrums(){}
     public void giveBrand(String brandValue) {
        
        brand = brandValue;
    }
        
     public void defineSynthesis(String synthesisUsed) {
        synthesisType = synthesisUsed;
    }
        
     public void  defineOscillator(String oscillatorUsed) {
        electronicOscillator = oscillatorUsed;
    }
        
     public void definelfo(String lfoType) {
        lfo = lfoType;
    }
     
     public void definevcf (String vcfType) {
        vcf = vcfType;
    }
     
}
