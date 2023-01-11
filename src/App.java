import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Contact;

public class App {

    private static Scanner _scan = new Scanner(System.in);


    private static void ajouterContact() throws IOException {
        Contact c = new Contact(0, null, null, null, null, null);
        int id = Contact.getId() + 1;
        c.setId(Integer.valueOf(id));
        System.out.println("Saisir le nom");
        c.setNom(_scan.nextLine());
    
        System.out.println("Saisir le prénom");
        c.setPrenom(_scan.nextLine());
    
        while (true) {
            try {
                System.out.println("Saisir le mail");
                c.setMail(_scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }
    
        while (true) {
            try {
                System.out.println("Saisir le téléphone");
                c.setTelephone(_scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println("Mauvais téléphone!");
            }
        }
    
        while (true) {
            try {
                System.out.println("Saisir la date de naissance");
                c.setDateNaissance(_scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println("Mauvaise date de naissance!");
            }
        }
        c.enregistrer();
        System.out.println("Contact enregistré");
    
    }

    public static void afficherMenu(){
        ArrayList<String> menus = new ArrayList<>();
        menus.add("-- MENU --");
        menus.add("1- Ajouter un contact");
        menus.add("2- Lister les contacts");
        menus.add("3- Modifier un contact");
        menus.add("4- Supprimer un contact");
        menus.add("q- Quitter");
        for (String menu : menus) {
            System.out.println(menu);
        }
    }

    public static void main(String[] args) throws IOException {
        String choix;
        do {
            afficherMenu();
            choix = _scan.nextLine();
            switch (choix) {
                case "1":
                    ajouterContact();
                    break;
                case "2":
                    Contact.lister();
                    break;
                // case "3":
                //     Contact.modifier();
                //     break;
                // case "4":
                //     Contact.supprimer();
                //     break;
                case "q":
                    break;
                default:
                    System.out.println("Choix incorrect!");
                    break;
            }
        } while (!choix.equals("q"));
    }
}
