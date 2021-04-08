/**
 *  Classe principale du jeu "Zork". <p>
 *
 *  Zork est un jeu d'aventure très rudimentaire avec une interface en mode
 *  texte: les joueurs peuvent juste se déplacer parmi les différentes pieces.
 *  Ce jeu nécessite vraiment d'etre enrichi pour devenir intéressant!</p> <p>
 *
 *  Pour jouer a ce jeu, créer une instance de cette classe et appeler sa
 *  méthode "jouer". </p> <p>
 *
 *  Cette classe crée et initialise des instances de toutes les autres classes:
 *  elle crée toutes les pieces, crée l'analyseur syntaxique et démarre le jeu.
 *  Elle se charge aussi d'exécuter les commandes que lui renvoie l'analyseur
 *  syntaxique.</p>
 *
 * @author     Michael Kolling
 * @author     Marc Champesme (pour la traduction francaise)
 * @version    1.2
 * @since      March 2000
 */

public class Jeu {
	private AnalyseurSyntaxique analyseurSyntaxique;
	private Piece pieceCourante;


	/**
	 *  Crée le jeu et initialise la carte du jeu (i.e. les pièces).
	 */
	public Jeu() {
		creerPieces();
		analyseurSyntaxique = new AnalyseurSyntaxique();
	}


	/**
	 *  Crée toutes les pieces et relie leurs sorties les unes aux autres.
	 */
	public void creerPieces() {
		Piece Gymnase;
		Piece Cafeteria;
		Piece Bibliotheque;
		Piece Cantine;
		Piece Parking;
		Piece Chaufferie;
		Piece Forum;
		Piece SalleDeClasseA;
		Piece SalleDeClasseB;
		Piece Amphi1;
		Piece Amphi2;
		Piece Sortie;

		// création des pieces
		Gymnase = new Piece("Gymnase","Gymnase de l'université");
		Cafeteria= new Piece("Cafeteria", "Cafétéria de l'université");
		Bibliotheque= new Piece("Bibliothèque", "BU de l'université");
		Cantine = new Piece("Cantine", "Cantine de l'univ");
		Parking = new Piece("Parking", "Parking de l'université");
		Chaufferie = new Piece("Chaufferie", "Chaufferie de l'université");
		Forum = new Piece("Forum", "Forum de l'univ");
		SalleDeClasseA = new Piece("SalleDeClasseA", "Salle de Classe A");
		SalleDeClasseB = new Piece("SalleDeClasseB", "Salle de Classe B");
		Amphi1 = new Piece("Amphi1", "Amphi 1 de l'université");
		Amphi2 = new Piece("Amphi2", "Amphi2 de l'université");
		Sortie = new Piece("Sortie", "Sortie de l'université");
		
		

		// initialise les sorties des pieces
		Gymnase.setSorties(SalleDeClasseA, null, null, Cafeteria);
		Cafeteria.setSorties(SalleDeClasseB, Gymnase, null, null);
		Cantine.setSorties(null, null, Forum, Bibliotheque);
		Parking.setSorties(Sortie, Bibliotheque, null, null);
		Bibliotheque.setSorties(null, Cantine, SalleDeClasseA, Parking);
		Chaufferie.setSorties(Amphi1, null, null, null);
		Forum.setSorties(Cantine, null, null, SalleDeClasseA);
		SalleDeClasseA.setSorties(Bibliotheque, Forum, Gymnase, Amphi1);
		SalleDeClasseB.setSorties(Amphi2, Amphi1, Cafeteria, null);
		Amphi1.setSorties(null, SalleDeClasseA, Chaufferie, SalleDeClasseB);
		Amphi2.setSorties(null, null, SalleDeClasseB, null);
		Sortie.setSorties(null, null, Parking, null);
		
		
			// le jeu commence dehors
		pieceCourante = SalleDeClasseA;
	}


	/**
	 *  Pour lancer le jeu. Boucle jusqu'a la fin du jeu.
	 */
	public void jouer() {
		afficherMsgBienvennue();

		// Entrée dans la boucle principale du jeu. Cette boucle lit
		// et exécute les commandes entrées par l'utilisateur, jusqu'a
		// ce que la commande choisie soit la commande "quitter"
		boolean termine = false;
		while (!termine) {
			Commande commande = analyseurSyntaxique.getCommande();
			termine = traiterCommande(commande);
		}
		System.out.println("Merci d'avoir jouer.  Au revoir.");
	}


	/**
	 *  Affiche le message d'accueil pour le joueur.
	 */
	public void afficherMsgBienvennue() {
		System.out.println();
		System.out.println("Bienvennue dans le monde de Zork !");
		System.out.println("Zork est un nouveau jeu d'aventure, terriblement enuyeux.");
		System.out.println("Tapez 'aide' si vous avez besoin d'aide.");
		System.out.println();
		System.out.println(pieceCourante.descriptionLongue());
	}


	/**
	 *  Exécute la commande spécifiée. Si cette commande termine le jeu, la valeur
	 *  true est renvoyée, sinon false est renvoyée
	 *
	 * @param  commande  La commande a exécuter
	 * @return           true si cette commande termine le jeu ; false sinon.
	 */
	public boolean traiterCommande(Commande commande) {
		if (commande.estInconnue()) {
			System.out.println("Je ne comprends pas ce que vous voulez...");
			return false;
		}

		String motCommande = commande.getMotCommande();
		if (motCommande.equals("aide")) {
			afficherAide();
		} else if (motCommande.equals("aller")) {
			deplacerVersAutrePiece(commande);
		} else if (motCommande.equals("quitter")) {
			if (commande.aSecondMot()) {
				System.out.println("Quitter quoi ?");
			} else {
				return true;
			}
		}
		return false;
	}


	// implementations des commandes utilisateur:

	/**
	 *  Affichage de l'aide. Affiche notament la liste des commandes utilisables.
	 */
	public void afficherAide() {
		System.out.println("Vous etes perdu. Vous etes seul. Vous errez");
		System.out.println("sur le campus de l'Université Paris 13.");
		System.out.println();
		System.out.println("Les commandes reconnues sont:");
		analyseurSyntaxique.afficherToutesLesCommandes();
	}


	/**
	 *  Tente d'aller dans la direction spécifiée par la commande. Si la piece
	 *  courante possède une sortie dans cette direction, la piece correspondant a
	 *  cette sortie devient la piece courante, dans les autres cas affiche un
	 *  message d'erreur.
	 *
	 * @param  commande  Commande dont le second mot spécifie la direction a suivre
	 */
	public void deplacerVersAutrePiece(Commande commande) {
		if (!commande.aSecondMot()) {
			// si la commande ne contient pas de second mot, nous ne
			// savons pas ou aller..
			System.out.println("Aller où ?");
			return;
		}

		String direction = commande.getSecondMot();
		Direction d = null;
		try {
		    d = Direction.valueOf(direction);
		  } catch (IllegalArgumentException e) {
		      System.out.println("Pour indiquer une direction entrez une des chaînes de caractères suivantes:");
		      for (Direction dok : Direction.values()) {
		          System.out.println("-> \"" + dok + "\"");
		      }
		      return;
		  }

		// Tentative d'aller dans la direction indiquée.
		Piece pieceSuivante = pieceCourante.pieceSuivante(d);

		if (pieceSuivante == null) {
			System.out.println("Il n'y a pas de porte dans cette direction!");
		} else {
			pieceCourante = pieceSuivante;
			System.out.println(pieceCourante.descriptionLongue());
		}
	}
}

