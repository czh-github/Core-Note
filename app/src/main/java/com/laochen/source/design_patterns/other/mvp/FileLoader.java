/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.laochen.source.design_patterns.other.mvp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

/**
 * Every instance of this class represents the Model component in the Model-View-Presenter
 * architectural pattern.
 * <p>
 * It is responsible for reading and loading the contents of a given file.
 */
public class FileLoader implements Serializable {

    /**
     * Generated serial version UID
     */
    private static final long serialVersionUID = -4745803872902019069L;


    /**
     * Indicates if the file is loaded or not.
     */
    private boolean loaded;

    /**
     * The name of the file that we want to load.
     */
    private String fileName;

    /**
     * Loads the data of the file specified.
     */
    public String loadData() {
        String dataFileName = this.fileName;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(dataFileName)));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }

            this.loaded = true;

            return sb.toString();
        } catch (Exception e) {
            System.out.println("File " + dataFileName + " does not exist");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * Sets the path of the file to be loaded, to the given value.
     *
     * @param fileName The path of the file to be loaded.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return fileName The path of the file to be loaded.
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * @return True, if the file given exists, false otherwise.
     */
    public boolean fileExists() {
        return new File(this.fileName).exists();
    }

    /**
     * @return True, if the file is loaded, false otherwise.
     */
    public boolean isLoaded() {
        return this.loaded;
    }
}