package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
// import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact {
    private static final String SEPARATEUR = "  ;   ";
    private static Integer id;
    private static String nom;
    private static String prenom;
    private String mail;
    private String telephone;
    private Date dateNaissance;

    public Contact(Integer id, String nom, String prenom, String mail, String telephone, Date dateNaissance) {
        Contact.id = id;
        Contact.nom = nom;
        Contact.prenom = prenom;
        this.mail = mail;
        this.telephone = telephone;
        this.dateNaissance = dateNaissance;
    }
    public static Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        Contact.id = id;
    }
    public static String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        Contact.nom = nom;
    }

    public static String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        Contact.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) throws ParseException {
        Pattern pat = Pattern.compile("^[a-zA-Z0-9_.-]+@{1}[a-zA-Z0-9_.-]{2,}\\.[a-zA-Z.]{2,10}$");
        Matcher matcher = pat.matcher(mail);
        if (matcher.matches()) {
            this.mail = mail;
        } else {
            ParseException e = new ParseException("Le format du mail est incorrect.", 0);
            throw e;
        }
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) throws ParseException {
        Pattern pat = Pattern.compile("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$");
        Matcher matcher = pat.matcher(telephone);
        if (matcher.matches()) {
            this.telephone = telephone;
        } else {
            ParseException e = new ParseException("Le format du numéro est incorrect.", 0);
            throw e;
        }
    }

    public String getDateNaissance() {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        return f.format(dateNaissance);
    }

    public void setDateNaissance(String dateNaissance) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.dateNaissance = format.parse(dateNaissance);
    }

    public void enregistrer() throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", true)));
        try {
            pw.println(this.toString());
        } finally {
            pw.close();
        }
    }

   

    /**
     * @return
     */
    public static ArrayList<Contact> lister() {
        ArrayList<Contact> list = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("contacts.csv");
            BufferedReader buf = new BufferedReader(fileReader);
            String line = buf.readLine();
            while((line = buf.readLine()) != null) {
                String[] data = line.split(";");
                if(data.length == 5){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = dateFormat.parse(data[4]);
                    Contact contact = new Contact(id, data[1], data[2], data[3], data[4],date);
                    list.add(contact);
                    line = buf.readLine();
                }
            }
            buf.close();
            fileReader.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append(Contact.getId());
        build.append(SEPARATEUR);
        build.append(Contact.getNom());
        build.append(SEPARATEUR);
        build.append(Contact.getPrenom());
        build.append(SEPARATEUR);
        build.append(this.getMail());
        build.append(SEPARATEUR);
        build.append(this.getTelephone());
        build.append(SEPARATEUR);
        build.append(this.getDateNaissance());
        return build.toString();
    }

}
