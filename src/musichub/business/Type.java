package musichub.business;

public enum Type{
    JAZZ("Jazz"),CLASSIC("Classic"),HIPHOP("Hip-Hop"),ROCK("Rock"),POP("Pop"),RAP("Rap");
    private String type;  
    
    private Type(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
}