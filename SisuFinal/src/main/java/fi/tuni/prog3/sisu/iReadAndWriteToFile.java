/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fi.tuni.prog3.sisu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface with methods to read from a file and write to a file.
 */
public interface iReadAndWriteToFile {

    /**
     * Reads JSON from the given file.
     *
     * @param fileName name of the file to read from.
     * @return true if the read was successful, otherwise false.
     * @throws FileNotFoundException if the file cannot be accessed.
     */
    User readFromFile(File fileName) throws FileNotFoundException, IOException;

    /**
     * Write the student progress as JSON into the given file.
     *
     * @param fileName name of the file to write to.
     * @param user User object to include in the file
     * @return true if the write was successful, otherwise false.
     * @throws IOException if it cannot write to a file.
     */
    boolean writeToFile(String fileName, User user) throws IOException;
}
