import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.Contact;

public class App {

    private static Scanner _scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        while (true) {
            afficherMenu();
            String choix = _scan.nextLine();
            switch (choix) {
                case "1":
                    ajouterContact();
                    break;
                case "2":
                    listerContacts();
                    break;
                case "3":
                    rechercherContact();
                    break;
                case "4":
                    // modifierContact();
                    break;
                case "5":
                    supprimerContact();
                    break;
                case "q":
                    return;
                default:
                    System.out.println("Boulet !!!");
                    break;
            }
        }
    }

    private static void listerContacts() {
        try {
            ArrayList<Contact> list = Contact.lister();

            for (Contact contact : list) {
                System.out.println(contact.getNom() + " " + contact.getPrenom());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void ajouterContact() throws IOException {
        Contact c = new Contact();
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

    private static void rechercherContact() throws FileNotFoundException, IOException, ParseException {
        System.out.println("Saisir le prénom");
        String prenom = _scan.nextLine();
        ArrayList<Contact> list = Contact.lister();
        ArrayList<Contact> filteredList = (ArrayList<Contact>) list.stream()
                .filter(c -> c.getPrenom().startsWith(prenom))
                .collect(Collectors.toList());
        if (filteredList.isEmpty()) {
            System.out.println("Aucun contact trouvé avec ce prénom");
        } else {
            for (Contact contact : filteredList) {
                System.out.println(contact.getNom() + " " + contact.getPrenom() + " " + contact.getTelephone() + " "
                        + contact.getMail());
            }
        }
    }

    private static void afficherMenu() {
        ArrayList<String> menus = new ArrayList<>();
        menus.add("⥤ MENU ⥢");
        menus.add("1- ⌈Ajouter un contact⌋");
        menus.add("2- ⌈Lister les contacts⌋");
        menus.add("3- ⌈Recherche contact⌋");
        menus.add("4- ⌈Modifier un contact⌋");
        menus.add("5- ⌈Supprimer un contact⌋");
        menus.add("q- ⌈Quitter");
        menus.add("        ⥤ MENU ⥢");
        menus.add("1- ⌈Ajouter un contact⌋");
        menus.add("2- ⌈Lister les contacts⌋");
        menus.add("3- ⌈Rechercher contact⌋");
        menus.add("4- ⌈Modifier un contact⌋");
        menus.add("5- ⌈Supprimer un contact⌋");
        menus.add("q- ⌈Quitter⌋");
        for (String menu : menus) {
            System.out.println(menu);
        }
    }

    private static void supprimerContact() throws IOException, ParseException {
        System.out.println("Saisir le nom du contact à supprimer");
        String nom = _scan.nextLine();
        System.out.println("Saisir le prénom du contact à supprimer");
        String prenom = _scan.nextLine();
        ArrayList<Contact> list = Contact.lister();
        for (Contact contact : list) {
            if (contact.getNom().equals(nom) && contact.getPrenom().equals(prenom)) {
                try {
                    contact.supprimer();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("Contact supprimé");
                return;
            }
        }
        System.out.println("Contact non trouvé");
    }

}