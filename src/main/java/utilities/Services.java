package utilities;

import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

/** One shared instance of each business class, so data survives between requests. */
public class Services {
    public static final OptionBusiness OPTIONS = new OptionBusiness();
    public static final EtudiantBusiness ETUDIANTS = new EtudiantBusiness();
}