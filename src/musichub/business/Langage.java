package musichub.business;

public enum Langage{
    FRENCH("French"), ENGLISH("English"), ITALIAN("Italian"), SPANISH("Spanish"), GERMAN("German");
    private String langage;  
    
    private Langage(String langage){
        this.langage = langage;
    }
    public String getLange(){
        return this.langage;
    }
}