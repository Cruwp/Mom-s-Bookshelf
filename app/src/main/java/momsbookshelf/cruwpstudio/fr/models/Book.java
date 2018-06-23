package momsbookshelf.cruwpstudio.fr.models;

public class Book {
    Publisher[] publishers;
    Author[] authors;
    String title;
    String subtitle;
    int number_of_pages;
    String publish_date;
    Cover cover;


    class Cover {
        String small;
        String medium;
        String large;
    }
    class Publisher {
        String name;
    }
    class Author {
        String url;
        String name;
    }
}