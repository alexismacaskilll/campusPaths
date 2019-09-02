/*
 * Copyright ©2019 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2019 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package marvel;
import java.io.IOException;
import java.util.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


/** Parser utility to load the Marvel Comics dataset. */

public class MarvelParser {


    // No Abstraction Function or RepInvariant listed because MarvelParser is not
    // an Abstract Data Type.

    // Abstract description: MarvelParser is a function that parses data from TSV files into maps.

  /**
   * Reads a dataset from a file. Each line of the input file contains a character name and a
   * comic book the character appeared in, separated by a tab character
   *
   * @spec.requires filename is a valid file path
   * @param filename the file that will be parsed
   * @return a Map that maps each comic book to a list of characters that appeared in that comic book.
   */
  public static Map<String, List<String>> parseData(String filename) {
      Map<String, List<String>> books = new HashMap<>();
      try {
          Reader reader = Files.newBufferedReader(Paths.get(filename));
          CsvToBean<MarvelModel> tsvToBean = new CsvToBeanBuilder<MarvelModel>(reader)
                  .withType(MarvelModel.class)
                 .withSeparator('\t')
                 .withIgnoreLeadingWhiteSpace(true)
                 .build();

          Iterator<MarvelModel> tsvMarvelIterator = tsvToBean.iterator();
          //while the file still has more tab-separated values to parse
          while (tsvMarvelIterator.hasNext()) {
              MarvelModel tsvMarvel = tsvMarvelIterator.next();
              String name = tsvMarvel.getHero();
              String book = tsvMarvel.getBook();
              //adds the book to the book map if it doesn't already exist
              if (!books.containsKey(book)) {
                  books.put(book, new ArrayList<String>());
              }
              books.get(book).add(name);
          }
    } catch(IOException e) {
      e.printStackTrace();
      }
      return books;
  }
}
