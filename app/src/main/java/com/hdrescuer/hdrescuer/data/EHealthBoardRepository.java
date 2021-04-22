package com.hdrescuer.hdrescuer.data;

/**
 * Repositorio para la eHealthBoard
 * @author Domingo Lopez
 */
public class EHealthBoardRepository {


    private Integer BPM;
    private Integer OxBlood;
    private Integer airFlow;

    /**
     * Constructor vacío
     * @author Domingo Lopez
     */
    public EHealthBoardRepository() {
        super();

       this.BPM = 0;
       this.OxBlood = 0;
       this.airFlow = 0;
    }

    public Integer getBPM() {
        return BPM;
    }

    public void setBPM(Integer BPM) {
        this.BPM = BPM;
    }

    public Integer getOxBlood() {
        return OxBlood;
    }

    public void setOxBlood(Integer oxBlood) {
        OxBlood = oxBlood;
    }

    public Integer getAirFlow(){return this.airFlow; }

    public void setAirFlow(Integer air){this.airFlow = air;}


    /**
     * Método que resetea los valores del repositorio
     * @author Domingo Lopez
     */
    public void reset(){
        this.BPM = 0;
        this.OxBlood = 0;
        this.airFlow = 0;
    }
}