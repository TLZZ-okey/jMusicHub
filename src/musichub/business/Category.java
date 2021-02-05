package musichub.business;

public enum Category{
	YOUTH("Youth"),NOVEL("Novel"),THEATER("Theater"),SPEECH("Speech"),DOCUMENTARY("Documentary");
	private String category;  
    
    private Category(String category){
        this.category = category;
    }
    public String getLange(){
        return this.category;
    }
}