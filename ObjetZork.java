
public class ObjetZork {
	private String nom;
	private String description;
	private int vitalite;
	private int poids;
	
	public ObjetZork(String nom, String description, int poids) {
		
		this.nom= nom;
		this.description= description;
		this.poids=poids;
		this.vitalite=0;
	}
	
	public ObjetZork(String nom, String description, int poids, int vitalite) {
		this(nom,description,poids);
		this.vitalite=vitalite;		
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getVitalite() {
		return this.vitalite;
	}
	public int getPoids() {
		return this.poids;
	}
	 
	public boolean equals(ObjetZork o) {
		if(!(o instanceof ObjetZork)) {
			return false;
		}else if(!((this.nom).equals(o.getNom()))) {
				return false;
		}else if(!((this.description).equals(o.getDescription()))) {
			return false;
		}else if(!((this.vitalite==o.getVitalite()))) {
				return false;
		}else if(!(this.poids==o.getPoids())){
			return false;
		}
		return true;
		}
	
	
	
	
	
	
	
	
	
	
	
	
}
