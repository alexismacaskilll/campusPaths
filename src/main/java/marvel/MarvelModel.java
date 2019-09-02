package marvel;

import com.opencsv.bean.CsvBindByName;
/**
 * This is the Java representation of a Marvel Character
 */
public class MarvelModel {

    // No Abstraction Function or RepInvariant listed because MarvelModel is not
    // an Abstract Data Type.

    // Abstract description: MarvelModel represents a model for MarvelParser to follow.

    /**
     * name of the character
     */
    @CsvBindByName (column = "hero")
    private String name;

    /**
     * the title of a comic book that the character appeared in
     */
    @CsvBindByName (column = "book")
    private String book;

    /**
     * get the name of the character
     *
     * @return name of the character
     */
    public String getHero() {
        return name;
    }

    /**
     * get the title of a comic book that the character appeared in
     *
     * @return the title of a comic book that the character appeared in
     */
    public String getBook() {
        return book;
    }


}
