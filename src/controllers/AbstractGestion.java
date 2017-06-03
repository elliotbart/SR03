package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import dao.ClientDao;

public class AbstractGestion {
	protected static final String CHAMP_EMAIL = "email";
	protected static final String CHAMP_PASS = "password";
	protected static final String CHAMP_NOM = "nom";
	protected static final String CHAMP_PRENOM = "prenom";
	protected static final String CHAMP_DATE_NAISSANCE = "date_naissance";
	protected static final String CHAMP_PASS_CHECK = "check";
	
	Map<String, String> erreurs = new HashMap<String, String>();

	protected String resultat;



	protected static String getValeurChamp(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}
	
	protected void checkBirthDate(Date date) throws Exception {
		//Date currentDate = Calendar.getInstance().getTime();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -18);
		Date majorityDate = cal.getTime();

		if (majorityDate != null) {
			if(date.after(majorityDate)) {
				throw new Exception("Vous devez être majeur(e) pour vous inscrire... Vous pouvez quand même consulter le catalgue sans authentification :) ");
			} 
		}
		else 
			throw new Exception("Merci d'entrer une date de naissance"); // Impossible d'arriver ici avec JS google
	}

	protected static Date getStringToDate(String date) throws ParseException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Date returnDate = formatter.parse(date);
		
		return returnDate;
		
	}

	protected void validationPassword(String motDePasse) throws Exception {
		if (motDePasse != null) {
			if (motDePasse.length() < 5) {
				throw new Exception("Le mot de passe doit contenir au moins 5 caractères.");
			}
		} else {
			throw new Exception("Merci de saisir votre mot de passe.");
		}
	}

	protected void checkReTypePassword(String motDePasse, String checkmotDePasse) throws Exception {
		if (checkmotDePasse != null) {
			if (checkmotDePasse.length() < 5) {
				throw new Exception("Le mot de passe doit contenir au moins 5 caractères.");
			} else if (!checkmotDePasse.equals(motDePasse)) {
				throw new Exception("Les mots de passe doivent être identiques !");
			}
		} else {
			throw new Exception("Merci de saisir votre verification du mot de passe.");
		}
	}
	
	protected void checkNames(String name) throws Exception {
		if (name == null) {
			throw new Exception("Nom ou prénom vide");
		}
		else if (!name.matches("^[a-zA-Zçàâéêèìôùû -]+$")){ 
			throw new Exception("Caractère(s) interdit(s) dans le nom ou le prénom");
		}
	}
	
	protected void validationEmail(String email) throws Exception {
        
        if (email == null){
        	throw new Exception("Merci d'entrer votre email");
        }
        else if (!email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" )) {
            throw new Exception( "Merci de saisir une adresse mail valide.");
        }
    }
	
	protected void checkExistingClient(String email) throws Exception {
        
		if (ClientDao.findSQL(email) != null){
			throw new Exception("Il existe déjà un utilisateur avec cet email...");
		}
	}

	
	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}
	
	public Map<String,String> getErreurs() {
		return erreurs;
	}

	

	/*
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
	protected void setErreur( String champ, String message ) {
		erreurs.put( champ, message );
	}

	


}