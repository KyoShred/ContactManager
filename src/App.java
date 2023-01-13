import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Comparator;

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
                    modifierContact();
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
        System.out.println("trier ? \n1- ⌈par nom⌋ \n2- ⌈par mail⌋ \n3- ⌈par date de naissance⌋");
        String choix = _scan.nextLine();
        try {
            ArrayList<Contact> list = Contact.lister();
            switch (choix) {
                case "1":
                    Contact.sortByNameAndFirstName(list);
                    for (Contact contact : list) {
                        System.out.println(contact.getNom() + " " + contact.getPrenom());
                    }
                    break;
                case "2":
                    Contact.sortByEmail(list);
                    for (Contact contact : list){
                        System.out.println(contact.getNom() + " " + contact.getPrenom() + " " + contact.getMail());
                    }
                    break;
                case "3":
                    trierDate();
                    break;
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
        menus.add("\u27E5 MENU \u27E4");
        menus.add("\033[m1- \033[32m⌈Ajouter un contact⌋\033[30m");
        menus.add("\033[m2- \033[32m⌈Lister les contacts⌋\033[30m");
        menus.add("\033[m3- \033[32m⌈Recherche contact⌋\033[30m");
        menus.add("\033[m4- \033[32m⌈Modifier un contact⌋\033[30m");
        menus.add("\033[m5- \033[32m⌈Supprimer un contact⌋\033[30m");
        menus.add("\033[mq- \033[31m⌈Quitter⌋\033[30m");
        for (String menu : menus) {
            System.out.println(menu);
        }
    }
    
    private static void modifierContact() throws FileNotFoundException, IOException, ParseException{
        System.out.println("Saisir le nom du contact à modifier");
        String nom = _scan.nextLine();
        System.out.println("Saisir le prénom du contact à modifier");
        String prenom = _scan.nextLine();
        ArrayList<Contact> list = Contact.lister();
        for (Contact contact : list) {
            if (contact.getNom().equals(nom) && contact.getPrenom().equals(prenom)) {
                Contact c = new Contact();
                System.out.println("Saisir le nouveau nom");
                c.setNom(_scan.nextLine());
        
                System.out.println("Saisir le nouveau prénom");
                c.setPrenom(_scan.nextLine());
        
                while (true) {
                    try {
                        System.out.println("Saisir le nouveau mail");
                        c.setMail(_scan.nextLine());
                        break;
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                }
        
                while (true) {
                    try {
                        System.out.println("Saisir le nouveau numéro de téléphone");
                        c.setTelephone(_scan.nextLine());
                        break;
                    } catch (ParseException e) {
                        System.out.println("Mauvais téléphone!");
                    }
                }
        
                while (true) {
                    try {
                        System.out.println("Saisir la nouvelle date de naissance");
                        c.setDateNaissance(_scan.nextLine());
                        break;
                    } catch (ParseException e) {
                        System.out.println("Mauvaise date de naissance!");
                    }
                }
                contact.supprimer();
                c.enregistrer();
                System.out.println("Contact modifié");
            }
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

    private static void trierDate() throws FileNotFoundException, IOException, ParseException {
        ArrayList<Contact> list = Contact.lister();
        Collections.sort(list, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getDateNaissance().compareTo(c2.getDateNaissance());
            }
        });
        for (Contact contact : list) {
            System.out.println(contact.getNom() + " " + contact.getPrenom() + " " + contact.getTelephone() + " " + contact.getDateNaissance());
        }
    }
}
